// import { requestLogin } from "../common/api/accountAPI";

const state = {
    id:null
};

const getters = {
  getId: (state) => {
    return state.id;
  },
};

const mutations = {
    contentInfo: (state, idData) => {
    state.id = idData.contentId
  },
};

const actions = {
  pageAction: ({ commit }, idData) => {
    commit("contentInfo", idData);
  },
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
