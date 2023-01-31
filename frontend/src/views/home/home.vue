<template>
  <div>
    <Highlight />
    <hr />
    <h2><b>Room List</b></h2>

    <ul class="infinite-list">
      <li
        v-for="i in state.count"
        @click="clickConference(i)"
        class="infinite-list-item"
        :key="i"
      >
        <conference />
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
// import { createStore } from "vuex";

export default {
  name: "HomeBox",

  components: {
    Conference,
    Highlight,
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
    };

    return { state, load, clickConference };
  },
};
</script>
