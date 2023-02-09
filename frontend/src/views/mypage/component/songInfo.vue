<template>
  <v-row @click="addPickList">
    <v-col align="start" class="song_info">{{ song.title }}</v-col>
    <v-col align="center" class="song_info">{{ song.singer }}</v-col>
    <v-col v-show="!state.pick" align="end" class="song_info"
      ><v-icon>mdi-checkbox-blank-outline</v-icon></v-col
    >
    <v-col v-show="state.pick" align="end" class="song_info"
      ><v-icon>mdi-checkbox-marked</v-icon></v-col
    >
  </v-row>
</template>

<script>
// import axios from "axios";
import { reactive } from "vue";
import { useStore } from "vuex";

export default {
  name: "songInfo",
  props: ["song"],
  setup(props) {
    const state = reactive({
      pick_list: [],
      token: localStorage.getItem("jwt"),
      pick: false,
    });

    const store = useStore();

    const addPickList = function () {
      store.dispatch("playlistStore/addPickList", {
        songId: props.song.id,
      });
      state.pick = true;
    };

    return {
      state,
      addPickList,
    };
  },
};
</script>

<style>
.song_info {
  padding-top: 5px;
  padding-bottom: 5px;
}
</style>