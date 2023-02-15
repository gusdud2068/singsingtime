<template>
  <video v-if="playerUser" class="live1" autoplay />
  <video v-else class="live2" autoplay />
</template>

<script>
export default {
  name: "OvVideo",

  props: {
    streamManager: Object,
    playerUser: Boolean,
  },

  mounted() {
    console.log("ovvvvvvvvvvv")
    console.log(this.playerUser)
    this.streamManager.addVideoElement(this.$el);
  },
};
</script>
<style>
.live1 {
  width: 695px;
  margin-right: 2rem;
}
.live2 {
  width: 300px;
  margin-right: 2rem;
}
</style>
