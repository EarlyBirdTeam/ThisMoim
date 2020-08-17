<template>
  <div class="user" id="join">
    <div class="wrapC table">
      <div class="middle">
        <v-card
          class="d-inline-block px-auto mx-auto"
          style=" width:70em;  background-color:#f2f2f2"
        >
          <div class="form-wrap" style="width:90%; margin:auto">
            <div class="input-wrap">
              <p style="text-align: left">Name</p>
              <input v-model="realName" id="realName" placeholder="Enter your name" type="text" />
            </div>
            <div class="input-wrap">
              <p style="text-align: left">Email</p>
              <input
                v-model.lazy="email"
                id="email"
                placeholder="Enter your email"
                type="text"
                @change="emailCheck"
              />
              <!-- 이메일이 사용중인지 체크 -->
              {{this.$store.getters.canIUseIt}}
            </div>
            <div class="input-wrap">
              <p style="text-align: left">닉네임</p>
              <input v-model="nickName" id="nickName" placeholder="Enter your nickname" type="text" />
            </div>

            <div class="input-wrap password-wrap">
              <p style="text-align: left">Password</p>
              <input
                v-model="password"
                id="password"
                :type="passwordType"
                placeholder="Make sure it's at least 8 characters"
              />
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
                placeholder="Please enter your password again"
              />
              <span :class="{active : passwordConfirmType==='text'}">
                <i class="fas fa-eye"></i>
              </span>
            </div>
             <div style="margin:auto">
            <label class="middle" style="text-align:center">
              <v-dialog v-model="dialog" width="600px" style="text-align:center">
                <template v-slot:activator="{ on, attrs }" style="text-align:center">
                  <div style="width:400px">
                    <div
                      d-flex
                      v-bind="attrs"
                      class="btn"
                      v-on="on"
                      style=" width:100px"
                    >Term of Use</div>

                    <p d-flex>Please check the terms and conditions</p>
                  </div>
                </template>
                <v-card>
                  <v-card-title>
                    <span class="headline">회원가입 약관</span>
                  </v-card-title>
                  <v-card-text>
                    제1장 총 칙
                    <br /><strong>제1조(목적)</strong>
                    <br />이 약관은 이거모임(http://i3a510.p.ssafy.io/)이 온라인으로 제공하는 각종 서비스의 이용과 관련하여 이거모임과 이용자의 권리, 의무 및 책임사항 등을 규정함을 목적으로 합니다.
                    <br /><br /><strong>제2조(정의)</strong>
                    <br />이 약관에서 사용하는 용어의 정의는 다음과 같습니다.
                    <br />1. "이거모임회원(이하 "회원"이라 합니다)"이라 함은 회원 아이디(ID)를 부여받은 이용자로서 이거모임에서 제공하는 서비스를 지속적으로 이용할 수 있는 자를 말합니다.
                    <br />2. "아이디(ID)"라 함은 회원의 식별과 서비스 이용을 위하여 회원이 정하고 연수원이 승인하는 문자 또는 숫자의 조합을 말합니다.
                    <br />3. "비밀번호(PASSWORD)"라 함은 회원이 부여받은 아이디와 일치되는 회원임을 확인하고 비밀보호를 위해 회원 자신이 정한 문자 또는 숫자의 조합을 말합니다.
                  </v-card-text>
                  <v-checkbox v-model="checkbox1" :label="`약관에 동의합니다`"></v-checkbox>
                </v-card>
              </v-dialog>
              <br />
            </label>
          </div>
          <div class="right">
          <button style="width:70px; margin:5px; background-color:#d9d9d9; border:solid 0px;" @click="createUserRequest" class="btn">
            <span>확인</span>
          </button>
          <router-link to= "/" style="width:70px; margin:5px; background-color:#d9d9d9; border:solid 0px;" class="btn" >
            <span>취소</span>
          </router-link>
          </div>
          </div>
         
          
        </v-card>
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
  watch: {},
  created() {
    this.$store.commit("toggleModal");
  },
  methods: {
    createUserRequest: function () {
      var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
      var passwordExp = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d$@$!%*#?&]{8,}$/;
      if (nickName.value == "") {
        alert("닉네임을 입력해주세요");
      } else if (exptext.test(email.value) == false) {
        //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우
        alert("이메일형식이 올바르지 않습니다.");
      } else if (realName.value == "") {
        alert("이름을 입력해주세요");
      } else if (password.value == "") {
        alert("비밀번호를 입력해주세요");
      } else if (passwordExp.test(password.value) == false) {
        alert("비밀번호 형식이 잘못되었습니다.");
      } else if (password.value != passwordConfirm.value) {
        alert("비밀번호가 동일하지않습니다. 다시 입력해주세요.");
      } else if (!this.isTerm) {
        alert("약관을 읽어보시고, 동의란에 체크해주세요.");
      } else {
        this.$store.dispatch(constants.METHODS.CREATE_USER, {
          email,
          password,
          nickName,
          realName,
        });
        this.$router.push(constants.URL_TYPE.USER.JOINDONE);
      }
    },
    emailCheck: function () {
      this.$store.dispatch(constants.METHODS.EMAILCHECK, email.value);
    },
    test1: function () {
      console.log(this.email);
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
    };
  },
};
</script>


