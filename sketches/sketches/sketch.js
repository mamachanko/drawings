const totalWidth = window.innerWidth;
const totalHeight = window.innerHeight;

const margin = 50;
const width = totalWidth - (2 * margin);
const height = totalHeight - (2 * margin);
const originX = margin;
const originY = margin;

const cols = width * .05;
const rows = height * .05;

const cellWidth = width / cols;
const cellHeight = height / rows;

var xoff = 0;
var yoff = 0;
const offInc = .02;

function setup() {
    createCanvas(totalWidth, totalHeight);
    noLoop();
}

function draw() {
    clear();
    rect(originX, originY, width, height);
    fill(1);

    for (y = 0; y < rows; y++) {
        for (x = 0; x < cols; x++) {

            // rect(originX + (x * cellWidth), originY + (y * cellHeight), cellWidth, cellHeight);
            // point(originX + (x * cellWidth) + cellWidth * .5, originY + (y * cellHeight) + cellHeight * .5);
            const radius = map(noise(xoff, yoff), 0, 1, 1, cellWidth);
            // const random = map(random(1), 0, 1, - (cellWidth * 5), (cellWidth * 5));
            ellipse(
                originX + (x * cellWidth) + cellWidth * .5,
                originY + (y * cellHeight) + cellHeight * .5,
                radius,
                radius
            );
            // xoff += offInc * (width / height);
            xoff += .15;
        }
        // yoff += offInc;
        yoff += .01;
    }
}