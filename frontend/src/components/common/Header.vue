<template>
    <div id="header" style="background: linear-gradient(90deg, #efd5ff 0%, #515ada 100%); border:solid 0px;
">
        <h1>
            <router-link class="white--text" style="font-size:20px" v-bind:to="{name:constants.URL_TYPE.POST.ENTER}">
                이거모임
            </router-link>
        </h1>
        <div class="right">
            <!-- <template v-if="this.$store.state.email == ''"> -->
            <template v-if="this.$store.getters.accessToken == ''">
                <div class="headBox" style="height:35px; font-size:20px;c padding-top:5px; padding-bottom:12px; ">
                    <LoginModal/>
                </div>
            </template>

            <template v-else>
                <div class="headBox">
                    환영합니다! 
                    <router-link v-bind:to="{name:constants.URL_TYPE.USER.MYPAGE}" class="btn--text">
                        {{this.$store.getters.userData.nickname}}
                    </router-link> 

                    <button @click="logout">
                        로그아웃
                    </button>
                </div>
            </template>

            
                    <!-- <button @click="check">
                        스토어 체크
                    </button> -->

        </div>  
        

        
    </div>
</template>   

<script> 
    import LoginModal from './LoginModal'
    import constants from '../../lib/constants'
    import cookies from 'vue-cookie'

    export default {
        name: 'Header',
        components: { 
            LoginModal
        },
        props: ['isHeader'],
        watch: {
            modal: function(val) {
                console.log(val);
            }
        },
        // computed: {
        //   isLogged() {
        //     this.$router.go(0)
        //     return this.$store.state.isLogged;  
        //   } 
        // },
        created() {
            const arr = document.cookie.split(";");
            console.log(this.$store.getters.userData);
            // console.log("arr is ");
            // console.log(arr);
            arr.forEach(element => {
                if(element.split('=')[0] == 'AccessToken'){
                    this.userinfo = element.split('=')[1];
                }
            });

            // if(document.cookie.split(";")[0].split('=')[0])
            // console.log();

            
        },
        methods : {
            // openModal(){
            //     this.modal = true;
            //     this.$store.commit("toggleModal");
            // },
            closeModal(){
                this.modal = false;
                this.$store.commit("toggleModal");
            },
            async logout(){
                this.$store.commit(constants.METHODS.LOGOUT_USER);
                try{
                   await this.$router.push('/');
                } catch(error) {
                    // console.log("route same path!");
                    this.$router.go(0);
                } 
            },
            check(){
                console.log(this.$store.state);
            },
            toggle(){
                this.t = !this.t;
            },
             testMethod(email, password){
                console.log(email,password);

                var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
                if(exptext.test(email)==false){
                    //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우            
                    alert("이메일형식이 올바르지 않습니다.");
                  
                }
                else if(password=='')
                {
                    alert("비밀번호를 입력해주세요");
                }
                else {

                    const result = this.$store.dispatch(constants.METHODS.LOGIN_USER, {email, password});
                    console.log(this.userData);this.modal = !this.modal;
                    this.email = '';
                    
                };

                    
                    this.password = '';
            },
            
        },
        data: function() {
           return {
               constants,
               keyword : "",
               modal:this.$store.getters.modal,
                userinfo: '',
                email:"",
                password:"",
                isLogged: this.$store.getters.isLogged,
           }
        },

    }


</script>




