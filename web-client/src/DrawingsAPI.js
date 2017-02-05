var http = require('axios');

function DrawingsAPI() {
};

DrawingsAPI.prototype.get = function (onDrawing) {
    http.get('/api/drawing')
        .then(function (response) {
            onDrawing(response.data)
        });
};

DrawingsAPI.prototype.getFake = function (onDrawing) {
    onDrawing(require('./drawing.json'));
};

module.exports = DrawingsAPI;