<template>
  <div style=" padding:0px; margin:0px;">
    <div style="left:30px; top:30px; position:fixed; ">
      <a class="white--text" @click="ScrollDown" href="#First">Home</a>
      <br />
      <a class="white--text" href="#Second">About</a>
      <br />
      <a class="white--text" href="#Third">Developers</a>
    </div>
    <div style="position:fixed; top:30px; right:30px">
      <LoginModal />
    </div>


    <section
      id="First"
      class="box1"
      style="width: 100vw;
            height: 100vh;
            background: linear-gradient(90deg, rgba(33,33,33,1) 50%, rgba(37,37,37,1) 50%);"
    >
      <div style="text-align: center;">
        <img
          src="../../assets/img/Logo.png"
          style="  margin:20vh; margin:20vw ; width:30%; height:auto;"
        />
        <footer
          class="white--text"
          style="width:100vw; text-align: center; position:absolute; bottom:10px; "
        >
          <p>ⓒEarlyBird</p>
        </footer>
      </div>
    </section>

    <section
    
      id="Second"
      style="width: 100vw;
            height: 100vh;
            background: linear-gradient(90deg, rgba(33,33,33,1) 50%, rgba(37,37,37,1) 50%);"
      class="white--text d-flex box2"
    >
      <div class="d-flex" style="padding:20vh; padding:10vw ">
        <div class="d-flex" style="width:50%; text-align: center; ">
          <h4 style=" font-size:30px;">이거모임은 포스트잇, 캘린더, 칸반보드 등의 기능을 제공하여 다양한 사람들과의 협업을 도와드립니다</h4>
        </div>
        <div class="d-flex" style="width:50%; ">
          <img style="width:100%" src="../../assets/img/Exampleboard.png" />
        </div>
      </div>
    </section>
    <section
      class="d-flex"
      style="width: 100vw;
            height: 100vh; background: linear-gradient(90deg, rgba(33,33,33,1) 50%, rgba(37,37,37,1) 50%);"
    >
      <div style="width:50%; padding:20vh; padding:10vw ">
        <h4 class="white--text" style=" font-size:30px;">이거모임은 최상의 서비스를 제공하기위해 다양한 기술들을 통해 구현하였습니다</h4>
      </div>
      <div style="width:50%;  padding:20vh; padding:10vw">
        <img style="width:100% " src="../../assets/img/Used.png" />
      </div>
    </section>

    <section id="Third" class="box3 d-flex black--text" style="width: 100vw;
            height: 100vh;

 ">
      <div
        class="white--text"
        style="width:40%; padding:10vw; padding-top: 50px; background:rgb(33,33,33);"
      >
        <h2>
          TEAM
          <br />Early Bird
        </h2>
      </div>
      <div style="width:100%; padding:60px; padding-top:90px;">
        열정적인 이거모임!
        <p />
        <h4 style=" font-size:30px;">이거모임은 열정적인 개발자들과 함께 하고 있습니다</h4>
        <v-row style="width:50%">
        <v-col
          v-for="(slide, i) in slides" :key="i"
          md="4"
           
        >
          <v-hover v-slot:default="{ hover }">
            <v-card>
              <v-img
                :src="slide.src"
                class="white--text align-end"
                gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                
                
              >
                <v-expand-transition>
                  <div
                    v-if="hover"
                    flex
                    class="transition-fast-in-fast-out text-center black white--text darken-3 v-card--reveal"
                    style="font-size:10px; padding:10px ;height: 100%; align-items: center; bottom: 0; justify-content: center; opacity:.5; position: absolute; width: 100%;"
                  >
                    <h2>{{slide.name}}</h2>
                    <p >{{slide.text}}</p>
                  </div>
                </v-expand-transition>
                
              </v-img>
            </v-card>
          </v-hover>
        </v-col>
      </v-row>
        <!-- <router-link class="black--text" v-bind:to="{name:constants.URL_TYPE.POST.INTRODUCE}">더 알아보기</router-link> -->
      </div>
    </section>
  </div>
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
import img1 from "../../assets/img/developer1.jpg";
import img2 from "../../assets/img/developer2.jpg";
import img3 from "../../assets/img/developer3.jpg";
import img4 from "../../assets/img/developer4.jpg";
import img5 from "../../assets/img/developer5.jpg";
import img6 from "../../assets/img/developer6.jpg";
import { onMounted, onUnmounted } from "vue";
export default {
  components: { LoginModal, Login },

  data: () => ({
    channels: [],
    isLoggin: false,
    slides: [
      {
        name: "Choi Munkyung",
        src: img1,
        text:"1일 1즐거움~"
      },
      {
        name: "Jeong Yongwoo",
        src: img2,
        text:"콘솔불바다.... 내가 책임질게...... 시켜줘.. 얼리버드 명예소방관.."
      },
      {
        name:"Bae Jaewon",
        src: img3,
        text:"문경아.. 이거 인증이 안되는거같아..."
      },
      {
        name:"Kim Kanghyeon",
        src: img4,
        text:"배포 그만해.. 그게 뭔데.. 배포 그만하라구.. 그거 어떻게 하는건데..."
      },
      {
        name:"Kim Dongryul",
        src: img5,
        text: "내 얼굴 짜릿해... 늘 새로워.. 잘생긴게 최고야..."
      },
      {
        name:"Bae Minkyu",
        src: img6,
        text: "돈뺏기는거 아님"
      },
    ],
    constants,
  }),
  created() {
    console.log(this.$store.state.userData.email);
    this.findAllChannel();
    this.ScrollAll();
  },
  methods: {
    hi: function () {
      scroll.animateScroll(0);
    },
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
    ScrollDown() {
      $("a[href*=#]").on("click", function (e) {
        e.preventDefault();
        $("div").animate(
          { scrollTop: $($(this).attr("href")).offset().top,  },
          500,
          "smooth"
        );
      });
    },
    ScrollAll() {
      var Flocation = document.querySelector("#First").offsetHeight;
      var Slocation = document.querySelector("#Second").offsetTop;
      var Tlocation = document.querySelector("#Third").offsetTop;
      window.scrollTo({top:Slocation - Flocation, behavior:'smooth'});
      // const _width = window.innerWidth;
      // const _height = window.innerHeight;
      // const boxArr = document.getElementsByTagName("section");
      // console.log(boxArr)
      // for (let i = 0; i < boxArr.length; i++) {
      //   boxArr[i].style.width = _width + "px";
      //   boxArr[i].style.height = _height + "px";
      // }

      // window.addEventListener("mousewheel", function (e) {
      //   e.preventDefault();
      //   if (e.wheelDeltaY > 0) {
      //     console.log("업");
      //     window.scrollTo(0, window.scrollY - _height - e.deltaY);
      //   } else {
      //     console.log("다운");
      //     window.scrollTo(0, window.scrollY + _height - e.deltaY);
      //   }
      // });
    },
  },
};
</script>

<style scoped>
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
  transition: 300ms box-shadow cubic-bezier(0.4, 0, 0.6, 1),
    300ms background-color cubic-bezier(0.4, 0, 0.6, 1),
    300ms color cubic-bezier(0.4, 0, 0.6, 1);
  color: #515ada;
}

.magnifyBorder:hover {
  box-shadow: inset 0 0 0 4px #515ada;
  color: #515ada;
}
.container {
  width: 80%;
  min-width: 450px;
  margin: 0 auto;
}

.fade-in {
  background-color: #2ecc71;
  height: 500px;
  margin-bottom: 50px;
  opacity: 0;
  transition: 0.3s all ease-out;
  transform: scale(0.8);
  box-sizing: border-box;
  padding: 20px;
  display: inline-block;
}
.full-width {
  width: 100%;
}

.half-width {
  width: 47.5%;
}

.half-width:nth-of-type(2n + 1) {
  margin-right: 2.5%;
}

.half-width:nth-of-type(2n) {
  margin-left: 2.5%;
}
</style>