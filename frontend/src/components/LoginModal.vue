
<template>
    <v-dialog v-model="dialog" width="350px">
      <template v-slot:activator="{ on, attrs }">
        
        <button
          
          style="float:right"
          v-bind="attrs"
          v-on="on"
          color="rgb(223, 223, 223)"
        >
        
          로그인
        </button>
      </template>
      <v-card style="width:350px height:200px">
        <v-card-title>로그인</v-card-title>
        <v-card-text style=" background-color:white padding:0 height:120px"><Login style="height:120px"/></v-card-text>
        
      </v-card>
    </v-dialog>
</template>
<script>
    import Login from '../page/user/Login'
    export default {
        components: {
            Login,
        },
    }
</script>

<style>

</style>