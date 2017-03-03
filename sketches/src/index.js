import './index.css';

import Navigo from 'navigo';

import _01 from './01';
import _02 from './02';
import _03 from './03';
import _04 from './04';
import _05 from './05';

const router = new Navigo();

router.on({
    '01': () => { _01() },
    '02': () => { _02() },
    '03': () => { _03() },
    '04': () => { _04() },
    '05': () => { _05() },
    '*': () => {
        let app = document.querySelector('#app');
        app.innerHTML = '<ul><li><a href="01">01</a></li><li><a href="02">02</a></li><li><a href="03">03</a></li><li><a href="04">04</a></li><li><a href="05">05</a></li></ul>';
    }}).resolve();
