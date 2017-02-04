require('file-loader?name=[name].[ext]!./index.html');
require('file-loader?name=[name].[ext]!./styles.css');
require('./ga_tracking');

var App = require('./app');
var p5 = require('p5');

new p5(App, document.body);
