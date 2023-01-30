<template>
  <HeaderBar />
  <SideBar />
  <FooterBar />
  <!-- <LogIn />
  <SignUp /> -->
</template>
<script>
import HeaderBar from "./components/HeaderBar.vue";
import SideBar from "./components/SideBar.vue";
import FooterBar from "./components/FooterBar.vue";
// import LogIn from "./components/LogIn.vue";
// import SignUp from "./components/SignUp.vue";

export default {
  name: "MainBox",
  components: {
    HeaderBar,
    SideBar,
    FooterBar,
    // LogIn,
    // SignUp,
  },
};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
