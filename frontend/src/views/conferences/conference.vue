<template>
  <!-- <v-col> -->
  <div
    id="main-container"
    class="container"
    :class="
      ({ musicOn: this.selectedVideo && this.finish == false }, { win: this.finish == true })
    "
  >
    <v-row
      style="
        color: white;
        margin-left: 100px;
        margin-right: 50px;
        padding-bottom: 30px;
      "
      justify="space-between"
    >
      <a ref="down" style="display: none"></a>
      <div><v-icon color="yellow" size="x-large">mdi-bell</v-icon></div>
      <div
        style="margin-left: 2%"
        v-if="
          this.readyVideo &&
          !this.selectedVideo &&
          this.myUserId != this.sessionInfo.champion
        "
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

      <v-col
        justify="center"
        v-if="this.selectedVideo && this.finish == false"
        style="padding-top: 0px"
      >
        <MARquee
          style="font-size: 30px"
          scrollamount="25"
          direction="right"
          loop="3"
          >도전자가
          <span style="color: orange">{{ nowplaysong }}</span>
          를(을) 신청하였습니다. 대결이 시작됩니다!
        </MARquee>
        <!-- <MARquee 
          class="blinking"
          v-if="vote_please"
          style="font-size: 25px"
          scrollamount="25"
          direction="right"
          loop="1"
          >투표시간이 
          <span style="color: orange">30초</span>
          남았습니다. 투표를 진행해주세요!
        </MARquee> -->
        <v-row justify="space-between">
          <vue-countdown
            class="championCount"
            :time="21 * 1000"
            v-slot="{ seconds }"
            v-if="this.nowPart == 'champion'"
          >
            <h2 style="margin-top: 50px; margin-left: 270px; color: #18ffff">
              {{ seconds }} 초
            </h2>
          </vue-countdown>
          <h2
            style="margin-top: 50px; margin-left: 270px; color: #18ffff"
            v-if="this.nowPart == 'challenger'"
          >
            준비
          </h2>
          <vue-countdown
            class="votetime"
            :time="nowplaytime * 1000 * 0.98"
            v-slot="{ minutes, seconds }"
          >
            <h2
              :class="{ hurryup: minutes == 0 && seconds <= 30 }"
              style="margin-top: 50px; margin-left: 60px"
            >
              남은 투표 시간 : {{ minutes }} 분 {{ seconds }} 초
            </h2>
          </vue-countdown>
          <vue-countdown
            class="challengerCount"
            :time="21 * 1000"
            v-slot="{ seconds }"
            v-if="this.nowPart == 'challenger'"
          >
            <h2 style="margin-top: 50px; margin-right: 260px; color: #b2ff59">
              {{ seconds }} 초
            </h2>
          </vue-countdown>
          <h2
            style="margin-top: 50px; margin-right: 250px; color: #b2ff59"
            v-if="this.nowPart == 'champion'"
          >
            준비
          </h2>
        </v-row>
      </v-col>

      <div>
        <input
          class="btn btn-large btn-danger"
          type="button"
          id="buttonLeaveSession"
          @click="leaveSession"
          value="나가기"
          style="margin-right: 20px; font-size: 20px"
        />
      </div>
    </v-row>

    <Modal ref="championSongListShowModal">
      <div style="text-align: center">
        <v-card class="mx-auto black" max-width="500">
          <v-list dark>
            <h3
              type="button"
              @click="closeChampionSongListShowModal()"
              style="margin: 10px; margin-right: 20px; text-align: right"
            >
              X
            </h3>

            <h2>챔피언 {{ this.sessionInfo.champion }}님의</h2>
            <h2>플레이리스트</h2>

            <hr />
            <v-list-item-group v-model="model">
              <v-list-item
                v-for="championSong in sessionInfo.championSongList"
                :key="championSong.title"
              >
                <v-list-item-title
                  >{{ championSong.title }} -

                  {{ championSong.singer }}</v-list-item-title
                >
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </div>
    </Modal>

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

            <h2>챔피언 {{ this.sessionInfo.champion }}님의</h2>
            <h2>플레이리스트</h2>

            <hr />
            <v-list-item-group v-model="model">
              <v-list-item
                v-for="championSong in sessionInfo.championSongList"
                @click="
                  battleApplication(championSong), closeChampionSongListModal()
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

    <!-- <Modal ref="championSongListModal">
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
    </Modal> -->

    <div
      v-if="champion_confirm && this.myUserId == this.sessionInfo.champion"
      style="margin-bottom: 10px"
    >
      <h1>
        도전자로부터 [<span style="color: green">{{ nowplaysong }}</span
        >] 대결 신청이 들어왔습니다!
      </h1>
      <h2>대결을 시작하시겠습니까?</h2>

      <v-btn @click="sendStartSignal()" color="green" variant="outlined" style="font-size:20px;"
        >확인</v-btn
      >
      <!-- 취소 만들기 -->
    </div>

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

        <div class="smallboxl" style="margin-right: 300px" display="flex">
          <!--스몰박스 left, 노래화면 왼쪽. 여기에 스트림매니저로 챔피언을 넘겨줘야함-->

          <v-card style="padding: 5px; font-size: 20px" color="primary"
            ><img
              src="../../assets/images/sparkling.gif"
              style="width: 30px" /><span style="color: white; font-size:30px;">챔피언</span>

            <img src="../../assets/images/sparkling.gif" style="width: 30px"
          /></v-card>

          <user-video
            :class="{championVideo: this.nowPart == 'champion' && this.selectedVideo && !this.finish}"
            :stream-manager="championStreamManager"
            @click.native="updateMainVideoStreamManager(championStreamManager)"
            :player="playerJoined"
          />
          <VoteChampion v-if="this.voteBtnShow" @voteChampion="voteChampion" />
        </div>

        <!-- </v-col> -->

        <div class="musicbox" style="margin-right: 300px">
          <SongDetail
            v-if="this.selectedVideo && this.finish == false"
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
          <div style="margin-top: 30px">
            <input
              class="btn btn-large btn-danger"
              type="button"
              @click="challengeFirst(myUserId)"
              value="대결 신청"
              style="
                margin-top: 20px;
                margin-bottom: 20px;
                margin-right: 20px;
                font-size: 20px;
              "
            />

            <input
              v-if="this.myUserId == this.sessionInfo.challenger"
              class="btn btn-large btn-warning"
              type="button"
              @click="showChampionSongList"
              value="도전 가능곡"
              style="margin-right: 20px; font-size: 20px; width:140px;"
            />

            <input
              v-if="this.myUserId != this.sessionInfo.challenger"
              class="btn btn-large btn-warning"
              type="button"
              @click="showChampionSongShowList"
              value="챔피언 플레이리스트"
              style="margin-right: 20px; width: 200px; font-size: 20px"
            />

            <input
              class="btn btn-large btn-success"
              type="button"
              @click="showWaitingQueue"
              value="도전자 목록"
              style="
                margin-top: 20px;
                margin-bottom: 20px;
                margin-right: 20px;
                font-size: 20px;
                width: 120px;
              "
            />
            <input
              v-if="stickerFilterBtnClicked"
              class="btn btn-large btn-danger"
              type="button"
              @click="applyStickerFilter"
              value="필터 ON"
              style="
                margin-top: 20px;
                margin-bottom: 20px;
                margin-right: 20px;
                font-size: 20px;
              "
            />
            <input
              v-else
              class="btn btn-large btn-primary"
              type="button"
              @click="applyStickerFilter"
              value="필터 OFF"
              style="
                margin-top: 20px;
                margin-bottom: 20px;
                margin-right: 20px;
                font-size: 20px;
              "
            />
            <input
              v-if="echoFilterBtnClicked"
              class="btn btn-large btn-danger"
              type="button"
              @click="applyEchoFilter"
              value="에코 ON"
              style="
                margin-top: 20px;
                margin-bottom: 20px;
                margin-right: 20px;
                font-size: 20px;
              "
            />
            <input
              v-else
              class="btn btn-large btn-primary"
              type="button"
              @click="applyEchoFilter"
              value="에코 OFF"
              style="
                margin-top: 20px;
                margin-bottom: 20px;
                margin-right: 20px;
                font-size: 20px;
              "
            />
            <br />
            <input
              class="btn btn-large btn-info"
              type="button"
              ref="captureBtn"
              @click="capture"
              value="녹화 화면 지정"
              style="
                margin-top: 20px;
                margin-bottom: 20px;
                margin-right: 20px;
                width: 140px;
                font-size: 20px;
              "
            />
            <input
              class="btn btn-large btn-info"
              type="button"
              ref="startBtn"
              @click="start"
              value="녹화 시작"
              disabled
              style="
                margin-top: 20px;
                margin-bottom: 20px;
                margin-right: 20px;
                font-size: 20px;
              "
            />
            <input
              class="btn btn-large btn-info"
              type="button"
              ref="stopBtn"
              @click="stop"
              value="녹화 중지"
              disabled
              style="
                margin-top: 20px;
                margin-bottom: 20px;
                margin-right: 20px;
                font-size: 20px;
              "
            />
            <a id="download" ref="down" href="#" style="display: none"
              >Download</a
            >
          </div>
        </div>

        <!--스몰박스 right, 노래화면 오른쪽, 여기에 챌린져가 들어가야 함-->

        <!--비디오 위치 테스트용으로 퍼블리셔 넣어놓음 -->

        <div class="smallboxl" display="flex">
          <!--스몰박스 left, 노래화면 왼쪽. 여기에 스트림매니저로 챔피언을 넘겨줘야함-->

          <v-card style="padding: 5px; font-size: 20px" color="green"
            ><img src="../../assets/images/sparkling.gif" style="width: 30px" />

            <span style="color: white; font-size:30px;">도전자 </span
            ><img src="../../assets/images/sparkling.gif" style="width: 30px"
          /></v-card>

          <user-video
            :class="{challengerVideo: this.nowPart == 'challenger' && this.selectedVideo && !this.finish}"
            :stream-manager="challengerStreamManager"
            @click.native="
              updateMainVideoStreamManager(challengerStreamManager)
            "
            :player="playerJoined"
          />
          <VoteChallenger
            v-if="this.voteBtnShow"
            @voteChallenger="voteChallenger"
          />
        </div>
      </div>
    </div>

    <!-- 관중들 들어갈 자리 -->
    <v-row class="smallboxb" justify="space-around" style="width: 2500px;">
      <div>
        <user-video
          :stream-manager="publisher"
          @click.native="updateMainVideoStreamManager(publisher)"
        />
      </div>

      <user-video
        v-for="sub in subscribers"
        :key="sub.stream.connection.connectionId"
        :stream-manager="sub"
        @click.native="updateMainVideoStreamManager(sub)"
        :player="crowdJoined"
      />
    </v-row>

    <v-col>
      <room-chat
        ref="chat"
        @message="sendMessage"
        :subscribers="subscribers"
      ></room-chat>
    </v-col>
  </div>
  <!-- </v-col> -->
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
import VueCountdown from "@chenfengyuan/vue-countdown";
import RoomChat from "./components/room-chat.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";
const API_KEY = "AIzaSyBGF5ljIuwHbPn27YSImtkkgk8KooR8q7I";

