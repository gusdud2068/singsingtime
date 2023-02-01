<template>
  <v-app>
    <MainBox />
  </v-app>
</template>

<script>
import MainBox from "./views/main/main.vue";

export default {
  name: "App",
  components: {
    MainBox,
  },
};
</script>

<style>
html {
  overflow: hidden !important;
  scrollbar-width: none;
  -ms-overflow-style: none;
}

html::-webkit-scrollbar {
  width: 0;
  height: 0;
}
.v-application__wrap {
  min-height: fit-content;
}
</style>
