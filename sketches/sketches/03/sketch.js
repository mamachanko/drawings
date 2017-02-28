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

function setup() {
    createCanvas(totalWidth, totalHeight);
    noLoop();
}

function draw() {
    clear();
    noStroke();
    fill(1);

    for (y = 0; y < rows; y++) {
        for (x = 0; x < cols; x++) {
            const radius = map(
                Math.sin(map(x, 0, cols, 0, PI)) * Math.sin(map(y, 0, rows, 0, PI)),
                0, 1, 0, cellWidth * .25);
            ellipse(
                originX + (x * cellWidth) + (cellWidth * .5),
                originY + (y * cellHeight) + (cellHeight * .5),
                radius,
                radius
            );
        }
    }
}