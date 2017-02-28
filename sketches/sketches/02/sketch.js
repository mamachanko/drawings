const totalWidth = window.innerWidth;
const totalHeight = window.innerHeight;

const relativeMargin = .05;
const margin = Math.max(totalWidth, totalHeight) * relativeMargin;

const width = totalWidth - (2 * margin);
const height = totalHeight - (2 * margin);
const originX = margin;
const originY = margin;

const cols = parseInt(width * .1);
const rows = parseInt(height * .1);

const cellWidth = width / cols;
const cellHeight = height / rows;

var xoff = 0;
var yoff = 0;
const xoffInc = .5;
const yoffInc = .04;

function setup() {
    createCanvas(totalWidth, totalHeight);
    noLoop();
}

function draw() {
    clear();
    noStroke();

    for (y = 0; y < rows; y++) {
        for (x = 0; x < cols; x++) {
            fill(map(noise(xoff, yoff), 0, 1, 0, 255));
            ellipse(
                originX + (x * cellWidth) + (cellWidth * .5),
                originY + (y * cellHeight) + (cellHeight * .5),
                cellWidth,
                cellHeight
            );
            xoff += xoffInc;
        }
        yoff += yoffInc;
    }
}