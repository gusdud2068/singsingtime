<template>
  <v-container class="pa-4 text-center">
    <v-row class="fill-height" align="center" justify="center">
      <template v-for="(item, i) in items" :key="i">
        <v-col>
          <v-hover v-slot="{ isHovering, props }">
            <v-card
              class="video_content"
              :elevation="isHovering ? 12 : 2"
              :class="{ 'on-hover': isHovering }"
              v-bind="props"
            >
              <v-img :src="item.img" height="225px" cover>
                <v-card-title
                  class="text-h6 text-black d-flex flex-column justify-space-between"
                  style="padding: 0px; height: 100%"
                >
                  <p class="song_info" align="start">비 - 폴킴</p>
                  <div class="d-flex justify-space-between info_box">
                    <div class="champion_info">챔피온 : 광주김범수</div>
                    <div class="view_info">1,809 View</div>
                  </div>
                </v-card-title>
              </v-img>
            </v-card>
            <p align="start">{{ item.title }}</p>
          </v-hover>
        </v-col>
      </template>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "ContentBox",
  data: () => ({
    icons: ["mdi-rewind", "mdi-play", "mdi-fast-forward"],
    items: [
      {
        title: "01/07 광주김범수 대결 영상ㄷ",
        img: "https://images.unsplash.com/photo-1429514513361-8fa32282fd5f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=3264&q=80",
      },
    ],
    transparent: "rgba(255, 255, 255, 0)",
  }),
};
</script>

<style>
.video_content {
  transition: opacity 0.4s ease-in-out;
}

.video_content:not(.on-hover) {
  opacity: 0.6;
}

.show-btns {
  color: rgba(255, 255, 255, 1) !important;
}

.song_info {
  color: white;
  font-size: 15px;
  padding-left: 10px;
  margin: 0px;
}

.info_box {
  color: white;
  font-size: 12px;
  background-color: rgba(255, 255, 255, 0.2);
}

.champion_info {
  margin-left: 10px;
}

.view_info {
  margin-right: 10px;
}
</style>
