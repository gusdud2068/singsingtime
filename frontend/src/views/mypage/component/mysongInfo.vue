<template>
  <v-row justify="space-between">
    <v-col align="center">{{ mysong.title }}</v-col>
    <v-col align="center">{{ mysong.singer }}</v-col>
    <v-col align="center">{{ mysong.singer }}</v-col>
  </v-row>
</template>

<script>
// import axios from "axios";
import { reactive, onMounted } from "vue";
// import { useStore } from "vuex";

export default {
  name: "mysongInfo",
  props: ["mysong"],
  setup(props) {
    const state = reactive({
      pick_list: [],
      token: localStorage.getItem("jwt"),
      pick: false,
    });

    onMounted(() => {
        // console.log(555555555555)
        // console.log(props.mysong)
    })

    return {
      state,
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