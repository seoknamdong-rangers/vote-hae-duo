import { createWebHistory, createRouter } from "vue-router";
import Main from "@/views/Main";
import Votes from "@/views/Votes";
import Error from "@/views/Error";

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main
  },
  {
    path: "/votes",
    name: "Votes",
    component: Votes
  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404"
  },
  {
    path: "/404",
    name: "Error",
    component: Error
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;