<template>
  <v-text-field
    class="card col-md-3"
    dark
    label="노래를 검색하세요 :) "
    color="#BDBDBD"
    @keyup.enter="onInputKeyword"
  />
</template>

<script>
export default {
  name: "Search",
  methods: {
    onInputKeyword: function (event) {
      console.log(event.target.value);
      this.$emit("input-search", event.target.value);
      event.target.value = "";
    },
  },
};
</script>

<style scoped>
.card {
  position: relative;
  left: 36%;
  top: -30%;
  width: 33%;
  padding: 2vh 1vw 0 1vw;
  background: rgba(77, 14, 114, 0.47);
  border: 2px solid #ffffff;
}
</style>
