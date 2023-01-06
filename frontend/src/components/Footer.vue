<template>
  <v-footer
      dark
      padless
  >
    <v-card
        flat
        tile
        class="indigo lighten-1 white--text text-center"
    >
      <v-card-text>
        <v-btn
            v-for="icon in icons"
            :key="icon"
            class="mx-4 white--text"
            :href="icon.path"
            target="_blank"
            icon
        >
          <v-icon size="24px">
            {{ icon.name }}
          </v-icon>
        </v-btn>
      </v-card-text>
      <v-divider></v-divider>

      <small class="ma-auto"
      >Copyright 2023. SEOKNAMDONG_RANGERS All rights reserved.</small
      >
    </v-card>
  </v-footer>
</template>
<script>
export default {
  data: () => ({
    icons: [
      {name: 'mdi-github', path: "https://github.com/seoknamdong-rangers/vote-hae-duo"},
      {name: 'mdi-instagram', path: ""},
      {name: 'mdi-facebook', path: ""},
      {name: 'mdi-twitter', path: ""},
    ],
  }),
};
</script>
<style scoped></style>
