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
  created() {
    this.readyvideo = false;
    console.log(this.readyvideo);
    // this.session.on("signal:ready", () => {
    //   this.readyvideo = true;
    // });
  },
};
</script>

<style>
</style>
