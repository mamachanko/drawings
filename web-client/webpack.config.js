var path = require("path");
var HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
    entry: './app.js',
    output: {
        path: 'dist',
        filename: 'bundle.js'
    },
    devServer: {
        contentBase: path.join(__dirname, "dist"),
        port: 8081,
        proxy: {
            "/api": "http://localhost:8080"
        }
    },
    plugins: [new HtmlWebpackPlugin({
        title: "Kelleybert"
    })]
}