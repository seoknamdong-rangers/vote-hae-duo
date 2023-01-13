import { createRouter, createWebHistory } from "vue-router";
import Main from "@/views/Main";
import VoteDetail from "@/views/VoteDetail.vue";
import Votes from "@/views/Votes";
// import VoteCreate from "@/views/VoteCreate";
import Error from "@/views/Error";

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/votes",
    name: "Votes",
    component: Votes,
  },
  {
    path: "/votes/:id(\\d+)",
    name: "VoteDetail",
    component: VoteDetail,
  },
  // {
  //   path: "/votes/create",
  //   name: "VoteCreate",
  //   component: VoteCreate,
  // },
  {
    path: "/:catchAll(.*)",
    redirect: "/404",
  },
  {
    path: "/404",
    name: "Error",
    component: Error,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
