import { createApp } from "vue";
import App from "./App.vue";
import vuetify from "./plugins/vuetify";
import router from "./router/index.js";
import store from "./store";
import { loadFonts } from "./plugins/webfontloader";

loadFonts();

createApp(App).use(vuetify).use(router).use(store).mount("#app");
