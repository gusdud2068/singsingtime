<template>
  <v-card>
    <!-- <v-system-bar color="deep-purple darken-3"></v-system-bar> -->
    <v-bar
      class="header_bar"
      color="primary"
      :class="{ dark: $route.name == 'ConferencesBox' }"
    >
      <img
        class="logo"
        src="../../../assets/images/logo.png"
        v-if="$route.name == 'ConferencesBox'"
      />
      <button>
        <img
          class="logo"
          src="../../../assets/images/logo.png"
          @click="clickLogo"
          v-if="$route.name !== 'ConferencesBox'"
        />
      </button>
      <!-- 
          <v-app-bar-nav-icon
          variant="text"                
          @click.stop="drawer = !drawer"
        ></v-app-bar-nav-icon> 
      -->
      <v-spacer></v-spacer>
      <div style="width: 60%; padding-right: 20px">
        <div style="text-align: right">
          <h4 v-show="state.token">{{ user_nickname.nickname }}님 환영합니다</h4>
        </div>
        <div style="display: flex; width: 100%">
          <v-text-field
            hide-details
            placeholder="검색"
            single-line
            style="margin: 20px; width: 40%"
            v-show="state.search"
            @keydown.enter="search_thing"
          ></v-text-field>
          <div style="width: 100%; height: 96px" v-show="!state.search"></div>
          <v-btn
            @click="search_hover"
            class="inline"
            variant="text"
            icon="mdi-magnify"
            style="margin-top: 25px"
          ></v-btn>

          <v-dialog v-model="signup_dialog" persistent max-width="600px">
            <template v-slot:activator="{ on }">
              <v-btn
                class="inline"
                variant="text"
                @click="logout"
                v-on="on"
                v-show="state.token"
                :class="{ dark: $route.name == 'ConferencesBox' }"
              >
                로그아웃
              </v-btn>

              <v-btn
                class="inline"
                variant="text"
                @click="signup_dialog = true"
                v-on="on"
                v-show="!state.token"
              >
                회원가입
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <img
                  class="logo"
                  src="../../../assets/images/login_logo.png"
                  style="width: 220px; margin-left: 180px"
                />
                <v-spacer></v-spacer>
                <span class="text-h5" style="margin-left: 22px"
                  ><b>회원가입</b></span
                >
              </v-card-title>
              <v-card-text style="padding-bottom: 0px">
                <v-form ref="form" @submit.prevent="save">
                  <v-row>
                    <v-col
                      cols="6"
                      style="padding-top: 0px; padding-bottom: 0px"
                    >
                      <v-text-field
                        v-model="signup_state.form.name"
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
                        v-model="signup_state.form.id"
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
                        v-model="signup_state.form.nickname"
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
                        v-model="signup_state.form.password"
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
                        v-model="signup_state.form.password_confirm"
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
                        v-model="signup_state.form.genre"
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
                <v-spacer></v-spacer>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="signup_dialog = false"
                >
                  Close
                </v-btn>
                <v-btn
                  color="blue darken-1"
                  text
                  @click="(signup_dialog = false), save()"
                >
                  Save
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </div>
      </div>

      <!--로그인-->
      <v-dialog v-model="login_dialog" persistent max-width="600px">
        <template v-slot:activator="{ on }">
          <v-btn
            class="inline"
            variant="text"
            @click="login_dialog = true"
            v-on="on"
            v-show="!state.token"
          >
            로그인
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <img
              class="logo"
              src="../../../assets/images/login_logo.png"
              style="width: 220px; margin-left: 180px"
            />
            <v-spacer></v-spacer>
            <span class="text-h5" style="margin-left: 22px"><b>로그인</b></span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="6">
                  <v-text-field
                    label="아이디"
                    v-model="login_state.form.id"
                    @keyup.enter="(login_dialog = false), clickLogin()"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field
                    label="비밀번호"
                    type="password"
                    v-model="login_state.form.password"
                    required
                    @keyup.enter="(login_dialog = false), clickLogin()"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="login_dialog = false">
              Close
            </v-btn>
            <v-btn
              color="blue darken-1"
              text
              @click="(login_dialog = false), clickLogin()"
            >
              Save
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <!--
        <v-btn variant="text" icon="mdi-dots-vertical"></v-btn>
    -->
    </v-bar>

    <v-navigation-drawer v-model="drawer" location="bottom" temporary>
      <v-list :items="items"></v-list>
    </v-navigation-drawer>
    <v-main></v-main>
  </v-card>
</template>

