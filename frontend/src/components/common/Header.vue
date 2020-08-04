<template>
    <div id="header" style="height:80px  align-items:center;" v-if="isHeader">
       
            <div style="float:right width:300px; padding-bottom:0px">
            <div style="margin-right:30px" v-if="this.$store.state.email == ''">
                <LoginModal/>
            </div>
                        <div style="float:right" v-if="this.$store.state.email != ''">

                    <CreateModal style="width:10px"/>
                    <router-link v-bind:to="{name:constants.URL_TYPE.USER.MYPAGE}" class="btn--text white--text" style="textDecoration:none">
                        {{userData}}
                    </router-link> 

                
                    <button @click="logout" class="white--text">
                        로그아웃
                    </button>
                
            </div>
            </div>
        <h3 style="width:200px float:left  ">
            <router-link class="white--text" v-bind:to="{name:constants.URL_TYPE.POST.MAIN}" style="textDecoration:none">
                이거모임?
            </router-link>
        </h3>
  
<!--         
            <div v-if="this.$store.state.email == ''">
                <LoginModal/>
            </div> -->
    
            <!-- <div class="search-input">
                <i class="fas fa-search"></i>
                <input v-model="keyword" type="text"/>
            </div>  -->
            
            <!-- <router-link v-bind:to="{name:constants.URL_TYPE.USER.LOGIN}" class="login-btn">
                로그인 
            </router-link>   -->

            
            <!-- <button @click="check">
                store 체크
            </button> -->
            <!-- <p>{{this.$store.state.email}}</p> -->
            



        
        
            
            <!-- <loginModal @close="closeModal" v-if="modal">
                
                
                <div class="input-wrap">
                        <input v-model="email"
                        id="email" 
                        placeholder="이메일을 입력해주세요"
                        type="text"/>
                    </div>
                    <div class="input-wrap">
                        <input v-model="password" type="password"
                        id="password"
                        placeholder="영문, 숫자 혼용 8자 이상"/>
                    </div>
                
                
                <template slot="footer">
                                <button class="btn btn--back btn--login" v-on:click="testMethod(email, password)">
                                    로그인 하기
                                </button>
                                
                </template>

            </loginModal> -->
        
    </div>
</template>   

<script> 
    import LoginModal from '../LoginModal'
    import constants from '../../lib/constants'
    import cookies from 'vue-cookie'
    import CreateModal from '../CreateModal'
    export default {
        name: 'Header',
        components: { 
            LoginModal,
            CreateModal
    
            
        },
        props: ['isHeader'],
        computed:{
            userData(){
                return this.$store.state.email;
            }
            // userinfo:{
            //     get() {
            //         const arr = document.cookie.split(";");
            //         console.log("asdfasdfasdf");

            //         arr.forEach(element => {
            //             if(element.split('=')[0] == 'Logged'){
            //                 userinfo = element.split('=')[1];
            //             }
            //         });
            //     }
            // },

        },
        watch: {
            modal: function(val) {
                console.log(val);
            }
        },
        created() {
            // console.log("arr is ");
            // console.log(arr);
            // const arr = document.cookie.split(";");

            // arr.forEach(element => {
            //     if(element.split('=')[0] == 'Logged'){
            //         this.userinfo = element.split('=')[1];
            //     }
            // });

            // if(document.cookie.split(";")[0].split('=')[0])
            // console.log();

            
        },
        methods : {
            openModal(){
                this.modal = true;
            },
            closeModal(){
                this.modal = false;
            },
            async logout(){
                this.$store.dispatch(constants.METHODS.LOGOUT_USER);
                try{
                   await this.$router.push('/');
                   alert("정상적으로 로그아웃 되었습니다")
                } catch(error) {
                    console.log("route same path!");
                } 
            },
            check(){
                console.log(this.$store.state);
            },
            toggle(){
                this.t = !this.t;
            },
            //  testMethod(email, password){
            //     console.log(email,password);

            //     var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
            //     if(exptext.test(email)==false){
            //         //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우            
            //         alert("이메일형식이 올바르지 않습니다.");
                  
            //     }
            //     else if(password=='')
            //     {
            //         alert("비밀번호를 입력해주세요");
            //     }
            //     else {

            //         const result = this.$store.dispatch(constants.METHODS.LOGIN_USER, {email, password});
            //         console.log(this.userData);this.modal = !this.modal;
            //         this.$router.push('/board')
                  
            //     };

                    
            //     // this.email = '';
            //     // this.password = '';
            // },
            
        },
        data: function() {
           return {
               constants,
               keyword : "",
               modal:this.$store.state.modal,
               userinfo:this.$store.state.email,
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
    
</style>




