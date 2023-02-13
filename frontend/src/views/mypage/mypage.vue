<template>
  <v-template>
    <v-row class="outbox" justify="space-around">
      <v-col col="5" md="3" lg="5" class="mypage_box">
        <h2 style="text-align: left"><b>My Page</b></h2>
        <v-row>
          <v-col col="3" class="user_image">
              <img :src="image" alt="" v-if="state.there==false" style="width:300px;height:200px">
              <img class="user_ex" src="../../assets/images/user_ex.png" v-else-if="state.there==true" />
          </v-col>
          <v-col col="3" class="user_info">
            <v-row>
              <v-col col="1" class="info_category">
                <p class="item_name"><b>닉네임</b></p>
                <p class="item_name"><b>우승횟수</b></p>
                <p class="item_name"><b>랭킹</b></p>
              </v-col>
              <v-col col="2">
                <p class="item_value">{{ state.nickname }}</p>
                <p class="item_value">{{ state.championCnt }}</p>
                <p class="item_value">{{ state.userRank }}</p>
              </v-col>
              <!--회원정보 수정-->
              <v-dialog v-model="update_dialog" persistent max-width="600px">
                <template v-slot:activator="{ on }">
                  <v-row justify="end" style="margin-right: 20px">
                    <div class="filebox">
                      <label for="chooseFile" >프로필사진</label>
                      <input ref="image" @change="uploadImg()" type="file" id="chooseFile" accept="image/*">
                    </div>
                    <v-btn
                      class="inline"
                      color="yellow-lighten-5"
                      outlined
                      @click="update_dialog = true"
                      v-on="on"
                      v-show="token"
                    >
                      회원 정보 수정
                    </v-btn>
                  </v-row>
                </template>
                <v-card>
                  <v-card-title>
                    <img
                      class="logo"
                      src="../../assets/images/login_logo.png"
                      style="width: 220px; margin-left: 180px"
                    />
                    <v-spacer></v-spacer>
                    <span class="text-h5" style="margin-left: 22px"
                      ><b>회원정보 수정</b></span
                    >
                  </v-card-title>
                  <v-card-text style="padding-bottom: 0px">
                    <v-form ref="form" @submit.prevent="update">
                      <v-row>
                        <v-col
                          cols="6"
                          style="padding-top: 0px; padding-bottom: 0px"
                        >
                          <v-text-field
                            v-model="update_state.form.name"
                            label="이름"
                            :rules="rule.name_rule"
                            required
                          ></v-text-field>
                        </v-col>
                        <v-col
                          cols="9"
                          style="padding-top: 0px; padding-bottom: 0px"
                        >
                          <v-text-field
                            v-model="update_state.form.id"
                            label="아이디"
                            hint="영문과 숫자를 포함하여 5자~10자 이내로 작성해주세요"
                            :rules="rule.id_rule"
                            required
                          ></v-text-field>
                        </v-col>
                        <v-btn
                          color="primary"
                          style="margin-top: 10px"
                          @click="id_check"
                          >중복 확인</v-btn
                        >

                        <v-col
                          cols="9"
                          style="padding-top: 0px; padding-bottom: 0px"
                        >
                          <v-text-field
                            v-model="update_state.form.nickname"
                            label="닉네임"
                            hint="2자~10자 이내로 작성해주세요"
                            :rules="rule.nickname_rule"
                            required
                          ></v-text-field>
                        </v-col>
                        <v-btn
                          color="primary"
                          style="margin-top: 10px"
                          @click="nickname_check"
                          >중복 확인</v-btn
                        >

                        <v-col
                          cols="12"
                          style="padding-top: 0px; padding-bottom: 5px"
                        >
                          <v-text-field
                            v-model="update_state.form.email"
                            label="이메일*"
                            hint="이메일 형식에 맞춰 작성해 주세요"
                            persistent-hint
                            :rules="rule.email_rule"
                            required
                          ></v-text-field>
                        </v-col>

                        <v-col
                          cols="12"
                          style="padding-top: 0px; padding-bottom: 5px"
                        >
                          <v-text-field
                            v-model="update_state.form.password"
                            label="비밀번호*"
                            hint="영문자, 숫자, 특수문자를 조합하여 8자~13자 이내로 작성해주세요"
                            persistent-hint
                            :rules="rule.password_rule"
                            type="password"
                            required
                          ></v-text-field>
                        </v-col>
                        <v-col
                          cols="12"
                          style="padding-top: 0px; padding-bottom: 5px"
                        >
                          <v-text-field
                            v-model="update_state.form.password_confirm"
                            label="비밀번호 확인*"
                            hint="비밀번호를 다시 입력해주세요"
                            persistent-hint
                            :rules="rule.password_confirm_rule"
                            type="password"
                            required
                          ></v-text-field>
                        </v-col>
                        <v-col
                          cols="6"
                          style="padding-top: 0px; padding-bottom: 5px"
                        >
                          <v-autocomplete
                            v-model="update_state.form.genre"
                            :items="[
                              '발라드',
                              '힙합',
                              '댄스',
                              '인디',
                              '트로트',
                              '팝송',
                            ]"
                            label="좋아하는 장르"
                            multiple
                          ></v-autocomplete>
                        </v-col>
                      </v-row>
                    </v-form>
                  </v-card-text>
                  <v-card-actions>
                    <v-row justify="space-between">
                      <div>
                        <v-dialog
                          v-model="confirm_dialog"
                          persistent
                          max-width="500px"
                        >
                          <template v-slot:activator="{ on2 }">
                            <v-btn
                              color="red darken-1"
                              style="margin-left: 20px"
                              v-on="on2"
                              @click="confirm_dialog = true"
                            >
                              회원 탈퇴
                            </v-btn>
                          </template>
                          <v-card>
                            <v-card-title>
                              <span
                                class="text-h6"
                                style="margin-left: 22px; margin-top: 5px"
                                ><b>회원 탈퇴를 하시겠습니까?</b></span
                              >
                            </v-card-title>
                            <v-card-actions>
                              <v-spacer></v-spacer>
                              <v-btn
                                color="blue darken-1"
                                text
                                @click="confirm_dialog = false"
                              >
                                아니오
                              </v-btn>
                              <v-btn
                                color="blue darken-1"
                                text
                                @click="(confirm_dialog = false), userDelete()"
                              >
                                네
                              </v-btn>
                            </v-card-actions>
                          </v-card>
                        </v-dialog>
                      </div>
                      <div>
                        <v-btn
                          color="blue darken-1"
                          text
                          @click="update_dialog = false"
                        >
                          취소
                        </v-btn>
                        <v-btn
                          color="blue darken-1"
                          text
                          @click="(update_dialog = false), update()"
                        >
                          저장
                        </v-btn>
                      </div>
                    </v-row>
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-row>
          </v-col>
        </v-row>
      </v-col>
      <v-col col="4 " md="3" lg="4 " class="myplaylist_box">
        <v-row justify="space-between ">
          <h2 style="text-align: left">
            <b>My Playlist</b><v-icon>mdi-music</v-icon>
          </h2>
          <v-dialog
            v-model="state.playlist_dialog"
            persistent
            max-width="600px"
          >
            <template v-slot:activator="{ on }">
              <v-btn
                class="inline"
                variant="text"
                @click="state.playlist_dialog = true"
                v-on="on"
                v-show="!state.token"
              >
                +
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <v-spacer></v-spacer>
                <span class="text-h5" style="margin-left: 22px"
                  ><b>플레이리스트 추가</b></span
                >
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col style="padding: 0px">
                      <v-text-field
                        label="키워드"
                        class="keyword"
                        v-model="state.keyword"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <playlistBox />
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="state.playlist_dialog = false"
                >
                  Close
                </v-btn>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="(state.playlist_dialog = false), addMyPlaylist()"
                >
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
        <myplaylistBox />
      </v-col>
    </v-row>
    <br />
    <v-divider></v-divider>
    <br />
    
    <!--마이 하이라이트-->
    <v-row align="center" justify="center">
      <v-col sm="5" lg="10" class="d-flex">
        <h2 style="text-align: left"><b>My Highlight</b></h2>
      </v-col>
    </v-row>
    <ul class="infinite-list">
      <li
        v-for="i in state.user_videos"
        class="infinite-list-item"
        @click="clickContent(i.id)"
        :key="i"
      >
        <v-col>
          <v-hover v-slot="{ isHovering, props }">
            <v-card
              class="video_content"
              :elevation="isHovering ? 12 : 2"
              :class="{ 'on-hover': isHovering }"
              v-bind="props"
            >
              <v-img
                src="https://images.unsplash.com/photo-1429514513361-8fa32282fd5f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3264&q=80"
                height="225px"
                cover
              >
                <v-card-title
                  class="
                    text-h6 text-black
                    d-flex
                    flex-column
                    justify-space-between
                  "
                  style="padding: 0px; height: 100%"
                >
                  <p class="song_info" align="start"></p>
                  <div class="d-flex justify-space-between info_box">
                    <div class="champion_info">{{ i.description }}</div>
                    <div class="view_info">{{ i.title }}</div>
                  </div>
                </v-card-title>
              </v-img>
            </v-card>
          </v-hover>
        </v-col>
      </li>
    </ul>
  </v-template>
