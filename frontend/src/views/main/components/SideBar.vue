<template>
  <v-card>
    <v-layout>
      <v-navigation-drawer
        expand-on-hover
        rail
        class="sidebar"
        style="transform: translateX(0%)"
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

          <v-list-item prepend-icon="mdi-account" link>
            <router-link to="/mypage">
              <v-list-item title="mypage"></v-list-item>
            </router-link>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
      <v-main style="height: 84vh; overflow: scroll">
        <router-view />
      </v-main>
    </v-layout>
  </v-card>
</template>

<script>
export default {
  name: "SideBar",
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
