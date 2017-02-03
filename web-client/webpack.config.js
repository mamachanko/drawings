var path = require('path');
var HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: [
        path.join(__dirname, 'src/index.js'),
    ],
    output: {
        path: path.join(__dirname, 'dist'),
        filename: 'bundle.js'
    },
    devServer: {
        contentBase: path.join(__dirname, 'dist'),
        port: 8081,
        proxy: {
            '/api': 'http://localhost:8080'
        }
    },
    devtool: 'source-map',
    module: {
        rules: [{
            test: /\.css$/,
            exclude: /node_modules/,
            loader: 'css-loader'
        }]
    },
    plugins: [new HtmlWebpackPlugin({
        title: 'Kelleybert'
    })]
};
