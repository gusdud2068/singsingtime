<template>
  <div class="footer" :class="{dark: $route.name == 'ConferencesBox'}">FooterBar</div>
</template>

<script>
export default {
  name: "FooterBar",
};
</script>

<style>
.footer {
  border: 1px solid black;
}
</style>
