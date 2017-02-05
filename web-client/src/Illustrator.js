function Illustrator(surface) {
    this.surface = surface;
}

Illustrator.prototype.draft = function (drawing) {
    for (var p = 0; p < drawing.shapes.length; p++) {
        var polygon = drawing.shapes[p].vertices;
        var color = drawing.shapes[p].color;
        this.surface.addPolygon(polygon, color)
    }
};

module.exports = Illustrator;
