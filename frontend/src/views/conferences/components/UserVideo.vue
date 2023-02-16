<template>
  <div v-if="streamManager" style="width:fit-content">
    <ov-video :stream-manager="streamManager" :playerUser="player" />
    
          <!--스몰박스 left, 노래화면 왼쪽. 여기에 스트림매니저로 챔피언을 넘겨줘야함-->
          <h4 style="margin-left:200px; margin-top:20px; width:300px;font-size: 30px;" ><img src='../../../assets/images/sparkling.gif' style="width:30px">{{ clientData }}님<img src='../../../assets/images/sparkling.gif' style="width:30px"></h4>

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
    player: Boolean,
  },

  computed: {
    clientData() {
      const clientData = this.getConnectionData();
      return clientData.clientNickname;
    },
  },

  methods: {
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
  },
};
</script>
<style>
.smallboxl{
  width:500px;
}</style>
