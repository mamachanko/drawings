require('file-loader?name=[name].[ext]!./index.html');
require('file-loader?name=[name].[ext]!./styles.css');
require('./ga_tracking');

const Device = require('./Device');
const Illustrator = require('./Illustrator');
const DrawingsAPI = require('./DrawingsAPI');

function main() {
    const drawingsAPI = new DrawingsAPI();
    const device = new Device(window, document).init();
    drawingsAPI.create(device, function (drawing) {
        new Illustrator(device.getSurface()).draft(drawing);
    });
}

main();