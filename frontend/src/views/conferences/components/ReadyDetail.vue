<template>
  <div>
    <h2>
      지금 챔피언에게 도전하세요!
    </h2>
    <iframe
      src="https://sstvideo.s3.ap-northeast-2.amazonaws.com/images/readyVideo.mp4"
      frameborder="0"
      style="width: 700px; height: 500px"
      allow="autoplay; loop"
    ></iframe>
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

<style scoped></style>
