<template>
  <div v-if="video">
    <iframe
      src="https://sstvideo.s3.ap-northeast-2.amazonaws.com/images/pexels-cottonbro-6127660.mp4"
      frameborder="0"
      style="width: 700px; height: 500px"
      allow="autoplay"
    ></iframe>
    <!-- <h5>{{ video.snippet.title | stringUnescape }}</h5> -->
  </div>
</template>
<script>
export default {
  name: "SongDetail",
  data: function () {
    return {
      videoId: undefined,
      video: false,
    };
  },
  props: {
    selectvideo: "",
    session: Object,
  },
  created() {
    this.video = false;
    console.log();
    this.session.on("signal:song", (event) => {
      const id = event.data.slice(1, -1);
      console.log("session에서 받은 id : " + id);
      this.videoId = id;
      console.log(id);
      this.video = true;
    });
  },
};
</script>

<style scoped></style>
