<template>
  <div style=" padding:0px; ">
    <div
      style="height:700px; 
background: linear-gradient(90deg, #efd5ff 0%, #515ada 100%);"
    >
    
      <div
        class="d-flex white--text"
        style=" font-size:70px; vertical-align:middle; padding:100px; "
      >
        <div style="width:50%; padding:50px; padding-top:50px;">
          어디서든 이거모임!
          <p />




          <h4
            style=" font-size:30px;"
          >이거모임은 어디서든 협업이 가능하게 만들어드립니다 이거모임에서 생성한 채널에 접속하여 팀원들과 함께 다양한 기능을 사용하여 협업하세요</h4>
          <router-link v-bind:to="{name:constants.URL_TYPE.USER.JOIN}" class="white--text magnifyBorder" style="font-size:20px;">회원가입</router-link>
        </div>
        <div style="width:50%; padding:60px; padding-top:10px;">
          <img style=" right:0; width:100%; height:100%; " src="../../assets/img/EnterFirst.png" />
        </div>
      </div>

    </div>
  

    <section style="height:600px;">
      <h2 class="white--text" style="text-align:center; background-color:black">이거모임에서 다양한 사람들과 협업해보세요</h2>
      <v-card class="d-inline-block mx-auto" style=" width:80%">
      <div
        class="d-flex "
        style=" font-size:70px; vertical-align:middle; padding:100px;"
      >
        <div style="width:50%; padding: 50px; ">
          어디서든 이거모임!
          <p />
          <h4 style=" font-size:30px;">이거모임은 포스트잇, 캘린더, 칸반보드 등의 기능을 제공하여 다양한 사람들과의 협업을 도와드립니다</h4>
        </div>
        <div style="width:50%; padding:60px; padding-top:90px;">
          <img style="width:100%" src="../../assets/img/Exampleboard.png" />
        </div>
      </div>
  </v-card>


      <!-- <v-container fluid>
        <h2>공개채널</h2>
        <v-row>
          <v-col cols="1"></v-col>
          <v-col cols="10">
            <v-slide-group multiple show-arrows>
              <v-slide-item v-for="card in channels" :key="card.channelId">
                <v-dialog width="350px">
                  <template v-slot:activator="{ on, attrs }">
                    <v-hover v-slot:default="{ hover }">
                      <v-card v-bind="attrs" v-on="on" class="card-channel" @click="NeedLogin">
                        <v-img
                          :src="getRandomImage(card.channelName)"
                          class="white--text align-end"
                          gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                          height="300px"
                        >
                          <v-expand-transition>
                            <div
                              v-if="hover"
                              class="transition-fast-in-fast-out orange darken-2 v-card--reveal display-3 white--text"
                              style="height: 100%; align-items: center; bottom: 0;  justify-content: center; opacity:.5; position: absolute; width: 100%;"
                            >저희는 이런 모임입니다아아아~~~</div>
                          </v-expand-transition>

                          <v-card-title v-text="card.channelName"></v-card-title>
                        </v-img>
                      </v-card>
                    </v-hover>
                  </template>
                  <v-card style="width:350px height:200px">
                    <v-card-title>로그인</v-card-title>
                    <v-card-text style=" background-color:white padding:0 height:120px">
                      <Login style="height:120px" />
                    </v-card-text>
                  </v-card>
                </v-dialog>
              </v-slide-item>
            </v-slide-group>
          </v-col>
          <v-col cols="1"></v-col>
        </v-row>
      </v-container>-->
      <!-- v-for로 카드 -->
    </section>
    <br/><br/><br/><br/><br/>
    <v-card class="d-inline-block mx-auto" style=" width:80%">
        <section>

  <div
        class="d-flex black--text"
        style=" font-size:70px; vertical-align:middle; padding:100px; "
      >
        <div style="width:50%; padding-top: 50px; ">
             <v-carousel style="border-radius: 100%; width:80%;  height:80%;" cycle hide-delimiters show-arrows-on-hover>
          <v-carousel-item style=""  v-for="(slide, i) in slides" :key="i" :src="slide.src"></v-carousel-item>
        </v-carousel>
        </div>
        <div style="width:100%; padding:60px; padding-top:90px;">
        열정적인 이거모임!
          <p />
          <h4 style=" font-size:30px;">이거모임은 열정적인 개발자들과 함께 하고 있습니다</h4>
          <!-- <router-link class="black--text" v-bind:to="{name:constants.URL_TYPE.POST.INTRODUCE}">더 알아보기</router-link> -->
        </div>
      </div>


     
      </section>
</v-card>




<v-card class="d-inline-block mx-auto" style=" width:80%">
    <section  class="d-flex">
       
        <div style="width:50%; padding:50px; padding-top:90px;">
          <h4 style=" font-size:30px;">이거모임은 최상의 서비스를 제공하기위해 다양한 기술들을 통해 구현하였습니다
          </h4>
        </div>
        <div style="width:50%; padding:60px; padding-top:10px;">
           <img style="width:80%" src="../../assets/img/Used.png" />
        </div>
    

      <div></div>
     
    </section>
</v-card>
<div>
    <footer class="d-flex middle" style="text-align:center">
      <router-link class="d-flex black--text" v-bind:to="{name:constants.URL_TYPE.POST.INTRODUCE}">이거모임은</router-link>
      <p class="d-flex">
        <strong>개인정보처리방침</strong>
      </p>
      <p class="d-flex">이용약관</p>
    </footer>
  </div></div>
</template>

<script>
import "../../assets/css/post.scss";
import constants from "../../lib/constants";
import SockJS from "sockjs-client";
import Stomp from "stomp-websocket";
import axios from "axios";
import http from "../../http-common.js";
import LoginModal from "../../components/common/LoginModal";
import Login from "../user/Login";
import img1 from "../../assets/img/study1.jpg";
import img2 from "../../assets/img/study2.jpg";
import img3 from "../../assets/img/study3.jpg";
import img4 from "../../assets/img/study4.jpg";

export default {
  components: { LoginModal, Login },
  data: () => ({
    channels: [],
    isLoggin: false,
    slides: [
      {
        src: img1,
      },
      {
        src: img2,
      },
      {
        src: img3,
      },
      {
        src: img4,
      },
    ],
    constants,
  }),
  created() {
    console.log(this.$store.state.userData.email);
    this.findAllChannel();
  },
  methods: {
    findAllChannel: function () {
      http.get("/board/channels").then((response) => {
        // prevent html, allow json array
        if (Object.prototype.toString.call(response.data) === "[object Array]")
          this.channels = response.data;
      });
    },
    getRandomImage(idString) {
      return `https://picsum.photos/seed/${idString}/200/300`;
    },
    NeedLogin() {
      this.isLoggin = true;
    },
  },
};
</script>

<style>
.custom-shape-divider-bottom-1597218054 {
  position: absolute;

  left: 0;
  width: 100%;
  overflow: hidden;
  line-height: 0;
  transform: rotate(180deg);
}

.custom-shape-divider-bottom-1597218054 svg {
  position: relative;
  display: block;
  width: calc(131% + 1.3px);
  height: 123px;
}

.custom-shape-divider-bottom-1597218054 .shape-fill {
  fill: #fcfcfc;
}
.magnifyBorder {
    padding: 18px 36px;
    border-radius: 61px;
    box-shadow: inset 0 0 0 2px #515ada;
    transition: 300ms box-shadow cubic-bezier(0.4, 0, 0.6, 1), 300ms background-color cubic-bezier(0.4, 0, 0.6, 1), 300ms color cubic-bezier(0.4, 0, 0.6, 1);;
    color: #515ada;
}

.magnifyBorder:hover {
    box-shadow: inset 0 0 0 4px #515ada;
    color: #515ada;
}
</style>