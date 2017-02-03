var DrawingApp = require("../drawingApp");

describe("DrawingApp", function() {

  describe("when creating", function() {

    it("it takes a p5", function() {
        var p5 = function() {};
        var drawingApp = DrawingApp(p5);
        expect(drawingApp.p5).toEqual(p5);
    });

  });


});
