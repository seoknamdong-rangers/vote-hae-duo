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
      profileUrl: "",
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
    },
  },
  actions: {
    loginKakao(context) {
      const member = JSON.parse(localStorage.getItem("member"));
      if (member) {
        context.commit("setMember", member);
      } else {
        location.href = "/oauth/authorize";
      }
    },
    setMemberFromTokenUrl(context, token) {
      const member = context.state.member;
      if (member.id < 1 && token) {
        // eslint-disable-next-line no-undef
        const member = JSON.parse(Base64.decode(token));
        const createMemberRequest = {
          kakaoMemberNo: member.sub,
          nickname: member.nickname,
          profileUrl: member.picture,
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
