<template>
  <div>
    <video
      src="https://sstvideo.s3.ap-northeast-2.amazonaws.com/images/readyVideo.mp4"
      class="readyVideo"
      frameborder="0"
      style="width: 700px; height: 100%"
      autoplay
      loop
    ></video>
  </div>
</template>
<script>
export default {
  name: "ReadyDetail",
  data: function () {
    return {
      readyvideo: false,
    };
  },
  props: {
    session: Object,
  },
  created() {
    this.readyvideo = false;
    console.log(this.readyvideo);
    // this.session.on("signal:ready", () => {
    //   this.readyvideo = true;
    // });
    // console.log(1111111111111111111111111);
  },
  mounted() {
    this.session.on("signal:ready", () => {
      console.log("마운트이벤트");
      console.log(this.session)
      this.readyvideo = true;
    });
  },
  // updated() {
  //   this.session.on("signal:ready", (event) => {
  //     console.log("업데이트이벤트"+event);
  //     this.readyvideo = true;
  //     console.log("동시에");
  //   });
  // },
};
</script>

<style></style>
