<template>
  <div>
    <div
      v-for="video in videos"
      :key="video.id.videoId"
      @click="setVideo(video)"
    >
      <VideoItem :video="video" />
    </div>
  </div>
</template>

<script>
import VideoItem from "./VideoItem.vue";
import { mapGetters, mapMutations } from "vuex";

export default {
  name: "VideoList",
  components: {
    VideoItem,
  },
  computed: {
    ...mapGetters(["videos"]),
  },
  methods: {
    ...mapMutations(["setVideo"]),
  },

};
</script>

<style></style>
