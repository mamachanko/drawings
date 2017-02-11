var http = require('axios');

function DrawingsAPI() {
};

DrawingsAPI.prototype.create = function (device, onDrawing) {
    const params = {width: device.width, height: device.height};
    const createDrawingRequest = {
        method: 'post',
        url: '/api/drawings',
        params: params
    };
    http(createDrawingRequest)
        .then(function (response) {
            onDrawing(response.data)
        })
        .catch(function (error) {
            console.log('error getting drawing:' + error);
        });
};

DrawingsAPI.prototype.createFake = function (device, onDrawing) {
    onDrawing(require('./drawing.json'));
};

module.exports = DrawingsAPI;