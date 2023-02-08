<template>
  <v-template>
    <v-row class="outbox" justify="center">
      <v-col col="5" md="3" lg="5" class="mypage_box">
        <h2 style="text-align: left"><b>My Page</b></h2>
        <v-row>
          <v-col col="3" class="user_image">
            <img class="user_ex" src="../../assets/images/user_ex.png" />
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
            </v-row>
          </v-col>
        </v-row>
      </v-col>
      <v-col col="5" md="3" lg="5" class="myplaylist_box">
        <v-row justify="space-between ">
          <h2 style="text-align: left">
            <b>My Playlist</b><v-icon>mdi-music</v-icon>
          </h2>
          <v-dialog v-model="add_dialog" persistent max-width="600px">
            <template v-slot:activator="{ on }">
              <v-btn
                color="aliceblue"
                v-on="on"
                class="plus"
                @click="add_dialog = true"
              >
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5" style="margin: 20px">Search</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-card-text>
                    <v-autocomplete
                      placeholder="노래 혹은 가수를 검색해주세요"
                      prepend-icon="mdi-magnify"
                      return-object
                    ></v-autocomplete>
                  </v-card-text>
                  <!-- <v-divider></v-divider> -->
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="add_dialog = false">
                  Close
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-row>
      </v-col>
    </v-row>
    <br />
    <v-divider></v-divider>
    <br />
    <v-row align="center" justify="center">
      <v-col sm="5" lg="10" class="d-flex">
        <h2 style="text-align: left"><b>My Highlight</b></h2>
        <div class="custom-file" style="margin-top: 25px; margin-left: 25px">
          <input id="customFile" type="file" @change="handleFileChange" />
          <label class="custom-file-label" for="customFile">{{
            file_name
          }}</label>
        </div>
      </v-col>
    </v-row>
  </v-template>
</template>

<script>
import axios from "axios";
import { onMounted } from "vue";
// import { useStore } from "vuex";
import { reactive } from "vue";

export default {
  name: "MypageBox",
  data: () => ({
    add_dialog: false,
    file_name: "파일을 선택하세요.",
    message: "Hello, world",
  }),
  setup() {
    const state = reactive({
      nickname: "",
      championCnt: "",
      userRank: "",
    });

    onMounted(() => {
      // console.log(state.form.id);

      const token = localStorage.getItem("jwt");
      axios({
        method: "get",
        url: `http://localhost:8080/api/v1/users/me`,
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => {
          console.log(res);
          state.nickname = res.data.nickname;
          state.championCnt = res.data.championCnt;
          state.userRank = res.data.userRank;
          // console.log(nickname)
          // return nickname
        })
        .catch((err) => {
          // console.log(token)
          alert(err);
        });
    });

    return {
      // getUserInfo,
      // form,
      state,
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
</style>
