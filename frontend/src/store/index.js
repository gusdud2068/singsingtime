import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import accountStore from "./accountStore";
import youtubeStore from "./youtubeStore";

export default createStore({
  modules: {
    accountStore,
    youtubeStore,
  },
  plugins: [
    createPersistedState({
      paths: ["accountStore"],
    }),
  ],
});
