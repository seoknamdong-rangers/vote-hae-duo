<template>
  <v-main class="main-wrap">
    <v-container class="sub-wrap">
      <h1>404</h1>
      <h3 class="mb-5">찾을 수 없는 페이지를 요청했어요.</h3>
      <h3>뒤로 돌아가시면 됩니다.😝</h3>
    </v-container>
  </v-main>
</template>
<script>
export default {};
</script>
<style scoped>
@font-face {
  font-family: 'BMDOHYEON';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}
@import url('https://cdn.rawgit.com/moonspam/NanumSquare/master/nanumsquare.css');
.sub-wrap {
  font-family: NanumSquare;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
}
h1 {
  font-family: "BM DoHyeon";
  font-size: 130px;
  letter-spacing: 10px;
  color: rgba(19,84,122,.8);
}
h3 {
  letter-spacing: 2px;
}
</style>
