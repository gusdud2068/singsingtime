<template>
  <div class="p-2" style="">
    <section @click="selectVideo" style="display: flex">
      <div>
        <v-img
          :src="youtubeImageSrc"
          alt="youtube-thumbnail-image"
          width="200px"
        ></v-img>
      </div>
      <h5>{{ video.snippet.title }}</h5>
    </section>
  </div>
</template>

<script>
import _ from "lodash";

export default {
  name: "SongListItem",
  props: {
    video: {
      type: Object,
    },
  },
  methods: {
    selectVideo: function () {
      this.$store.state.video = this.video;
      this.$emit("select-video", this.video);
      console.log(this.$store.state.video);
    },
  },
  computed: {
    youtubeImageSrc: function () {
      return this.video.snippet.thumbnails.default.url;
    },
  },
};
</script>

<style scoped>
.listitem h5 {
  color: white;
  font-size: 15px;
  width: 40%;
  float: right;
  padding-top: 30px;
}
</style>
