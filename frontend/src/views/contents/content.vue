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
          ref="videoPlayer"
          width="700"
          height="500"
          style="margin-bottom:20px"
          @click="play()"

        ></video>
        <!-- <button @click="play">play</button> -->

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
      <CommentsBox :replys="state.ordering"/>
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
import { onMounted, reactive, computed} from "vue";
import CommentsBox from "./components/comments.vue";
import axios from "axios";
import { useStore } from "vuex";
import _ from "lodash"

export default {
  name: "ContentsBox",

  components: {
    CommentsBox,
  },
  data() {
    return {
      status:false,
    }
  },
  methods: {
    play() {
      this.status = !this.status
      if (this.status) {
        this.$refs.videoPlayer.play();
      }
      else{
        this.$refs.videoPlayer.pause();
      }
    },
  },
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
      replys:[],
      ordering: [],
    });
    const activeBtn = () => {
      state.heartcheck = !state.heartcheck;
    };

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
          state.replys=res.data.replys
        })
        .catch((err) => {
          console.log(err);
        });

        state.ordering = computed(() => _.orderBy(state.replys,'createdDate','desc'))
    
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
.active {
  color: red;
}
</style>
