<template>
  <div justify="center" align="center" style="margin-top: 40px">
    <v-row>
      <v-col lg="7">
        <h3 align="start" style="margin-left: 80px">
          <!-- <b>{{title}}</b> -->
          <b style="margin-left: 0px">제목 : {{ videoInfo.title }}</b>
        </h3>

        <iframe
          :src="videoInfo.video"
          ref="videoPlayer"
          width="600"
          height="400"
          style="margin-bottom: 20px"
          autoplay
        ></iframe>

        <!-- <button @click="play">play</button> -->

        <v-row justify="space-between">
          <p style="margin-left: 80px">{{ videoInfo.viewCnt }} View</p>
          <p style="margin-right: 80px">
            {{ videoInfo.likeCnt }}
            <button
              @click="decreaseLikeCnt"
              v-if="this.heartcheck"
              class="active"
            >
              <v-icon>mdi-heart</v-icon>
            </button>
            <button
              @click="increaseLikeCnt"
              v-else
              class="active"
            >
              <v-icon>mdi-heart-outline</v-icon>
            </button>
          </p>
        </v-row>
      </v-col>
      <CommentsBox :replys="videoInfo.replys" />
      <!-- <v-col lg="4" class="comment_box">
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
      </v-col> -->
    </v-row>
  </div>
</template>

<script>
// import { onMounted, reactive, computed } from "vue";
import { reactive } from "vue";
import CommentsBox from "./components/comments.vue";
import axios from "axios";
import { useStore } from "vuex";
import _ from "lodash";

export default {
  name: "ContentsBox",

  components: {
    CommentsBox,
  },
  data() {
    return {
      status: false,
      heartCheck: false,
      videoInfo:{
        title: "",
        description: "",
        usernickname: "",
        video : "",
        likeCnt: 0,
        viewCnt: 0,
        replys: [],
      }
    };
  },
  created() {
    this.setVideoInfo();
  },
  computed: {
    computedViewCnt(){
      return this.videoInfo.viewCnt;
    },
    computedLikeCnt(){
      return this.videoInfo.likeCnt;
    }
  },
  methods: {
    play() {
      this.status = !this.status;
      if (this.status) {
        this.$refs.videoPlayer.play();
      } else {
        this.$refs.videoPlayer.pause();
      }
    },
    increaseLikeCnt(){
      // 프론트 likeCnt + 1
      this.videoInfo.likeCnt += 1;
      // db likeCnt + 1
      this.addLikes();
      this.heartcheck = true;
    },
    decreaseLikeCnt(){
      this.videoInfo.likeCnt -= 1;
      this.minusLikes();
      this.heartcheck = false;
    },

    setVideoInfo(){
      const getid = localStorage.getItem("page");
      axios({
        method: "get",
        url: import.meta.env.VITE_APP_URL + `/api/v1/videos/${getid}`,
        // url: import.meta.env.VITE_APP_URL + `api/v1/videos/${state.keyId}`,
      })
        .then((res) => {
          console.log(res);
          this.videoInfo.title = res.data.title;
          this.videoInfo.description = res.data.description;
          this.videoInfo.usernickname = res.data.user.nickname;
          this.videoInfo.video = res.data.url;
          this.videoInfo.likeCnt = res.data.likeCnt;
          this.videoInfo.viewCnt = res.data.viewCnt;
          this.videoInfo.replys = res.data.replys;
        })
        .catch((err) => {
          console.log(err);
        });
    },

    
  },

  setup() {
    const store = useStore();
    const state = reactive({
      keyId: "",
      title: "",
      description: "",
      usernickname: "",
      video: "",
      viewCnt: 0,
      likeCnt: 0,
      replys: [],
      ordering: [],
    });
    const videoId = localStorage.getItem("page");

    const addLikes = () => {
      axios({
        method: "put",
        url: import.meta.env.VITE_APP_URL + `/api/v1/videos/likes/${videoId}`,
      })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });

      // axios({
      //   method: "get",
      //   url: import.meta.env.VITE_APP_URL + `/api/v1/videos/${videoId}`,
      //   // url: import.meta.env.VITE_APP_URL + `api/v1/videos/${state.keyId}`,
      // })
      //   .then((res) => {
      //     console.log(res);
      //   })
      //   .catch((err) => {
      //     console.log(err);
      //   });
    };

    const minusLikes = () => {
      axios({
        method: "put",
        url: import.meta.env.VITE_APP_URL + `/api/v1/videos/likes-cancel/${videoId}`,
      })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    };
    // const fullheart =function(){
    //   localStorage.getItem('likes')
    //   // state.binheart='true';
    // }

    // onMounted(() => {
    //   const getid = localStorage.getItem("page");
    //   axios({
    //     method: "get",
    //     url: import.meta.env.VITE_APP_URL + `/api/v1/videos/${getid}`,
    //     // url: import.meta.env.VITE_APP_URL + `api/v1/videos/${state.keyId}`,
    //   })
    //     .then((res) => {
    //       console.log("hhhhhhhhhhhhhhh");
    //       console.log(res);
    //       state.title = res.data.title;
    //       state.description = res.data.description;
    //       state.usernickname = res.data.user.nickname;
    //       state.video = res.data.url;
    //       state.likeCnt = res.data.likeCnt;
    //       state.viewCnt = res.data.viewCnt;
    //       state.replys = res.data.replys;
    //       // this.likeCnt = state.likeCnt;
    //     })
    //     .catch((err) => {
    //       console.log(err);
    //     });

    //   state.ordering = computed(() =>
    //     _.orderBy(state.replys, "createdDate", "desc")
    //   );
    // });
    return {
      state,
      store,
      addLikes,
      minusLikes,
      // changes,
      // getid,
      // fullheart
    };
  },
};
</script>

<style>
.active {
  color: red;
}
</style>
