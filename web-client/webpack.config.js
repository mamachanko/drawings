var path = require("path");
var HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  entry: [
    './app.js',
    './ga_tracking.js'
    ],
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
  module: {
      rules: [{
          test: /\.css$/,
          exclude: /node_modules/,
          loader: 'css-loader'
      }]
  },
  plugins: [new HtmlWebpackPlugin({
    title: "Kelleybert"
  })]
}
