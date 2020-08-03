<!doctype html>
<html lang="en">
  <head>
    <title>Websocket Chat</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- CSS -->
    <link rel="stylesheet" href="/webjars/bootstrap/4.3.1/dist/css/bootstrap.min.css">
    <style>
      [v-cloak] {
          display: none;
      }
    </style>
  </head>
  <body>
    <div class="container" id="app" v-cloak>
        <div class="row">
            <div class="col-md-6">
                <h3>채널 리스트</h3>
            </div>
            <div class="col-md-6 text-right">
                <a class="btn btn-primary btn-sm" href="/logout">로그아웃</a>
            </div>
        </div>
        <div class="input-group">
            <div class="input-group-prepend">
                <label class="input-group-text">방제목</label>
            </div>
            <input type="text" class="form-control" v-model="channel_name" v-on:keyup.enter="createChannel">
            <div class="input-group-append">
                <button class="btn btn-primary" type="button" @click="createChannel">채널 개설</button>
            </div>
        </div>
        <ul class="list-group">
            <li class="list-group-item list-group-item-action" v-for="item in channels" v-bind:key="item.channelId" v-on:click="enterRoom(item.channelId, item.channelName)">
                <h6>{{item.channelName}} <span class="badge badge-info badge-pill">{{item.userCount}}</span></h6>
            </li>
        </ul>
    </div>
    <!-- JavaScript -->
    <script src="/webjars/vue/2.5.16/dist/vue.min.js"></script>
    <script src="/webjars/axios/0.17.1/dist/axios.min.js"></script>
    <script>
        var vm = new Vue({
            el: '#app',
            data: {
                channel_name : '',
                channels: [
                ]
            },
            created() {
                this.findAllChannel();
            },
            methods: {
                findAllChannel: function() {
                    axios.get('/board/channels').then(response => {
                        // prevent html, allow json array
                        if(Object.prototype.toString.call(response.data) === "[object Array]")
                            this.channels = response.data;
                    });
                },
                createChannel: function() {
                    if("" === this.channel_name) {
                        alert("채널 이름을 입력해 주십시오.");
                        return;
                    } else {
                        var params = new URLSearchParams();
                        params.append("name",this.channel_name);
                        console.log(params.toString());
                        axios.post('/board/channel', params)
                        .then(
                            response => {
                                console.log(response);
                                alert(response.data.channel_name+"채널 개설에 성공하였습니다.")
                                this.channel_name = '';
                                this.findAllChannel();
                            }
                        )
                        .catch( response => { alert("채널 개설에 실패하였습니다."); } );
                    }
                },
                enterRoom: function(channelId, channelName) {
                    localStorage.setItem('wsboard.channelId',channelId);
                    localStorage.setItem('wsboard.channelName',channelName);
                    location.href="/board/channel/enter/"+channelId;
                }
            }
        });
    </script>
  </body>
</html>