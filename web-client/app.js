var p5 = require('p5');

var width;
var height;

function sketch(p) {
    p.setup = function() {
        console.log("setting up");

        width = window.innerWidth;
        height = window.innerHeight;

        console.log("width: ", width);
        console.log("height: ", height);

        var canvas = p.createCanvas(width, height)
        canvas.id("drawingCanvas")
        window.blankCanvasData = document.getElementById("drawingCanvas").toDataURL()

        p.noStroke();
        p.noLoop();
    }

    p.draw = function() {
        console.log("drawing");
        p.fill(255);
        p.rect(0, 0, width, height);
        loadShapes(drawShapes);
    }

    function loadShapes(success) {
        shapesUrl = "/api/drawing?width=" + width + "&height=" + height;
        console.log("loading shapes from: ", shapesUrl);
        p.loadJSON(shapesUrl, success);
    }

    function drawShapes(shapesJson) {
        console.log("drawing shapes: ", shapesJson);
        var shape;
        for (s = 0; s < shapesJson.shapes.length; s++) {
            shape = shapesJson.shapes[s];
            console.log("filling with color:", shape.color.red, shape.color.blue, shape.color.green)
            p.fill(shape.color.red, shape.color.green, shape.color.blue);
            console.log("drawing shape: ", shape);
            p.beginShape();
            for (v = 0; v < shape.vertices.length; v++) {
                var coordinates = shape.vertices[v];
                console.log("drawing vertex at: ", coordinates);
                p.vertex(coordinates.x, coordinates.y);
            }
            p.endShape();
            console.log("shape done");
        }
        console.log("shapes done");
    }
}

var app = new p5(sketch, document.body);
