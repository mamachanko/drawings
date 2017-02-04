var Surface = require('../src/Surface');

describe('Surface', function () {

    var surface, renderer;

    beforeEach(function () {
        renderer = jasmine.createSpyObj('renderer', ['fill', 'beginShape', 'vertex', 'endShape']);
        surface = new Surface(renderer);
    });

    describe('when implementing the shape protocol with its rendered', function () {


        it('it must begin a shape with a color, add vertices and end the shape', function () {
            surface.beginShapeWithColor({r: 180, g: 210, b: 85});

            expect(renderer.fill).toHaveBeenCalledWith(180, 210, 85);
            expect(renderer.beginShape).toHaveBeenCalled();

            surface.vertex(1, 2);
            surface.vertex(3, 4);
            surface.endShape();

            expect(renderer.vertex.calls.allArgs()).toEqual([[1, 2], [3, 4]]);
            expect(renderer.endShape).toHaveBeenCalled();
        });

        it('it can add shapes after one another', function () {
            surface.beginShapeWithColor({r: 180, g: 210, b: 85});
            surface.vertex(1, 2);
            surface.endShape();

            surface.beginShapeWithColor({r: 180, g: 210, b: 85});
            surface.vertex(3, 4);
            surface.endShape();
        });

        it('it cannot end shape without having begun shape', function () {
            expect(function () {
                surface.endShape()
            }).toThrowError('cannot end shape without beginning shape');
        });

        it('it cannot begin shape without having ended shape', function () {
            surface.beginShapeWithColor({r: 180, g: 210, b: 85});
            expect(function () {
                surface.beginShapeWithColor({r: 180, g: 210, b: 85})
            }).toThrowError('cannot begin shape without ending shape');
        });

        it('it cannot add vertex without having begun shape', function () {
            expect(function () {
                surface.vertex(1, 2)
            }).toThrowError('cannot add vertex without beginning shape');
        });
    });

    describe('when adding a colored polygon', function () {

        it('it adds it via its renderer', function () {
            var polygon = [
                {x: 10, y: 10},
                {x: 50, y: 10},
                {x: 10, y: 50}
            ];
            var color = {r: 180, g: 50, b: 50};
            surface.addPolygon(polygon, color);
            expect(renderer.fill).toHaveBeenCalledWith(180, 50, 50);
            expect(renderer.vertex.calls.allArgs()).toEqual([[10, 10], [50, 10], [10, 50]]);
        });

    });

});