import "es6-promise/auto";
import { createStore } from "vuex";
import axios from "axios";

export default createStore({
  state: {
    member: {
      id: -1,
      kakaoMemberNo: "",
      nickname: "",
      email: "",
    },
  },
  getters: {
    getMember(state) {
      return state.member;
    },
  },
  mutations: {
    setMember(state, member) {
      state.member = member;
      console.log(state.member);
    },
  },
  actions: {
    setMemberFromLocalStorage(context) {
      const member = JSON.parse(localStorage.getItem("member"));
      console.log(member);
      if (member) {
        context.commit("setMember", member);
      }
    },
    loginKakao(context) {
      const member = context.state.member;
      if (member.id < 1) {
        location.href = "/oauth/authorize";
      }
    },
    setMemberFromToken(context, token) {
      const member = context.state.member;
      if (member.id < 1) {
        // eslint-disable-next-line no-undef
        const member = JSON.parse(Base64.decode(token));
        const createMemberRequest = {
          kakaoMemberNo: member.sub,
          nickname: member.nickname,
          email: member.email,
        };
        axios
          .post("/api/members", createMemberRequest)
          .then((res) => {
            context.commit("setMember", res.data);
            localStorage.setItem("member", JSON.stringify(res.data));
          })
          .catch((error) => alert(error.response.data));
      }
    },
  },
  modules: {},
});
