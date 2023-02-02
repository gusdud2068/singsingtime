<template>
  <v-card>
    <v-layout>
      <v-navigation-drawer
        expand-on-hover
        rail
        class="sidebar"
        style="transform: translateX(0%)"
        v-if="$route.name !== 'ConferencesBox'"
      >
        <!-- <v-list>
          <v-list-item
            prepend-avatar="https://randomuser.me/api/portraits/women/85.jpg"
            title="Sandra Adams"
            subtitle="sandra_a88@gmailcom"
          ></v-list-item>
        </v-list> -->

        <v-list density="compact" nav>
          <v-list-item prepend-icon="mdi-home" link>
            <router-link to="/">
              <v-list-item title="Home"></v-list-item>
            </router-link>
          </v-list-item>

          <v-list-item prepend-icon="mdi-medal" link>
            <router-link to="/rank">
              <v-list-item title="rank"></v-list-item>
            </router-link>
          </v-list-item>

          <v-list-item prepend-icon="mdi-forum" link>
            <router-link to="/community">
              <v-list-item title="community"></v-list-item>
            </router-link>
          </v-list-item>

          <v-list-item prepend-icon="mdi-account" link v-show="state.token">
            <router-link to="/mypage">
              <v-list-item title="mypage"></v-list-item>
            </router-link>
          </v-list-item>
          <v-list-item
            @click="handleClick"
            prepend-icon="mdi-account"
            link
            v-show="state.token"
          >
            <v-list-item title="create"></v-list-item>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
      <v-main style="height: 84vh; overflow: scroll">
        <ConfirmationModal ref="modal" :content="modalContent" />
        <router-view />
      </v-main>
    </v-layout>
  </v-card>
</template>

<script>
import { reactive } from "vue";
import { ref } from "vue";
import ConfirmationModal from "../../createconference/ConfirmationModal.vue";

export default {
  name: "SideBar",
  components: {
    ConfirmationModal,
  },
  setup() {
    const state = reactive({
      token: localStorage.getItem("jwt"),
    });
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
      state,
      modal,
      modalContent,
      result,
      handleClick,
    };
  },
};
</script>

<style>
.sidebar {
  height: 80vh;
  background-color: rgb(244, 247, 255);
  padding: 20px;
}
.side_logo {
  height: 70px;
  width: 140px;
}
a {
  text-decoration: none;
}
.side {
  width: 5vw;
}
</style>
