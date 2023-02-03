<template>
  <div>
    <input class="form-control" @keypress.enter="getVideos" type="text" />
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "SearchBar",
  methods: {
    ...mapActions(["getVideos"]),
  },
};
</script>

<style></style>
