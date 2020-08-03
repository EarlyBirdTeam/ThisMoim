<template>
    <div id="sidebar">
        <div class="tag-list-wrap">
                <!-- <h4>{{Moim.profile}}</h4> -->
                <h4>프로필</h4>
                <ul class="tag-list">
                    <li>
                        <router-link v-bind:to="{name:constants.URL_TYPE.POST.BASE}" class="btn--text">
                          <img src="https://image.flaticon.com/icons/svg/846/846449.svg" alt="">
                        </router-link> 
                    </li>
                    <li>
                        <router-link v-bind:to="{name:constants.URL_TYPE.POST.CHAT}" class="btn--text">
                          <img src="https://image.flaticon.com/icons/svg/992/992675.svg" alt="">
                        </router-link> 
                    </li>
                    <li>
                        <router-link v-bind:to="{name:constants.URL_TYPE.USER.MYPAGE}" class="btn--text">
                          <img src="https://image.flaticon.com/icons/svg/1098/1098534.svg" alt="">
                        </router-link> 
                    </li>
                    <li>
                        <router-link v-bind:to="{name:constants.URL_TYPE.POST.TEST_BOARD}" class="btn--text">
                          <img src="https://image.flaticon.com/icons/svg/645/645822.svg" alt="">
                        </router-link> 
                    </li>

                    <li>
                        <router-link v-bind:to="{name:constants.URL_TYPE.POST.MEMBERS}" class="btn--text">
                         <img src="https://image.flaticon.com/icons/svg/615/615075.svg" alt="">
                        </router-link> 
                    </li>
                </ul>   
            </div>
    </div>
</template>

<script>
import constants from '../../lib/constants'
export default {
    
        name: 'Sidebar',
        props: ['isSidebar'],
        data: function() {
           return {
               constants,
               Moim:[]
           }
        },
            methods: {
        // getMoim() {
        //     const API_MOIM_URL = API_BASE_URL + '/board'
        //     const config ={}
        //     this.$axios.get(API_MOIM_URL, config)
        //     .then(res => {
        //         this.members = res.data
        //     })
        //     .catch(err => {
        //         console.error(err)
        //     })            
        // }
    },
    created() {
        this.getMoim()
    }
}
</script>

<style scoped>
 .tag-list-wrap{
     background-color: white;
     width: 5vw;
     float: left;
     margin:1%;
     /* top: auto; */
     border-right: 3px solid rgb(223, 223, 223);
     text-align: center;
     
     height: 40%;
     top: 7.5%;
     left: 1%;
 }
 
 .tag-list{
     padding-left: 0;
     text-align: center;
 }

 img{
     height: 3vh;
     width: 3vw;
 }
</style>