<template>
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
</template>
<script>
export default {
    name: 'Chat',
    created() {
        // this.$socket.on('chat', (data)=> {
        //     this.textarea += data.message + "\n"
        //     })

        console.log(this.$socket.id)

        this.$socket.emit("login", {
            name: this.$store.state.email,
            userid: this.$store.state.email
        });

        this.$socket.on("login", (data)=> {

            $("#chatLogs").append("<div><strong>" + data + "</strong>님이 입장하셨습니다</div>");
        });

        this.$socket.on("s2c chat", (data)=> {
            $("#chatLogs").append("<div>" + data.from.name + " : " + data.msg + "</div>");
        });

        this.$socket.on("out", (data)=> {
                    $("#chatLogs").append("<div>"+data.from.name+"님이 나가셨습니다.</div>");

                });
        },
    data() {
        return {
            textarea: "", message: '',
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
            
            }
        }
    }

</script>

<style>
</style>