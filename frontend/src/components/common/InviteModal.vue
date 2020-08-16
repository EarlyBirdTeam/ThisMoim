<template>
  <v-dialog max-width="600px" persistent v-model="dialog">
    <v-card>
      
      <v-snackbar
        app
        top
        v-model="snackbar"
        timeout="2000"
        color="success"
      >성공적으로 전송 되었습니다</v-snackbar>
      <v-card-title>
        <h3>멤버 초대</h3>
      </v-card-title>
      <v-card-text>
        <v-form class="px-3" ref="form">
          <div class="mail-form">
            <v-text-field
              label="메일 주소"
              v-model="member"
              prepend-icon="mdi-mail"
              :rules="[rules.required, rules.email]"
              @keyup.enter="append"
            ></v-text-field>
            <v-btn text class="primary white--text mx-2 mt-3" @click="append">추가</v-btn>
          </div>
          <div class="mail-list">
            <v-icon>mdi-account-group</v-icon>
            <span>리스트</span><br>
            <div class="members">
              <div class="member" v-for="(member, idx) in memberList" :key="idx">
                <div>{{ member }}
                  <v-icon @click="deleteMember(idx)" color="white">mdi-close</v-icon>
                </div>
              </div>
            </div>
          </div>
          <div class="text-center">
            <v-btn text class="primary white--text mx-2 mt-3" @click="submit">보내기</v-btn>
            <v-btn text class="primary white--text mx-2 mt-3" @click="close">닫기</v-btn>
          </div>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  created() {
    this.$store.state.inviteModal = true;
  },
  data() {
    return {
      memberList: [],
      member : '',
      snackbar: false,
      // cnt: 0,
      rules : {
        required: value => !!value || 'Required.',
        email: value => {
          const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
          return pattern.test(value) || 'Invalid e-mail.'
        },
      },
      colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
    };
  },
  computed: {
    dialog() {
      return this.$store.state.inviteModal;
    },
  },
  methods: {
    submit() {
      console.log('auth에 보낼꺼야~~~~~~')
      // this.snackbar = true;
      this.$store.state.inviteModal = false;
    },
    append() {
      this.memberList.push(this.member)
      this.member = ''
    },
    close() {
      this.$store.state.inviteModal = false;
    },
    deleteMember(idx) {
      this.memberList.splice(idx, 1);
    },
    rndColor() {
      return this.colors[this.rnd(0, this.colors.length - 1)];
    },
  }
};
</script>
<style>
.mail-form {
  display: flex;
  margin-bottom: 16px;
}
.mail-list {
  margin-bottom: 16px;
}
.mail-list i {
  padding-bottom: 5px;
}
.mail-list span {
  flex: 1 1 auto;
  font-size: 1.05rem;
  margin-left: 8px;
  /* line-height: 12px; */
}

.members {
  /* background-color: #E3F2FD;
  padding: 10px; */
  border-radius: 4px;
  min-height: 56.47px;
  margin: 10px 8px 5px 10px;
}

.member {
    color: #fff;
    background-color: #64B5F6;

    display: inline-block;
    padding: .25em .4em;
    /* font-size: 75%; */
    margin: 3px 6px;
    font-weight: 700;
    line-height: 1;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25rem;
    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}
.member i {
  cursor: pointer;
} 
</style>