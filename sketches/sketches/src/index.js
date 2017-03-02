import Navigo from 'navigo';

import _01 from './01';
import _02 from './02';
import _03 from './03';

const router = new Navigo();

router.on({
    '01': () => { _01() },
    '02': () => { _02() },
    '03': () => { _03() },
    '*': () => {
        let app = document.querySelector('#app');
        app.innerHTML = '<h2>Index</h2><ul><li><a href="01">01</a></li><li><a href="02">02</a></li><li><a href="sketch">sketch</a></li><li><a href="anothersketch">anothersketch</a></li></ul>';
    }}).resolve();
