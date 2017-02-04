var DrawingsAPI = require('../src/DrawingsAPI');

describe('DrawingsAPI', function () {

    describe('when getting a drawing', function () {

        it('returns a drawing', function () {
            var drawing = new DrawingsAPI().get();
            expect(drawing.shapes.length).toBeGreaterThan(0);
        });
    });
});