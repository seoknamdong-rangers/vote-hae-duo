const { defineConfig } = require('@vue/cli-service')
const path = require("path")

module.exports = defineConfig({
  outputDir: path.resolve("__dirname", "../../src/main/resources/static"),
  devServer: {
    proxy: {
      "/": {
        target: "http://localhost:8080",
        ws: true,
        changeOrigin: true
      }
    },
    port: 4000
  },
  transpileDependencies: ["vuetify"],
})
