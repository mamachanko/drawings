function Surface(renderer) {
    this.renderer = renderer;
    this.currentlyAddingShape = false;
}

Surface.prototype.addPolygon = function (polygon, color) {
    this.beginShapeWithColor(color);
    for (var v = 0; v < polygon.length; v++) {
        this.vertex(polygon[v].x, polygon[v].y);
    }
    this.endShape();
};

Surface.prototype.beginShapeWithColor = function (color) {
    if (this.currentlyAddingShape) {
        throw new Error('cannot begin shape without ending shape');
    }
    this.renderer.fill(color.r, color.g, color.b);
    this.renderer.beginShape();
    this.currentlyAddingShape = true;
};

Surface.prototype.endShape = function () {
    if (!this.currentlyAddingShape) {
        throw new Error('cannot end shape without beginning shape');
    }
    this.renderer.endShape();
    this.currentlyAddingShape = false;
};

Surface.prototype.vertex = function (x, y) {
    if (!this.currentlyAddingShape) {
        throw new Error('cannot add vertex without beginning shape');
    }
    this.renderer.vertex(x, y);
};

Surface.prototype.fill = function (r, g, b) {
    if (this.currentlyAddingShape) {
        throw new Error('cannot fill while adding shape');
    }
    this.renderer.fill(r, g, b);
};

module.exports = Surface;