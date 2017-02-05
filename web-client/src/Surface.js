function Surface(renderer) {
    this.renderer = renderer;
    this.currentlyAddingShape = false;
}

Surface.prototype.addPolygon = function (polygon, color) {
    this.beginPathWithColor(color);
    for (var v = 0; v < polygon.length; v++) {
        this.addVertex(polygon[v].x, polygon[v].y);
    }
    this.closePath();
};

Surface.prototype.beginPathWithColor = function (color) {
    if (this.currentlyAddingShape) {
        throw new Error('cannot begin path without closing path');
    }
    this.renderer.fillStyle = 'rgb(' + color.red + ',' + color.green + ',' + color.blue + ')';
    this.renderer.beginPath();
    this.currentlyAddingShape = true;
};

Surface.prototype.closePath = function () {
    if (!this.currentlyAddingShape) {
        throw new Error('cannot close path without beginning path');
    }
    this.renderer.closePath();
    this.renderer.fill();
    this.currentlyAddingShape = false;
};

Surface.prototype.addVertex = function (x, y) {
    if (!this.currentlyAddingShape) {
        throw new Error('cannot add add vertex without beginning path');
    }
    this.renderer.lineTo(x, y);
};

module.exports = Surface;