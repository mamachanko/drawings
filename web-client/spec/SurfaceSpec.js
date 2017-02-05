var Surface = require('../src/Surface');

describe('Surface', function () {

    var surface, renderer;

    beforeEach(function () {
        renderer = jasmine.createSpyObj('renderer', ['fill', 'beginPath', 'lineTo', 'closePath']);
        surface = new Surface(renderer);
    });

    describe('when implementing the path protocol with its rendered', function () {

        it('it must begin a path, draw lines between vertices, close the path and fill it with colour', function () {
            surface.beginPathWithColor({red: 180, green: 210, blue: 85});

            expect(renderer.fillStyle).toEqual('rgb(180,210,85)');
            expect(renderer.beginPath).toHaveBeenCalled();

            surface.addVertex(1, 2);
            surface.addVertex(3, 4);
            expect(renderer.lineTo.calls.allArgs()).toEqual([[1, 2], [3, 4]]);

            expect(renderer.fill).not.toHaveBeenCalled();
            surface.closePath();
            expect(renderer.closePath).toHaveBeenCalled();
            expect(renderer.fill).toHaveBeenCalled();
        });

        it('it can add paths one after another', function () {
            surface.beginPathWithColor({red: 180, green: 210, blue: 85});
            surface.addVertex(1, 2);
            surface.closePath();

            surface.beginPathWithColor({red: 180, green: 210, blue: 85});
            surface.addVertex(3, 4);
            surface.closePath();
        });

        it('it cannot close path without having begun path', function () {
            expect(function () {
                surface.closePath()
            }).toThrowError('cannot close path without beginning path');
        });

        it('it cannot begin path without having ended path', function () {
            surface.beginPathWithColor({red: 180, green: 210, blue: 85});
            expect(function () {
                surface.beginPathWithColor({red: 180, green: 210, blue: 85})
            }).toThrowError('cannot begin path without closing path');
        });

        it('it cannot add vertex without having begun path', function () {
            expect(function () {
                surface.addVertex(1, 2)
            }).toThrowError('cannot add add vertex without beginning path');
        });

    });

    describe('when adding a colored polygon', function () {

        it('it adds it via its renderer', function () {
            var polygon = [
                {x: 10, y: 10},
                {x: 50, y: 10},
                {x: 10, y: 50}
            ];
            var color = {red: 180, green: 50, blue: 50};
            surface.addPolygon(polygon, color);
            expect(renderer.fillStyle).toEqual('rgb(180,50,50)');
            expect(renderer.lineTo.calls.allArgs()).toEqual([[10, 10], [50, 10], [10, 50]]);
        });

    });

});