<template>
  <!-- <div v-if="video">
    <vue-countdown
      v-if="!this.selectedVideo"
      :time="4 * 60 * 1000"
      v-slot="{ minutes, seconds }"
    >
      <h3 :class="{ hurryup: minutes == 0 && seconds <= 30 }">
        남은 투표 시간 : {{ minutes }} 분 {{ seconds }} 초
      </h3>
    </vue-countdown>
    <iframe
      :src="videoId"
      frameborder="0"
      style="width: 700px; height: 500px"
      allow="autoplay"
    ></iframe>
  </div> -->
  <div style="margin-left:10px;margin-top: 2px;">
    <v-btn color="red" @click="pickChampion">
      투표하기
    </v-btn>
  </div>
</template>
<script>
// import VueCountdown from "@chenfengyuan/vue-countdown";

export default {
  name: "VoteChampion",
  components: {
    // VueCountdown,
  },
  data: function () {
    return {
      videoId: undefined,
      video: false,
    };
  },
  methods:{
    pickChampion: function(){
      this.$emit('voteChampion', true)
    }
  }
  // props: {
  //   selectvideo: "",
  //   session: Object,
  // },
  // created() {
  //   this.video = false;
  //   console.log();
  //   this.session.on("signal:song", (event) => {
  //     console.log("여기서 노래 한개 정보 받기");
  //     console.log(event.data);
  //     const id = event.data.slice(1, -1);
  //     console.log("session에서 받은 id : " + id);
  //     this.videoId =
  //       "https://sstvideo.s3.ap-northeast-2.amazonaws.com/images/" +
  //       id +
  //       ".mp4";
  //     console.log(11111111111111111111111);
  //     console.log(id);
  //     this.video = true;
  //   });
  // },
};
</script>

<style scoped></style>
