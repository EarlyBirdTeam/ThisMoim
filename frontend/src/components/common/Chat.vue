<template>
<div>

 <div class="chat-container" id="chatContainer">
   <div v-if="chattingBox">
        <div class="chat-header" id="chatHeader">
          <button id="minimize" class="header-btn" @click="minimize"></button>
          <button id="maximize" class="header-btn" @click="maximize"></button>
          <img id="profile-pic" src='../../assets/img/picture.jpg' width="1">
          <span id="username">나</span>
        </div>
    
        <div class="chatbox" id="chatBox">
          <div class="friend-bubble bubble">
            매너 채팅 해주세요 :)
          </div>
        </div>
    
        <form>
        <div class="text-box" id="textBox">
          <textarea id="msgForm" placeholder="메시지를 입력해주세요 :)" @keyup="enter" ></textarea>
          <button id="sendChat" @click=sendChat>전송</button>
          <div class="clearfix"></div>
        </div>
       </form>
   </div>
 </div>
 
    <div class="chat-container2" id="chatContainer" v-if="!chattingBox">
       <div class="chat-header" id="chatHeader">
          <button id="minimize" class="header-btn" @click="minimize"></button>
          <button id="maximize" class="header-btn" @click="maximize"></button>
          <img id="profile-pic" src='../../assets/img/picture.jpg' width="1">
          <span id="username">나</span>
        </div>
    </div>
 </div>



</template>



<script>

      // var chatcontainer = document.getElementById("chatContainer");
      // var chatheader = document.getElementById("chatHeader");
      // var chatbox = document.getElementById("chatBox");
      // var textbox = document.getElementById("textBox");

      // $("#minimize").click(function(){
      //   chatbox.style.display = 'none';
      //   textbox.style.display = 'none';
      //   chatcontainer.style.top = '90%';
      //   //alert("최소화");
      // });

      // $("#maximize").click(function(){
      //   chatbox.style.display = 'block';
      //   textbox.style.display = 'block';
      //   chatcontainer.style.top = '23%';
      // })

export default {
  name: "Chat",
  created() {
    var chatcontainer = document.getElementById("chatContainer");
    var chatheader = document.getElementById("chatHeader");
    var chatbox = document.getElementById("chatBox");
    var textbox = document.getElementById("textBox");
    
    var $msgForm = $('#msgForm').val();
    var myname = this.makeRandomName();
    this.naname = myname;

    console.log(this.naname);
    
    this.$socket.emit("login", {
      //name: this.$store.state.name,
      name: myname,
      userid: myname,
    });

    this.$socket.on("login", (data) => {
      // this.chatLogs.push(data.name + "님이 접속하셨습니다");
      // this.chatComes.push(data.name);
      console.log("입장!");
      $('.chatbox').append('<div class="inout-bubble">'+data+'님이 입장하셨습니다.</div>');
    
    });

     this.$socket.on("enter", (data) => {
      // this.chatLogs.push(data.name + "님이 접속하셨습니다");
      // this.chatComes.push(data.name);
      $('.chatbox').append('<div class="inout-bubble">'+data+'님이 입장하셨습니다.</div>');
    
    });

    this.$socket.on("s2c chat", (data) => {
      var name = data.from.name;
      var msg = data.msg;
      // var chatMsg = {
      //   name: data.from.name,
      //   msg: data.msg,
      // };
      //this.chatMsgs.push(chatMsg);
      $('.chatbox').append('<div class="friend-bubble bubble">('+name+'님) '+msg+'</div>');
    });
    this.$socket.on("s2c chat me", (data) => {
      var name = data.from.name;
      var msg = data.msg;
     
      $('.chatbox').append('<div class="my-bubble bubble">'+msg+'</div>');
    });

    this.$socket.on("out", (data) => {
      $('.chatbox').append('<div class="inout-bubble">'+data.from.name+'님이 나가셨습니다.</div>');
    
    });

    this.$socket.on(() => {});


    
  },

  data() {
    return {
      chattingBox: true,
      textarea: "",
      message: "",
      chatmem: [],
      chatLogs: [],
      chatComes: [],
      chatNames: [],
      chatMsgs: [],
      naname: "",
      
    };
  },
  methods: {

    sendChat() {
      event.preventDefault(); // 줄바꿈 방지?
      event.stopPropagation();
        var $msgForm = $('#msgForm').val();
        console.log("msgForm : "+$msgForm);


        this.$socket.emit("chat", {msg: $msgForm});
        $('#msgForm').val("");
    },

     enter() { // 엔터 처리
       var code = event.keyCode;
        if(code==13){

          if(event.shiftKey === true){ // Shift + Enter 처리
            //console.log("Shift도 눌러짐");
          
          }
          else this.sendChat();
        }

    },

    minimize(){
      this.chattingBox = false;
      //alert("최소화");
    },
    maximize(){
      this.chattingBox = true;
    },

    makeRandomName() {
      var name = "";
      var possible = "abcdefghijklmnopqrstuvwxyz";
      for (var i = 0; i < 3; i++) {
        name += possible.charAt(Math.floor(Math.random() * possible.length));
      }
      return name;
      // var name = 1;
      // return name + Math.random()*10;
    },
  },
};
</script>

