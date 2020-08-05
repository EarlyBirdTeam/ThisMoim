import axios from "axios";

export default axios.create({
  // baseURL: "http://localhost/auth",
  baseURL: "http://localhost:9004",
  headers: {
    "Content-type": "application/json",
  }
});