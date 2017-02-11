var axios = require('axios');
var MockAdapter = require('axios-mock-adapter');
var DrawingsAPI = require('../src/DrawingsAPI');

describe('DrawingsAPI', function () {

    const device = {width: 489, height: 825};

    describe('when creating a drawing for a device with dimensions', function () {

        var httpMock;
        const fakeDrawingResponse = {thisIsA: 'fakeDrawing'};

        beforeEach(function () {
            httpMock = new MockAdapter(axios);
            httpMock.onPost('/api/drawings').reply(function (config) {
                if (config.params.width === 489 && config.params.height === 825) {
                    return [201, fakeDrawingResponse]
                } else {
                    return [400]
                }
            });
        });

        afterEach(function () {
            httpMock.restore();
        });

        it('it provides a drawing to a callback', function (done) {
            new DrawingsAPI().create(device, function (drawingResponse) {
                expect(drawingResponse).toEqual(fakeDrawingResponse);
                done();
            });
        });
    });

    describe('when creating a fake drawing', function () {

        it('it provides a drawing to a callback', function (done) {
            new DrawingsAPI().createFake(device, function (drawingResponse) {
                expect(drawingResponse.shapes.length).toBeGreaterThan(0);
                done();
            });
        });

    });

});