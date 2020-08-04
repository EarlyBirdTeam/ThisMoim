<template>
   <div style="z-index:1000; border: 1px solid; position: absolute; right: 0px; bottom: 0px; width: 350px; height:300px">
    <button id="closeChat" @click="doDisplay()">닫기</button>
    <button id="openChat" @click="doDisplay()">열기</button>

    
    
    
    
    <div id="chat" class="chat_list_wrap">
        <div class="header">
        채팅
        </div>
        
        <div style="margin-top:45px">
    <h3>착한 말 고운말 ^^</h3>
    <div style="border:solid 1px;">
    <div id="chatLogs"></div>
    <div style="height:100px"></div>

    </div>
    <form style="margin-top:5px position:absolute; left:0; bottom:0;" class="form-inline">
      <div class="form-group">
        <label for="msgForm"></label>
        <input type="text" style="border:solid 1px;" placeholder="귓말 대상" class="form-control d-flex-1" id="other">
        <input type="text" style="border:solid 1px;" placeholder="메시지를 입력해주세요 :)" class="form-control d-flex-2" id="msgForm"/>
        <button @click="sendMessage()">작성</button>  
      </div>
      
    </form>
    
  </div>
        
    </div>
</div>
</template>   

<script> 
    
    export default {
        name: 'Bottom',
        components: { 
        },
        props: ['isHeader'],
        computed:{
          
        },
        watch: {
          
        },
        created() {
           // this.$socket.on('chat', (data)=> {
        //     this.textarea += data.message + "\n"
        //     })
        var myname = this.makeRandomName();


        this.$socket.emit("login", {
            //name: this.$store.state.name,
            name: myname
            ,userid: this.$store.state.email,
            
        });

        this.$socket.on("login", (data)=> {

            $("#chatLogs").append("<div><strong>" + data.name + "</strong>님이 입장하셨습니다</div>");
        });

        this.$socket.on("s2c chat", (data)=> {
            $("#chatLogs").append("<div>" + data.from.name + " : " + data.msg + "</div>");
        });

        this.$socket.on("out", (data)=> {
                    $("#chatLogs").append("<div>"+data.from.name+"님이 나가셨습니다.</div>");

                });
            
        },
        methods : {
            doDisplay(){
                var con = document.getElementById("chat");
                if(con.style.display=='none'){
                    con.style.display='block';
                }else{
                    con.style.display='none';
                }
            },

            sendMessage () {
            event.preventDefault();
            var $msgForm = $("#msgForm");
            var $other = $("#other");
            // 서버로 메시지를 전송한다.
            if($other.val() == ''){
                // 서버로 메시지를 전송한다.
                this.$socket.emit("chat", { msg: $msgForm.val() });
                $msgForm.val("");
            }
            else{
                console.log('귓속말 대상 : '+$other.val());
                this.$socket.emit("chatto", { msg: $msgForm.val(), id: $other.val() });
                $msgForm.val("");
            }
            
        },
        makeRandomName(){
            var name = "";
            var possible = "abcdefghijklmnopqrstuvwxyz";
            for( var i = 0; i < 3; i++ ) {
                name += possible.charAt(Math.floor(Math.random() * possible.length));
            }
            return name;
            // var name = 1;
            // return name + Math.random()*10;
           }
           
            
        },
        data: function() {
           return {
             
           }
        },

    }

    
</script>

<style>
    #header{
        border-bottom: 3px solid rgb(255, 235, 221);

        background-color: rgb(255,157,91);
    };
    #header header_name{
        color:rgb(223, 223, 223);
    }

    * {
  margin: 0;
  padding: 0;
}
body {
  font-size: 11px;
}
.chat_list_wrap {
  list-style: none;
}
.chat_list_wrap .header {
  font-size: 14px;
  padding: 15px 0;
  background: #F18C7E;
  color: white;
  text-align: center;
  font-family: "Josefin Sans", sans-serif;
}
.chat_list_wrap .search {
  background: #eee;
  padding: 5px;
}
.chat_list_wrap .search input[type="text"] {
  width: 100%;
  border-radius: 4px;
  padding: 5px 0;
  border: 0;
  text-align: center;
}
.chat_list_wrap .list {
  padding: 0 16px;
}
.chat_list_wrap .list ul {
  width: 100%;
  list-style: none;
  margin-top: 3px;
}
.chat_list_wrap .list ul li {
  padding-top: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e5e5e5;
}
.chat_list_wrap .list ul li table {
  width: 100%;
}
.chat_list_wrap .list ul li table td.profile_td {
  width: 50px;
  padding-right: 11px;
}
.chat_list_wrap .list ul li table td.profile_td img {
  width: 50px;
  height: auto;
}
.chat_list_wrap .list ul li table td.chat_td .email {
  font-size: 12px;
  font-weight: bold;
}
.chat_list_wrap .list ul li table td.time_td {
  width: 90px;
  text-align: center;
}
.chat_list_wrap .list ul li table td.time_td .time {
  padding-bottom: 4px;
}
.chat_list_wrap .list ul li table td.time_td .check p {
  width: 5px;
  height: 5px;
  margin: 0 auto;
  -webkit-border-radius: 50%;
  -moz-border-radius: 50%;
  border-radius: 50%;
  background: #e51c23;
}

    
</style>




