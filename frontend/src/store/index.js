import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import accountStore from "./accountStore";
import youtubeStore from "./youtubeStore";
import playlistStore from "./playlistStore";

export default createStore({
  modules: {
    accountStore,
    youtubeStore,
    playlistStore
  },
  plugins: [
    createPersistedState({
      paths: ["accountStore"],
    }),
  ],
});
