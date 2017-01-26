
var width;
var height;

function setup() {
    console.log("setting up");

    width = window.innerWidth;
    height = window.innerHeight;

    console.log("width: ", width);
    console.log("height: ", height);

    createCanvas(width, height);
    noStroke();
    noLoop();
}

function draw() {
    console.log("drawing");
    fill(255);
    rect(0, 0, width, height);
    loadShapes(drawShapes);
}

function loadShapes(success) {
    shapesUrl = "/api/shapes?width=" + width + "&height=" + height;
    console.log("loading shapes from: ", shapesUrl);
    loadJSON(shapesUrl, success);
}

function drawShapes(shapesJson) {
    console.log("drawing shapes: ", shapesJson);
    fill(0);
    var shape;
    for (s = 0; s < shapesJson.shapes.length; s++) {
        shape = shapesJson.shapes[s];
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