<style>
* {
    box-sizing: border-box;
  }
  
  body {
    /* background-image: url('../images/background.jpg'); */
    font-family: 'Noto Sans KR', sans-serif;
  }
  
  .chat-container {
    box-shadow: 0 2px 4px 0 rgba(0,0,0,0.50);
    transition: width 0.3s ease;
    position: absolute;
    
    left: 75%;
    width: 25%;
    top: 38%;

  }
  
  .chat-header {
    background-color: white;
    padding: 30px 8px 8px 8px;
  }
  
  .chat-header .header-btn {
    border-radius: 50%;
    border: none;
    width: 12px;
    height: 12px;
    cursor: pointer;
    position: absolute;
    top: 8px;
    padding: 0;
  }
  
  .chat-header #close {
    background-color: #ff6059;
    left: 8px;
  }
  
  .chat-header #minimize {
    background-color: #ffbf2f;
    left: 26px;
  }
  
  .chat-header #maximize {
    background-color: #29cd42;
    left: 44px;
  }
  
  .chat-header #profile-pic {
    vertical-align: middle;
    border-radius: 50%;
    width: 5%;
    height: 5%;
  }
  
  .chat-header #username {
    vertical-align: middle;
    font-size: 14px;
    font-weight: 500;
    margin-left: 5px;
    color: #343434;
  }

  /* only header */
  .chat-container2 {
    box-shadow: 0 2px 4px 0 rgba(0,0,0,0.50);
    transition: width 0.3s ease;
    position: absolute;
    
    left: 75%;
    width: 25%;
    top: 92%;

  }
  
  
  /* chat box */
  
  .chatbox {
    height: 400px;
    background-color: #d7e4f2;
    padding: 10px;
    overflow-y: scroll;
    position: relative;
  }
  
  .bubble {
    margin: 5px 0;
    display: inline-block;
    max-width: 300px;
    font-size: 14px;
    position: relative;
  }
  
  .inout-bubble {
    background-color:lightslategray;
    border-radius: 14px 14px 14px 14px;
    padding: 7px 50px 7px 50px;
    float: left;
    clear: both;

    margin: 5px 0;
    max-width: 300px;
    font-size: 14px;
    position: relative;
  }

  .friend-bubble {
    background-color: white;
    border-radius: 14px 14px 14px 0;
    padding: 7px 15px 7px 15px;
    float: left;
    clear: both;
  }
  
  .my-bubble {
    background-color: #fff46d;
    border-radius: 14px 14px 0px 14px;
    padding: 7px 15px 7px 15px;
    float: right;
    clear: both;
  }
  
  /* text box */
  
  .text-box {
    background-color: #fafafa;
    padding: 10px;
  }
  
  .text-box textarea {
    height: 60px;
    float: left;
    width: calc(100% - 70px);
    border-radius: 3px;
    background-color: #ffffff;
    border: solid 0.5px #d7d7d7;
    resize: none;
    padding: 10px;
    font-size: 14px;
  }
  
  #sendChat {
    background-color: orange;
    width: 60px;
    height: 60px;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    margin-left: 10px;
    float: left;
  }
  
  .clearfix {
    clear: both;
  }
</style>