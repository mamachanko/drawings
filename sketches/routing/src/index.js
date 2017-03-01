import Navigo from 'navigo';
import p5 from 'p5';

const xyz = () => new p5((p5) => {
    console.log("xyz here");

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
    const yoffInc = .5;

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
    };
});

const abc = () => new p5((p5) => {
    console.log("abc here");

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
    const yoffInc = .5;

    p5.setup = () => {
        p5.createCanvas(totalWidth, totalHeight);
        p5.noLoop();
    };

    p5.draw = () => {
        p5.clear();
        p5.noStroke();

        for (let y = 0; y < rows; y++) {
            for (let x = 0; x < cols; x++) {
                const radius = p5.map(p5.noise(xoff, yoff), 0, 1, 0, cellWidth * 2);
                p5.fill(p5.map(p5.noise(xoff, yoff), 0, 1, 255, 0));
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
    };
});

const router = new Navigo();

router.on({
    '01': () => {
        let app = document.querySelector('#app');
        app.innerHTML = '<h2>01</h2>';
    },
    '02': () => {
        let app = document.querySelector('#app');
        app.innerHTML = '<h2>02</h2>';
    },
    'anothersketch': () => {
        abc();
    },
    'sketch': () => {
        xyz();
    },
    '*': () => {
        let app = document.querySelector('#app');
        app.innerHTML = '<h2>Index</h2><ul><li><a href="01">01</a></li><li><a href="02">02</a></li><li><a href="sketch">sketch</a></li><li><a href="anothersketch">anothersketch</a></li></ul>';
    }}).resolve();
