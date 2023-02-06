<template>
  <Modal>
    <div class="video-position">
      <Search @input-search="onInputSearch" />
      <!-- 비디오는 선택된 비디오 -->
      <!-- 검색어가 있어야 리스트 뜸 -->
      <SongList v-if="show" :videos="videos" :session="session" />
    </div>
    <div class="buttons-container" style="float: right">
      <button class="btn confirm" @click="confirm">확인</button>
      <button class="btn cancel" @click="cancel">취소</button>
    </div>
  </Modal>
</template>

<script>
import Search from "./Search.vue";
import SongList from "./SongList.vue";
import { ref, reactive } from "vue";
import Modal from "./Modal.vue";

const API_KEY = "AIzaSyBGF5ljIuwHbPn27YSImtkkgk8KooR8q7I";
// const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY
export default {
  name: "SongModal",
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
    Modal,
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
      this.selectedVideo = video;
    },
  },
  setup() {
    // 자식 컴포넌트를 핸들링하기 위한 ref
    const baseModal = ref(null);
    // Promise 객체를 핸들링하기 위한 ref
    const resolvePromise = ref(null);
    const state = reactive({
      conferencename: "",
    });
    const show = () => {
      // baseModal을 직접 컨트롤합니다.
      baseModal.value.open();
      // Promise 객체를 사용하여, 현재 모달에서 확인 / 취소의
      // 응답이 돌아가기 전까지 작업을 기다리게 할 수 있습니다.
      return new Promise((resolve) => {
        // resolve 함수를 담아 외부에서 사용합니다.
        resolvePromise.value = resolve;
      });
    };

    const confirm = () => {
      baseModal.value.close();
      resolvePromise.value(true);
      const url = "#/conferences/" + state.conferencename + "/";
      window.open(url);
      state.conferencename = "";
    };

    const cancel = () => {
      baseModal.value.close();
      resolvePromise.value(false);
      state.conferencename = "";
    };
    return { state, baseModal, show, confirm, cancel };
  },
};
</script>

<style>
.video-position {
  margin-top: 120px;
  border: 1px solid white;
  width: fit-content;
  display: flex;
  border: 1px solid white;
}
</style>
