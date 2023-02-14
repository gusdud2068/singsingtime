<template>
  <div
    id="main-container"
    class="container"
    :class="
      ({ musicOn: this.selectedVideo.length>0}, { win: this.finish == true })
    "
  >
  <a ref="down" style="display: none;"></a>

    <div
      style="
        color: white;
        display: flex;
        justify-content: space-between;
        padding-bottom: 50px;
      "
    >
      <div
        style="margin-left: 40%"
        v-if="this.readyVideo && !this.selectedVideo"
      >
        <h2>
          지금

          <img
            src="../../assets/images/champion.gif"
            style="width: 50px"
          /><span style="color: red"> 챔피언</span
          ><img
            src="../../assets/images/champion.gif"
            style="width: 50px"
          />에게 도전하세요!
        </h2>
      </div>

      <div>
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="나가기"
          style="margin-right: 20px"
        />
      </div>
    </div>

    <Modal ref="championSongListModal">
      <div style="text-align: center">
        <v-card class="mx-auto black" max-width="500">
          <v-list dark>
            <h3
              type="button"
              @click="closeChampionSongListModal()"
              style="margin: 10px; margin-right: 20px; text-align: right"
            >
              X
            </h3>

            <h2>챔피언 {{ this.champion }}님의</h2>
            <h2>플레이리스트</h2>

            <hr />
            <v-list-item-group v-model="model">
              <v-list-item
                v-for="championSong in championSongList"
                @click="
                  onSelectVideo(championSong), closeChampionSongListModal()
                "
                :key="championSong.title"
              >
                <v-list-item-title
                  >{{ championSong.title }}

                  {{ championSong.singer }}</v-list-item-title
                >
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </div>
    </Modal>

    <Modal ref="waitingQueueModal">
      <div style="text-align: center">
        <v-card class="mx-auto black" max-width="500">
          <v-list dark>
            <h3
              type="button"
              @click="closeWaitingQueueModal()"
              style="margin: 10px; margin-right: 20px; text-align: right"
            >
              X
            </h3>
            <h2>현재 대기중인 도전자 목록</h2>
            <hr />

            <v-list-item-group v-model="model">
              <v-list-item-title>
                (현재 도전자):{{ sessionInfo.challenger }}
              </v-list-item-title>
              <v-list-item
                v-for="(waitingUser, i) in sessionInfo.waitingQueue"
                :key="waitingUser"
              >
                <v-list-item-title>
                  {{ i + 1 }}번 - {{ waitingUser }}</v-list-item-title
                >
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

        <!-- <v-col> -->

        <div class="smallboxl" display="flex">
          <!--스몰박스 left, 노래화면 왼쪽. 여기에 스트림매니저로 챔피언을 넘겨줘야함-->

          <v-card style="padding: 5px; font-size: 20px" color="primary"
            ><img
              src="../../assets/images/sparkling.gif"
              style="width: 20px" /><span style="color: white">챔피언</span>

            <img src="../../assets/images/sparkling.gif" style="width: 20px"
          /></v-card>

          <user-video
            :stream-manager="championStreamManager"
            @click.native="updateMainVideoStreamManager(championStreamManager)"
          />
          <VoteChampion v-if="this.voteBtnShow" @voteChampion="voteChampion" />
        </div>

        <!-- </v-col> -->

        <div class="musicbox">
          <SongDetail
            v-if="this.selectedVideo && !this.finish"
            :session="session"
            @endGame="endGame"
          />

          <v-row
            v-if="this.finish"
            justify="center"
            margin-top="0px"
            padding-top="0px;"
          >
            <v-col>
              <h1 style="color: orange">
                {{ this.winner }}
                <span style="color: white"> 의 승리입니다!!</span>
              </h1>
              <img src="../../assets/images/pang.gif" style="width: 300px"
            /></v-col>
          </v-row>
          <ReadyDetail
            v-if="this.readyVideo && !this.selectedVideo"
            :session="session"
          />
        </div>

        <!--스몰박스 right, 노래화면 오른쪽, 여기에 챌린져가 들어가야 함-->

        <!-- <div class="smallboxr"> 
    
                <user-video
    
                :stream-manager="challenger" 
    
                @click.native="updateMainVideoStreamManager(challenger)"
    
              />
    
              />
    
            </div> -->

        <!--비디오 위치 테스트용으로 퍼블리셔 넣어놓음 -->

        <div class="smallboxl" display="flex">
          <!--스몰박스 left, 노래화면 왼쪽. 여기에 스트림매니저로 챔피언을 넘겨줘야함-->

          <v-card style="padding: 5px; font-size: 20px" color="green"
            ><img src="../../assets/images/sparkling.gif" style="width: 20px" />

            <span style="color: white">도전자 </span
            ><img src="../../assets/images/sparkling.gif" style="width: 20px"
          /></v-card>
          <user-video
            :stream-manager="challengerStreamManager"
            @click.native="
              updateMainVideoStreamManager(challengerStreamManager)
            "
          />
          <VoteChallenger
            v-if="this.voteBtnShow"
            @voteChallenger="voteChallenger"
          />
        </div>

        <!-- </v-col> -->
      </div>
    </div>

    <input
      class="btn btn-large btn-danger"
      type="button"
      @click="challenge(myUserId)"
      value="대결 신청"
      style="margin-top: 20px; margin-bottom: 20px; margin-right: 20px"
    />

    <input
      class="btn btn-large btn-warning"
      type="button"
      @click="showChampionSongList"
      value="도전 가능곡"
      style="margin-right: 20px"
    />

    <input
      class="btn btn-large btn-success"
      type="button"
      @click="showWaitingQueue"
      value="도전자 목록"
      style="margin-top: 20px; margin-bottom: 20px; margin-right: 20px"
    />


    <input
      class="btn btn-large btn-info"
      type="button"
      @click="startRecord"
      value="녹화 시작"
      style="margin-top: 20px; margin-bottom: 20px "
    />
    <!-- 관중들 들어갈 자리 -->
    <v-card class="audiences" color="#3232FF" style="width: 200px"
      ><h3 style="color: white">관람객</h3></v-card
    >

    <div class="smallboxb">
      <!--스몰박스 right, 노래화면 오른쪽-->
        <div>
          <!-- <div style="position: relative; margin-left: 50px; margin-bottom: 500px"> -->
        <v-btn @click="imageConvert" style="position:absolute;width:20px;z-index: 3;margin-top: 220px; margin-left: 250px;"><span style="color:black;font-style:bold;">전환</span></v-btn>
        <!-- <user-video
        :stream-manager="publisher"
        @click.native="updateMainVideoStreamManager(publisher)"
        style="z-index:1;position:absolute"
        /> -->
        <user-video
        :stream-manager="publisher"
        @click.native="updateMainVideoStreamManager(publisher)"
        />
        <!-- <img
          :src="profileUrl"
          alt=""
          v-show="state.there"
          style="width:300px;height:250px;position:relative;border:1px solid white ;
          z-index: 2;"
        /> -->
        

          </div>
   

        <!-- <v-btn @click="imageGet" style="z-index: 3">눌러봐</v-btn> -->
      <!-- </div> -->
     

      <user-video
        v-for="sub in subscribers"
        :key="sub.stream.connection.connectionId"
        :stream-manager="sub"
        @click.native="updateMainVideoStreamManager(sub)"
      />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./components/UserVideo.vue";
