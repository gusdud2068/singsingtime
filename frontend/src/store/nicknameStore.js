// import { requestLogin } from "../common/api/accountAPI";

const state = {
  nickname: null,
};

const getters = {
  getNickname: (state) => {
    return state.nickname;
  },
};

const mutations = {
  setNickname: (state, data) => {
    state.nickname = data.nickname;
  },
};

const actions = {
  saveNickname: ({ commit }, data) => {
    commit("setNickname", data);
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
