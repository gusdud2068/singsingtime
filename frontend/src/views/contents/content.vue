<template>
  <div justify="center" align="center" style="margin-top: 40px">
    <v-row>
      <v-col lg="7">
        <h3 align="start" style="margin-left: 80px">
          <!-- <b>{{title}}</b> -->
          <b style="margin-left: 0px;">제목 : {{ state.title }}</b>
        </h3>
        <video
          :src="state.video"
          width="700"
          height="500"
          autoplay
          style="margin-bottom:20px"
        ></video>
        
        <v-row justify="space-between">
          <p style="margin-left: 80px">{{ state.viewCnt }}View</p>
          <p style="margin-right: 80px">
            {{ state.likeCnt }}
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
import { onMounted, reactive} from "vue";
import axios from "axios";
import { useStore } from "vuex";

export default {
  name: "ContentsBox",
  setup(){
    const store = useStore();
    const state = reactive({
      heartcheck: false,
      keyId: "",
      title : "",
      description:"",
      usernickname:"",
      video:"",
      likeCnt:"",
      viewCnt:"",
    });
    const activeBtn = () => {
      state.heartcheck = !state.heartcheck;
    };
    // const info = () =>{
    //   axios({
    //     method: "get",
    //     url: "http://localhost:8080//api/v1/videos/{videoId}",
    //     params : this.id
    //   }).then((res) => {
    //     this.title = res.data.title
    //     this.user = res.data.user
    //     this.description = res.data.description
    //   }).catch((err) => {
    //     if (err.message.indexOf('Network Error') > -1) {
    //       alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
    //     }
    //   })

    onMounted(() => {
      const getid = localStorage.getItem('page')
      

      axios({
        method: "get",
        url: import.meta.env.VITE_APP_URL+`/api/v1/videos/${getid}`
        // url: import.meta.env.VITE_APP_URL + `api/v1/videos/${state.keyId}`,
      })
        .then((res) => {
          console.log(res); 
          state.title = res.data.title
          state.description = res.data.description
          state.usernickname = res.data.user.nickname
          state.video=res.data.url
          state.likeCnt=res.data.likeCnt
          state.viewCnt=res.data.viewCnt
        })
        .catch((err) => {
          console.log(err);
        });
    });
    return {
      state,
      store,
      activeBtn,
      // changes,
      // getid,
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
