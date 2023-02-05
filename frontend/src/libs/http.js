import axios from "axios";
// axios 객체 생성

export default axios.create({
  baseURL: import.meta.env.VITE_APP_URL,
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});
