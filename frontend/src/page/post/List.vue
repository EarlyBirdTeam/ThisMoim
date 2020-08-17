<template>
  <div>
    <v-container fluid>
      <div class="d-flex justify-space-between head">
        <div class="head-title">나의 모임목록 </div>
        <div class="d-flex">
          <button class="circleScaleBtn" @click="openModal"><span>모임 생성</span></button>
          <!-- <v-text-field @keyup.enter="createChannel" placeholder="채널 제목" v-model="channel_name"></v-text-field>
          <button @click="createChannel" class="circleScaleBtn"><span>채널 생성</span></button> -->
        </div>
      </div>
      <v-row>
        <div class="p-3" v-if="channels.length === 0">
          <span>생성하거나 가입된 모임이 없습니다 🤦‍♀️🤦‍♂️      </span>
          <a @click="openModal">만들기</a>
        </div>
        <v-col
          v-for="card in channels"
          :key="card.channelId"
          cols="4"
          md="3"
          @click="enterRoom(card.channelId, card.channelName)"
        >
          <v-card class="card-channel">
            <v-img
              :src="getRandomImage(card.channelName)"
              class="white--text align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="300px"
            >
              <v-card-title v-text="card.channelName"></v-card-title>
            </v-img>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <v-dialog max-width="600px" persistent v-model="modal">
      <v-card>
      <v-card-title>
        <h3>모임 생성</h3>
      </v-card-title>
      <v-card-text>
        <v-form ref="form" v-model="valid" @submit.prevent>
          <div class="mail-form">
            <v-text-field
              label="모임 이름"
              v-model="channel_name"
              prepend-icon="mdi-account-supervisor"
              :rules="rules"
              counter="20"
               @keyup.enter="createChannel(valid)"
            ></v-text-field>
          </div>
        </v-form>
        <div class="text-center">
          <v-btn text class="primary white--text mx-2 mt-3" @click="createChannel(valid)">생성</v-btn>
          <v-btn text class="primary white--text mx-2 mt-3" @click="close">닫기</v-btn>
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
  </div>
</template>
 
<script>
import "../../assets/css/post.scss";
import constants from "../../lib/constants";
import SockJS from "sockjs-client";
import Stomp from "stomp-websocket";
import axios from "axios";
import http from "../../http-common.js";

export default {
  data: () => ({
    channel_name: "",
    channels: [],
    modal: false,
    rules: [v => ((4 <= v.length) && (v.length<= 20 ))|| '모임 이름은 4-20자여야 합니다!'],
    valid: false,
  }),
  created() {
    this.findAllChannel();
  },
  methods: {
    getRandomImage(idString) {
      return `https://picsum.photos/seed/${idString}/200/300`;
    },
   findAllChannel: function () {
      if(this.$cookie.get('AccessToken') === null){
        return
      }
      http.post("/board/channels", {email: this.$store.state.userData.email}, {
          headers: {
            "Authorization" : "Bearer " + this.$store.getters.accessToken
          }
      }
      ).then((response) => {
        console.log(response);
        // prevent html, allow json array
        if (Object.prototype.toString.call(response.data) === "[object Array]")
          this.channels = response.data;
      });
    },
    createChannel: function (valid) {
      if(this.$cookie.get('AccessToken') === null){
        return
      }
      if (!valid) {
        alert("모임 이름을 입력해 주십시오.");
        return;
      } else {
        // var params = new URLSearchParams();
        // params.append("channelName", this.channel_name);
        const params = {
          channelName: this.channel_name,
          email: this.$store.state.userData.email,
        }
        console.log(params);
        // params.append("token", this.$store.getters.accessToken)
        const config = {
          headers: {
            "Authorization" : "Bearer " + this.$store.getters.accessToken
          }
        }
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
      this.modal=false;
    },
    enterRoom: function (channelId, channelName) {
      localStorage.setItem("wsboard.channelId", channelId);
      localStorage.setItem("wsboard.channelName", channelName);
      location.href = "/channel/" + channelId;
    },
    openModal() {
      if(this.$cookie.get('AccessToken') === null){
        // 로그인 되어있지 않은 사용자 -> 로그인 모달 
        this.$store.commit("toggleModal");
        return
      }
      this.modal = true;
    },
    close() {
      this.modal = false;
    },
  },
};
</script>

<style src="../../assets/css/my-component.css"></style>
<style scoped>
.head {
  height: 40px;
}
.card-channel {
  cursor: pointer;
  transition: opacity 2s ease-in-out;
}
.card-channel :hover {
  opacity: 0.6;
}
.head-title {
  font-size: 1.875em;
  font-weight: 600;
}
</style>