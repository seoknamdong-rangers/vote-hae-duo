<template>
  <v-main>
    <v-container fluid class="wrap">
      <v-row dense>
        <v-col
            v-for="(vote, index) in votes"
            :key="index"
            cols="12"
        >
          <v-card
              prepend-icon="mdi-soccer"
              :title="vote.title"
              :subtitle="`만든 사람: ${vote.createdBy}`"
              :text="`참여 인원: ${vote.uniqueCount}
              기간: ${vote.startDate} ~ ${vote.endDate}`"
              elevation="3"
          >
            <v-card-actions>
              <v-btn
                  variant="text"
                  color="teal-accent-4"
                  :to="`/votes/${vote.id}`"
              >
                <v-icon class="mb-1">mdi-vote-outline</v-icon>
                투표하러 가기
              </v-btn>
              <v-spacer></v-spacer>
              <v-btn size="small" class="float-right ma-0 pa-0">
                수정
              </v-btn>
              <v-btn size="small" class="float-right ma-0 pa-0">
                삭제
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
  components: {},
  data: () => ({
    votes: [
      {
        id: 1,
        title: "1월 6일 금",
        startDate: "2022-12-20",
        endDate: "2023-01-06",
        createdBy: "홍준성",
        uniqueCount: 2
      },
      {
        id: 2,
        title: "1월 17일 금",
        startDate: "2023-01-10",
        endDate: "2023-01-17",
        createdBy: "김성준",
        uniqueCount: 5
      },
      {
        id: 3,
        title: "1월 27일 금",
        startDate: "2023-01-17",
        endDate: "2023-01-27",
        createdBy: "최성욱",
        uniqueCount: 7
      },
      {
        id: 4,
        title: "2월 8일 금",
        startDate: "2023-02-01",
        endDate: "2023-02-08",
        createdBy: "최성욱",
        uniqueCount: 7
      },
    ],
  }),
};
</script>
<style scoped>
.wrap {
  background-color: whitesmoke;
  white-space: pre-line;
}

</style>
