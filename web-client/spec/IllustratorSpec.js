var Illustrator = require("../src/Illustrator");
var Surface = require("../src/Surface");

describe("Illustrator", function () {

    var illustrator;

    describe("given a surface", function () {

        var surface;

        beforeEach(function () {
            surface = jasmine.createSpyObj('surface', ['addPolygon']);
            illustrator = new Illustrator(surface);
        });

        describe("when drafting", function () {

            var drawing;

            beforeEach(function () {
                drawing = {shapes: [
                    {
                        vertices: [
                            {x: 10, y: 10},
                            {x: 50, y: 10},
                            {x: 10, y: 50},
                        ],
                        color: {red: 180, green: 50, blue: 50}
                    },
                    {
                        vertices: [
                            {x: 25, y: 100},
                            {x: 75, y: 100},
                            {x: 75, y: 150},
                            {x: 25, y: 150},
                        ],
                        color: {red: 50, green: 180, blue: 50}
                    },
                ]};
            });

            it("it adds coloured polygons to the surface", function () {
                illustrator.draft(drawing);
                expect(surface.addPolygon.calls.allArgs()).toEqual([
                    [[{x: 10, y: 10}, {x: 50, y: 10}, {x: 10, y: 50}], {red: 180, green: 50, blue: 50}],
                    [[{x: 25, y: 100}, {x: 75, y: 100}, {x: 75, y: 150}, {x: 25, y: 150}], {red: 50, green: 180, blue: 50}],
                ]);
            });
        });
    });
});
