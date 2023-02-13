<template>
  <div>
    <!-- <vue-countdown
      :time="songTime * 1000"
      v-slot="{ minutes, seconds }"
    >
      <h4 :class="{ hurryup: minutes == 0 && seconds <= 30 }" style="margin-top:50px">
        남은 투표 시간 : <h1>{{ minutes }} 분 {{ seconds }} 초 </h1>
      </h4>
    </vue-countdown> -->
    <iframe
      :src="videoId"
      frameborder="5px solid white"
      style="width: 700px; height: 500px"
      allow="autoplay"
    ></iframe>
    <!-- <h5>{{ video.snippet.title | stringUnescape }}</h5> -->
  </div>
</template>
<script>
// import VueCountdown from "@chenfengyuan/vue-countdown";


export default {
  name: "SongDetail",
  components: {
    // VueCountdown,
  },
  data: function () {
    return {
      videoId: undefined,
      video: false,
      songTime: 0,
      TimeCounter: 0,
      Timer:null,
      not_show : false,
    };
  },
  props: {
    // selectvideo: "",
    session: Object,
  },
  created() {
    this.video = false;
    console.log();
    // this.session.on("signal:songTitle", (event) => {
      
    //   console.log("노래 제목은 " + event.data);
    //   const id = event.data.slice(1, -1);
    //   this.videoId =
    //     "https://sstvideo.s3.ap-northeast-2.amazonaws.com/images/" +
    //     id +
    //     ".mp4";
    //   this.video = true;
      
    // });
    // this.session.on("signal:songTime", (event) => {
    //   this.songTime = event.data;
    //   console.log("노래 시간은 " + event.data);
    // });
    
  },
  mounted() {
    this.session.on("signal:songTitle", (event) => {
      
      console.log("노래 제목은 " + event.data);
      const id = event.data.slice(1, -1);
      this.videoId =
        "https://sstvideo.s3.ap-northeast-2.amazonaws.com/images/" +
        id +
        ".mp4";
      this.video = true;
      
    });
    this.session.on("signal:songTime", (event) => {
      this.songTime = event.data;
      console.log("노래 시간은 " + event.data);
    });
  },
  updated() {
    // this.timerStart();
  },
  methods: {
    // timerStart: function() {
    //   // 1초에 한번씩 start 호출
    //   this.TimeCounter = this.songTime;
    //   console.log("======================================="+this.TimeCounter)
    //   var interval = setInterval(() => {
    //     this.TimeCounter-=0.5; //1초씩 감소
    //     console.log("시간 : "+this.TimeCounter);
    //     if (this.TimeCounter <= 0) this.timerStop(interval);
    //   }, 1000);
    //   return interval;
    // },
    // timerStop: function(Timer) {
    //   clearInterval(Timer);
    //   this.$emit('endGame', true)
    //   // console.log("게임 끝~~~~~~~~~~~~~~~~")
    // },
  },
};
</script>

<style >
</style>
