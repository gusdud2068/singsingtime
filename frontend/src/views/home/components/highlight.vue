<template>
  <h2><b>Today's Highlight</b></h2>
  <carousel
    class=""
    :items-to-show="3.95"
    :wrap-around="true"
    :transition="500"
  >
    <slide v-for="slide in state.highlights[0]" :key="slide">
      <v-col class="carousel_item" @click="clickContent(slide)">
        <div class="text-h6">
          오늘의 {{ slide.id }}번째 영상<v-icon color="red">mdi-heart</v-icon>
        </div>
        <video
          :src="slide.url"
          width="300"
          height="200"
          disabled
        ></video>
        <v-row align="end" justify="space-between" style="padding: 10px">
          <div class="text-h6" style="margin-left: 20px">{{ slide.viewCnt }}</div>
          <div class="text-h6" style="margin-right: 20px">{{ slide.likeCnt }}</div>
        </v-row>
      </v-col>
    </slide>

    <template #addons>
      <navigation />
      <pagination />
    </template>
  </carousel>
</template>

<script>
import { defineComponent } from "vue";
import { reactive, onMounted } from "vue";
import "vue3-carousel/dist/carousel.css";
import { Carousel, Slide, Pagination, Navigation } from "vue3-carousel";
import { useRouter } from "vue-router";
import axios from "axios";

export default defineComponent({
  name: "HighlightBox",
  data: () => ({
    model: 0,
    colors: ["primary", "secondary", "yellow darken-2", "red", "orange"],
  }),
  components: {
    Carousel,
    Slide,
    Pagination,
    Navigation,
  },
  setup() {
    const router = useRouter();
    const state = reactive({
      highlights:[],
    });
    const clickContent = function (id) {
      router.push({
        name: "ContentsBox",
        params: { Id: "highlight" + id },
      });
    };

    onMounted(() => {
      axios({
        method: "get",
        url: import.meta.env.VITE_APP_URL + "/api/v1/videos/daily-video",
      })
        .then((res) => {
          // console.log(res);
          state.highlights.push(res.data);
          // console.log(state.highlights[0][0].url)
          })
        .catch((err) => {
          console.log(err);
        });
      
    });
    return { clickContent, state };

  },
});
</script>

<style scoped>
.carousel__slide {
  padding: 5px;
}

.carousel__viewport {
  perspective: 2000px;
}

.carousel__track {
  transform-style: preserve-3d;
}

.carousel__slide--sliding {
  transition: 0.5s;
}

.carousel__slide {
  opacity: 0.9;
  transform: rotateY(10deg) scale(0.9);
  filter: brightness(60%);
  /* 가운데 왼쪽 끝 */
}

.carousel__slide--prev {
  opacity: 1;
  transform: rotateY(-20deg) scale(0.95);
  filter: brightness(90%);
  /* 가운데 왼쪽 */
}

.carousel__slide--next {
  opacity: 1;
  transform: rotateY(20deg) scale(0.95);
  filter: brightness(90%);
  /* 가운데 오른쪽 */
}

.carousel__slide--active {
  opacity: 1;
  transform: rotateY(0) scale(1.1);
  filter: brightness(100%);
}
.carousel__slide--active :hover {
  cursor: pointer;
}
.carousel_item {
  margin: 0;
  width: 500px;
  height: 300px;
  padding: 10px;
  padding-bottom: 40px;
  background-color: skyblue;
}
</style>
