<template>
  <div>
    <v-container fluid>
      <div class="d-flex justify-content-between">
        <h2>나의 모임목록</h2>
        <button @click="alertStore($store.state.userData)">store</button>
        {{ this.$store.state.userData }}
        <div class="d-flex">
          <v-text-field
            @keyup.enter="createChannel"
            placeholder="채널 제목"
            style="height:30px; padding:0"
            v-model="channel_name"
          ></v-text-field>
          <button
            @click="createChannel"
            style="padding:2px; margin-top:7px; height:30px; background-color:rgb(0,0,0)"
            class="white--text"
          >채널 생성</button>
        </div>
      </div>

      <v-btn icon @click="showAlbum">
        <v-icon>mdi-view-module</v-icon>
      </v-btn>
      <v-btn icon @click="showList">
        <v-icon>mdi-view-list</v-icon>
      </v-btn>

      <v-row style="width:80%; margin:auto;" v-if="howto">
        <v-col
          v-for="card in listChannels"
          :key="card.channelId"
          cols="4"
          md="4"
          @click="enterRoom(card.channelId, card.channelName)"
        >
          <v-hover v-slot:default="{ hover }">
            <v-card class="card-channel">
              <v-img
                :src="getRandomImage(card.channelName)"
                class="white--text align-end"
                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                height="300px"
              >
                <v-expand-transition>
                  <div
                    v-if="hover"
                    flex
                    class="d-flex transition-fast-in-fast-out black darken-2 v-card--reveal display-3 white--text"
                    style="height: 100%; align-items: center;
                          bottom: 0;
                          justify-content: center;
                          opacity: .5;
                          position: absolute;
                          width: 100%; "
                  >
                    <br />
                    <br />
                    <div class="btn">입장하기</div>
                  </div>
                </v-expand-transition>
                <v-card-title v-text="card.channelName"></v-card-title>
              </v-img>
            </v-card>
          </v-hover>
        </v-col>
      </v-row>
      <ul style="width:80%; margin:auto;" class="list-group" v-if="!howto">
        <li
          class="list-group-item list-group-item-action"
          v-for="item in listChannels"
          v-bind:key="item.channelId"
          style
          v-on:click="enterRoom(item.channelId, item.channelName)"
        >
          <h6 style="text-align:left">{{item.channelName}}</h6>
        </li>
      </ul>
    </v-container>
  </div>
</template>

<script>
import "../../assets/css/post.scss";
import constants from "../../lib/constants";
import SockJS from "sockjs-client";
import Stomp from "stomp-websocket";
import axios from "axios";
import http from "../../http-common.js";
import lodash from "lodash";

export default {
  data: () => ({
    channel_name: "",
    channels: [],
    howto: true,
  }),
  created() {
    this.findAllChannel();
  },
  methods: {
    showAlbum() {
      this.howto = true;
    },
    showList() {
      this.howto = false;
    },
    getRandomImage(idString) {
      return `https://picsum.photos/seed/${idString}/200/300`;
    },
    findAllChannel: function () {
      console.log(this.$store.state.userData.email);
      http
        .post(
          "/board/channels",
          { email: this.$store.state.userData.email },
          {
            headers: {
              Authorization: "Bearer " + this.$store.getters.accessToken,
            },
          }
        )
        .then((response) => {
          console.log(response);
          // prevent html, allow json array
          if (
            Object.prototype.toString.call(response.data) === "[object Array]"
          )
            this.channels = response.data;
        });
    },
    createChannel: function () {
      if ("" === this.channel_name) {
        alert("채널 이름을 입력해 주십시오.");
        return;
      } else {
        // var params = new URLSearchParams();
        // params.append("channelName", this.channel_name);
        const params = {
          channelName: this.channel_name,
          email: this.$store.state.userData.email,
        };
        console.log(params);
        // params.append("token", this.$store.getters.accessToken)
        const config = {
          headers: {
            Authorization: "Bearer " + this.$store.getters.accessToken,
          },
        };
        http
          .post("/board/channel", params, config)
          .then((response) => {
            alert(response.data.channelName + "채널 개설에 성공하였습니다.");
            this.channel_name = "";
            this.findAllChannel();
          })
          .catch((response) => {
            alert("채널 개설에 실패하였습니다.");
          });
      }
    },
    enterRoom: function (channelId, channelName) {
      localStorage.setItem("wsboard.channelId", channelId);
      localStorage.setItem("wsboard.channelName", channelName);
      location.href = "/channel/" + channelId;
    },
    alertStore() {
      console.log(this.$store.state.userData);
    },
  },

  computed: {
    listChannels() {
      return _.orderBy(this.channels, "channelName", "asc");
    },
  },
};
</script>

<style src="../../assets/css/my-component.css"></style>
<style scoped>
.card-channel {
  cursor: pointer;
  transition: opacity 2s ease-in-out;
}
.card-channel :hover {
  opacity: 0.6;
}
</style>