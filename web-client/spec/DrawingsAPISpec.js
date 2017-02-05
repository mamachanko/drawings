var axios = require('axios');
var MockAdapter = require('axios-mock-adapter');
var DrawingsAPI = require('../src/DrawingsAPI');

describe('DrawingsAPI', function () {

    describe('when getting a drawing', function () {

        var httpMock;
        const fakeDrawingResponse = { thisIsA: 'fakeDrawing'};

        beforeEach(function () {
            httpMock = new MockAdapter(axios);
            httpMock.onGet('/api/drawing').reply(200, fakeDrawingResponse);
        });

        afterEach(function () {
            httpMock.restore();
        });

        it('it provides a drawing to a callback', function (done) {
            new DrawingsAPI().get(function(drawingResponse) {
                expect(drawingResponse).toEqual(fakeDrawingResponse);
                done();
            });
        });
    });

    describe('when getting a fake drawing', function () {

        it('it provides a drawing to a callback', function (done) {
            new DrawingsAPI().getFake(function(drawingResponse) {
                expect(drawingResponse.shapes.length).toBeGreaterThan(0);
                done();
            });
        });

    });

});