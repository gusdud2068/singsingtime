<template>
  <Modal ref="baseModal">
    <div class="containers">
      <div class="row align-items-center">
        <div class="col-12">
          <h1 class="text-center">Youtube Search</h1>
          <button class="btn cancel" @click="cancel">취소</button>
          <SearchBar />
        </div>
      </div>
        <div class="col-4" @click="cancel">
          <VideoList />
        </div>
    </div>
    
  </Modal>
</template>

<script>
import SearchBar from "./components/SearchBar.vue";
import VideoList from "./components/VideoList.vue";
import Modal from "./components/Modal.vue";
import { ref, reactive } from "vue";

export default {
  name: "App",
  components: {
    SearchBar,
    VideoList,
    Modal,
  },
  setup() {
    // 자식 컴포넌트를 핸들링하기 위한 ref
    const baseModal = ref(null);
    // Promise 객체를 핸들링하기 위한 ref
    const resolvePromise = ref(null);
    const state = reactive({
      conferencename: "",
    });
    const show = () => {
      // baseModal을 직접 컨트롤합니다.
      baseModal.value.open();
      // Promise 객체를 사용하여, 현재 모달에서 확인 / 취소의
      // 응답이 돌아가기 전까지 작업을 기다리게 할 수 있습니다.
      return new Promise((resolve) => {
        // resolve 함수를 담아 외부에서 사용합니다.
        resolvePromise.value = resolve;
      });
    };

    const confirm = () => {
      baseModal.value.close();
      resolvePromise.value(true);
      const url = "#/conferences/" + state.conferencename + "/";
      window.open(url);
      state.conferencename = "";
    };

    const cancel = () => {
      baseModal.value.close();
      resolvePromise.value(false);
      state.conferencename = "";
    };
    return { state, baseModal, show, confirm, cancel };
  },
};
</script>

<style>
.containers {
  width: 100%;
  height: 90vh;
}
</style>
