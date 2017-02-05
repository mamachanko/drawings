var http = require('axios');

function DrawingsAPI() {
};

DrawingsAPI.prototype.get = function (device, onDrawing) {
    const params = {width: device.width, height: device.height}
    http.get('/api/drawing', {params: params})
        .then(function (response) {
            onDrawing(response.data)
        })
        .catch(function (error) {
            console.log('error getting drawing:' + error);
        });
};

DrawingsAPI.prototype.getFake = function (device, onDrawing) {
    onDrawing(require('./drawing.json'));
};

module.exports = DrawingsAPI;