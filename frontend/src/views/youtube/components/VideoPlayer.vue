<template>
  <div v-if="video.id" class="embed-responsive embed-responsive-16by9">
    <iframe
      class="embed-responsive-item"
      frameborder="0"
      allowfullscreen="1"
      allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
      :src="`https://www.youtube.com/embed/${video.id.videoId}`"
    ></iframe>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "VideoPlayer",
  computed: {
    ...mapGetters(["video"]),
  },
};
</script>

<style></style>
