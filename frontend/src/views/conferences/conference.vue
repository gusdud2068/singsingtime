<template>
  <div id="main-container" class="container">
    <input
      class="btn btn-large btn-danger exit"
      type="button"
      id="buttonLeaveSession"
      @click="leaveSession"
      value="나가기"
    />
    <input
      class="btn btn-large btn-danger exit"
      type="button"
      @click="handleClick"
      value="유튜브"
    />
    <YouTube ref="modal" :content="modalContent" />
    <div id="session" v-if="jwt">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }} 번방</h1>
      </div>
      <div v-if="sharing === true" class="buttomMenu">
          <button
            class="btn btn-large btn-default footerBtn"
            type="button"
            id="buttonLeaveSession"
            @click="startScreenSharing()"
          >
            <b-icon
              icon="file-arrow-up"
              class="buttomMenuIcon"
              aria-hidden="true"
            ></b-icon>
            <span class="footerBtnText">화면공유</span>
          </button>
        </div>

        <div v-else class="buttomMenu">
          <button
            class="btn btn-large btn-default footerBtn"
            type="button"
            id="buttonLeaveSession"
            @click="leaveSessionForScreenSharing()"
          >
            <b-icon
              icon="file-arrow-down"
              class="buttomMenuIcon"
              aria-hidden="true"
            ></b-icon>
            <span class="footerBtnText">공유중지</span>
          </button>
          <!-- 나가기 버튼 -->
        </div>
      <!-- <video
        src="https://sstvideo.s3.ap-northeast-2.amazonaws.com/images/test.mp4"
        width="800"
        height="500"
        controls
        class="music"
      ></video> -->

      <div v-if="video.id" class="embed-responsive embed-responsive-16by9" style="border: 1px solid white; height: 500px; width: 800px; margin: auto;">
    <iframe
      class="embed-responsive-item"
      frameborder="0"
      allowfullscreen="1"
      allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
      :src="`https://www.youtube.com/embed/${video.id.videoId}`"
      style="width: 800px; height: 500px; "
    ></iframe>
  </div>
      <div class="play" style="display: flex">
        <div id="main-video" class="user_video">
          <user-video :stream-manager="mainStreamManager" />
        </div>
        <div id="video-container">
          <div class="user_video" style="margin-left: 270px">
            <user-video
              :stream-manager="publisher"
              @click="updateMainVideoStreamManager(publisher)"
            />
          </div>
          <div class="user_video">
            <user-video
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              :stream-manager="sub"
              @click="updateMainVideoStreamManager(sub)"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./components/UserVideo.vue";
import YouTube from "../youtube/youtube.vue";
import {ref} from "vue"
import { mapGetters } from "vuex";

axios.defaults.headers.post["Content-Type"] = "application/json";

const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === "production" ? "" : "http://localhost:8080/";

export default {
  name: "App",

  components: {
    UserVideo,
    YouTube,
  },

  data() {
    return {
      // OpenVidu objects
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      // Join form
      mySessionId: this.$route.params.Id,
      myUserName: localStorage.name,
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
    this.joinSession();
    this.getname();
  },
  methods: {
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
            let publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
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
      if (this.session) this.session.disconnect();

      // Empty all properties...
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      // Remove beforeunload listener
      window.removeEventListener("beforeunload", this.leaveSession);
      window.close();
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
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
      return await this.createToken(sessionId);
    },

    async createSession(sessionId) {
      const response = await axios.post(
        APPLICATION_SERVER_URL + "api/sessions",
        { customSessionId: sessionId },
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data; // The sessionId
    },

    async createToken(sessionId) {
      const response = await axios.post(
        APPLICATION_SERVER_URL + "api/sessions/" + sessionId + "/connections",
        {},
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data; // The token
    },
  },
  setup(){
    const modal = ref(null);
    const modalContent = ref([
      "확인/취소를 누르고",
      "배경에 결과가 출력되는 것을",
      "확인해보세요",
    ]);
    const result = ref("");

    // async-await을 사용하여, Modal로부터 응답을 기다리게 된다.
    const handleClick = async () => {
      const ok = await modal.value.show();
      if (ok) {
        result.value = "확인을 눌렀군요!";
      } else {
        result.value = "취소를 눌렀네요?";
      }
    };
    return {
      modal,
      modalContent,
      result,
      handleClick,
    }
  },
};
</script>
<style>
.container {
  height: 100%;
  width: 100vw;
  background-color: black;
  color: white;
  padding: 20px;
}
#session {
}
.play {
  display: flex;
  width: 50%;
  margin: auto;
  top: 15%;
  left: 26%;
  /* border: 1px solid red; */
  position: absolute;
  justify: space-between;
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
</style>
