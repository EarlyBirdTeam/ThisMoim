<template>
  <div id="app" v-cloak @click="test2">
        <div class="row">
            <div class="col-md-6">
                <h4>{{channelName}} <span class="badge badge-info badge-pill">{{userCount}}</span></h4>
        <v-btn @click="sendMessage()">보내기</v-btn><br>
            </div>
        </div>
        <v-toolbar class="toolBox">
            <v-btn icon color="orange" @click="createText">
              <v-icon>mdi-message</v-icon>
            </v-btn>
        </v-toolbar> 
        
        <div class="bodyBox " ref="whiteBoard" 
        @dblclick="focusAction"
        @click="changeTargetAction">

          <Moveable
          ref="moveable"
          class="moveable"
          v-bind="moveable"
          @drag="handleDrag"
          @dragEnd="handleDragEnd"
          @resize="handleResize"
          @scale="handleScale"
          @rotate="handleRotate"
          @warp="handleWarp"
          style="display: none;"
          >
          </Moveable> 

          
            <!-- <Postit :id="pi.id" :postit="pi" style="position: relative; display: inline-block"/> -->
          <Postit
          v-for="(pi, idx) in this.postitList" :key="idx"
          :id="pi.frontPostitId" 
          :postit="pi" 
          :style="{left: pi.left, top: pi.top}"
          />
          {{ idCount }} {{ postitList }} 
        </div>
        
    </div>
</template>

<script>
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import axios from 'axios';
import http from '../../http-common.js';
import Postit from '../../components/common/Postit'
import Moveable from 'vue-moveable';

export default {
  data () {
    return {
      ws: null,
      channelId: '',
      channelName: '',
      sender: '',
      postit: {title: 'title!!!!', contents: ''},
      dummyTitle: '',
      dummyContents: '',
      postitList: [],
      board:'',
      boards: [],
      token: '',
      userCount: 0,
      moveable: {
        target: '',
        draggable: true,
        throttleDrag: 1,
        resizable: false,
        throttleResize: 1,
        keepRatio: false,
        scalable: true,
        throttleScale: 0,
        rotatable: true,
        throttleRotate: 0,
        origin: false,
      },
      idCount: 1,
    }
  },
  created() {
    this.init();
    window.oncontextmenu = function() { // 우클릭 default이벤트 차단
      return false;
    };
    this.initRecv();
  },
   methods: {
    init() {
      // var BASE_URL =  "http://i3a510.p.ssafy.io/api"
      var BASE_URL =  "http://localhost:8080"
      var sock = new SockJS(BASE_URL + "/ws-stomp");
      var ws = Stomp.over(sock);
      this.ws = ws;

      this.channelId = localStorage.getItem('wsboard.channelId');
      this.channelName = localStorage.getItem('wsboard.channelName');
      var _this = this;
      http.get('/board/user').then(response => {
          _this.token = response.data.token;
          ws.connect({"token":_this.token}, function(frame) {
              ws.subscribe("/sub/board/channel/"+_this.channelId, function(message) {
                  var recv = JSON.parse(message.body);
                  _this.recvMessage(recv);
              });
          }, function(error) {
              alert("서버 연결에 실패 하였습니다. 다시 접속해 주십시요.");
              location.href="/";
          });
      });
    },
    initRecv() {
      // 접속시 처음 값을 받아오도록 하기
      console.log('접속@@@@')
      http.get(`/board/${this.channelId}`)
        .then(response => {
          this.postitList = response.data.postitList
          console.log(response.data);
        }).catch(e => {
          console.log(e)
        })
    },
    sendMessage: function(type) {
      // var tempPostitList = this.postitList.unshift({title: this.dummyTitle, contents: this.dummyContents});
      this.ws.send("/pub/board/message", {"token":this.token}, JSON.stringify({channelId:this.channelId, idCount:this.idCount, postitList:this.postitList}));
      this.dummy = '';
    },
    recvMessage: function(recv) {
      this.userCount = recv.userCount;
      this.idCount = recv.idCount;
      // this.postitList.unshift({"sender":recv.sender,"postitList":recv.postitList})
      this.postitList = recv.postitList;
    },
    createText(event) {
      event.stopPropagation();
      const idc = this.idCount++;
      // postitList에 새로운 포스트잇 더하기
      this.postitList.unshift({
        frontPostitId: idc,
        left: '500px',
        top: '170px',
        title: '', 
        contents: '',
        channel: this.channelId,
      })
      this.sendMessage();
    },    
    handleDrag({ target, left, top }) {
      target.style.left = `${left}px`;
      target.style.top = `${top}px`;
      this.postitList.map(postit => {
        if(postit.frontPostitId == target.id) {
          postit.left = `${left}px`,
          postit.top = `${top}px`
        }
        return {
          ...postit,
        }
      })
    },
    handleDragEnd(){
      this.sendMessage();
    },
    handleResize({ target, width, height, delta }) {
      console.log("onResize", width, height, delta);
      delta[0] && (target.style.width = `${width}px`);
      delta[1] && (target.style.height = `${height}px`);
    },
    handleScale({ target, transform, scale }) {
      target.style.transform = transform;
    },
    handleRotate({ target, dist, transform }) {
      target.style.transform = transform;
    },
    handleWarp({ target, transform }) {
      target.style.transform = transform;
    },  
    focusAction({ target, transform }){
        target.focus();
    },
    changeTargetAction({target}){
      this.test();
      if(target.getAttribute('class') != null){
        var clas = target.getAttribute('class').split(' ');
      
        for(var cla in clas){
          if(clas[cla] == 'MoveableBox'){
            event.stopPropagation();
            target.blur();
            this.$refs.moveable.moveable.target = target;
          }
        }
      }
    },
    deleteTargetAction(idx ,{target}){
      console.log("delete TARGET!!!!!!")
      console.log(idx, target)
      if(confirm("요소를 삭제하시겠습니까?") === true) {
        this.postitList.splice(idx, 1);
        target.remove();
      }
    },
    test(){
      document.querySelector('.moveable-control-box').style.display = 'block';
    },
    test2(){
      console.log("click body!");
      document.querySelector('.moveable-control-box').style.display = 'none';
      
    },
    test3({target}){
      console.log("click target!");
      
      console.log(target.style.left);
    },
    test4({target}){
      target.style.left = "100px";

    },

  },
  components: {
    Postit,
    Moveable,
  }
}
</script>

<style>
.moveable {
  font-family: "Roboto", sans-serif;
  position: relative;
  width: 300px;
  height: 100px;
  text-align: center;
  font-size: 40px;
  margin: 0 auto;
  font-weight: 100;
  letter-spacing: 1px;
  /* background-color: yellow; */
}
</style>