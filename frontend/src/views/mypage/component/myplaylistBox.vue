<template>
  <v-row justify="sapce-between">
    <v-col align="start"><h4>제목</h4></v-col>
    <v-col align="center"><h4>가수</h4></v-col>
    <v-col align="end"><h4>삭제</h4></v-col>
  </v-row>
  <ul style="margin-top:5px;">
    <p v-for="mysong in state.mysongs" :key="mysong">
      <mysongInfo :mysong="mysong" />
      <hr>
    </p>
  </ul>
</template>

<script>
import axios from "axios";
import { onMounted, reactive } from "vue";
import mysongInfo from './mysongInfo.vue'

export default {
  name: "myplaylistBox",
  components: {
    mysongInfo,
  },
  setup() {
    const state = reactive({
      token: localStorage.getItem("jwt"),
      mysongs : [],
    });
    onMounted(() => {
      axios({
        method: "get",
        url: import.meta.env.VITE_APP_URL + "/api/v1/users/my-page/songs",
        headers: {
          Authorization: `Bearer ${state.token}`,
        },
      })
        .then((res) => {
        //   console.log(res.data);
          // console.log("가져오기 성공");
          state.mysongs = res.data
          // console.log(state.mysongs)
        })
        .catch((err) => {
          console.log(err);
        });
    });
    // 사이트에 등록된 전체 노래 조회
    return {
      state,
    };
  },
};
</script>

<style>

</style>