<template>
  <div style="margin-left: 10px; margin-top: 20px;">
    <v-btn class="votebtn" @click="pickChampion" style="font-size:25px;">
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
  width: 140px;
  height: 140px;
  border-radius: 100px;
  background-color:mediumblue;
}
  .votebtn:hover {
    border: 5px solid rgb(4, 4, 104);
    color: black;
    background-color: rgb(177, 177, 247);
  }
</style>
