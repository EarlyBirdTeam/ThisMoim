<template>
  <div id="app" v-cloak @click="cloakMoveable">
    <div class="toolbar">
      <v-snackbar
        app
        bottom
        v-model="snackbar.isPresent"
        :timeout="snackbar.timeout"
        :color="snackbar.color"
      >{{ snackbar.text }}</v-snackbar>
      <div>
        <div class="toolBox">
          <v-btn
            icon
            color="orange"
            @click="pleaseDrag"
            draggable="true"
            @dragenter="dragging=true"
            @dragend="moduleDragEnd('postit', $event)"
          >
            <v-icon>mdi-message</v-icon>
          </v-btn>
          <v-btn
            icon
            color="orange"
            @click="pleaseDrag"
            draggable="true"
            @dragend="moduleDragEnd('kanban', $event)"
          >
            <v-icon>mdi-clipboard-list-outline</v-icon>
          </v-btn>
          <!-- <v-btn icon color="orange" @click="createMap">
          <v-icon>mdi-map</v-icon>
          </v-btn>-->
          <v-btn
            icon
            color="orange"
            @click="pleaseDrag"
            draggable="true"
            @dragend="moduleDragEnd('scheduler', $event)"
          >
            <v-icon>mdi-calendar</v-icon>
          </v-btn>
          <v-btn
            icon
            color="orange"
            @click="pleaseDrag"
            draggable="true"
            @dragend="moduleDragEnd('poll', $event)"
          >
            <v-icon>mdi-vote</v-icon>
          </v-btn>
        </div>
        <br />
      </div>
    </div>

    <v-responsive class="vueBox text-center ma-3"></v-responsive>

    <v-responsive>
      <v-responsive
        class="userListBadge badge-info text-center lighten-2 rounded-circle d-inline-flex align-center justify-center ma-3"
        @mouseover="testIn"
        @mouseout="testOut"
      >
        <v-img src="@/assets/img/user.png">{{userCount}}</v-img>
      </v-responsive>

      <transition name="slide-fade">
        <v-responsive
          id="memberList"
          class="text-center ma-3 badge-info"
          v-if="memberView"
          align="center"
          justify="center"
        >김강현, 배민규, 정용우, 최문경, 김동률, 배재원</v-responsive>
      </transition>
    </v-responsive>

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
    ></Moveable>

    <div
      class="bodyBox"
      ref="whiteBoard"
      @dblclick="focusAction"
      @click="changeTargetAction"
      @wheel="wheelEvent"
      style="height: 100%; width: 100%;"
    >
      <div class="MoveableBox realBoard" @click.right="test3">
        <div
          class="postit"
          v-for="(pi, idx) in this.board.postitList"
          :key="pi.frontPostitId"
          @click.right="deleteTargetAction(idx, $event)"
        >
          <Postit :id="pi.frontPostitId" :postit="pi" :style="{left: pi.left, top: pi.top}" />
        </div>

        <div class="kanban" @click.right="deleteKanban">
          <Kanban v-if="board.isKanban" :style="{left:board.kanban.left, top:board.kanban.top}"/>
        </div>
        <div class="map" @click.right="deleteAction">
          <Map v-if="map.isPresent" />
        </div>

        <div class="Scheduler" @click.right="deleteAction">
          <Scheduler
            v-if="!!board.scheduler"
            :style="{left:board.scheduler.left, top:board.scheduler.top}"
          />
        </div>

        <div class="Poll" @click.right="deleteAction">
          <Poll
            v-if="!!board.poll"
            :style="{left: $store.state.poll.left, top: $store.state.poll.top}"
          />
        </div>
        {{ board.isKanban}}<br>
        board : {{ board.kanban }} 
        <br />
        <br />
        <br />

        store : {{ $store.state.Kanban }}
      </div>

      <!-- <Postit :id="pi.id" :postit="pi" style="position: relative; display: inline-block"/> -->
    </div>
    <Chat />
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "stomp-websocket";
import http from "../../http-common.js";
import Moveable from "vue-moveable";
import Postit from "../../components/module/Postit";
import Map from "../../components/module/Map";
import Scheduler from "../../components/module/Scheduler";
import Chat from "../../components/common/Chat";
import Poll from "../../components/common/Poll";
import Kanban from "../../components/module/Kanban";

