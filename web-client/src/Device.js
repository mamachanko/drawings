var Surface = require('./Surface');

function Device(window, document) {
    this.window = window;
    this.document = document;
}

Device.prototype.init = function () {
    this.canvas = this.document.createElement('canvas');
    this.canvas.id = 'canvas';
    this.canvas.width = this.window.innerWidth;
    this.canvas.height = this.window.innerHeight;
    this.document.body.appendChild(this.canvas);
    return this;
};

Device.prototype.getSurface = function () {
    return new Surface(this.canvas.getContext('2d'));
};

module.exports = Device;