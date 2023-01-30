<template>
  <div class="scroll">
  <Highlight />
  <hr />
  <h2><b>Room List</b></h2>

  <ul class="infinite-list" v-infinite-scroll="load" style="overflow: auto">
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
};

</style>
<script>
import Conference from "./components/conference.vue";
import Highlight from "./components/highlight.vue";
import { reactive } from "vue";
import { useRouter } from "vue-router";
// import { createStore } from "vuex";

export default {
  name: "HomeBox",

  components: {
    Conference,
    Highlight,
  },

  setup() {
    const router = useRouter();
    // const store = createStore();
    const state = reactive({
      count: 12,
    });

    const load = function () {
      state.count += 4;
    };

    const clickConference = function (id) {
      router.push({
        name: "ConferencesBox",
        params: { Id: id, name: "나다" },
      });
    };

    return { state, load, clickConference };
  },
};
</script>
