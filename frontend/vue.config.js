const { defineConfig } = require("@vue/cli-service");
const path = require("path");

module.exports = defineConfig({
  outputDir: path.resolve("__dirname", "../../src/main/resources/static"),

  devServer: {
    proxy: {
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
        ws: false,
      },
    },
    port: 4000,
  },

  transpileDependencies: ["vuetify"],

  pluginOptions: {
    vuetify: {
      // https://github.com/vuetifyjs/vuetify-loader/tree/next/packages/vuetify-loader
    },
  },
  // EsLint multi-word-component-names 에러 방지용
  lintOnSave: false,
});
