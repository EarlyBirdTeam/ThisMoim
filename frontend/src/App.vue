<template>
  <v-app>
    <v-main id="bg">
      <Header :isHeader="isHeader"/>
      
      <div v-if="isBoard"><Baseboard/></div>

      <router-view id="container"></router-view>
    </v-main>
  </v-app>
</template>

<script>
// import './assets/css/style.scss' 
import Header from './components/common/Header.vue'
// import Sidebar from './components/common/Sidebar'
import Baseboard from './page/post/BaseBoard'
import CreateModal from './components/CreateModal'
import constants from './lib/constants' 
import axios from 'axios'

export default {
  name: 'App',
  components: { 
    Header,
    CreateModal,
    Baseboard
    // Sidebar

  },
  created() {
      let url = this.$route.name;

      this.checkUrl(url);
      this.checkBoard(url);
  },
  watch: {
      $route (to){

          this.checkUrl(to.name);
          this.checkBoard(to.name)
      }
  },
  methods : {
      checkUrl(url) { 

          let array = [
              constants.URL_TYPE.USER.LOGIN,
              constants.URL_TYPE.USER.JOIN,
              constants.URL_TYPE.USER.JOINDONE
          ];

          let isHeader = true;
         
          // array.map(path => {
          //     if (url === path)
          //         isHeader = false;
          //         isSidebar = false;
          // })
          this.isHeader = isHeader;
    

      },
      checkBoard(url) {
        let array = [
          "members","test_vue","base","chat",'schedule'
        ];
        console.log(url)
        let isBoard = false;
        array.map(path => {
              if (url === path)
              
                  isBoard = true;
          })
        this.isBoard = isBoard;

      }
  },
  data: function () {
        return {
            isHeader: true,
            isBoard: false,
            constants,
        } 
    }
}
</script>

<style >
  #container{
    /* width: 90vw; */
    float: center;
    /* border: solid gray 1px; */
    background-color: white;
    border-radius: 2%;

    text-align: center;
    margin: 2.5vh 2vw;
    
  }
  #bg{
    background-color: rgb(255, 235, 221);

  }
</style>