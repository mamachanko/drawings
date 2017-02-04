function Surface(renderer) {
    this.renderer = renderer;
    this.currentlyAddingShape = false;
}

Surface.prototype.beginShape = function() {
    if (this.currentlyAddingShape) {
        throw new Error('cannot beginShape without endShape');
    }
    this.renderer.beginShape();
    this.currentlyAddingShape = true;
};

Surface.prototype.endShape = function() {
    if (!this.currentlyAddingShape) {
        throw new Error('cannot endShape without beginShape');
    }
    this.renderer.endShape();
    this.currentlyAddingShape = false;
};

Surface.prototype.vertex = function(x, y) {
    if (!this.currentlyAddingShape) {
        throw new Error('cannot add vertex without beginShape');
    }
    this.renderer.vertex(x, y);
};

Surface.prototype.fill = function(r, g, b) {
    if (this.currentlyAddingShape) {
        throw new Error('cannot fill while adding shape');
    }
    this.renderer.fill(r, g, b);
};

module.exports = Surface;