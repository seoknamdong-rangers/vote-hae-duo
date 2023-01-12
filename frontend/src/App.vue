<template>
  <v-app>
    <v-main class="bg-grey-lighten-2">
      <v-card max-width="448" class="mx-auto" flat>
        <v-layout>
          <v-app-bar color="teal-darken-4" class="fixed-bar">
            <template v-slot:image>
              <v-img
                  gradient="to top right, rgba(19,84,122,.8), rgba(128,208,199,.8)"
              ></v-img>
            </template>

            <v-app-bar-title>
              <router-link to="/" custom v-slot="{ navigate }">
                <span @click="navigate" @keypress.enter="navigate" role="link"
                >투표해듀오</span
                >
              </router-link>
            </v-app-bar-title>
            {{ member.nickname }}님
            <v-menu location="bottom">
              <template v-slot:activator="{ props }">
                <v-btn v-bind="props">
                  <v-icon>mdi-menu</v-icon>
                </v-btn>
              </template>

              <v-list>
                <v-list-item v-for="(menu, index) in menus" :key="index">
                  <v-list-item-title>
                    <router-link :to="menu.path" custom v-slot="{ navigate }">
                      <span
                          @click="navigate"
                          @keypress.enter="navigate"
                          role="link"
                      >{{ menu.title }}</span
                      >
                    </router-link>
                  </v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-app-bar>
          <router-view/>
        </v-layout>
        <Footer/>
      </v-card>
    </v-main>
  </v-app>
</template>

<script>
import {mapActions} from "vuex";
import Footer from "@/components/Footer";

export default {
  name: "App",
  components: {Footer},
  created() {
    this.setMemberFromLocalStorage();
    this.loginKakao();
    this.$watch(
        () => this.$route.query.token,
        (toParams) => this.setMemberFromToken(toParams)
    );
  },
  computed: {
    member() {
      return this.$store.state.member;
    },
  },
  methods: {
    ...mapActions([
      "setMemberFromLocalStorage",
      "loginKakao",
      "setMemberFromToken",
    ]),
  },
  data: () => ({
    drawer: true,
    menus: [
      {
        path: "/votes/create",
        title: "투표 생성",
      },
    ],
  }),
};
</script>
<style scoped>
/* https://noonnu.cc */
@font-face {
  font-family: "BMHANNAPro";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_seven@1.0/BMHANNAPro.woff") format("woff");
  font-weight: normal;
  font-style: normal;
}

.v-toolbar-title {
  letter-spacing: 1px;
}

* {
  font-family: BMHANNAPro;
}

.fixed-bar {
  position: sticky;
  position: -webkit-sticky; /* for Safari */
  top: 6em;
  z-index: 2;
}
</style>
