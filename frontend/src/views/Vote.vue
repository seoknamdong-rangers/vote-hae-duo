<template>
  <v-main>
    <v-container fluid class="wrap">
      <v-card elevation="3" class="pa-3">
        <h1 class="ma-3 mb-5">{{ vote.title }}</h1>
        <div v-for="(voteItem, index) in vote.voteItems" :key="index">
          <v-row>
            <v-checkbox color="red" :value="voteItem.id" hide-details="true">
              <template v-slot:label>
                <div>
                  <span class="text-black item-title">{{ voteItem.title }}</span>
                </div>
              </template>
            </v-checkbox>
            <span class="text-black float-right ma-auto pr-6 item-count mb-3">{{ voteItem.memberIds.length }} 명 <v-icon
                color="warning"
                class="mb-1"
            >mdi-account-question-outline</v-icon></span>
          </v-row>
          <div class="pl-7 pr-3 mb-5 mt-1">
            <v-progress-linear
                :model-value="(voteItem.memberIds.length / totalCount) * 100"
                bg-color="light-grey"
                :color="
                voteItem.memberIds.length === maxCount ? 'warning' : 'grey'
              "
                height="20"
            >
            </v-progress-linear>
          </div>
        </div>
        <v-container class="pa-0">
          <v-container class="pt-0">
            <span class="float-right"> 총 {{ maxCount }} 명 참여</span>
          </v-container>
          <v-container class="text-center mt-3">
            <v-btn width="70%" class="vote">투표하기</v-btn>
          </v-container>
        </v-container>
      </v-card>
    </v-container>
  </v-main>
</template>
<script>
export default {
  computed: {
    totalCount() {
      let totalCount = 0;
      this.vote.voteItems.forEach((it) => (totalCount += it.memberIds.length));
      return totalCount;
    },
    maxCount() {
      return Math.max(...this.vote.voteItems.map((it) => it.memberIds.length));
    },
  },
  data: () => ({
    vote: {
      id: 1,
      title: "1월 6일 금",
      startDate: "2022-12-20",
      endDate: "2023-01-06",
      createdBy: "홍준성",
      uniqueCount: 2,
      voteItems: [
        {
          id: 1,
          title: "11시 ~ 1시 실외",
          memberIds: [1, 2],
        },
        {
          id: 2,
          title: "12시 ~ 2시 실내",
          memberIds: [4],
        },
        {
          id: 3,
          title: "불참",
          memberIds: [7, 8, 9, 10],
        },
      ],
    },
  }),
};
</script>
<style scoped>
@font-face {
  font-family: "LINESeedKR-Bd";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/LINESeedKR-Bd.woff2") format("woff2");
  font-weight: 700;
  font-style: normal;
}

.wrap {
  font-family: LINESeedKR-Bd;
  background-color: whitesmoke;
}

.item-title {
  font-size: 18px;
}

.item-count {
  opacity: 60%;
}

.vote {
  background-color: rgb(70, 145, 149);
  color: white;
}

</style>
