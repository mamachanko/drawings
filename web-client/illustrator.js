function Illustrator(surface) {
    this.surface = surface;
}

Illustrator.prototype.drawShape = function(shape) {
    this.surface.beginShape();
    for (var v = 0; v < shape.vertices.length; v++) {
      var vertex = shape.vertices[v];
      this.surface.vertex(vertex.x, vertex.y);
    }
    this.surface.endShape();
};

module.exports = Illustrator;
