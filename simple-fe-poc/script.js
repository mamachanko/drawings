var shapesUrl = "/shapes.json";

function setup() {
    console.log("setting up");
    createCanvas(1000, 1000);
    noStroke();
    noLoop();
}

function draw() {
    console.log("drawing");
    fill(255);
    rect(0, 0, 1000, 1000);
    loadJSON(shapesUrl, drawShapes);
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