<template>
  <div class="footer" :class="{ dark: $route.name == 'ConferencesBox' }">
    <span style="color:lightgrey">MADEBY.BEAKOHJOE's삼성사이다[SINGSINGTIME]</span>
  </div>
</template>

<script>
export default {
  name: "FooterBar",
};
</script>

<style>
.footer {
  background-color: rgb(244, 247, 255);
}
.dark{
 background-color: black;
}
</style>
