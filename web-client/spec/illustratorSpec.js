var Illustrator = require("../src/illustrator");

describe("Illustrator", function() {

  describe("when creating", function() {

    it("it takes a surface", function() {
        var surface = function() {};
        var illustrator = new Illustrator(surface);
        expect(illustrator.surface).toEqual(surface);
    });

  });

  describe("when drawing shapes", function() {

    var surface;
    var shape;

    beforeEach(function() {
      surface = jasmine.createSpyObj('surface', ['fill', 'beginShape', 'vertex', 'endShape']);
      shape = {
        vertices: [
          {x: 1.2, y: 3.4},
          {x: 1.2, y: 5.6},
          {x: 7.8, y: 5.6}
        ],
        color: {r: 256, g: 180, b: 120}
      }
    });

    it("creates shapes on the surface", function() {
      new Illustrator(surface).drawShape(shape);

      expect(surface.fill).toHaveBeenCalledWith(256, 180, 120);
      expect(surface.beginShape).toHaveBeenCalled();
      expect(surface.vertex.calls.allArgs()).toEqual([[1.2, 3.4], [1.2, 5.6], [7.8, 5.6]]);
      expect(surface.endShape).toHaveBeenCalled();
    });
  });

});
