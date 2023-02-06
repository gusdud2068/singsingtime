import { createStore } from "vuex";
import createPersistedState from "vuex-persistedstate";
import accountStore from "./accountStore";
import nicknameStore from "./nicknameStore";
import youtubeStore from "./youtubeStore";

export default createStore({
  modules: {
    accountStore,
    nicknameStore,
    youtubeStore,
  },
  plugins: [
    createPersistedState({
      paths: ["nicknameStore"],
    }),
  ],
});