<script>
import { reactive, onMounted } from "vue";
import axios from "axios";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
// import http from "@/libs/http";
export default {
  name: "HeaderBar",
  data() {
    return {
      signup_dialog: false,
      login_dialog: false,
      drawer: false,
      group: null,
      items: [
        {
          title: "Foo",
          value: "foo",
        },
        {
          title: "Bar",
          value: "bar",
        },
        {
          title: "Fizz",
          value: "fizz",
        },
        {
          title: "Buzz",
          value: "buzz",
        },
      ],
    };
  },
  computed: {
    my_nickname() {
      return useStore().getters["nicknameStore/getNickname"];
    },
  },

  watch: {
    group() {
      this.drawer = false;
    },
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      search: false,
      token: localStorage.getItem("jwt"),
    });

    const signup_state = reactive({
      form: {
        name: "",
        id: "",
        password: "",
        password_confirm: "",
        nickname: "",
        genre: "",
      },
    });
    const login_state = reactive({
      form: {
        id: "",
        password: "",
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
          v === signup_state.form.password_confirm ||
          "비밀번호가 일치하지 않습니다.",
      ],
    };

    // 회원가입
    const save = function () {
      // const validate = this.$refs.form.validate();

      const genre_list = [];
      for (let i = 0; i <= signup_state.form.genre.length - 1; i++) {
        genre_list.push(signup_state.form.genre[i]);
      }
      const genre_string = genre_list.join(",");
      const user = {
        name: signup_state.form.name,
        id: signup_state.form.id,
        password: signup_state.form.password,
        nickname: signup_state.form.nickname,
        genre: genre_string,
      };

      axios({
        method: "post",
        url: import.meta.env.VITE_APP_URL + "/api/v1/users/",
        data: user,
      })
        .then((res) => {
          (signup_state.form.name = ""),
            (signup_state.form.id = ""),
            (signup_state.form.nickname = ""),
            (signup_state.form.password = ""),
            (signup_state.form.genre = "");
          alert("회원가입 성공!");
          console.log(res);
        })
        .catch((err) => {
          alert(err);
        });
    };

    // 로그인
    const user_nickname = reactive({
      nickname : store.getters["nicknameStore/getNickname"],
    })
    const clickLogin = function () {
      const user = {
        id: login_state.form.id,
        password: login_state.form.password,
      };
      axios({
        method: "post",
        url: "https://i8c105.p.ssafy.io/api/v1/auth/login",
        data: user,
      })
        .then((res) => {
          alert("로그인 성공!");
          // console.log(res);
          console.log("submit");
          // console.log("accessToken " + store.getters["accountStore/getToken"]);
          state.token = res.data.accessToken;
          store.dispatch("accountStore/loginAction", {
            id: login_state.form.id,
            password: login_state.form.password,
            token: state.token,
          });
          console.log(2222222)
          localStorage.setItem("jwt", res.data.accessToken);
          axios({
            method: "get",
            url: `https://i8c105.p.ssafy.io/api/v1/users/me`,
            headers: {
              Authorization: `Bearer ${state.token}`,
            },
          })
            .then((res) => {
              store.dispatch("nicknameStore/saveNickname", {
                nickname: res.data.nickname,
              });
              console.log(res)

            })
            .catch((err) => {
              alert(err);
            });
          window.location.reload(true);
        })
        .catch(() => {
          alert("올바르지않은 아이디 혹은 비밀번호 입니다.");
        });
    };

    //로그아웃
    const logout = function () {
      localStorage.removeItem("jwt");
      localStorage.removeItem("vuex");
      state.token = false;
      console.log(state.token);
      window.location.reload(true);
      router.push("/");
    };

    // 아이디 중복 체크
    const id_check = function () {
      // console.log(state.form.id);
      axios({
        method: "get",
        url: `https://i8c105.p.ssafy.io/api/v1/users/id/${signup_state.form.id}`,
      })
        .then((res) => {
          console.log(res.data.message);
          alert(res.data.message);
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    };

    // 닉네임 중복 체크
    const nickname_check = function () {
      axios({
        method: "get",
        url: `https://i8c105.p.ssafy.io/api/v1/users/nickname/${signup_state.form.nickname}`,
      })
        .then((res) => {
          console.log(res.data.message);
          alert(res.data.message);
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    };

    const clickLogo = function () {
      if (router.name !== "ConferencesBox") {
        router.push("/");
      }
    };

    const search_hover = () => {
      state.search = !state.search;
      console.log(state.search);
    };
    const search_thing = () => {
      state.search = false;
    };

    onMounted(() => {
      // console.log(loginForm.value)
    });

    return {
      search_hover,
      state,
      signup_state,
      login_state,
      rule,
      save,
      user_nickname,
      clickLogin,
      logout,
      id_check,
      nickname_check,
      search_thing,
      clickLogo,
    };
  },
};
</script>

<style>
.inline {
  margin: 10px;
  margin-top: 30px;
}
.header_bar {
  display: flex;
  background-color: rgb(244, 247, 255);
}
.dark {
  background-color: black;
  color: white;
}
.logo {
  height: 70px;
  width: 140px;
  margin: 10px;
  margin-bottom: 16px;
}
.inputdata {
  margin-top: 20px;
  margin-left: 50px;
  margin-right: 150px;
  height: 50px;
  width: 50%;
}
.btnlogin {
  margin: auto;
}
</style>
