<template>
  <div id="main-container" class="container">
    <div style="color:white; display: flex; justify-content: space-between;">
      <div style="margin-left: 50%;">
        <h1>{{ this.mySessionId }}</h1>
      </div>
      <div>
        <div>{{playroom.title}}</div>
        <div>{{playroom.champion}}</div>

        <input
        class="btn btn-large btn-danger"
        type="button"
        @click="isShow"
        value="노래"
        />
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="나가기"
        />
      </div>
    </div>
    <Modal ref="baseModal"  v-if="!this.selectedVideo">
    <div style="text-align: center; ">
      <v-card
    class="mx-auto black"
    max-width="500"
  >
    <v-list dark>
        <h3 type="button" @click="afterselect()" style="margin: 10px; margin-right: 20px; text-align: right;">X</h3>
        <h1>노래목록</h1>
        <hr>
      <v-list-item-group v-model="model">
        <v-list-item
          v-for="(item) in items"
          @click="onSelectVideo(item), afterselect()"
        >
            <v-list-item-title v-text="item.icon"></v-list-item-title>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
    </div>
  </Modal>
    <div class="participation">
      <div id="video-container" class="bigbox">
        <!-- <div id="video-container" class=""> -->
        <!-- 나 -->
        <div>
          <div ></div>
          <div></div>
        </div>
        <div class="smallboxl">
          <user-video
            :stream-manager="publisher"
            @click.native="updateMainVideoStreamManager(publisher)"
          />
        </div>
        <div class="musicbox">
          <SongDetail v-if="!this.selectedVideo" :session="session" />
        </div>
        <!-- 나 빼고 나머지 참가자들 -->
      <div class="smallboxr">
        <user-video
          v-for="sub in subscribers"
          :key="sub.stream.connection.connectionId"
          :stream-manager="sub"
          @click.native="updateMainVideoStreamManager(sub)"
        />
      </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./components/UserVideo.vue";
import { ref } from "vue";
import { mapGetters } from "vuex";
import Modal from "./components/Modal.vue";
import SongDetail from "./components/SongDetail.vue";


axios.defaults.headers.post["Content-Type"] = "application/json";
const API_KEY = "AIzaSyBGF5ljIuwHbPn27YSImtkkgk8KooR8q7I";


