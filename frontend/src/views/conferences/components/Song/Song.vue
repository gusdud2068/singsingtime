<template>
  <!-- <p :color="white">sadfsdf{{ state.play }}</p> -->
  <div
    v-if="!this.selectedVideo"
    style="position: absolute; border: 1px solid white; width: 95%"
  >
    <Search @input-search="onInputSearch" />
    <div class="video-position">
      <!-- 비디오는 선택된 비디오 -->
      <!-- 검색어가 있어야 리스트 뜸 -->
      <SongList v-if="show" :videos="videos" :session="session" />
    </div>
  </div>
  <div v-if="!this.selectedVideo">
    <SongDetail :session="session" />
  </div>
</template>

<script>
import Search from "./Search.vue";
import SongList from "./SongList.vue";
import SongDetail from "./SongDetail.vue";
import { reactive } from "vue";
import { useStore } from "vuex";

const API_KEY = "AIzaSyBGF5ljIuwHbPn27YSImtkkgk8KooR8q7I";
// const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY
export default {
  name: "Song",
  data: function () {
    return {
      inputValue: "",
      videos: [],
      selectedVideo: "", // 선택한 비디오를 SongDetail.vue 로 보내고, 출력
      selectvideo: "",
      show: false,
    };
  },
  props: {
    session: Object,
  },
  // mounted: function () {
  //   this.sesson(this.session);
  // },
  components: {
    Search,
    SongList,
    SongDetail,
  },
  created() {
    console.log(this.play);
    console.log("이건언제됨");
    this.mainStreamManager = this.publisher;
    console.log("song에서 this.videoId : " + this.videoId);
  },
  methods: {
    onInputSearch: function (inputText) {
      console.log("데이터가 Search로부터 올라왔다.");

      this.show = true;
      console.log(inputText);
      this.inputValue = inputText;
      // part(필수), key(필수), q(검색어), type(video만) 매개 변수를 요청에 넣어서 보냄
      const params = {
        key: API_KEY,
        part: "snippet",
        type: "video",
        q: "뮤즈온라인" + this.inputValue + "mr",
      };

      fetch(
        `https://www.googleapis.com/youtube/v3/search?key=AIzaSyBGF5ljIuwHbPn27YSImtkkgk8KooR8q7I&part=snippet&type=video&q=${params.q}`
      )
        .then((res) => {
          console.log(this.videos);
          return res.json();
        })
        .then((data) => {
          console.log("두번째 then");
          console.log(data.items);
          this.videos = data.items;
          // console.log('두번째')
          console.log(this.videos);
        })
        .catch((err) => {
          console.log(err);
        });
      console.log(this.videos);
    },
    onVideoSelect: function (video) {
      this.selectVideo = video;
    },
  },
  setup() {
    const store = useStore();
    const state = reactive({
      play: store.state.video,
    });
    return { state };
  },
};
</script>

<style>
.video-position {
  margin-top: 120px;
  border: 1px solid white;
  width: fit-content;
  display: flex;
  
}
</style>
