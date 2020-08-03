import axios from "axios";

export default axios.create({
  baseURL: "http://i3a510.p.ssafy.io/api",
  // headers: {
  //   "Content-type": "application/json",
  // }
});