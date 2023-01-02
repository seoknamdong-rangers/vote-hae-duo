<template>
  <v-main>
    <v-container fluid class="wrap">
      <v-row dense>
        <v-col
            v-for="n in 6"
            :key="n"
            cols="12"
        >
          <v-card
              prepend-icon="mdi-soccer"
              :title="`투표 ${n}`"
              :subtitle="`만든 사람: 홍준성 `"
              :text="`참여 인원: 7명
              기간: 2022-12-31 ~ 2023-01-06`"
              elevation="3"
          >
            <v-card-actions>
              <v-btn
                  variant="text"
                  color="teal-accent-4"
              >
                <v-icon class="mb-1">mdi-vote-outline</v-icon> 투표하러 가기
              </v-btn>
            </v-card-actions>

          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>
<script>

export default {
  components: {}
};
</script>
<style scoped>
.wrap {
  background-color: whitesmoke;
  white-space: pre-line;
}

</style>
