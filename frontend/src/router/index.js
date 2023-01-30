import { createRouter, createWebHashHistory } from "vue-router";
import CommunityView from "../views/community/community.vue";
import ConferencesView from "../views/conferences/conference.vue";
import HomeView from "../views/home/home.vue";
import MypageView from "../views/mypage/mypage.vue";
import RankView from "../views/rank/rank.vue";
import ContentsView from "../views/contents/content.vue";

const routes = [
  {
    path: "/",
    name: "HomeBox",
    component: HomeView,
  },
  {
    path: "/rank",
    name: "RankBox",
    component: RankView,
  },
  {
    path: "/conferences/:Id",
    name: "ConferencesBox",
    component: ConferencesView,
    props: true,
  },
  {
    path: "/contents/:Id",
    name: "ContentsBox",
    component: ContentsView,
    props: true,
  },
  {
    path: "/community",
    name: "CommunityBox",
    component: CommunityView,
  },
  {
    path: "/mypage",
    name: "MypageBox",
    component: MypageView,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
