var path = require('path');

module.exports = {
    context: path.resolve(__dirname, 'src'),
    entry: './index.js',
    output: {
        path: __dirname,
        filename: 'bundle.js'
    },
    devServer: {
        contentBase: path.join(__dirname, 'dist'),
        port: 8081,
        proxy: {
            '/api': 'http://localhost:8080'
        }
    },
    devtool: 'source-map'
};
