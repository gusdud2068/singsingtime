<template>
  <div>
    <Highlight />
    <hr />
    <h2><b>Room List</b></h2>

    <ul class="infinite-list">
      <li
        v-for="playroom in playrooms"
        @click="clickConference(playroom.title)"
        class="infinite-list-item"
        :key="playroom.title"
        style="margin-bottom:20px;"
      >
        <conference :playroom="playroom" />
        <v-row class="roominfof" justify="space-between">
          <p style="margin-left:30px;">{{ playroom.title }}</p>
          <p style="margin-right:33px;"><v-icon>mdi-account</v-icon>{{ playroom.userCnt }} / {{ 20 }}</p>
          <!-- <p>챔피언 : {{ playroom.champion }}</p> -->
        </v-row>
      </li>
    </ul>
  </div>
</template>
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
::-webkit-scrollbar {
  width: 15px;
  height: 8px;
  background-color: rgb(246, 246, 247); /* 또는 트랙에 추가한다 */
}

::-webkit-scrollbar-thumb {
  background: rgb(130, 166, 215);
  border-radius: 10cm;
}
</style>
<script>
import Conference from "./components/conference.vue";
import Highlight from "./components/highlight.vue";
import { reactive } from "vue";
import axios from "axios";
// import { createStore } from "vuex";

export default {
  name: "HomeBox",
  components: {
    Conference,
    Highlight,
  },

  data() {
    return {
      playrooms: [],
    };
  },
  setup() {
    // const store = createStore();
    const state = reactive({
      count: 12,
    });

    const load = function () {
      state.count += 4;
    };

    const clickConference = function (id) {
      const url = "#/conferences/" + id + "/";
      window.open(url);
      // router.push({
      //   name: "ConferencesBox",
      //   params: { Id: id},
      // });
      window.location.reload(true);
    };
    return { state, load, clickConference };
  },

  created() {
    this.getPlayroom();
  },
  methods: {
    getPlayroom() {
      axios({
        method: "get",
        url: import.meta.env.VITE_APP_URL + "/api/v1/playrooms",
      })
        .then((res) => {
          console.log(33333333333);
          console.log(res);
          this.playrooms = res.data;
        })
        .catch((err) => {
          console.log("플레이룸 불러오기 실패");
          console.log(err);
        });
    },
  },
};
</script>
