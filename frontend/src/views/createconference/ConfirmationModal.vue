<template>
  <Modal ref="baseModal">
    <div class="content-container">
      <img
        class="logo"
        src="../../assets/images/login_logo.png"
        style="width: 220px; margin-bottom: 5px"
      />
      <h4>방 만들기</h4>
      <div style="padding-top: 10px; padding-bottom: 10px">
        <v-text-field
          v-model="state.conferencename"
          label="방 이름"
          @keyup.enter="confirm"
        ></v-text-field>
      </div>
    </div>
    <div class="buttons-container" style="float: right">
      <button class="btn confirm" @click="confirm">확인</button>
      <button class="btn cancel" @click="cancel">취소</button>
    </div>
  </Modal>
</template>

<script>
import Modal from "@/views/createconference/conponents/Modal.vue";
import { ref, reactive } from "vue";

export default {
  name: "ConfirmationModal",
  components: {
    Modal,
  },
  props: {
    content: Array,
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
      window.location.reload(true);
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
.content-container {
  text-align: center;
}
.buttons-container {
  /* border: 1px solid black; */
  width: fit-content;
  margin: auto;
}
.cancel {
  margin-left: 15px;
}
</style>