export default {
  computed: {
    poll() {
      return this.$store.state.poll;
    },
  },
  data() {
    return {
      ws: null,
      channelName: "",
      // 소켓 서버 전송
      board: {
        channelId: "",
        idCount: 1,
        crudModule: {
          modulType: "",
          crudType: "",
          moduleObject: Object,
        },
        postitList: [],
        isKanban: false,
        kanban: this.$store.state.Kanban,
        scheduler: {},
        // poll: {},
        isDelete: false,
        delete: {
          moduleName: "",
          id: -1,
        },
      },
      token: "",
      userCount: 0,
      moveable: {
        target: "",
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

      map: {
        isPresent: false,
        left: "",
        top: "",
        lat: 0,
        lng: 0,
      },
      snackbar: {
        isPresent: false,
        text: "",
        timeout: 1000,
      },
      boardLength: 10000,
      boardScale: 1,
      boardX: this.boardLength / 2,
      boardY: this.boardLength / 2,
      lp: 0,
      tp: 0,
      lastBX: this.boardX,
      lastBY: this.boardY,

      memberView: false,
      idc: 0,
    };
  },
  created() {
    this.init();
    window.oncontextmenu = function () {
      // 우클릭 default이벤트 차단
      return false;
    };
    // this.initRecv();
  },
  mounted() {
    document.querySelector(".realBoard").style.height = this.boardLength + "px";
    document.querySelector(".realBoard").style.width = this.boardLength + "px";

    // console.log((boardLength/2) - (window.innerWidth * 0.4));
    document.querySelector(".realBoard").style.left =
      -(this.boardLength / 2) + window.innerWidth * 0.5 + "px";

    document.querySelector(".realBoard").style.top =
      -(this.boardLength / 2) + window.innerHeight * 0.5 + "px";

    document.querySelector(".realBoard").style.transformOrigin = `${
      this.boardLength / 2
    }px ${this.boardLength / 2}px`;
  },
  methods: {
    init() {
      // var BASE_URL =  "http://i3a510.p.ssafy.io/api"
      var BASE_URL = "http://localhost:8080";
      // var BASE_URL = "http://218.146.39.122:8080";
      var sock = new SockJS(BASE_URL + "/ws-stomp");
      var ws = Stomp.over(sock);
      this.ws = ws;

      this.board.channelId = localStorage.getItem("wsboard.channelId");
      this.channelName = localStorage.getItem("wsboard.channelName");
      var _this = this;
      http.get("/board/user").then((response) => {
        _this.token = response.data.token;
        ws.connect(
          { token: _this.token },
          function (frame) {
            ws.subscribe(
              "/sub/board/channel/" + _this.board.channelId,
              function (message) {
                var recv = JSON.parse(message.body);
                _this.recvMessage(recv);
              }
            );
          },
          function (error) {
            alert("서버 연결에 실패 하였습니다. 다시 접속해 주십시요.");
            location.href = "/";
          }
        );
      });
    },
    initRecv() {
      // 접속시 처음 값을 받아오도록 하기
      http
        .get(`/board/${this.board.channelId}`)
        .then((response) => {
          console.log('initRecv@@@@');
          console.log(response.data);
          // this.board.postitList = response.data.postitList;
          // this.board.idCount = response.data.idCount;
          this.board = response.data;
          this.board.delete = { moduleName: "", id: -1 };
          this.$store.state.Kanban.states = response.data.kanban.states;
          // this.board.Kanban.columns = response.data.kanban.columns;
        })
        .catch((e) => {
          console.log('initRecv 실패')
          console.log(e);
        });
        this.createSnackbar(
        `'${this.channelName}' 채널에 입장하였습니다!`,
        3000,
        "info"
      );
    },
    sendMessage: function (type) {
      this.ws.send(
        "/pub/board/message",
        { token: this.token },
        JSON.stringify(this.board)
      );
      this.createSnackbar("수정되었습니다", 1000, "warning");
    },
    recvMessage: function (recv) {
      this.userCount = recv.userCount;
      this.board.idCount = recv.idCount;
      this.board.postitList = recv.postitList;
      this.board.isDelete = false;
      if (!!recv.scheduler) {
        this.board.scheduler = recv.scheduler;
        this.$store.state.scheduler.events = recv.scheduler.events;
      }
      this.board.poll = recv.poll;
      this.$store.state.poll = recv.poll;
      this.board.kanban = recv.kanban;
      this.$store.state.Kanban = recv.kanban;
      //crudModule 초기화
      this.board.crudModule = {
        modulType: "",
        crudType: "",
        moduleObject: null,
      };
    },
    createPostit(left = "500px", top = "170px") {
      if (this.board.postitList.length > 20) {
        this.createSnackbar("포스트잇이 너무 많습니다!", 3000, "error");
        return;
      }
      event.stopPropagation();
      const idc = this.board.idCount++;
      // postitList에 새로운 포스트잇 더하기
      var newPostit = {
        frontPostitId: idc,
        left: this.boardX - 120 + "px",
        top: this.boardY - 120 + "px",
        title: "",
        contents: "",
        channel: this.board.channelId,
      };
      this.board.postitList.push(newPostit);
      this.crudMethod("POSTIT", "CREATE", newPostit);
      this.sendMessage();
      // snackbar
      this.createSnackbar("포스트잇이 생성되었습니다!", 1500, "success");
    },

    createKanban(event) {
      if (this.board.isKanban == true) {
        this.createSnackbar("보드가 이미 생성되어 있습니다", 3000, "error");
        return;
      }

      this.createSnackbar("보드가 생성되었습니다", 1500, "success");
      this.board.isKanban = true;
      this.board.kanban = this.$store.state.Kanban;
      this.crudMethod("KANBAN", "CREATE", this.board.kanban);
      this.sendMessage();
    },

    deleteKanban({ target }) {
      if (confirm("요소를 삭제하시겠습니까?") === true) {
        target.remove();
        this.cloakMoveable();
        this.board.isKanban = false;
        this.crudMethod("KANBAN", "DELETE", this.board.kanban);
        this.$store.state.Kanban.states = [
          {
            columnTitle: "TO DO",
            tasks: [],
          },
          {
            columnTitle: "IN PROGRESS",
            tasks: [],
          },
          {
            columnTitle: "DONE",
            tasks: [],
          },
        ];
        this.sendMessage();
      }
    },

    // createMap(event) {
    //   if (this.map.isPresent) {
    //     this.createSnackbar("이미 카카오맵이 있습니다!", 3000, "error");
    //   } else {
    //     this.map.isPresent = true;
    //   }
    // },
    createScheduler(left = "600px", top = "270px") {
      if (!!this.board.scheduler) {
        this.createSnackbar("이미 달력이 있습니다!", 3000, "error");
      } else {
        this.board.scheduler = {
          left: left,
          top: top,
          events: this.$store.state.scheduler.events,
        };
        console.log("create Scheduler");
        this.crudMethod("SCHEDULER", "CREATE", this.board.scheduler);
        this.sendMessage();
        // snackbar
        this.createSnackbar("달력이 생성되었습니다!", 1500, "success");
      }
    },

    createPoll(left = "500px", top = "170px") {
      if (!!this.board.poll) {
        this.createSnackbar("이미 투표가 있습니다!", 3000, "error");
      } else {
        const idc = this.board.idCount++;
        this.board.poll = this.$store.state.poll;
        this.board.poll.left = left;
        this.board.poll.top = top;
        this.crudMethod("POLL", "CREATE", this.board.poll);
        this.sendMessage();
        // snackbar
        this.createSnackbar("투표가 생성되었습니다!", 1500, "success");
      }
    },

    createSnackbar(text, timeout, color) {
      this.snackbar.isPresent = true;
      this.snackbar.text = text;
      this.snackbar.timeout = timeout;
      this.snackbar.color = color;
    },
    handleDrag({ target, left, top }) {
      target.style.top = `${top}px`;
      target.style.left = `${left}px`;
      if (target.getAttribute("class") != null) {
        var clas = target.getAttribute("class").split(" ");
        for (var cla in clas) {
          if (clas[cla] == "paper") {
            this.board.postitList.map((postit) => {
              if (postit.frontPostitId == target.id) {
                (postit.left = `${left}px`), (postit.top = `${top}px`);
              }
              return {
                ...postit,
              };
            });
          } else if (clas[cla] == "scheduler") {
            this.board.scheduler.left = `${left}px`;
            this.board.scheduler.top = `${top}px`;
          } else if (clas[cla] == "Pollx") {
            // this.board.poll.left = `${left}px`
            // this.board.poll.top = `${top}px`
          } else if (clas[cla] == "realBoard") {
            this.lp = target.style.left.replace("px", "");
            this.tp = target.style.top.replace("px", "");
            // console.log();

            this.boardX = this.lp * -1 + window.innerWidth / 2;
            this.boardY = this.tp * -1 + window.innerHeight / 2;

            var limitUnit =
              (this.boardScale / 0.05) * 250 - this.boardLength / 2;

            // console.log("origin : ", document.querySelector('.realBoard').style.transformOrigin);
            // if(this.lp > limitUnit) {
            //   document.querySelector('.bodyBox').style.borderLeft = "red 3px solid";
            //   target.style.left = limitUnit+'px'
            // }
            // else if((this.lp) < (-this.boardLength + (window.innerWidth)) - limitUnit) {
            //   document.querySelector('.bodyBox').style.borderRight = "red 3px solid";
            //   target.style.left = (-this.boardLength + (window.innerWidth) - limitUnit) +'px';
            // }
            // else {
            //   document.querySelector('.bodyBox').style.borderRight = "1px pink solid";
            //   document.querySelector('.bodyBox').style.borderLeft = "1px pink solid";
            // }

            // if(this.tp > limitUnit) {
            //   target.style.top = limitUnit+'px'
            //   document.querySelector('.bodyBox').style.borderTop = "red 3px solid";
            // }
            // else if (this.tp < (-this.boardLength + (window.innerHeight)) - limitUnit) {
            //   target.style.top = (-this.boardLength + (window.innerHeight)) - limitUnit +'px';
            //   document.querySelector('.bodyBox').style.borderBottom = "red 3px solid";
            // }
            // else {
            //   document.querySelector('.bodyBox').style.borderTop = "1px pink solid";
            //   document.querySelector('.bodyBox').style.borderBottom = "1px pink solid";
            // }

            return;
          }

          // document.querySelector('.testerDot').style.top = this.boardY + 'px';
          // document.querySelector('.testerDot').style.left = this.boardX  + 'px';
        }
      }
    },
    handleDragEnd({ target }) {
      var moduleObj = null;
      switch (target.nodeName) {
        case "POSTIT":
          moduleObj = this.board.postitList.find(
            (postit) => postit.frontPostitId == target.id
          );
          break;
        case "SCHEDULER":
          break;
        case "DIV":
          return;
      }
      this.crudMethod(target.nodeName, "UPDATE", moduleObj);
      this.sendMessage();

      if (target.getAttribute("class") != null) {
        var clas = target.getAttribute("class").split(" ");
        for (var cla in clas) {
          if (clas[cla] == "realBoard") {
            //  console.log("its realBoard!");

            //  console.log("before : ", this.lastBX, ", ", this.lastBY);

            //  let diffX = this.lastBX - this.boardX;
            //  let diffY = this.lastBY - this.boardY;
            //  console.log("diff : ", diffX, ", ", diffY);
            //  console.log("after : ", this.boardX, ", ", this.boardY);

            //  this.boardX = this.lastBX + (diffX / this.boardScale);
            //  this.boardY = this.lastBY + (diffY / this.boardScale);

            //  this.lastBX = this.boardX;
            //  this.lastBY = this.boardY;

            //  document.querySelector('.testerDot').style.top = this.boardY + 'px';
            //  document.querySelector('.testerDot').style.left = this.boardX  + 'px';
            document.querySelector(
              ".realBoard"
            ).style.transformOrigin = `${event.offsetX}px ${event.offsetY}px`;

            document.querySelector(".testerDot").style.top =
              event.offsetY + "px";
            document.querySelector(".testerDot").style.left =
              event.offsetX + "px";
            //  target.style.transformOrigin = `${event.offsetX}px ${event.offsetY}px`
            this.crudMethod(target.nodeName, "UPDATE", moduleObj);
            this.sendMessage();
            this.crudMethod("", "", null);
            console.log("ltp : ", this.lp, ",", this.tp);
            console.log(
              "bodyBox wh : ",
              window.innerWidth,
              ", ",
              window.innerHeight
            );
            console.log("boardXY  : ", this.boardX, ", ", this.boardY);
            console.log(
              "origin : ",
              document.querySelector(".realBoard").style.transformOrigin
            );
            console.log("event : ", event);
          }
        }
      }
    },
    handleResize({ target, width, height, delta }) {
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
    focusAction({ target, transform }) {
      target.focus();
    },
    changeTargetAction({ target }) {
      this.blockMoveable();

      if (target.getAttribute("class") != null) {
        var clas = target.getAttribute("class").split(" ");

        for (var cla in clas) {
          // console.log(clas[cla]);
          if (clas[cla] == "MoveableBox") {
            event.stopPropagation();
            target.blur();
            this.$refs.moveable.moveable.target = target;
          }

          if (clas[cla] == "realBoard" || clas[cla] == "bodyBox") {
            // event.stopPropagation();
            // target.blur();
            // this.$refs.moveable.moveable.target = target;
            this.cloakMoveable();
          }
        }
      }
    },
    deleteTargetAction(idx, { target }) {
      if (confirm("요소를 삭제하시겠습니까?") === true) {
        target.remove();
        this.board.isDelete = true;
        this.board.delete.moduleName = "postit";
        this.board.delete.id = this.board.postitList[idx].frontPostitId;
        this.crudMethod("POSTIT", "DELETE", this.board.postitList[idx]);
        this.board.postitList.splice(idx, 1);
        this.sendMessage();
        this.cloakMoveable();
      }
    },
    wheelEvent: function (event) {
      if (event.deltaY < 0) {
        console.log("up!");
        this.boardScale += 0.05;

        if (this.boardScale > 1.3) {
          this.boardScale = 1.3;
          return;
        }
      } else if (event.deltaY > 0) {
        this.boardScale -= 0.05;

        if (this.boardScale < 0.6) {
          this.boardScale = 0.6;
          return 0;
        }
      }
      console.log(this.boardScale);
      let lastOriginX = document
        .querySelector(".realBoard")
        .style.transformOrigin.split(" ")[0];
      let lastOriginY = document
        .querySelector(".realBoard")
        .style.transformOrigin.split(" ")[1];

      console.log("LastOrigin : ", lastOriginX, " ", lastOriginY);

      let diffX = lastOriginX.replace("px", "") - event.offsetX;
      let diffY = lastOriginY.replace("px", "") - event.offsetY;

      console.log("Diff : ", diffX, ",", diffY);

      document.querySelector(
        ".realBoard"
      ).style.transformOrigin = `${event.offsetX}px ${event.offsetY}px`;

      document.querySelector(".testerDot").style.top = event.offsetY + "px";
      document.querySelector(".testerDot").style.left = event.offsetX + "px";

      let leftPoint =
        document.querySelector(".realBoard").style.left.replace("px", "") * 1;
      let topPoint =
        document.querySelector(".realBoard").style.top.replace("px", "") * 1;

      console.log("realBoard left and top : ", leftPoint, ", ", topPoint);
      console.log("so its now  :  ", leftPoint + diffX, ", ", topPoint + diffY);

      // if(this.boardScale != 1){
      // document.querySelector('.realBoard').style.left = (leftPoint + diffX)+'px';
      // document.querySelector('.realBoard').style.top =  (topPoint + diffY)+'px';
      // }

      console.log(
        "origin : ",
        document.querySelector(".realBoard").style.transformOrigin
      );

      document.querySelector(
        ".realBoard"
      ).style.transform = `scale(${this.boardScale})`;
    },
    deleteAction({ target }) {
      if (confirm("요소를 삭제하시겠습니까?") === true) {
        if (target.getAttribute("class") != null) {
          var clas = target.getAttribute("class").split(" ");
          console.log(clas);
          for (var cla in clas) {
            if (clas[cla] == "scheduler") {
              this.crudMethod("SCHEDULER", "DELETE", this.board.scheduler);
              this.board.scheduler = {
                left: null,
                top: null,
                events: [
                  {
                    name: "오프라인 미팅",
                    content: "예시 일정입니다.",
                    start: "2020-08-05T08:30:00",
                    end: "2020-08-05T18:00:00",
                  },
                ],
              };
            
            } else if (clas == "poll") {
              this.board.poll = null;
            }
          }
        }
        target.remove();
        this.sendMessage();
        this.cloakMoveable();
      }
    },
    blockMoveable() {
      document.querySelector(".moveable-control-box").style.display = "block";
    },
    cloakMoveable() {
      document.querySelector(".moveable-control-box").style.display = "none";
    },
    moduleDragEnd(moduleName, { offsetX, offsetY }) {
      switch (moduleName) {
        case "postit":
          this.createPostit(`${offsetX}px`, `${offsetY}px`);
          break;
        case "scheduler":
          this.createScheduler(`${offsetX}px`, `${offsetY}px`);
          break;
        case "poll":
          this.createPoll(`${offsetX}px`, `${offsetY}px`);
          break;
      }
    },
    pleaseDrag() {
      this.createSnackbar(
        "생성하고자 하는 위치로 드래그 해주세요!",
        3000,
        "default"
      );
    },
    crudMethod(moduleType, crudType, moduleObject) {
      this.board.crudModule = {
        moduleType: moduleType,
        crudType: crudType,
        moduleObject: moduleObject,
      };
    },

    testIn() {
      if (!this.memberView) {
        this.memberView = true;
      }
    },
    testOut() {
      if (this.memberView) {
        this.memberView = false;
      }
    },
    test3(event) {
      // let lastOriginX = document.querySelector('.realBoard').style.transformOrigin.split(" ")[0];
      // let lastOriginY = document.querySelector('.realBoard').style.transformOrigin.split(" ")[1];
      // console.log("LastOrigin : ", lastOriginX, " ", lastOriginY);
      // let diffX = lastOriginX.replace("px", "") - event.offsetX;
      // let diffY = lastOriginY.replace("px", "") - event.offsetY;
      // console.log("Diff : ", diffX, "," , diffY);
      // document.querySelector('.realBoard').style.transformOrigin = `${event.offsetX}px ${event.offsetY}px`;
      // document.querySelector('.testerDot').style.top = event.offsetY + 'px';
      // document.querySelector('.testerDot').style.left = event.offsetX + 'px';
      // let leftPoint = document.querySelector('.realBoard').style.left.replace("px", "") * 1;
      // let topPoint = document.querySelector('.realBoard').style.top.replace("px", "") * 1;
      // console.log("realBoard left and top : ", leftPoint, ", ", topPoint);
      // console.log("so its now  :  ", (leftPoint + diffX),", ", (topPoint + diffY));
      // // if(this.boardScale != 1){
      // document.querySelector('.realBoard').style.left = (leftPoint + diffX)+'px';
      // document.querySelector('.realBoard').style.top =  (topPoint + diffY)+'px';
      // // }
      // console.log("origin : ", document.querySelector('.realBoard').style.transformOrigin);
    },
  },
  components: {
    Moveable,
    Postit,
    Map,
    Scheduler,
    Chat,
    Kanban,
    Poll,
  },
};
</script>

<style scoped>
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

.bodyBox {
  position: relative;
  height: 75vh;
  margin: 0;
  width: 100vw;
  /* transform: translate(-50%, -50%); */
  /* border: solid 1px; */
  background-color: rgb(255, 255, 255);
  overflow: hidden;
}

.realBoard {
  /* boardLength와 동일해야함! */
  /* height: 2000px;
  width: 2000px; */
  /* left: -680px;
  top: -680px; */
  border: 1px solid pink;
  background: rgb(247, 236, 236);

  background-image: linear-gradient(
      0deg,
      transparent 0%,
      transparent 0px,
      rgba(104, 104, 104, 0.1) 0px,
      rgba(104, 104, 104, 0.1) 1px,
      transparent 1px,
      transparent 49px,
      rgba(104, 104, 104, 0.1) 49px,
      rgba(104, 104, 104, 0.1) 50px,
      transparent 1px,
      transparent 99px,
      rgba(104, 104, 104, 0.1) 99px,
      rgba(104, 104, 104, 0.1) 100px,
      transparent 1px,
      transparent 149px,
      rgba(104, 104, 104, 0.1) 149px,
      rgba(104, 104, 104, 0.1) 150px,
      transparent 1px,
      transparent 199px,
      rgba(104, 104, 104, 0.1) 199px,
      rgba(104, 104, 104, 0.1) 200px,
      transparent 1px,
      transparent 249px,
      rgba(104, 104, 104, 0.3) 249px,
      rgba(104, 104, 104, 0.3) 250px,
      transparent 1px
    ),
    linear-gradient(
      -90deg,
      transparent 0%,
      transparent 0px,
      rgba(104, 104, 104, 0.1) 0px,
      rgba(104, 104, 104, 0.1) 1px,
      transparent 1px,
      transparent 49px,
      rgba(104, 104, 104, 0.1) 49px,
      rgba(104, 104, 104, 0.1) 50px,
      transparent 1px,
      transparent 99px,
      rgba(104, 104, 104, 0.1) 99px,
      rgba(104, 104, 104, 0.1) 100px,
      transparent 1px,
      transparent 149px,
      rgba(104, 104, 104, 0.1) 149px,
      rgba(104, 104, 104, 0.1) 150px,
      transparent 1px,
      transparent 199px,
      rgba(104, 104, 104, 0.1) 199px,
      rgba(104, 104, 104, 0.1) 200px,
      transparent 1px,
      transparent 249px,
      rgba(104, 104, 104, 0.3) 249px,
      rgba(104, 104, 104, 0.3) 250px,
      transparent 1px
    );

  background-size: 250px 250px;
}

.moveable-control-box {
  display: none;
}

.toolBox {
  font-family: "Roboto", sans-serif;
  /* position: relative; */
  position: fixed;
  z-index: 3;
  width: 64px;
  bottom: 50%;
  left: 2%;
  padding: 10px;
  display: inline;
  background-color: white;
  text-align: center;
  vertical-align: middle;
}

.toolbar {
  position: fixed;
  z-index: 3;
}

.userListBadge {
  position: fixed;
  z-index: 3;
  bottom: 1%;
  left: 20px;

  /* background-color: white; */
  /* border-radius: 50%; */
  width: 64px;
  height: 64px;
}

.vueBox {
  background-color: white;
  border: 1px solid black;
  position: fixed;
  z-index: 3;
  right: 10%;
  bottom: 1%;
  width: 200px;
  height: 200px;
}

#memberList {
  width: auto;
  height: 40px;
  position: fixed;
  z-index: 2;
  bottom: 1%;
  left: 50px;
  text-align: right;
  padding-right: 1%;
  padding-left: 5%;
}

.slide-fade-enter-active {
  transition: all 0.4s ease;
}
.slide-fade-leave-active {
  transition: all 0.4s cubic-bezier(1, 0.5, 0.8, 1);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active below version 2.1.8 */ {
  transform: translateX(-10px);
  opacity: 0;
}

.testerDot {
  height: 4px;
  width: 4px;
  background-color: black;
  position: fixed;
  z-index: 4;
  display: none;
}
.testBox {
  display: inline;
}
</style>
