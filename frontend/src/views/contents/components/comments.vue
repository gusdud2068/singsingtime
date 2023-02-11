<template>
  <!-- <div> -->
  <v-col lg="4" class="comment_box">
    <v-row>
      <h4><b>Comment</b></h4>
      <input
        class="comment_input"
        type="text"
        placeholder=" 댓글을 달아 주세요"
        v-model="state.comment"
        @keydown.enter="commentSubmit"
      />
      <v-btn class="comment_create" @click="commentSubmit">+</v-btn>
    </v-row>
    <div style="margin-top: 0px">
        <!-- <v-col> -->
            <v-row
            justify="space-between"
            style="margin-top: 20px"
            v-for="reply in replys"
            :key="reply"
            >
    <!-- <div justify="space-between"
            style="margin-top: 20px"
            v-for="reply in replys"
            :key="reply" -->
        <v-col lg="8">
          <p>{{ reply.context }}</p>
        </v-col>

        <div>
          <v-row>
            <p style="margin-right: 10px; margin-top: 25px">
              {{ reply.user.nickname }}
            </p>
            <button
              v-show="reply.user.nickname == state.nickname"
              @click="commentDelete(reply.id)"
            >
              <v-icon style="color: brown; margin-top: 15px">mdi-close</v-icon>
            </button>
        </v-row>
    </div>
<!-- </div> -->
      </v-row>
    <!-- </v-col> -->
    </div>
    <div class="comments_list"></div>
  </v-col>
  <!-- </div> -->
  <!-- <div class="commentItem">
      <div>{{ name }}</div>
      <div>{{ commentItem.created_at }}</div>
      <div>{{ commentItem.context }}</div>
      <b-button @click="updateComment">수정</b-button>
      <b-button @click="deleteComment">삭제</b-button>
    </div> -->
</template>
  
  <script>
import { useStore } from "vuex";
import axios from "axios";
import { reactive } from "vue";

export default {
  name: "CommentsBox",
  props: ["replys"],

  setup() {
    const store = useStore();
    const state = reactive({
      commenttitle: "",
      comment: "",
      token: localStorage.getItem("jwt"),
      videoId: localStorage.getItem("page"),
      nickname: localStorage.getItem("nickname"),
    });
    const commentSubmit = function () {
      axios({
        method: "post",
        url: import.meta.env.VITE_APP_URL + "/api/v1/videos/replys",
        data: {
          context: state.comment,
          videoId: state.videoId,
        },
        headers: {
          Authorization: `Bearer ${state.token}`,
        },
      })
        .then((res) => {
          console.log(res);
          console.log("댓글입력");
          state.comment = "";
          window.location.reload(true);
        })
        .catch((err) => {
          console.log("댓글실패");
          console.log(err);
        });
    };
    const commentDelete = function (id) {
      axios({
        method: "delete",
        url: import.meta.env.VITE_APP_URL + `/api/v1/videos/replys/${id}`,
        headers: {
          Authorization: `Bearer ${state.token}`,
        },
      })
        .then((res) => {
          console.log(res);
          window.location.reload(true);
        })
        .catch((err) => {
          console.log(err);
        });
    };
    return {
      state,
      store,
      commentSubmit,
      commentDelete,
    };
  },
};
</script>
  <style>
.comment_box {
  margin-top: 80px;
}

.comment_input {
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
</style>


