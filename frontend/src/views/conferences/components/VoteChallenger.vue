<template>
  <div style="margin-left:10px;margin-top: 20px;">
    <v-btn class="votebtn" color="green" @click="pickChallenger">
      투표하기
    </v-btn>
  </div>
</template>
<script>

export default {
  name: "VoteChallenger",
  components: {
  },
  data: function () {
    return {
      videoId: undefined,
      video: false,
    };
  },
  methods:{
    pickChallenger: function(){
      this.$emit('voteChallenger', true)
    }
  }
};
</script>

<style scoped>
.votebtn {
  width: 100px;
  height: 100px;
  border-radius: 100px;
  /* border: 3px solid white; */
  /* color:yellow; */
}
/* .votebtn:hover {
  border: 3px solid rgb(149, 230, 248);
  color: blue;
  background-color: red;
} */

</style>
