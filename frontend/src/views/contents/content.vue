<template>
  <div justify="center" align="center" style="display: flex">
    <div style="margin: 20px; margin-left: 60px; margin-right: 60px">
      <h3 style="margin-left: 30px">
        <b>어제자 광주 김범수 대결 영상 ㄷㄷ</b>
      </h3>
      <video
        src="https://sstvideo.s3.ap-northeast-2.amazonaws.com/images/test.mp4"
        width="800"
        height="500"
        controls
      ></video>
    </div>
    <v-row>
      <v-col lg="10" class="comment">
        <h5>댓글</h5>
        <hr />
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "ContentsBox",
};
</script>

<style>
.comment {
  border: 1px solid black;
  height: 95%;
  margin: 20px;
}
</style>
