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

<div class="my-2">
          <v-btn color="success" style="right:0; bottom:0; position:absolute;" fab x-large dark @click="chatonoff()">
            <v-icon>mdi-message-bulleted</v-icon>
          </v-btn>
        </div>
        <div style="">
     <Chat v-if="ischat"/>
        </div>



<!-- <v-dialog v-model="dialog" width="600px">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          v-btn color="success" style="right:0; bottom:0; position:absolute;"
          dark fab x-large
          v-bind="attrs"
          v-on="on"
        >
          <v-icon>mdi-message-bulleted</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Use Google's location service?</span>
        </v-card-title>
        <v-card-text>
          <Chat/>

        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">Disagree</v-btn>
          <v-btn color="green darken-1" text @click="dialog = false">Agree</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog> -->

<!-- <div class="my-2">
          <v-btn color="success" style="right:0; bottom:0; position:absolute;" fab x-large dark>
            <v-icon>mdi-message-bulleted</v-icon>
          </v-btn>
        </div> -->
  </div>
</template>

<script>
import Testboard from "./Test_Board"
import Chat from "./Chat"
export default {
    components: {Testboard,Chat },
    data: () => ({
          ischat:true
          ,
          items: [
          '보드1','보드2','보드3','보드4',
        ],
    }),
    methods: {
        chatonoff() {
          console.log(this.ischat)
          if (this.ischat==true) {
            this.ischat=false
          }
          else {
            this.ischat=true
          }
        }
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