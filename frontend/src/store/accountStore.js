// import { requestLogin } from "../common/api/accountAPI";

const state = {
  token: null,
  id: null,
  password: null,
  nickname: null,
};

const getters = {
  getToken: (state) => {
    return state.token;
  },
  getId: (state) => {
    return state.id;
  },
  getPassword: (state) => {
    return state.password;
  },
  getNickname: (state) => {
    return state.nickname;
  },
  getAll: (state) => {
    return state;
  }
};

const mutations = {
  setUserInfo: (state, loginData) => {
    state.id = loginData.id;
    state.password = loginData.password;
    state.token = loginData.token;
  },
  setNickname: (state, data) => {
    state.nickname = data.nickname;
  },
};

const actions = {
  loginAction: ({ commit }, loginData) => {
    commit("setUserInfo", loginData);
  },
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
