function Illustrator(surface) {
    this.surface = surface;
}

Illustrator.prototype.draft = function (drawing) {
    for (var p = 0; p < drawing.length; p++) {
        var polygon = drawing[p].vertices;
        var color = drawing[p].color;
        this.surface.addPolygon(polygon, color)
    }
};

module.exports = Illustrator;