</template>

<script>
import axios from "axios";
import { onMounted, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { reactive } from "vue";
import playlistBox from "./component/playlistBox.vue";
import myplaylistBox from "./component/myplaylistBox.vue";

export default {
  name: "MypageBox",
  components: {
    playlistBox,
    myplaylistBox,
  },
  data: () => ({
    confirm_dialog: false,
    update_dialog: false,
    add_dialog: false,
    file_name: "파일을 선택하세요.",
    message: "Hello, world",
    image:'',
  }),
  methods: {
    uploadImg() {
      console.log('들어왔다')
      var image = this.$refs['image'].files[0]

      const url = URL.createObjectURL(image)
      this.image = url
      this.state.there=false
      console.log(url)
      console.log(this.image)
    }},
  setup() {
    const store = useStore();
    // const update_dialog = false;

    // 유저 정보, 다이얼로그에 사용할 state
    const state = reactive({
      id: "",
      nickname: "",
      championCnt: "",
      userRank: "",
      user_dialog: false,
      playlist_dialog: false,
      keyword: "",
      user_videos: [],
      there:true
    });

    // store 연결해서 가져온 유저 데이터
    const user_info = reactive({
      token: null,
      id: null,
      password: null,
      nickname: null,
    });

    const token = localStorage.getItem("jwt");

    // 회원정보 수정에 사용할 state
    const update_state = reactive({
      form: {
        name: "",
        id: "",
        email: "",
        password: "",
        password_confirm: "",
        nickname: "",
        genre: "",
      },
    });

    const rule = {
      name_rule: [
        (v) => !!v || "이름은 필수 입력사항 입니다",
        (v) =>
          /^[a-zA-Z가-힣]*$/.test(v) || "이름에는 숫자가 들어갈 수 없습니다.",
      ],
      id_rule: [
        (v) => !!v || "아이디는 필수 입력사항 입니다",
        (v) =>
          /^[a-zA-Z]+[0-9]+$/.test(v) ||
          "아이디는 영문과 숫자를 포함해야 합니다.",
        (v) =>
          !((v.length <= 4) | (v.length >= 11)) ||
          "아이디는 5자 이상  10자 이하로 작성해 주세요.",
      ],
      nickname_rule: [
        (v) => !!v || "닉네임은 필수 입력사항 입니다",
        (v) =>
          /^[a-zA-Z가-힣0-9]+$/.test(v) ||
          "닉네임은 영문 또는 한글로 입력 가능합니다.",
        (v) =>
          !((v.length <= 1) | (v.length >= 11)) ||
          "닉네임은 2자 이상  10자 이하로 작성해 주세요.",
      ],
      email_rule: [
        (v) => !!v || "이메일을 입력해주세요.",
        (v) => {
          const replaceV = v.replace(/(\s*)/g, "");
          const pattern =
            /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
          return pattern.test(replaceV) || "이메일 형식으로 입력해주세요";
        },
      ],
      password_rule: [
        (v) => !!v || "비밀번호는 필수 입력사항 입니다",
        (v) =>
          /^[a-zA-Z]+[0-9]+[!@#$%^&+=]+$/.test(v) ||
          "비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다.",
        (v) =>
          !((v.length <= 7) | (v.length >= 14)) ||
          "비밀번호는 8자 이상 13자 이하로 작성해 주세요.",
      ],
      password_confirm_rule: [
        (v) => !!v || "비밀번호 확인은 필수 입력사항 입니다",
        (v) =>
          v === update_state.form.password_confirm ||
          "비밀번호가 일치하지 않습니다.",
      ],
    };

    // 회원 정보 수정
    const update = function () {
      // const validate = this.$refs.form.validate();

      const genre_list = [];
      for (let i = 0; i <= update_state.form.genre.length - 1; i++) {
        genre_list.push(update_state.form.genre[i]);
      }
      const genre_string = genre_list.join(",");

      // axios로 보낼 수정한 유저 정보
      const user = {
        name: update_state.form.name,
        id: update_state.form.id,
        email: update_state.form.email,
        password: update_state.form.password,
        nickname: update_state.form.nickname,
        genre: genre_string,
      };

      axios({
        method: "put",
        url: import.meta.env.VITE_APP_URL + "/api/v1/users/my-page",
        // url: import.meta.env.VITE_APP_URL + "/api/v1/users/",
        data: user,
      })
        .then((res) => {
          alert("회원정보 수정 성공!");
          console.log(res);

          // store와 localstorage에 수정된 정보 반영
          store.dispatch("accountStore/loginAction", {
            id: update_state.form.id,
            password: update_state.form.password,
            token: localStorage.getItem("jwt"),
          });
          store.dispatch("accountStore/saveNickname", {
            nickname: update_state.form.nickname,
          });
          localStorage.setItem("nickname", update_state.form.nickname);
          // console.log(localStorage.getItem("nickname"))
          window.location.reload(true);
        })
        .catch((err) => {
          alert(err);
        });
    };

    // 회원 탈퇴 axios
    const router = useRouter();
    const userDelete = function () {
      axios({
        method: "delete",
        url: import.meta.env.VITE_APP_URL + "/api/v1/users/my-page",
        // url : `http://localhost:8080/api/v1/users/${state.id}`
        // url: import.meta.env.VITE_APP_URL + "/api/v1/users/",
      })
        .then((res) => {
          alert("싱싱타임 탈퇴 완료!");
          console.log(res);
          localStorage.removeItem("jwt");
          localStorage.removeItem("vuex");
          localStorage.removeItem("nickname");
          state.token = false;
          router.push("/");
          window.location.reload(true);
        })
        .catch((err) => {
          alert(err);
        });
    };

    const playlist = computed(() => store.getters["playlistStore/getPickList"]);
    const addMyPlaylist = function () {
      console.log("================================================");
      console.log(playlist.value);
      const valueLength = playlist.value.length;
      for (let i = 0; i < valueLength; i++) {
        const addSong = {
          songId: playlist.value[i],
        };
        console.log(playlist.value[i]);
        axios({
          method: "post",
          // url: import.meta.env.VITE_APP_URL + "/api/v1/users/songs",
          url: import.meta.env.VITE_APP_URL + "/api/v1/users/my-page/songs",
          headers: {
            Authorization: `Bearer ${user_info.token}`,
          },
          data: addSong,
        })
          .then((res) => {
            console.log(res);
            window.location.reload(true);
          })
          .catch((err) => {
            console.log(err);
            console.log(addSong);
          });
      }
    };

    const clickContent = function (id) {
      localStorage.setItem('page', id)
      store.dispatch("contentStore/pageAction", {
            contentId: id,
          });
      router.push({
        name: "ContentsBox",
        params: { Id: id },
      });
    };


    onMounted(() => {
      // console.log(state.form.id);

      // store에서 유저 정보 가져오기
      const all = computed(() => store.getters["accountStore/getAll"]);
      user_info.token = all.value.token;
      user_info.id = all.value.id;
      user_info.password = all.value.password;
      user_info.nickname = all.value.nickname;

      const token = localStorage.getItem("jwt");
      axios({
        method: "get",
        url: import.meta.env.VITE_APP_URL + "/api/v1/users/my-page",
        // url : "http://localhost:8080/api/v1/users/me",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => {
          // console.log(33333333333)
          // console.log(res);
          // 유저 정보 저장
          state.id = res.data.userId;
          state.nickname = res.data.nickname;
          state.championCnt = res.data.championCnt;
          state.userRank = res.data.userRank;

          // 유저 정보 수정 다이얼로그에 기존 유저 정보 값 넣어두기
          update_state.form.name = res.data.name;
          update_state.form.id = res.data.userId;
          update_state.form.nickname = res.data.nickname;
          update_state.form.email = res.data.email;
          update_state.form.password = user_info.password;
          update_state.form.password_confirm = user_info.password;
          update_state.form.genre = res.data.genre;
        })
        .catch((err) => {
          // console.log(token)
          alert(err);
        });

      // 탈퇴 진행 후 화면 전환이 잘 안되어서 넣어놓은 함수
      if (!token) {
        router.push("/");
      }

      // 유저 비디오 가져오기
      axios({
        method: "get",
        url: import.meta.env.VITE_APP_URL + "/api/v1/videos/myvideo",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => {
          console.log(res);
          state.user_videos = res.data;
        })
        .catch((err) => {
          alert(err);
        });
    });

    return {
      // getUserInfo,
      // form,
      store,
      token,
      user_info,
      state,
      update_state,
      rule,
      update,
      userDelete,
      playlist,
      addMyPlaylist,
      clickContent
    };
  },
};
</script>

<style>
.outbox {
  margin-top: 20px;
}

.user_image {
  margin-top: 10px;
  padding-right: 0px;
  padding-top: 15px;
}

.user_ex {
  height: 170px;
  background-color: aliceblue;
}

.user_info {
  margin-top: 20px;
  /* line-height: 50px; */
}

.item_name {
  margin: 20px;
  /* font-size: 1rem; */
}

.item_value {
  background-color: aliceblue;
  margin: 20px;
  width: 130px;
  /* border: 10px; */
  border-radius: 30px;
  /* font-size: 0.9rem; */
}
/* .info_category{
  text-align: left;
} */

.plus {
  background-color: aliceblue;
  border-radius: 50px;
  height: 30px;
  margin-top: 23px;
  margin-left: 15px;
}

.song_inpupt {
  border: 0px;
  border-bottom: 3px solid rgb(0, 0, 0);
  margin-right: 10px;
  margin-top: 10px;
  /* height: 30px; */
  width: 100%;
}

.infinite-list .infinite-list-item {
  min-width: 335px;
  max-width: 25%;
  display: inline-block;
  cursor: pointer;
}

.video_content {
  transition: opacity 0.4s ease-in-out;
}

/* .video_content:not(.on-hover) {
  opacity: 0.6;
} */

.song_info {
  color: white;
  font-size: 15px;
  padding-left: 10px;
  margin: 0px;
}

.info_box {
  color: white;
  font-size: 12px;
  background-color: rgba(255, 255, 255, 0.2);
}

.champion_info {
  margin-left: 10px;
}

.view_info {
  margin-right: 10px;
}
.filebox label {
  display: inline-block;
  padding: 6px;
  color: black;
  font-size: small;
  font-size:inherit;
  font-display: center;
  line-height: normal;
  vertical-align: middle;
  width: 120px;
  height: 36px;
  box-shadow: 1px 2px 2px lightgrey;
  background-color: rgb(255, 253, 231);
  cursor: pointer;
  border: 1px solid #ebebeb;
  border-bottom-color: #e2e2e2;
  border-radius: .25em;
  margin-top: 30px;
}
.filebox input[type="file"] {  /* 파일 필드 숨기기 */
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip:rect(0,0,0,0);
  border: 0;
}
</style>
