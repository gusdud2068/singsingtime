<template>
  <v-row justify="sapce-between">
    <v-col align="start" class="header_info"><h4>제목</h4></v-col>
    <v-col align="center" class="header_info"><h4>가수</h4></v-col>
    <v-col align="end" class="header_info"><h4>추가</h4></v-col>
  </v-row >
  <v-row >
    <ul style="margin-top:5px;">
      <p v-for="song in state.songs" :key="song">
        <songInfo :song="song" />
        <hr>
      </p>
    </ul>
  </v-row>
</template>

<script>
import axios from "axios";
import { onMounted, reactive } from "vue";
import songInfo from "./songInfo.vue";
// import { onMounted, computed } from "vue";
// import { useStore } from "vuex";
// import { useRouter } from "vue-router";
// import { reactive } from "vue";

export default {
  name: "playlistBox",
  components: {
    songInfo,
  },
  setup() {
    // 사이트에 등록된 노래 리스트
    const state = reactive({
      songs: [],
    });
    onMounted(() => {
      console.log(3333333)
      axios({
        method: "get",
        // url: "http://localhost:8080/api/v1/users/songs",
        // url: import.meta.env.VITE_APP_URL + "/api/v1/users/",
        url: import.meta.env.VITE_APP_URL+"/api/v1/users/songs",
      })
        .then((res) => {
          console.log(res.data);
          state.songs = res.data;
          console.log(state.songs);
        })
        .catch((err) => {
          console.log(err);
        });
    });

    return {
      state,
    };
  },
};
</script>

<style>
.header_info {
  padding-top:0px;
  padding-bottom: 0px;
  margin-bottom:5px;
  background-color:aliceblue;
  color:black;
}
</style>