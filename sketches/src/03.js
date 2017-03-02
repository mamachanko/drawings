import p5 from 'p5';

export default () => new p5((p5) => {
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

    p5.setup = () => {
        p5.createCanvas(totalWidth, totalHeight);
        p5.noLoop();
    };

    p5.draw = () => {
        p5.clear();
        p5.noStroke();
        p5.fill(1);

        for (let y = 0; y < rows; y++) {
            for (let x = 0; x < cols; x++) {
                const radius = p5.map(
                    Math.sin(p5.map(x, 0, cols, 0, p5.PI)) * Math.sin(p5.map(y, 0, rows, 0, p5.PI)),
                    0, 1, 0, cellWidth * .25);
                p5.ellipse(
                    originX + (x * cellWidth) + (cellWidth * .5),
                    originY + (y * cellHeight) + (cellHeight * .5),
                    radius,
                    radius
                );
            }
        }
    }
});