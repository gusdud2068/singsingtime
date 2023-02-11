<template>
  <video class="live" autoplay />
</template>

<script>
export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
  },

  mounted() {
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>
<style>
.live {
  width: 300px;
  margin-right: 2rem;
}
</style>