let blobs;
let blob;
let rec;
let stream;
let voiceStream;
let desktopStream;
const mergeAudioStreams = (desktopStream, voiceStream) => {
  const context = new AudioContext();
  const destination = context.createMediaStreamDestination();
  let hasDesktop = false;
  let hasVoice = false;
  if (desktopStream && desktopStream.getAudioTracks().length > 0) {
    // If you don't want to share Audio from the desktop it should still work with just the voice.
    const source1 = context.createMediaStreamSource(desktopStream);
    const desktopGain = context.createGain();
    desktopGain.gain.value = 0.7;
    source1.connect(desktopGain).connect(destination);
    hasDesktop = true;
  }

  if (voiceStream && voiceStream.getAudioTracks().length > 0) {
    const source2 = context.createMediaStreamSource(voiceStream);
    const voiceGain = context.createGain();
    voiceGain.gain.value = 0.7;
    source2.connect(voiceGain).connect(destination);
    hasVoice = true;
  }
  return hasDesktop || hasVoice ? destination.stream.getAudioTracks() : [];
};

export default {
  name: "App",
  components: {
    UserVideo,
    Modal,
    SongDetail,
    ReadyDetail,
    VoteChallenger,
    VoteChampion,
    VueCountdown,
    RoomChat,
  },
  props: {
    id: "",
  },
  data() {
    return {
      sessionInfo: {
        waitingQueue: [],
        challenger: "",
        challengerUsername: "",
        likeChampion: 0,
        likeChallenger: 0,
        champion: "",
        championSongList: [],
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
      // champion: "",
      // championSongList: [],
      readyVideo: false, // 미러볼 비디오 화면을 띄울지 결정할 변수
      ready: false,
      voteBtnShow: false,
      test: false,
      finish: false,
      nowplaytime: 0, // 현재 대결곡의 소요 시간
      TimeCounter: 0,
      nowplaysong: "", // 현재 대결곡
      champion_confirm: false,
      vote_please: false, // 30초 남았을때 공지 띄우기
      nowPart: "champion", // 파트를 나누기위한 변수
      partTime: 20, // 한 파트당 20초
      partTimeCounter: 0,
      // likeChampion: 0,
      // likeChallenger: 0,
      winner: "",
      members: [],
      championStreamManager: undefined,
      challengerStreamManager: undefined,
      stickerFilterBtnClicked: false,
      echoFilterBtnClicked: false,
      playerJoined: true,
      crowdJoined: false,
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
    this.joinSession();
    this.getname();
    console.log("====================================================");
    console.log(this.subscribers);
    this.myUserId = localStorage.getItem("userId");
    // this.getReadyVideo();
  },
  mounted() {
    // this.getReadyVideo();
    // this.ready = !this.ready;
    this.test = !this.test;
    // this.leavePlayroom();
  },
  updated() {
    this.getReadyVideo();
  },
  methods: {
    // 챔피언만 실행하는 함수
    sendStartSignal() {
      // aa 관중 mute 적용
      this.session.signal({ type: "crowdMute" });
      this.session.signal({ type: "onSelectVideoSpread" }); // 데이터 상관없음
      this.onSelectVideo();
    },
    sendMessage({ content, to }) {
      let now = new Date();
      let current = now.toLocaleTimeString([], {
        hour: "2-digit",
        minute: "2-digit",
        hour12: false, // true인 경우 오후 10:25와 같이 나타냄.
      });
      let messageData = {
        content: content,
        sender: this.myUserName,
        time: current,
      };
      // 전체 메시지
      if (to === "all") {
        this.session
          .signal({
            data: JSON.stringify(messageData),
            to: [],
            type: "public-chat",
          })
          .then(() => {
            console.log("메시지 전송 완료");
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    async capture() {
      this.$refs.captureBtn.style.display = "none";
      const audio = true;
      const mic = true;

      desktopStream = await navigator.mediaDevices.getDisplayMedia({
        video: true,
        audio: audio,
      });

      if (mic === true) {
        voiceStream = await navigator.mediaDevices.getUserMedia({
          video: false,
          audio: mic,
        });
      }

      const tracks = [
        ...desktopStream.getVideoTracks(),
        ...mergeAudioStreams(desktopStream, voiceStream),
      ];

      console.log("Tracks to add to stream", tracks);
      stream = new MediaStream(tracks);
      console.log("Stream", stream);

      blobs = [];

      rec = new MediaRecorder(stream, { mimeType: "video/webm; " });
      rec.ondataavailable = (e) => blobs.push(e.data);
      rec.onstop = async () => {
        //blobs.push(MediaRecorder.requestData());
        blob = new Blob(blobs, { type: "video/mp4" });
        let url = window.URL.createObjectURL(blob);
        this.$refs.down.href = url;
        this.$refs.down.download = "test.mp4";
        this.$refs.down.style.display = "";
      };
      this.$refs.startBtn.disabled = false;
      this.$refs.startBtn.style.color = "lightsalmon";
      this.$refs.captureBtn.disabled = true;
    },

    start() {
      this.$refs.startBtn.disabled = true;
      this.$refs.startBtn.style.color = "white";
      this.$refs.stopBtn.style.color = "lightsalmon";
      this.$refs.stopBtn.disabled = false;
      rec.start();
    },

    stop() {
      this.$refs.captureBtn.disabled = false;
      this.$refs.startBtn.disabled = true;
      this.$refs.stopBtn.disabled = true;
      this.$refs.stopBtn.style.color = "white";
      rec.stop();

      stream.getTracks().forEach((s) => s.stop());
      stream = null;
      this.$refs.captureBtn.style.display = "inline";
    },
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

    startRecord() {
      navigator.mediaDevices
        .getDisplayMedia({
          audio: true,
          video: {
            mediaSource: "screen",
          },
        })
        .then((stream) => {
          const recorder = new MediaRecorder(stream);
          recorder.start();
          const buffer = [];
          recorder.addEventListener("dataavailable", (event) => {
            buffer.push(event.data);
          });
          recorder.addEventListener("stop", () => {
            const blob = new Blob(buffer, {
              type: "video/mp4",
            });
            this.$refs.down.href = URL.createObjectURL(blob);
            console.log("녹화테스트");
            console.log(this.$refs.down);
            this.$refs.down.download = "recording.mp4";
            this.$refs.down.click();
          });
        });
    },
    filterOff() {
      this.publisher.stream
        .removeFilter()
        .then(() => {
          console.log("-- Filter removed --");
        })
        .catch((error) => {
          console.error(error);
        });
    },
    // Kurento faceOverlayFilter 적용한 스티커 필터
    applyStickerFilter() {
      // 필터 해제, 버튼 OFF 전환
      if (this.stickerFilterBtnClicked) {
        this.filterOff();
        this.stickerFilterBtnClicked = false;
        return;
      }

      this.publisher.stream.applyFilter("FaceOverlayFilter").then((filter) => {
        var offsetX;
        var offsetY;
        var width;
        var height;
        offsetX = "-0.2F"; // -0.7F
        offsetY = "-0.7F";
        width = "1.4";
        height = "1.4";
        filter.execMethod("setOverlayedImage", {
          uri: "https://sstvideo.s3.ap-northeast-2.amazonaws.com/images/singmask.png",
          offsetXPercent: offsetX,
          offsetYPercent: offsetY,
          widthPercent: width,
          heightPercent: height,
        });
      });

      // 버튼 ON 전환
      this.stickerFilterBtnClicked = true;
    },

    // Kurento audioecho Filter 적용한 오디오 필터
    applyEchoFilter() {
      // 필터 해제, 버튼 OFF 전환
      if (this.echoFilterBtnClicked) {
        this.filterOff();
        this.echoFilterBtnClicked = false;
        return;
      }

      this.publisher.stream.applyFilter("GStreamerFilter", {
        command: "audioecho delay=50000000 intensity=0.9 feedback=0.5",
      });

      // this.publisher.stream.applyFilter("GStreamerFilter", {
      //   command:
      //     `gdkpixbufoverlay location=/assets/images/logo.png offset-x=10 offset-y=10 overlay-height=200 overlay-width=200`,
      // });

      // 버튼 ON 전환
      this.echoFilterBtnClicked = true;
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
    // 챔피언만 실행하는 함수
    onSelectVideo: function () {
      // if(this.myUserId == this.sessionInfo.champion){
      //   this.session.signal({ type: "onSelectVideoSpread" }); // 데이터 상관없음
      // }
      this.readyVideo = false;
      // this.nowplaytime = championSong.part4 + 10;
      // this.nowplaytime = 5;
      // this.nowplaysong = championSong.title;
      this.champion_confirm = false;
      this.TimeCounter = this.nowplaytime;
      setTimeout(() => {
        
        var interval = setInterval(() => {
          this.TimeCounter -= 1; //1초씩 감소
          // console.log(this.myUserName)
          // console.log(this.TimeCounter);
  
          // console.log("시간 : " + this.TimeCounter);
          if (this.TimeCounter == 30) {
            this.vote_please = true;
            this.session.signal({ data: this.vote_please, type: "vote_please" });
          }
          if (this.TimeCounter == 0) {
            console.log("timeStop22222");
            this.timerStop(interval);
          }
        }, 1000);
  
        setInterval(() => {
          if (this.nowPart == "champion") {
            this.session.signal({ data: "challenger", type: "part_change" })
          }
          if (this.nowPart == "challenger") {
            this.session.signal({ data: "champion", type: "part_change" })
          }
        }, 16 * 1000);
  
        // this.partTimeCounter = this.partTime;
        // this.session.signal({ data: this.partTime, type: "time_reset" });
        // var partinterval = setInterval(() => {
        //   this.partTimeCounter -= 1; //1초씩 감소
        //   if (this.partTimeCounter == -1) {
        //     this.session.signal({ data: this.nowPart, type: "part_change" });
        //     this.parttimerStop(partinterval);
        //   }
        // }, 1000);
  
        this.session.signal({
          data: this.readyVideo,
          type: "start_readyVideo",
        });
        this.session.signal({
          data: this.champion_confirm,
          type: "start_battle",
        });
        this.session.signal({
          data: this.readyVideo,
          type: "start_readyVideo",
        });
        // this.session.signal({
        //   data: championSong.title,
        //   type: "start_notice",
        // });
        // this.session.signal({
        //   data: championSong.part4 + 10,
        //   type: "start_nowplaytime",
        // });
        this.session.signal({
          data: this.nowplaytime,
          type: "start_timecounter",
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
            data: JSON.stringify(this.nowplaysong),
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
      }, 6000);
    },
    battleApplication: function (championSong) {
      // console.log("222222222222222222222222222");
      // console.log("챔피언 아이디는!!!" + this.champion);
      // console.log(this.myUserId)
      this.nowplaysong = championSong.title;
      // this.nowplaytime = (championSong.part4 + 10);
      this.nowplaytime = 120 + 10;
      // this.nowplaytime = 60;
      this.champion_confirm = true;
      this.session.signal({ data: championSong.title, type: "battleApply" });
      this.session.signal({
        data: 120 + 10,
        type: "battleApplySongTime",
      });
      this.session.signal({ data: true, type: "battlemodalshow" });
    },

    // 챔피언만 실행하는 함수
    timerStop: function (Timer) {
      clearInterval(Timer);
      // if ( minutes == 0 && seconds == 0) {
      //   this.endGame();
      // }
      this.endGame();
    },
    parttimerStop: function (Timer) {
      clearInterval(Timer);
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
      // 챔피언, 도전자가 아니면 mute
      this.session.on("signal:crowdMute", () => {
        if (
          this.myUserId != this.sessionInfo.champion &&
          this.myUserId != this.sessionInfo.challenger
        ) {
          this.publisher.publishAudio(false);
        }
      }),

      // 노래가 끝난 후 nowPart를 다시 champion으로 초기화
      this.session.on("signal:nowPartChampion", (event) => {
        this.nowPart = event.data
      })

      // 노래 끝나면 모두 mute 해제
      this.session.on("signal:crowdMuteCancel", () => {
          this.publisher.publishAudio(true);
      }),
        // onSelectVideoSpread
        this.session.on("signal:onSelectVideoSpread", () => {
          if (this.myUserId != this.sessionInfo.champion) {
            this.onSelectVideo();
          }
        }),
        this.session.on("signal:endalert", (event) => {
          this.finish = event.data;
        }),
        this.session.on("signal:battleApply", (event) => {
          this.nowplaysong = event.data;
        }),
        this.session.on("signal:battleApplySongTime", (event) => {
          this.nowplaytime = event.data;
        }),
        this.session.on("signal:battlemodalshow", (event) => {
          this.champion_confirm = event.data;
        }),
        this.session.on("signal:start_battle", (event) => {
          this.champion_confirm = event.data;
        }),
        this.session.on("signal:start_readyVideo", (event) => {
          this.readyVideo = event.data;
        }),
        this.session.on("signal:start_notice", (event) => {
          this.nowplaysong = event.data;
        }),
        this.session.on("signal:start_nowplaytime", (event) => {
          this.nowplaytime = event.data;
        }),
        this.session.on("signal:start_timecounter", (event) => {
          this.TimeCounter = event.data;
        }),
        this.session.on("signal:start_selectedVideo", (event) => {
          this.selectedVideo = event.data;
        }),
        // this.session.on("signal:selectVideo_false", (event) => {
        //   this.selectedVideo = event.data;
        // }),
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
        this.session.on("signal:vote_please", (event) => {
          this.vote_please = event.data;
        }),
        this.session.on("signal:time_reset", (event) => {
          console.log("time시그널 받았어!!!!!!!!!!!!!!!1");
          this.partTimeCounter = event.data;
        }),
        this.session.on("signal:part_change", (event) => {
          console.log("change시그널 받았어!!!!!!!!!!!!!!!1");
          this.nowPart = event.data;
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
        this.session.on("signal:public-chat", (event) => {
          this.$refs.chat.addMessage(
            event.data,
            JSON.parse(event.data).sender === this.myUserName,
            false
          );
        });

      // 1. 첫 도전자가 있을 때 (challenge 자리)
      this.session.on("signal:challengeFirst", (event) => {
        console.log("iiiiiiiiiiiiiiiiiii1");
        this.sessionInfo.challenger = JSON.parse(event.data).challenger;
        // this.sessionInfo.champion = JSON.parse(event.data).champion;
        // this.sessionInfo.championSongList = JSON.parse(
        //   event.data
        // ).championSongList;

        // 방 멤버들 중 도전자 화면만 생성
        for (let user of this.members) {
          if (
            JSON.parse(user.stream.connection.data).clientId ==
            this.sessionInfo.challenger
          ) {
            this.challengerStreamManager = user;
          }
        }
      });
      // 2. 게임 끝나고 다음 도전자 없을 때
      this.session.on("signal:updateNoChallengerSessionInfo", (event) => {
        console.log("iiiiiiiiiiiiiiiiiii2");
        // console.log(JSON.parse(event.data).challenger);
        // console.log(JSON.parse(event.data).champion);
        // console.log(JSON.parse(event.data).championSongList);
        this.sessionInfo.challenger = JSON.parse(event.data).challenger;
        this.sessionInfo.champion = JSON.parse(event.data).champion;
        this.sessionInfo.championSongList = JSON.parse(
          event.data
        ).championSongList;
        // 방 멤버들 중 도전자 빈 화면 생성
        this.challengerStreamManager = undefined;
        // 방 멤버들 중 챔피언 화면 생성
        this.setChampionStreamManager();
      });

      // 3. 게임 끝나고 다음 도전자가 있을 때
      this.session.on("signal:updateYesChallengerSessionInfo", (event) => {
        console.log("iiiiiiiiiiiiiiiiiii3");
        // console.log(JSON.parse(event.data).challenger);
        // console.log(JSON.parse(event.data).champion);
        // console.log(JSON.parse(event.data).championSongList);
        this.sessionInfo.challenger = JSON.parse(event.data).challenger;
        this.sessionInfo.champion = JSON.parse(event.data).champion;
        this.sessionInfo.championSongList = JSON.parse(
          event.data
        ).championSongList;
        // 방 멤버들 중 도전자 화면 생성
        this.setChallengerStreamManager();
        // 방 멤버들 중 챔피언 화면 생성
        this.setChampionStreamManager();
      });

      // aa 구조 파악 필요
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

            // Kurento 필터 적용을 위해 remote를 subscribe
            publisher.subscribeToRemote(true);

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
      // 윈도우 언로드 되기전에 방 정원수 감소
      // 윈도우 리로드에는 여길 안거치니까 감소를 안함
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
          // 들어오면 받는 정보
          // 1. 챔피언, 도전자, 챔피언 노래 목록
          this.sessionInfo.champion = res.data.champion;
          this.sessionInfo.challenger = res.data.challenger;
          this.getChampionSongList();
          // aa 플레이룸-도전자 조회
          this.getWaitingQueue();
          // 2. 방 멤버들 중 챔피언 화면 생성
          this.setChampionStreamManager();

          // 3. 방 멤버들 중 도전자 화면 생성
          this.setChallengerStreamManager();
        })
        .catch((err) => {
          alert(err);
        });
    },
    // 방 멤버들 중 챔피언 화면 생성
    async setChampionStreamManager() {
      for (let user of this.members) {
        if (
          JSON.parse(user.stream.connection.data).clientId ==
          this.sessionInfo.champion
        ) {
          this.championStreamManager = user;
        }
      }
    },

    // 방 멤버들 중 도전자 화면 생성
    setChallengerStreamManager() {
      for (let user of this.members) {
        if (
          JSON.parse(user.stream.connection.data).clientId ==
          this.sessionInfo.challenger
        ) {
          this.challengerStreamManager = user;
        }
      }
    },
    // 챔피언 노래목록 불러오기
    getChampionSongList() {
      axios({
        method: "get",
        url:
          import.meta.env.VITE_APP_URL +
          `/api/v1/playrooms/playlist/${this.mySessionId}`,
      })
        .then((res) => {
          console.log("jjjjjjjjjjjjjjjj");
          console.log(res.data);
          this.sessionInfo.championSongList = res.data;
        })
        .catch((err) => {
          alert(err);
        });
    },
    getWaitingQueue(){
      axios({
        method: "get",
        url:
          import.meta.env.VITE_APP_URL +
          `/api/v1/playrooms/getmember/${this.sessionId}`,
       data: {
        sessionId : this.sessionId,
       },
        })
        .then((res) => {
          console.log("대기열불러오기 1395");
          console.log(res.data);
          this.sessionInfo.waitingQueue = Array.from(res.data);
        })
        .catch((err) => {
          alert(err);
        });
    },
    // 챔피언만 실행하는 함수
    endGame() {
      // aa 모두 mute 해제 (에러 가능성)
      this.session.signal({ type: "crowdMuteCancel" });
      // this.finish true 전파
      this.finish = true;
      this.session.signal({
        data: this.finish,
        type: "endalert",
      });
      // aa mute 해제

      // 다음 대결을 위해 nowPart 를 다시 champion으로 수정
      this.session.signal({
        data: "champion",
        type: "nowPartChampion"
      })

      if (this.sessionInfo.likeChampion >= this.sessionInfo.likeChallenger) {
        this.winner = "챔피언";
      } else {
        this.winner = "도전자";
      }
      // this.winner 전파
      this.session.signal({
        data: this.winner,
        type: "showWinner",
      });
      this.sessionInfo.likeChampion = 0;
      this.sessionInfo.likeChallenger = 0;
      // 챔피언이 그대로면 champion, 바뀌었으면 challenger
      this.sessionInfo.champion =
        this.winner == "챔피언"
          ? this.sessionInfo.champion
          : this.sessionInfo.challenger;
      // DB 플레이룸 챔피언 정보 수정
      axios({
        method: "put",
        url: import.meta.env.VITE_APP_URL + `/api/v1/playrooms/end-song`,
        data: {
          sessionId: this.mySessionId,
          championId: this.sessionInfo.champion,
        },
      })
        .then((res) => {
          // alert(
          //   // this.sessionInfo.champion +
          //   //   `님이 ${res.data.winCnt}연승을 달성하셨습니다!!!`
          //   "승자가 결정되었습니다!"
          // );
          // 챔피언 노래 리스트 변경
          this.sessionInfo.championSongList = res.data.championSongList;
          const next = this.dequeue();
          console.log("next출력");
          console.log(next);
          console.log(this.subscribers);
          // 다음 사람이 없으면 challenger 비우고 sessionInfo 전파
          // 다음 사람이 있으면 next challenger 올리고 sessionInfo 전파
          if (next == "") {
            this.sessionInfo.challenger = "";
            // DB 플레이룸 도전자 정보 수정2
            // alert("다음 도전자 없음, 도전자 비우기")
            this.updateDBChallenger(this.sessionInfo.challenger);
            // challenger 없는거 전파
            this.session.signal({
              data: JSON.stringify(this.sessionInfo),
              type: "updateNoChallengerSessionInfo",
            });
          } else {
            this.sessionInfo.challenger = next;
            // DB 플레이룸 도전자 정보 수정3
            // alert("다음 도전자 있음, 도전자 수정");
            this.updateDBChallenger(this.sessionInfo.challenger);
            // challenger 다음 사람 전파
            this.session.signal({
              data: JSON.stringify(this.sessionInfo),
              type: "updateYesChallengerSessionInfo",
            });
          }
          // 챔피언만 실행하는 함수인데 자기만 화면 못생성함 -> 시그널 받는 곳과 별개로 생성
          // 방 멤버들 중 챔피언 화면 생성
          this.setChampionStreamManager();
          // 방 멤버들 중 도전자 화면 생성
          this.setChallengerStreamManager();
        })
        .catch((err) => {
          alert(err);
        });
    },
    updateDBChallenger(newChallenger) {
      axios({
        method: "put",
        url: import.meta.env.VITE_APP_URL + `/api/v1/playrooms/challenger`,
        data: {
          sessionId: this.mySessionId,
          challengerId: newChallenger,
        },
      })
        .then((res) => {
          console.log(res.data);
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
      // aa 플레이룸-도전자 등록
      this.sessionInfo.waitingQueue.push(data);
      axios({
        method: "post",
        url:
          import.meta.env.VITE_APP_URL +
          `/api/v1/playrooms/addmember/`,
       data: {
        sessionId : this.sessionId,
        challengerId : data,
       },
        })
        .then((res) => {
          console.log("jjjjjjjjjjjjjjjj");
          console.log(res.data);
          this.sessionInfo.championSongList = res.data;
        })
        .catch((err) => {
          alert(err);
        });
    },
    // 도전자만 실행하는 함수
    challengeFirst(myUserId) {
      if (this.sessionInfo.challenger == "") {
        this.sessionInfo.challenger = myUserId;
        // 방 멤버 중 대결신청 버튼 누른 유저의 화면 전파
        this.session.signal({
          data: JSON.stringify(this.sessionInfo),
          type: "challengeFirst",
        });
        // DB 플레이룸 도전자 정보 수정1
        this.updateDBChallenger(this.sessionInfo.challenger);

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
      this.enqueue(this.myUserId);
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
        // aa 플레이룸-도전자 삭제 (변수 선언)
        const data = this.sessionInfo.waitingQueue.shift();
        axios({
        method: "delete",
        url:
          import.meta.env.VITE_APP_URL +
          `/api/v1/playrooms/deletemember/`,
       data: {
        sessionId : this.sessionId,
        challengerId : data,
       },
        })
        .then((res) => {
          console.log("1574"+"deleteWaitingQueue");
          console.log(res);
        })
        .catch((err) => {
          alert(err);
        });


        return data;
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
        {
          // filter 사용을 위해 create connection 시 body를 추가
          type: "WEBRTC",
          role: "PUBLISHER",
          kurentoOptions: {
            allowedFilters: ["GStreamerFilter", "FaceOverlayFilter"],
          },
        },
        {
          headers: { "Content-Type": "application/json" },
        }
      );
      return response.data; // The token
    },
  },
  setup() {
    // 자식 컴포넌트를 핸들링하기 위한 ref
    const store = useStore();
    const championSongListModal = ref(null);
    const championSongListShowModal = ref(null);
    // const championBattleNoticeModal = ref(null);
    const waitingQueueModal = ref(null);
    // Promise 객체를 핸들링하기 위한 ref
    const resolvePromise = ref(null);
    const showChampionSongList = () => {
      // showChampionSongList을 직접 컨트롤합니다.
      championSongListModal.value.open();
      // Promise 객체를 사용하여, 현재 모달에서 확인 / 취소의
      // 응답이 돌아가기 전까지 작업을 기다리게 할 수 있습니다.
      return new Promise((resolve) => {
        // resolve 함수를 담아 외부에서 사용합니다.
        resolvePromise.value = resolve;
      });
    };
    const showChampionSongShowList = () => {
      // showChampionSongList을 직접 컨트롤합니다.
      championSongListShowModal.value.open();
      // Promise 객체를 사용하여, 현재 모달에서 확인 / 취소의
      // 응답이 돌아가기 전까지 작업을 기다리게 할 수 있습니다.
      return new Promise((resolve) => {
        // resolve 함수를 담아 외부에서 사용합니다.
        resolvePromise.value = resolve;
      });
    };

    const showWaitingQueue = () => {
      waitingQueueModal.value.open();
      return new Promise((resolve) => {
        resolvePromise.value = resolve;
      });
    };

    const closeChampionSongListModal = () => {
      championSongListModal.value.close();
    };

    const closeChampionSongListShowModal = () => {
      championSongListShowModal.value.close();
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
      championSongListShowModal,
      // championBattleNoticeModal,
      waitingQueueModal,
      showChampionSongList,
      showChampionSongShowList,
      // showChampionBattleNoticeModal,
      showWaitingQueue,
      confirm,
      cancel,
      closeChampionSongListModal,
      closeChampionSongListShowModal,
      // closeChampionBattleNoticeModal,
      closeWaitingQueueModal,
      store,
    };
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
  width: 700px;
}

.musicbox {
  top: 100%;
  left: -5%;
  width: 800px;
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
  /* border: 3px solid; */
  border: 0px solid;
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
.blinking {
  -webkit-animation: blink 0.5s ease-in-out alternate;
  -moz-animation: blink 0.5s ease-in-out alternate;
  animation: blink 0.5s ease-in-out alternate;
  animation-iteration-count: 4;
}

@-webkit-keyframes blink {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@-moz-keyframes blink {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes blink {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

/* .championCount {
  position: fixed;
  left: 950px;
  top: 180px;
}

.challengerCount {
  position: fixed;
  right: 950px;
  top: 180px;
} */

input {
  width: 110px;
  height: 60px;
}

.championVideo{
  box-shadow: 0 0 200px blue;
}
.challengerVideo{
  box-shadow: 0 0 200px green;
}

.exit {
  float: right;
}
</style>
