const width = window.innerWidth;
const height = window.innerHeight;

console.log(width, height);

function setup() {
    createCanvas(width, height);
}

function draw() {
    const offset = 50;
    stroke(.5);
    rect(offset, offset, width - (offset * 2), height - (offset * 2));
}