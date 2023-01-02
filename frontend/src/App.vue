<template>
  <v-app>
    <v-main>
      <v-card max-width="448" class="mx-auto" flat>
        <v-layout>
          <v-app-bar
              color="teal-darken-4"
          >
            <template v-slot:image>
              <v-img
                  gradient="to top right, rgba(19,84,122,.8), rgba(128,208,199,.8)"
              ></v-img>
            </template>

<!--            <template v-slot:prepend>-->
<!--              <v-app-bar-nav-icon></v-app-bar-nav-icon>-->
<!--            </template>-->

            <v-app-bar-title>투표해듀오</v-app-bar-title>
            <v-btn icon>
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </v-app-bar>
          <router-view/>
        </v-layout>
      </v-card>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',
  components: {
  },

  data: () => ({
    //
  }),
}
</script>
<style scoped>
@font-face {
  font-family: 'BMHANNAPro';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_seven@1.0/BMHANNAPro.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
.v-toolbar-title {
  letter-spacing: 1px;
}
* {
  font-family: BMHANNAPro;
}

</style>