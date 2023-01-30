<template>
  <h2><b>Community</b></h2>
  <div>
  <ul class="infinite-list" v-infinite-scroll="load" style="overflow: auto">
    <li
      v-for="i in video_list"
      class="infinite-list-item"
      @click="clickContent(i)"
      :key="i"
    >
      <ContentBox />
    </li>
    <div class="text-center">
      <v-pagination
        v-model="state.page"
        :length="max_page"
        @click="consolemethod(state.page)"
      ></v-pagination>
    </div>
  </ul>
</div>
</template>

<script>
import ContentBox from "./component/content.vue";
import { reactive } from "vue";
import { useRouter } from "vue-router";

export default {
  name: "CommunityBox",
  components: {
    ContentBox,
  },
  data() {
    return {
      page: 1,
      video_list: [],
    };
  },
  setup() {
    const router = useRouter();
    // const store = createStore();
    const state = reactive({
      count: 54,
      page: 1,
    });

    const max_page = parseInt(state.count / 12) + 1;

    const video_list = [];
    for (let i = 1 + (state.page - 1) * 12; i <= state.page * 12; i++) {
      video_list.push(i);
    }

    const load = function () {
      state.count += 4;
    };

    const clickContent = function (id) {
      router.push({
        name: "ContentsBox",
        params: { Id: id },
      });
    };
    return { state, load, clickContent, max_page, video_list };
  },
  methods: {
    consolemethod(value) {
      this.video_list = [];
      for (let i = 1 + (value - 1) * 12; i <= value * 12; i++) {
        if (i > 54) {
          return video_list;
        }
        this.video_list.push(i);
      }
      // this.$router.go()
      return this.video_list;
    },
  },
};
</script>

<style>
.infinite-list {
  padding-left: 0;
  max-height: calc(100% - 35px);
}

@media (min-width: 701px) and (max-width: 1269px) {
  .infinite-list {
    min-width: 700px;
  }
}

@media (min-width: 1270px) {
  .infinite-list {
    min-width: 1021px;
  }
}

.infinite-list .infinite-list-item {
  min-width: 335px;
  max-width: 25%;
  display: inline-block;
  cursor: pointer;
}
</style>
