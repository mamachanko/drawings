var width;
var height;

function setup() {
    console.log("setting up");

    width = window.innerWidth;
    height = window.innerHeight;

    console.log("width: ", width);
    console.log("height: ", height);

    var canvas = createCanvas(width, height)
    canvas.id("drawingCanvas")
    window.blankCanvasData = document.getElementById("drawingCanvas").toDataURL()

    noStroke();
    noLoop();
}

function draw() {
    console.log("drawing");
    fill(255);
    rect(0, 0, width, height);
    loadShapes(drawShapes);
}

function keyPressed() {
    draw();
}
function touchStarted() {
    draw();
}

function loadShapes(success) {
    shapesUrl = "/api/drawing?width=" + width + "&height=" + height;
    console.log("loading shapes from: ", shapesUrl);
    loadJSON(shapesUrl, success);
}

function drawShapes(shapesJson) {
    console.log("drawing shapes: ", shapesJson);
    var shape;
    for (s = 0; s < shapesJson.shapes.length; s++) {
        shape = shapesJson.shapes[s];
        console.log("filling with color:", shape.color.red, shape.color.blue, shape.color.green)
        fill(shape.color.red, shape.color.green, shape.color.blue);
        console.log("drawing shape: ", shape);
        beginShape();
        for (v = 0; v < shape.vertices.length; v++) {
            var coordinates = shape.vertices[v];
            console.log("drawing vertex at: ", coordinates);
            vertex(coordinates.x, coordinates.y);
        }
        endShape();
        console.log("shape done");
    }
    console.log("shapes done");
};