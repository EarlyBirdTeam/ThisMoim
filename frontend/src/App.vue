<template>
  <v-app>
    <v-main id="bg">
      <Header style="height:60px" :isHeader="isHeader"/>
      
      <div v-if="isBoard"><Baseboard/></div>

      <router-view id="container"></router-view>
    </v-main>
  </v-app>
</template>

<script>
import './assets/css/style.scss' 
import Header from './components/common/Header.vue'

import Baseboard from './page/post/BaseBoard'
import constants from './lib/constants' 
import axios from 'axios'

export default {
  name: 'App',
  components: { 
    Header,
     Baseboard
  },
  created() {
      let url = this.$route.name;

      this.checkUrl(url);
      this.checkBoard(url);
  },
  watch: {
      $route (to){
this.checkBoard(to.name)
          this.checkUrl(to.name);
      }
  },
  methods : {
      checkUrl(url) { 
          console.log(url);
          let array = [
              constants.URL_TYPE.USER.LOGIN,
              constants.URL_TYPE.USER.JOIN,
              constants.URL_TYPE.USER.JOINDONE
          ];

          let isHeader = true;
          let isSidebar = true;
          // array.map(path => {
          //     if (url === path)
          //         isHeader = false;
          //         isSidebar = false;
          // })
          this.isHeader = isHeader;
          this.isSidebar = isSidebar;

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