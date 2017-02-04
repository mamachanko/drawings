var Surface = require('../src/Surface');

describe("Surface", function () {

    describe("when adding a coloured polygon", function () {
        var surface, renderer;

        beforeEach(function () {
            renderer = jasmine.createSpyObj('renderer', ['fill', 'beginShape', 'vertex', 'endShape']);
            surface = new Surface(renderer);
        });

        it('it must set fill, begin a shape, add vertices and end the shape', function () {
            surface.fill(180, 210, 85);
            surface.beginShape();
            surface.vertex(1, 2);
            surface.vertex(3, 4);
            surface.endShape();

            expect(renderer.fill).toHaveBeenCalledWith(180, 210, 85);
            expect(renderer.beginShape).toHaveBeenCalled();
            expect(renderer.vertex.calls.allArgs()).toEqual([[1, 2], [3, 4]]);
            expect(renderer.endShape).toHaveBeenCalled();
        });

        it('it can add shapes after one another', function () {
            surface.beginShape();
            surface.vertex(1, 2);
            surface.endShape();

            surface.beginShape();
            surface.vertex(3, 4);
            surface.endShape();
        });

        it('it cannot end shape without having begun shape', function () {
            expect(function () {
                surface.endShape()
            }).toThrowError('cannot endShape without beginShape');
        });

        it('it cannot begin shape without having ended shape', function () {
            surface.beginShape();
            expect(function () {
                surface.beginShape()
            }).toThrowError('cannot beginShape without endShape');
        });

        it('it cannot fill while adding shape', function () {
            surface.beginShape();
            expect(function () {
                surface.fill(120, 130, 75)
            }).toThrowError('cannot fill while adding shape');
        });

        it('it cannot add vertex without having begun shape', function () {
            expect(function () {
                surface.vertex(1, 2)
            }).toThrowError('cannot add vertex without beginShape');
        });

    });

});