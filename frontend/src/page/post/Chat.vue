<template>
  <div style="margin-top:45px">
  
    <div style="border:solid 1px;">
        <h3>욕설 금지^^</h3>
        
        <div>
            <!-- {{chatLogs}} -->
        <p v-for="chatLog in chatLogs" style="border:solid 1px;">
            {{chatLog}}
            
        </p>
        </div>
    </div>
    <form style="margin-top:5px  left:0; bottom:0;" class="form-inline">
      <div class="form-group" style="margin-top:3px; " >
        <label for="msgForm"></label>
            
        <input type="text" style="border:solid 1px;" placeholder="귓말 대상" class="form-control d-flex-6" id="other">
        <!-- <v-overflow-btn
        style="margin:0 "
        background-color="rgb(230,230,230)"          
        :chatmem="chatmem"
        label="전체에게"
         class="form-control btn d-flex-1" id="other"
         dense
        >
        </v-overflow-btn> -->
        <input type="text" style="border:solid 1px;" placeholder="메시지를 입력해주세요 :)" class="form-control d-flex-3" id="msgForm"/>
        <button style="border:solid 1px;" @click="sendMessage()">작성</button>  
      </div>
      
    </form>
    
  </div>
</template>
<script>
export default {
    name: 'Chat',
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

            this.chatLogs.push(data.name + "님이 입장하셨습니다");
        });

        this.$socket.on("s2c chat", (data)=> {
            this.chatLogs.push("<div>" + data.from.name + " : " + data.msg + "</div>");
        });

        this.$socket.on("out", (data)=> {
                    this.chatLogs.push(data.from.name+"님이 나가셨습니다.");

                });
        this.$socket.on(()=> {
            
        })
        },
    data() {
        return {
            textarea: "", message: '', chatmem:[], chatLogs:[]
            }
        },
    methods: {
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
        }
    }

</script>

<style>
</style>