import { ref, computed, toRaw } from "vue";
import { mapGetters, useStore } from "vuex";
import Modal from "./components/Modal.vue";
import SongDetail from "./components/SongDetail.vue";
import ReadyDetail from "./components/ReadyDetail.vue";
import VoteChallenger from "./components/VoteChallenger.vue";
import VoteChampion from "./components/VoteChampion.vue";
import { reactive } from "vue";

axios.defaults.headers.post["Content-Type"] = "application/json";
const API_KEY = "AIzaSyBGF5ljIuwHbPn27YSImtkkgk8KooR8q7I";

export default {
  name: "App",
  components: {
    UserVideo,
    Modal,
    SongDetail,
    ReadyDetail,
    VoteChallenger,
    VoteChampion,
  },
  props: {
    id: "",
  },
  data() {
    return {
      sessionInfo: {
        waitingQueue: [],
        challenger: "",
        likeChampion: 0,
        likeChallenger: 0,
      },
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
      myUserName: localStorage.getItem("nickname"),
      myUserId: localStorage.getItem("userId"),
      token: null, // jwt토큰, 오픈비두 세션 접속용 getToken 파라미터랑 다름, this.token으로 구분
      champion: "",
      championSongList: [],
      readyVideo: false, // 미러볼 비디오 화면을 띄울지 결정할 변수
      ready: false,
      voteBtnShow: false,
      test: false,
      finish: false,
      // likeChampion: 0,
      // likeChallenger: 0,
      winner: "",
      // challenger: "",
      // waitingQueue: [],
      members: [],
      championStreamManager: undefined,
      challengerStreamManager: undefined,
    };
  },
  setup() {
    // 자식 컴포넌트를 핸들링하기 위한 ref
    const store = useStore();
    const championSongListModal = ref(null);
    const waitingQueueModal = ref(null);
    // Promise 객체를 핸들링하기 위한 ref
    const resolvePromise = ref(null);

    const showChampionSongList = () => {
      // showChampionSongList을 직접 컨트롤합니다.
      championSongListModal.value.open();
      return new Promise((resolve) => {
        // resolve 함수를 담아 외부에서 사용합니다.
        resolvePromise.value = resolve;
      });
      // Promise 객체를 사용하여, 현재 모달에서 확인 / 취소의
      // 응답이 돌아가기 전까지 작업을 기다리게 할 수 있습니다.
    };

    // 유저 정보, 다이얼로그에 사용할 state
    const state = reactive({
      there: true,
    });

    const showWaitingQueue = () => {
      waitingQueueModal.value.open();
      return new Promise((resolve) => {
        resolvePromise.value = resolve;
      });
    };

    const closeChampionSongListModal = () => {
      championSongListModal.value.close();
    };

    const closeWaitingQueueModal = () => {
      waitingQueueModal.value.close();
    };
    const confirm = () => {
      championSongListModal.value.close();
      resolvePromise.value(true);
      const url = "#/conferences/" + this.store.state.conferencename + "/";
      window.open(url); // 새로운 창에서 플레이룸 오픈
      this.store.state.conferencename = "";
    };

    const cancel = () => {
      championSongListModal.value.close();
      resolvePromise.value(false);
      this.store.state.conferencename = "";
    };
    // async-await을 사용하여, Modal로부터 응답을 기다리게 된다.
    return {
      championSongListModal,
      waitingQueueModal,
      showChampionSongList,
      showWaitingQueue,
      confirm,
      cancel,
      closeChampionSongListModal,
      closeWaitingQueueModal,
      store,
      state,
    };
  },
  computed: {
    movieVideo() {
      return `https://www.youtube.com/embed/${this.$store.state.movieVideo.key}?autoplay=1`;
    },
    sessionId() {
      return this.$route.params.Id;
    },

    ...mapGetters({
      userInfo: "accountStore/getAll",
    }),

    ...mapGetters(["video"]),
  },
  created() {
    // console.log(playroom+"그냥");
    this.joinSession();
    this.getname();
    console.log("====================================================");
    console.log(this.subscribers);
    // this.getReadyVideo();
  },
  mounted() {
    // this.getReadyVideo();
    // this.ready = !this.ready;
    this.test = !this.test;
  },
  updated() {
    this.getReadyVideo();
  },
  methods: {
    leavePlayroom() {
      axios({
        method: "delete",
        url:
          import.meta.env.VITE_APP_URL +
          `/api/v1/playrooms/${this.mySessionId}`,
      })
        .then((res) => {
          console.log(res.data);
        })
        .catch((err) => {
          alert(err);
        });
    },

       startRecord(){
        navigator.mediaDevices.getDisplayMedia(
        {
          audio:true,
          video: {
            mediaSource:"screen",
          }
        }
      ).then((stream)=>{
        const recorder = new MediaRecorder(stream);
        recorder.start();
        const buffer = [];
        recorder.addEventListener('dataavailable',(event)=>{
          buffer.push(event.data);
        })
        recorder.addEventListener('stop',()=>{
          const blob = new Blob(buffer,{
          type:'video/mp4'
        });
        this.$refs.down.href= URL.createObjectURL(blob);
        console.log("녹화테스트");
        console.log(this.$refs.down);
        this.$refs.down.download="recording.mp4"
        this.$refs.down.click();
        })
      })
      },
    // async imageGet() {
    async imageConvert() {
      this.token = localStorage.getItem("jwt");
      console.log("hhhhhhhhhhhh");
      console.log(this.token);
      this.publisher.stream.applyFilter("GStreamerFilter", {"command": "gdkpixbufoverlay location=/images/img.png offset-x=10 offset-y=10 overlay-height=200 overlay-width=200"})
      axios({
        method: "get",
        url: import.meta.env.VITE_APP_URL + `/api/v1/users/my-page/profile`,
        headers: {
          Authorization: `Bearer ${this.token}`,
        },
      })
        .then((res) => {
          console.log(res);
          this.profileUrl = res.data;
          console.log("4444444444444444444");
          console.log(this.profileUrl);
          console.log(this.state.there);
          this.state.there = !this.state.there;
          console.log(this.state.there);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getReadyVideo: function () {
      this.session
        .signal({
          type: "ready",
          to: [],
        })
        .then(() => {
          this.readyVideo = true;
          console.log("레디화면 시그널 전송");
        })
        .catch((err) => {
          console.log(
            "레디화면 전송 실패 ㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜ"
          );
          console.log(err);
        });
    },
    onSelectVideo: function (championSong) {
      this.readyVideo = false;
      this.session.signal({
        data: this.readyVideo,
        type: "start_readyVideo",
      });
      this.selectedVideo = true;
      this.session.signal({
        data: this.selectedVideo,
        type: "start_selectedVideo",
      });
      this.voteBtnShow = true;
      this.session.signal({
        data: this.voteBtnShow,
        type: "start_voteBtnShow",
      });
      this.finish = false;
      this.session.signal({
        data: this.finish,
        type: "start_finish",
      });
      this.session
        .signal({
          data: JSON.stringify(championSong.title),
          type: "songTitle",
        })
        // .signal({
        //   data : championSong.id,
        //   type: "songId",
        // })
        .then(() => {
          console.log("노래방 시그널 전송");
          // console.log(video.id.videoId)
        })
        .catch((err) => {
          console.log(err);
          console.log("전송 에러");
        });
      this.session.signal({
        // data: championSong.part4 + 10,
        data: 5,
        type: "songTime",
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
    handleClick() {
      this.$refs.Youtube[0].show();
    },
    getname() {
      this.jwt = localStorage.getItem("jwt");
      console.log(
        "get name==================================================="
      );
      console.log(this.name);
    },
    joinSession() {
      // --- 1) Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- 2) Init a session ---
      this.session = this.OV.initSession();

      // --- 3) Specify the actions when events take place in the session ---
      // 중간에 다른 유저가 들어왔을 때 도전자 data받기

      this.session.on("signal:endalert", (event) => {
        this.finish = event.data;
      }),
        this.session.on("signal:start_readyVideo", (event) => {
          this.readyVideo = event.data;
        }),
        this.session.on("signal:start_selectedVideo", (event) => {
          this.selectedVideo = event.data;
        }),
        this.session.on("signal:start_voteBtnShow", (event) => {
          this.voteBtnShow = event.data;
        }),
        this.session.on("signal:start_finish", (event) => {
          this.finish = event.data;
        }),
        this.session.on("signal:selectChampion", (event) => {
          this.sessionInfo.likeChampion = event.data;
        }),
        this.session.on("signal:selectChallenger", (event) => {
          this.sessionInfo.likeChallenger = event.data;
        }),
        this.session.on("signal:showWinner", (event) => {
          this.winner = event.data;
        }),
        this.session.on("signal:enterNewUser", (event) => {
          this.sessionInfo.challenger = JSON.parse(event.data).challenger;
          // 방 멤버들 중 도전자 유저의 화면 생성
          console.log(this.members);
          for (let user of this.members) {
            if (
              JSON.parse(user.stream.connection.data).clientId ==
              this.sessionInfo.challenger
            ) {
              this.challengerStreamManager = user;
              console.log("467467");
            }
          }
        }),
        // 도전 이벤트 발생했을 때
        this.session.on("signal:challenge", (event) => {
          console.log(JSON.parse(event.data).challenger);
          this.sessionInfo.challenger = JSON.parse(event.data).challenger;
          // 방 멤버들 중 도전자 유저의 화면 생성
          console.log(this.members);
          for (let user of this.members) {
            if (
              JSON.parse(user.stream.connection.data).clientId ==
              this.sessionInfo.challenger
            ) {
              this.challengerStreamManager = user;
              console.log("467467");
            }
          }
        });
      this.session.on("signal:sessionInfo", (event) => {
        console.log("이벤트발생시켜줘");
        const originData = JSON.parse(event.data);
        if (originData.challenger == "") {
          this.session.signal({
            data: JSON.stringify(this.sessionInfo),
            type: "enterNewUser",
          });
          return;
        } else {
          this.sessionInfo = originData;
        }
        console.log(originData);
      });
      this.session.on("signal:addWaitingQueue", (event) => {
        console.log("대기열에 넣어줘! 500");
        const originData = JSON.parse(event.data);
        this.sessionInfo = originData;
      });
      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        console.log("스트림!");
        const data = stream.connection.data;
        console.log(subscriber);
        const originInfo = JSON.stringify(this.sessionInfo);
        console.log(originInfo);
        this.session
          .signal({
            data: originInfo,
            type: "sessionInfo",
          })
          .catch((err) => {
            console.log(err);
            console.log("전송 에러");
          });
        this.subscribers.push(subscriber);
        this.members.push(subscriber);
      });

      // On every Stream destroyed...
      // 흠 세션이 파괴될때? 그냥 모두를 한번에 나가게 하면되는거 아닌가??
      // 스트림매니저가 각각을 부르는 명칭 아닌가..? 한명만 있는건가?
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0); // 이 세션의 구독자중에서 스트림매니저의 위치 찾기
        if (index >= 0) {
          this.subscribers.splice(index, 1); // 찾은 스트림매니저의 위치를 통해 구독자에서 제거
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- 4) Connect to the session with a valid user token ---

      // Get a token from the OpenVidu deployment
      // 유저토큰이 아니구 세션토큰!
      this.getToken(this.mySessionId).then((token) => {
        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.session
          .connect(token, {
            clientId: this.myUserId,
            clientNickname: this.myUserName,
          })
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
            this.members.push(publisher);
            this.publisher = publisher;

            // --- 6) Publish your stream ---
            this.session.publish(this.publisher);
            this.getSessionInfo();
          })
          .catch((error) => {
            console.log(
              "There was an error connecting to the session:",
              error.code,
              error.message
            );
            alert(token);
            window.close();

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

      // 방 정원수 감소
      this.leavePlayroom();
      // Remove beforeunload listener
      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },
    // 챔피언 갱신 때 필요
    getSessionInfo() {
      axios({
        method: "get",
        url:
          import.meta.env.VITE_APP_URL +
          `/api/v1/playrooms/${this.mySessionId}`,
      })
        .then((res) => {
          // this.sessionInfo = res.data;
          this.champion = res.data.champion;
          this.getChampionList();

          // 방 멤버들 중 챔피언 유저의 화면 생성
          for (let user of this.members) {
            console.log(user.stream.connection.data);
            if (
              JSON.parse(user.stream.connection.data).clientId == this.champion
            ) {
              this.championStreamManager = user;
            }
          }
        })
        .catch((err) => {
          alert(err);
        });
    },
    // 챔피언 노래목록 불러오기
    getChampionList() {
      axios({
        method: "get",
        url:
          import.meta.env.VITE_APP_URL +
          `/api/v1/playrooms/playlist/${this.mySessionId}`,
      })
        .then((res) => {
          this.championSongList = res.data;
          console.log(res.data);
        })
        .catch((err) => {
          alert(err);
        });
    },
    endGame() {
      this.finish = true;
      this.session.signal({
        data: this.finish,
        type: "endalert",
      });
      // console.log("게임 종료!!!!!!!" + this.finish)
      if (this.sessionInfo.likeChampion >= this.sessionInfo.likeChallenger) {
        this.winner = "챔피언";
      } else {
        this.winner = "도전자";
      }
      this.session.signal({
        data: this.winner,
        type: "showWinner",
      });
      this.sessionInfo.likeChampion = 0;
      this.sessionInfo.likeChallenger = 0;
      this.champion =
        this.winner == "챔피언" ? this.champion : this.sessionInfo.challenger;
      axios({
        method: "put",
        url: import.meta.env.VITE_APP_URL + `/api/v1/playrooms/end-song`,
        data: {
          sessionId: this.mySessionId,
          championId: this.champion,
        },
      })
        .then((res) => {
          console.log("게임끝난 상태에서 data 받아오기!!!!");
          console.log(res);
          alert(
            this.champion + `님이 ${res.data.winCnt}연승을 달성하셨습니다!!!`
          );
          this.championSongList = res.data.championSongList;
          const next = this.dequeue();
          console.log("next출력");
          console.log(next);
          console.log(this.subscribers);
          if (next == "") {
            this.sessionInfo.challenger = "";
          } else {
            this.sessionInfo.challenger = next;
            this.session.signal({
              data: JSON.stringify(this.sessonInfo),
              type: "challenge",
            });
          }
          // this.sessonInfo.challenger=this.sessonInfo.challenger == undefined ? "" : this.sessonInfo.challenger;
          // this.getSessionInfo();
          // alert("다음도전자는:" + this.sessonInfo.challenger);
          // for (let user of this.members) {
          //     console.log(user.stream.connection.data);
          //     if (
          //         JSON.parse(user.stream.connection.data).clientId == this.champion
          //     ) {
          //         this.championStreamManager = user;
          //     }
          // }
        })
        .catch((err) => {
          alert(err);
        });
    },
    voteChampion() {
      this.voteBtnShow = false;
      this.sessionInfo.likeChampion += 1;
      this.session.signal({
        data: this.sessionInfo.likeChampion,
        type: "selectChampion",
      });
    },
    voteChallenger() {
      this.voteBtnShow = false;
      this.sessionInfo.likeChallenger += 1;
      this.session.signal({
        data: this.sessionInfo.likeChallenger,
        type: "selectChallenger",
      });
    },
    enqueue(data) {
      this.sessionInfo.waitingQueue.push(data);
    },
    challenge(myUserId) {
      if (this.sessionInfo.challenger == "") {
        this.sessionInfo.challenger = myUserId;
        // 방 멤버 중 대결신청 버튼 누른 유저의 화면 전파

        this.session.signal({
          data: JSON.stringify(this.sessionInfo),
          type: "challenge",
        });
        return;
      }
      if (this.sessionInfo.challenger == myUserId) {
        alert("이미 도전자입니다.");
        return;
      }
      for (let userId of this.sessionInfo.waitingQueue) {
        if (userId === myUserId) {
          alert("이미 신청하셨습니다!!");
          return;
        }
      }
      this.enqueue(myUserId);
      console.log(this.sessionInfo.waitingQueue);
      console.log("대기열 출력!!");
      console.log(this.sessionInfo.waitingQueue);
      this.session.signal({
        data: JSON.stringify(this.sessionInfo),
        type: "addWaitingQueue",
      });
    },
    dequeue() {
      if (this.sessionInfo.waitingQueue.length == 0) return "";
      else {
        return this.sessionInfo.waitingQueue.shift();
      }
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
      console.log("디버깅1");
      console.log(sessionId);
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
        import.meta.env.VITE_APP_URL +
          "/api/v1/openvidu/sessions/" +
          sessionId +
          "/connections",
        {},
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data; // The token
    },
  },
};
</script>
<style>
.bigbox {
  /* position: absolute; */
  display: flex;
  margin: auto;
  padding: 0;
}

.audiences {
  width: 10%;
  justify-content: center;
  align-items: center;
  margin: auto;
  margin-bottom: 50px;
}

.smallboxb {
  display: flex;
  justify-content: center;
  align-items: center;
  /* justify-content: space-around; */
  margin-left: 200px;
  margin-right: 200px;
  width: 60%;
  margin: auto;
  padding: 0;
}

.smallboxl {
  width: 300px;
}

.musicbox {
  top: 100%;
  left: -5%;
  width: 700px;
  margin-left: 50px;
  margin-right: 50px;
}

/* .smallboxr{
  bottom: -30%;
  left: 70%;
} */

.container {
  height: 100%;
  width: 100vw;
  background-color: black;
  background-image: url("../../assets/images/sparkle-star.gif");
  background-size: 400px;
  background-repeat: repeat;
  color: white;
  padding: 20px;
}
::-webkit-scrollbar {
  width: 15px;
  height: 8px;
  background-color: rgb(246, 246, 247); /* 또는 트랙에 추가한다*/
}
::-webkit-scrollbar-thumb {
  background: rgb(1, 19, 45);
  border-radius: 10cm;
}

.musicOn {
  height: 100%;
  width: 100vw;
  background-color: black;
  background-image: url("../../assets/images/back.gif");
  background-size: 1200px;
  color: white;
  padding: 20px;
}

.hurryup {
  color: red;
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
.win {
  height: 100%;
  width: 100vw;
  background-color: black;
  background-image: url("../../assets/images/champion2.gif");
  background-repeat: repeat;
  background-size: 800px;
  color: white;
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
