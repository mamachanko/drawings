var Illustrator = require("../illustrator");

describe("Illustrator", function() {

  describe("when creating", function() {

    it("it takes a surface", function() {
        var surface = function() {};
        var illustrator = new Illustrator(surface);
        expect(illustrator.surface).toEqual(surface);
    });

  });

  describe("when drawing shapes", function() {

    it("creates shapes on the surface", function() {
      var surface = {
        beginShape: function(){},
        endShape: function(){},
        vertex: function(){}
      };
      spyOn(surface, 'beginShape');
      spyOn(surface, 'endShape');
      spyOn(surface, 'vertex');

      var illustrator = new Illustrator(surface);

      var shape = {
        vertices: [
          {x: 1.2, y: 3.4},
          {x: 1.2, y: 5.6},
          {x: 7.8, y: 5.6}
        ],
        color: {r: 256, g: 180, b: 120}
      }

      illustrator.drawShape(shape);

      expect(surface.beginShape).toHaveBeenCalled();
      expect(surface.vertex).toHaveBeenCalledWith(1.2, 3.4);
      expect(surface.vertex).toHaveBeenCalledWith(1.2, 5.6);
      expect(surface.vertex).toHaveBeenCalledWith(7.8, 5.6);
      expect(surface.endShape).toHaveBeenCalled();
    });
  });

});
