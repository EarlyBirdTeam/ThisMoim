<template>
<div style="margin:2vw">

    <div style="width:20%;">
    <v-overflow-btn
        style="margin:0 padding-top:0"
        background-color="rgb(255,157,91)"          
        :items="items"
        label="보드를 선택해주세요"
        >ㅎㅇ
    </v-overflow-btn>
</div>

    <v-tabs
      v-model="tab"
      background-color="rgb(255,157,91)"
      style="height:3px"
      centered
      dark
      text
    >
   
 <!-- <v-tabs-slider></v-tabs-slider> -->
      <v-tab router-link to="test" >
        <div class="d-flex">
            <v-icon>mdi-ballot</v-icon>Board
        </div>
      </v-tab>
      <v-tab router-link to="schedule">
        <div class="d-flex">
        <v-icon>mdi-calendar-today</v-icon>Schedule
        </div>
      </v-tab>  
      <v-tab router-link to="members" >
          <div class="d-flex">
        
        <v-icon>mdi-account-box</v-icon>Members
          </div>
      </v-tab>

      <v-tab router-link to="chat">
        <div class="d-flex">
        <v-icon>mdi-message-bulleted</v-icon>Chat
        </div>
      </v-tab>
          
    </v-tabs>
<!--  -->
  </div>
</template>

<script>
import Testboard from "./Test_Board"
export default {
    components: {Testboard },
    data: () => ({
        
          items: [
          '보드1','보드2','보드3','보드4',
        ],
    }),
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
    // created() {
    //     this.getMoim()
    // }

}
</script>

<style scoped>
    .v-text-field__details {
        height: 0;
        min-height: 0px;}
</style>