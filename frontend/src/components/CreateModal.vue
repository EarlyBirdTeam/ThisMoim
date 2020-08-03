<template>

    <v-dialog v-model="dialog" scrollable max-width="300px">
      <template v-slot:activator="{ on, attrs }">
        <button
          class="btn--text white--text"
          v-bind="attrs"
          v-on="on"
        style="width:0px padding:0"
        >
        모임생성하기
        </button>
      </template>
      <v-card>
        <v-card-title>모임 생성</v-card-title>
        <v-divider></v-divider>
        <v-card-text style="height: 300px;">
            <create/>
        </v-card-text>

      </v-card>
    </v-dialog>

</template>

<script>
    import create from '../page/post/Create_Community'
    export default {
        components: {
            create,
        },
    }
</script>

<style>

</style>