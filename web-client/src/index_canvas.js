require('file-loader?name=[name].[ext]!./index.html');
require('file-loader?name=[name].[ext]!./styles.css');
require('./ga_tracking');

var axios = require('axios');
var DrawingsAPI = require('./DrawingsAPI');

width = window.innerWidth;
height = window.innerHeight;

var newCanvas = document.createElement('div');
newCanvas.id = 'drawingCanvas';
newCanvas.width = width;
newCanvas.height = height;
document.body.appendChild(newCanvas);

var drawing = new DrawingsAPI().get();


axios.get("/api/drawing?width=" + width + "&height=" + height)
    .then(function (response) {

        var drawing = response.data;

        var canvas = document.getElementById('drawingCanvas');
        var context = canvas.getContext('2d');

        for (var p = 0; p < drawing.shapes.length; p++) {
            var polygon = drawing.shapes[p].vertices;
            var rgb = drawing.shapes[p].color;

            var color = 'rgb(' + rgb.red + ',' + rgb.green + ',' + rgb.blue + ')';

            context.fillStyle = color;
            context.beginPath();
            for (var v = 0; v < polygon.length; v++) {
                var vertex = polygon[v];

                context.lineTo(vertex.x, vertex.y);
            }
            context.closePath();
            context.fill();
        }

    });
/**
 * Created by max on 04/02/2017.
 */
