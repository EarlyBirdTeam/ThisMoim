<template>
  <div class="chat-container">
    <div class="chat-header">
      <button id="close" class="header-btn"></button>
      <button id="minimize" class="header-btn"></button>
      <button id="maximize" class="header-btn"></button>

      <!-- <img id="profile-pic" v-bind:src="picture.jpg" > -->
      <!-- <img id="profile-pic" v-bind:src="picutre.jpg" width="50" height="50"> -->
      <img id="profile-pic" src='../../assets/img/picture.jpg' width="1">
      <span id="username">나</span>
    </div>

    <div class="chatbox">
      <div class="friend-bubble bubble">
        매너 채팅 해주세요 :)
      </div>
    </div>

    <form>
    <div class="text-box">
      <textarea id="msgForm" placeholder="메시지를 입력해주세요 :)" @keyup="enter" ></textarea>
      <button id="send" @click=send>전송</button>
      <div class="clearfix"></div>
    </div>
    </form>
  </div>
</template>



<script>
// import ZZ from '../img/picture.jpg';

export default {
  name: "Chat",
  created() {

    var $msgForm = $('textarea').val();
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
      $('.chatbox').append('<div class="friend-bubble bubble">'+data+'님이 입장하셨습니다.</div>');
    
    });

     this.$socket.on("enter", (data) => {
      // this.chatLogs.push(data.name + "님이 접속하셨습니다");
      // this.chatComes.push(data.name);
      $('.chatbox').append('<div class="friend-bubble bubble">##'+data+'님이 입장하셨습니다.##</div>');
    
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
      $('.chatbox').append('<div class="friend-bubble bubble">##'+data.from.name+'님이 나가셨습니다.##</div>');
    
    });

    this.$socket.on(() => {});

  },

  data() {
    return {
      
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

    send() {
      event.preventDefault(); // 줄바꿈 방지?
        var $msgForm = $('textarea').val();
        console.log("msgForm : msgForm");

        // if(msgForm){
        // $('.chatbox').append('<div class="my-bubble bubble">'+$msgForm+'</div>');
        // $('textarea').val('');
        // }

        this.$socket.emit("chat", {msg: $msgForm});
        $('textarea').val("");
    },

     enter() { // 엔터 처리
       var code = event.keyCode;
        if(code==13){

          if(event.shiftKey === true){ // Shift + Enter 처리
            //console.log("Shift도 눌러짐");
          
          }
          else this.send();
        }

        //console.log(code);
    },

    sendMessage() {
      event.preventDefault();
      var $msgForm = $("#msgForm");
      var $other = $("#other");
      // 서버로 메시지를 전송한다.
      if ($other.val() == "") {
        // 서버로 메시지를 전송한다.
        this.$socket.emit("chat", { msg: $msgForm.val() });
        $msgForm.val("");
      } else {
        console.log("귓속말 대상 : " + $other.val());
        this.$socket.emit("chatto", { msg: $msgForm.val(), id: $other.val() });
        $msgForm.val("");
      }
      $('#chatLogs').scrollTop($('#chatLogs').prop('scrollHeight'));

                setTimeout(function(){
                    $('#chatLogs').scrollTop($('#chatLogs').prop('scrollHeight'));
                }, 300);

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
    
    width: 600px;
    box-shadow: 0 2px 4px 0 rgba(0,0,0,0.50);
    transition: width 0.3s ease;
  }
  
  .chat-header {
    background-color: white;
    position: relative;
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
  
  #send {
    background-color: #4a90e2;
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