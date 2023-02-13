<template>
  <div style="margin-left: 10px; margin-top: 20px">
    <v-btn class="votebtn" color="primary" @click="pickChampion">
      투표하기
    </v-btn>
  </div>
</template>
<script>
export default {
  name: "VoteChampion",
  components: {},
  data: function () {
    return {
      videoId: undefined,
      video: false,
    };
  },
  methods: {
    pickChampion: function () {
      this.$emit("voteChampion", true);
    },
  },
};
</script>

<style scoped>
.votebtn {
  width: 100px;
  height: 100px;
  border-radius: 100px;
}
/* .votebtn:hover {
  border: 3px solid blue;
  color: blue;
  background-color: blue;
} */
</style>
