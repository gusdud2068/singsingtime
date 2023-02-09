import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import accountStore from "./accountStore";
import youtubeStore from "./youtubeStore";
import contentStore from "./contentStore";
import playlistStore from "./playlistStore";

export default createStore({
  modules: {
    accountStore,
    youtubeStore,
    contentStore,
    playlistStore
  },
  plugins: [
    createPersistedState({
      paths: ["accountStore"],
    }),
  ],
});