export default {
  name: "App",

  components: {
    UserVideo,
    Modal,
    SongDetail,
  },
props:{
  id:"",
},
  data() {
    return {
      items: [
        {
          icon: '별이될께-디셈버',
          text: 'JwH89XpCrnI',
        },
        {
          icon: '출발-김동률',
          text: 'N1B3jJzmdmM',
        },
        {
          icon: '어제보다 오늘 더-김종국',
          text: 'ICwHBPum4QY',
        },
        {
          icon: 'heartshaker-twice',
          text: 'LPwUWNfMXJM',
        },
        {
          icon: '홍연-안예은',
          text: 'dxQm3HKwaYA',
        },
        {
          icon: '그대를 사랑하는 10가지 이유-이석훈',
          text: 'VSs38DHeRPc',
        },
        {
          icon: '라라라-sg워너비',
          text: 'IExTwnAf1Zo',
        },
        {
          icon: '내사람-sg워너비',
          text: 'hjIP6Tue8aI',
        },
      ],
      inputValue: "",
      videos: [],
      selectedVideo: "", // 선택한 비디오를 SongDetail.vue 로 보내고, 출력
      selectvideo: "",
      showValue: false,
      dialog: false,
      // OpenVidu objects
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      // Join form
      mySessionId: this.$route.params.Id,
      myUserName: localStorage.name,
      token: null, // jwt토큰, 오픈비두 세션 접속용 getToken 파라미터랑 다름, this.token으로 구분
      sessionInfo: null,
    };
  },
  computed: {
    movieVideo() {
      return `https://www.youtube.com/embed/${this.$store.state.movieVideo.key}?autoplay=1`;
    },
    sessionId() {
      return this.$route.params.Id;
    },
    ...mapGetters(["video"]),
  },
  created() {
    
    // console.log(playroom+"그냥");
    this.joinSession();
    this.getname();
    this.getSessionInfo();
  },
  methods: {
    onSelectVideo: function (video) {
      this.session
        .signal({
          data: JSON.stringify(video.text),
          type: "song",
        })
        .then(() => {
          console.log("노래방 시그널 전송");
          // console.log(video.id.videoId)
        })
        .catch((err) => {
          console.log(err);
          console.log("전송 에러");
        });
      console.log(this.$store.state.video);
    },
    onInputSearch: function (inputText) {
      console.log("데이터가 Search로부터 올라왔다.");

      this.showValue = true;
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
    handleClick(){
      this.$refs.Youtube[0].show()
    },
    getname() {
      this.jwt = localStorage.getItem("jwt");
      console.log(this.name);
    },
    joinSession() {
      // --- 1) Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- 2) Init a session ---
      this.session = this.OV.initSession();

      // --- 3) Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- 4) Connect to the session with a valid user token ---

      // Get a token from the OpenVidu deployment
      this.getToken(this.mySessionId).then((token) => {
        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.session
          .connect(token, { clientData: this.myUserName })
          .then(() => {
            // --- 5) Get your own camera stream with the desired properties ---

            // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
            // element: we will manage it on our own) and with the desired properties
            let publisher = this.OV.initPublisher(undefined,{
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: false, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            // Set the main video in the page to display our webcam and store our Publisher
            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- 6) Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
      window.close();
      if (this.session) this.session.disconnect();

      // Empty all properties...
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      // Remove beforeunload listener
      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },
    getSessionInfo(){
      axios({
        method:"get",
        url: import.meta.env.VITE_APP_URL+ `api/v1/playrooms/${this.mySessionId}`,
      })
      .then((res)=>{
        console.log(res.data);
        this.sessionInfo= res.data;
      })
      .catch((err) => {
            alert(err);
          });
    },
    /**
     * --------------------------------------------
     * GETTING A TOKEN FROM YOUR APPLICATION SERVER
     * --------------------------------------------
     * The methods below request the creation of a Session and a Token to
     * your application server. This keeps your OpenVidu deployment secure.
     *
     * In this sample code, there is no user control at all. Anybody could
     * access your application server endpoints! In a real production
     * environment, your application server must identify the user to allow
     * access to the endpoints.
     *
     * Visit https://docs.openvidu.io/en/stable/application-server to learn
     * more about the integration of OpenVidu in your application server.
     */
    async getToken(mySessionId) {
      const sessionId = await this.createSession(mySessionId);
      console.log("디버깅1")
      console.log(sessionId)
      return await this.createToken(sessionId);
    },

    async createSession(sessionId) {
      this.token = localStorage.getItem("jwt");
      const response = await axios.post(
        import.meta.env.VITE_APP_URL + "/api/v1/openvidu/sessions",
        { customSessionId: sessionId },
        {
          headers: { 
            "Content-Type": "application/json",
            Authorization: `Bearer ${this.token}`,
          },
        }
      );
      return response.data; // The sessionId
    },

    async createToken(sessionId) {
      const response = await axios.post(
        import.meta.env.VITE_APP_URL + "/api/v1/openvidu/sessions/" + sessionId + "/connections",
        {},
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data; // The token
    },
  },
  setup(){
    
    // 자식 컴포넌트를 핸들링하기 위한 ref
    const baseModal = ref(null);
    // Promise 객체를 핸들링하기 위한 ref
    const resolvePromise = ref(null);
    const isShow = () => {
      // baseModal을 직접 컨트롤합니다.
      baseModal.value.open();
      // Promise 객체를 사용하여, 현재 모달에서 확인 / 취소의
      // 응답이 돌아가기 전까지 작업을 기다리게 할 수 있습니다.
      return new Promise((resolve) => {
        // resolve 함수를 담아 외부에서 사용합니다.
        resolvePromise.value = resolve;
      });
    };
    const afterselect=()=>{
      baseModal.value.close()
    }
    const confirm = () => {
      baseModal.value.close();
      resolvePromise.value(true);
      const url = "#/conferences/" + this.store.state.conferencename + "/";
      window.open(url);
      this.store.state.conferencename = "";
    };

    const cancel = () => {
      baseModal.value.close();
      resolvePromise.value(false);
      this.store.state.conferencename = "";
    };
    // async-await을 사용하여, Modal로부터 응답을 기다리게 된다.
    return { baseModal, isShow, confirm, cancel, afterselect };
  },
};
</script>
<style>
.bigbox{
  /* position: absolute; */
  display: flex;
  margin: auto;
  padding: 0;
}
/* .smallboxl{
  top: 0%;
  left:40%;
} */
.musicbox{
  top: 100%;
  left: -5%;
  width: 700px;
}
/* .smallboxr{
  bottom: -30%;
  left: 70%;
} */
.container {
  height: 100%;
  width: 100vw;
  background-color: black;
  color: white;
  padding: 20px;
}
.play {
  display: flex;
  width: 50%;
  margin: auto;
  top: 15%;
  left: 26%;
  /* border: 1px solid red; */
  position: absolute;
}
.user_video {
  /* border: 1px solid white; */
  margin: 20px;
}
.music {
  position: relative;
  top: 0%;
  left: 0%;
  border: 1px solid white;
}
.exit {
  float: right;
}
/* #video-container p {
  font-family: "IM_Hyemin-Bold";
  display: inline-block;
  background: #f8f8f8;
  padding-left: 5px;
  padding-right: 5px;
  color: #3c90c9;
  font-weight: bold;
  border-radius: 2px;
  text-align: center;
} */
#video-container video + div {
  text-align: center;
  /* line-height: 75px; */
  float: left;
  width: 28%;
  position: relative;
  margin-left: -1.5%;
  display: flex;
  justify-content: space-around;
}
#video-container video {
  /* position: relative; */
  /* float: left; */
  margin-left: 0.6%;
  border: 3px solid;
  border-color: rgb(255, 255, 255);
  /* cursor: pointer; */
  /* margin:  2%;  */
  /* margin-left: 5%; */
  display: flex;
  align-items: center;
  justify-content: space-around;
}
.participation {
  /* width: 95vw; */
  /* height: 22vh; */
  /* text-align: justify; */
  /* border: 3px solid #ffa500; */
  display: flex;
  flex-direction: row;
  /* align-items: center;
   justify-content: space-around;   */
}
video {
  padding-top: 1.8vh;
  /* 맨 아래에 나오는 카메라화면 */
  /* width: ; */
  width: 90%;
  /* height: 48vh; */
  height: auto;
  position: relative;
}
#main-video p {
  /* position: absolute; */
  display: inline-block;
  background: #f8f8f8;
  padding-left: 5px;
  padding-right: 5px;
  font-size: 22px;
  color: #777777;
  font-weight: bold;
  border-radius: 5px;
}
.exit {
  float: right;
}
</style>
