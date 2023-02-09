import axios from "axios";

const state = {
  // data 저장소 ~= methods
  userInput: "",
  videos: [],
  video: {},
  open:false,
};

const getters = {
  // computed
  userInput(state) {
    return state.userInput;
  },
  videos(state) {
    return state.videos;
  },
  video(state) {
    return state.video;
  },
};

const mutations = {
  // data를 변경 ~= methods (동기적)
  setUserInput(state, data) {
    state.userInput = data;
  },
  setVideos(state, data) {
    state.videos = data;
  },
  setVideo(state, data) {
    state.video = data;
  },
};

const actions = {
  // data를 변경 ~= methods (비동기적)
  getVideos(context, e) {
    context.commit("setUserInput", e.target.value);
    // 1. 입력된 검색어를 가지고,
    const baseURL = "https://www.googleapis.com/youtube/v3/search";
    const API_KEY = process.env.VUE_APP_YOUTUBE_API_KEY;
    // 2. Youtube API에 요청을 보내어
    axios
      .get(baseURL, {
        params: {
          // key, part, q
          key: API_KEY,
          part: "snippet",
          type: "video",
          q: context.getters.userInput,
          maxResults: 10,
        },
      })
      .then((response) => {
        context.commit("setVideos", response.data.items);
      });
    // 3. 검색어로 검색한 결과를 가져옴
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
