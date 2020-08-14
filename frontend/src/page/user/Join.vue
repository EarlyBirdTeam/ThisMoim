<template>
  <div class="user" id="join">
    <!-- <div class="wrap " style=" padding-right:600px; padding-left:600px; padding-bottom:40px"> -->
    <div class="wrap d-flex" style=" padding-bottom:40px">
      <div style="width:50%; height:150px">
          <img src="../../assets/img/study4.jpg"/>
      </div>
      <div style="width:50%">
        <br />
        <h2>이거모임</h2>

        <div class="form-wrap"  style="width:50%; background-color:rgb(0,0,0)" >
            <div class="input-wrap">
                <p style="text-align: left">Name</p>
            <input v-model="realName" id="realName" placeholder="Enter your name" type="text" />
          </div>
            <div class="input-wrap">
                <p style="text-align: left">Email</p>
            <input v-model="email" id="email" placeholder="Enter your email" type="text" />
          </div>
          <div class="input-wrap">
              <p style="text-align: left">닉네임</p>
            <input v-model="nickName" id="nickName" placeholder="Enter your nickname" type="text" />
          </div>


          <div class="input-wrap password-wrap">
                <p style="text-align: left">Password</p>
            <input v-model="password" id="password" :type="passwordType" placeholder="Make sure it's at least 8 characters" />
            <span :class="{active : passwordType==='text'}">
              <i class="fas fa-eye"></i>
            </span>
          </div>

          <div class="input-wrap password-wrap">
                <p style="text-align: left">Password Confirm</p>
            <input
              v-model="passwordConfirm"
              id="passwordConfirm"
              :type="passwordConfirmType"
              placeholder=" 
Please enter your password again"
            />
            <span :class="{active : passwordConfirmType==='text'}">
              <i class="fas fa-eye"></i>
            </span>
          </div>
        </div>
<div>
        <label style="text-align:left">
          <v-dialog v-model="dialog" width="600px" style="text-align:left">
            <template v-slot:activator="{ on, attrs }" style="text-align:left">
              <div class="" >
              <div v-bind="attrs" class="btn" v-on="on" style="width:100px">Term of Use</div>
              
                     <p>Please check the terms and conditions</p>
                     </div>
            </template>
            <v-card>
              <v-card-title>
                <span class="headline">회원가입 약관</span>
              </v-card-title>
              <v-card-text>
                제1장 총 칙
                <br />제1조(목적)
                <br />이 약관은 이거모임(http://i3a510.p.ssafy.io/)이 온라인으로 제공하는 각종 서비스의 이용과 관련하여 이거모임과 이용자의 권리, 의무 및 책임사항 등을 규정함을 목적으로 합니다.
              </v-card-text>
                    <v-checkbox v-model="checkbox1" :label="`약관에 동의합니다`"></v-checkbox>
            </v-card>
          </v-dialog>
          <br />
    

          
        </label>
            
        
        </div>
        <button @click="createUserRequest" class="btn">
          <span>작성완료</span>
        </button>

        <!-- <router-link v-bind:to="{name:constants.URL_TYPE.USER.JOINDONE}" @click="createUserRequest" class="btn">
                작성완료 
        </router-link>-->
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/user.scss";
import constants from "../../lib/constants";

export default {
  components: {},
  created() {
      this.$store.commit("toggleModal")
  },
  methods: {
    createUserRequest: function () {
      var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
      var passwordExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,}$/;
      console.log(email.value)
      if (realName.value == "") {
        alert("이름을 입력해주세요");
      } else if (exptext.test(email.value) == false) {
        //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우
        alert("이메일형식이 올바르지 않습니다.");
      } else if (nickName.value == "") {
        alert("닉네임을 입력해주세요");
      } else if (password.value == "") {
        alert("비밀번호를 입력해주세요");
      } else if (passwordExp.test(password.value) == false) {
        alert("비밀번호 형식이 잘못되었습니다.");
      } else if (password.value != passwordConfirm.value) {
        alert("비밀번호가 동일하지않습니다. 다시 입력해주세요.");
      } else if(this.checkbox1 != true){
          alert("약관에 동의해주세요.");
      } 
      else {
        this.$store.dispatch(constants.METHODS.CREATE_USER, {
          email,
          password,
          nickName,
          realName,
        });
        this.$router.push(constants.URL_TYPE.USER.JOINDONE);
      }
    },
  },
  watch: {},
  data: () => {
    return {
      email: "",
      nickName: "",
      realName: "",
      password: "",
      passwordConfirm: "",
      isTerm: false,
      passwordType: "password",
      passwordConfirmType: "password",
      constants,
      checkbox1: false,
    };
  },
};
</script>


