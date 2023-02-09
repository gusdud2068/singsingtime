// import { requestLogin } from "../common/api/accountAPI";

const state = {
  pick_list: [],
};

const getters = {
  getPickList: (state) => {
    return state.pick_list;
  },
};

const mutations = {
  setPickList: (state, pickData) => {
    state.pick_list.push(pickData.songId)
  },
};

const actions = {
  addPickList: ({ commit }, pickData) => {
    commit("setPickList", pickData);
  },

};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions,
};
