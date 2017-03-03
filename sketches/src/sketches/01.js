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

    var xoff = 0;
    var yoff = 0;
    const xoffInc = .5;
    const yoffInc = .04;

    p5.setup = () => {
        p5.createCanvas(totalWidth, totalHeight);
        p5.noLoop();
    };

    p5.draw = () => {
        p5.clear();
        p5.fill(0);
        p5.stroke(0);

        for (let y = 0; y < rows; y++) {
            for (let x = 0; x < cols; x++) {
                const radius = p5.map(p5.noise(xoff, yoff), 0, 1, 0, cellWidth * 2);
                p5.ellipse(
                    originX + (x * cellWidth) + (cellWidth * .5),
                    originY + (y * cellHeight) + (cellHeight * .5),
                    radius,
                    radius
                );
                xoff += xoffInc;
            }
            yoff += yoffInc;
        }
    }
});