<template>
  <h2><b>Today's Highlight</b></h2>
  <v-carousel v-model="model" align="center">
    <v-carousel-item v-for="(color, i) in colors" :key="color">
      <v-sheet :color="color" height="100%" width="50%" tile>
        <v-row
          class="fill-height"
          align="start"
          justify="space-between"
          style="padding-top: 20px"
        >
          <div class="text-h6" style="margin-left: 20px">View 1,332</div>
          <div class="text-h6">
            오늘의 {{ i + 1 }}번째 영상<v-icon>mdi-heart</v-icon>
          </div>
          <div class="text-h6" style="margin-right: 20px">782 UP</div>
        </v-row>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
</template>

<script>
export default {
  name: "HighlightBox",
  data: () => ({
    model: 0,
    colors: ["primary", "secondary", "yellow darken-2", "red", "orange"],
  }),
};
</script>

<style></style>
