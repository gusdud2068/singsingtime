<template>
  <div justify="center" align="center" style="margin-top: 40px">
    <v-row>
      <v-col lg="7">
        <h3 align="start" style="margin-left: 80px">
          <b>어제자 광주 김범수 대결 영상 ㄷㄷ</b>
        </h3>
        <video
          src="../../assets/video/test.mp4"
          width="700"
          height="500"
          autoplay
        ></video>
        <v-row justify="space-between">
          <p style="margin-left: 80px">3,201 View</p>
          <p style="margin-right: 80px">
            219
            <button @click="activeBtn" v-if="!state.heartcheck" class="active">
              <v-icon>mdi-heart-outline</v-icon>
            </button>
            <button @click="activeBtn" v-if="state.heartcheck" class="active">
              <v-icon>mdi-heart</v-icon>
            </button>
          </p>
        </v-row>
      </v-col>
      <v-col lg="4" class="comment_box">
        <v-row>
          <h4><b>Comment</b></h4>
          <input
            class="comment_inpupt"
            type="text"
            placeholder=" 댓글을 달아 주세요"
            v-model="comment"
          />
          <v-btn class="comment_create">+</v-btn>
        </v-row>
        <v-row justify="space-between" style="margin-top: 20px">
          <p style="margin-left: 5px">내용</p>
          <p style="margin-right: 10px">작성자</p>
        </v-row>
        <div class="comments_list"></div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import { reactive } from "vue";

export default {
  name: "ContentsBox",
  data() {
    return {
      comment: "",
    };
  },
  setup() {
    const state = reactive({
      heartcheck: false,
    });
    const activeBtn = () => {
      state.heartcheck = !state.heartcheck;
    };
    return {
      state,
      activeBtn,
    };
  },
};
</script>

<style>
.comment_box {
  margin-top: 80px;
}

.comment_inpupt {
  border-bottom: 1px solid rgb(0, 0, 0);
  margin-right: 10px;
  margin-top: 10px;
  height: 30px;
  width: 85%;
}

.comment_create {
  border-radius: 20px;
}

.comments_list {
  margin-top: 10px;
  width: 100%;
  height: 70%;
}
.active {
  color: red;
}
</style>
