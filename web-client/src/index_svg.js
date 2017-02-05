/**
 * Created by max on 04/02/2017.
 */
require('file-loader?name=[name].[ext]!./index.html');
require('file-loader?name=[name].[ext]!./styles.css');
require('./ga_tracking');

var axios = require('axios');
var SVG = require('svg.js');
var DrawingsAPI = require('./DrawingsAPI');

width = window.innerWidth;
height = window.innerHeight;

var newSvg = document.createElement('svg');
newSvg.id = 'drawingContainer';
newSvg.width = width;
newSvg.height = height;
document.body.appendChild(newSvg);

var drawing = new DrawingsAPI().get();

axios.get("/api/drawing?width=" + width + "&height=" + height)
    .then(function (response) {

        var drawing = response.data;

        var draw = SVG('drawingContainer', width, height);
        // draw.rect(width, height).attr({ fill: '#f06' })
        for (var p = 0; p < drawing.shapes.length; p++) {

            var polygon = drawing.shapes[p].vertices;
            var rgb = drawing.shapes[p].color;

            var color = 'rgb(' + rgb.red + ',' + rgb.green + ',' + rgb.blue + ')';

            var vertices = [];
            for (var v = 0; v < polygon.length; v++) {
                var vertex = polygon[v];
                vertices.push(vertex.x + ',' + vertex.y);
            }
            var polyline = vertices.join(' ');
            console.log(polyline)
            draw.polygon(polyline).fill(color);

        }
    });
