import { createStore } from "vuex";
import accountStore from "./accountStore";
import youtubeStore from "./youtubeStore";

export default createStore({
  modules: {
    accountStore,
    youtubeStore,
  },
});
