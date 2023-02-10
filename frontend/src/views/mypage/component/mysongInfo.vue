<template>
  <v-row justify="space-between" @click="deleteMysong">
    <v-col align="start">{{ mysong.title }}</v-col>
    <v-col align="center">{{ mysong.singer }}</v-col>
  </v-row>
</template>

<script>
import axios from "axios";
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


    const deleteMysong = function () {
      axios({
        method: "delete",
        // url: "http://localhost:8080/api/v1/users/songs",
        // url: import.meta.env.VITE_APP_URL + "/api/v1/users/",
        url: import.meta.env.VITE_APP_URL + `/api/v1/users/my-page/songs/${props.mysong.id}`,
        headers: {
            Authorization: `Bearer ${state.token}`,
          },
      })
        .then((res) => {
          alert('삭제 되었습니다')
          console.log(res.data);
          window.location.reload(true);
        })
        .catch((err) => {
          console.log(err);
        });
    };

    onMounted(() => {
      // console.log(555555555555)
      // console.log(props.mysong)
    });

    return {
      state,
      deleteMysong,
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