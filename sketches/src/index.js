import './index.css';
import * as sketches from './sketches';
import Navigo from 'navigo';

let routes = {};
Object.values(sketches)
    .map((sketch, i) =>
        routes[`0${i + 1}`] = () => sketch()
    );

routes['any'] = () => {
    let sketchesArray = Object.values(sketches);
    sketchesArray[Math.floor(Math.random() * sketchesArray.length)]();
};

routes['*'] = () => {
    let app = document.querySelector('#app');
    app.innerHTML = '<ul>' +
        '<li><a href="01">01</a></li>' +
        '<li><a href="02">02</a></li>' +
        '<li><a href="03">03</a></li>' +
        '<li><a href="04">04</a></li>' +
        '<li><a href="05">05</a></li>' +
        '<li><a href="any">any</a></li>' +
        '</ul>';
};

new Navigo().on(routes).resolve();
