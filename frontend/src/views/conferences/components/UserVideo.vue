<template>
  <div v-if="streamManager" style="width:fit-content">
    <ov-video :stream-manager="streamManager" />
    <div>
      <p>{{ clientData }}</p>
    </div>
    <input
          class="btn btn-large btn-primary"
          type="button"
          id="buttonAudioDisabled"
          @click="disableAudio"
          value="음소거"
        />
  </div>
</template>

<script>
import OvVideo from "./OvVideo.vue";

export default {
  name: "UserVideo",

  components: {
    OvVideo,
  },

  props: {
    streamManager: Object,
  },

  computed: {
    clientData() {
      const { clientData } = this.getConnectionData();
      return clientData;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
    disableAudio() {
      this.streamManager.stream.publishAudio(false);
    }
  },
};
</script>
<style></style>
