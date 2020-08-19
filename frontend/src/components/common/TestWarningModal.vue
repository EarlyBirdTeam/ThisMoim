<template>
  <v-dialog max-width="600px" persistent v-model="dialog">
    <v-card>
      <v-card-title>
        <h3>모임 탈퇴</h3>
      </v-card-title>
      <v-card-text>
        <div class="withdrawal">
          <span>정말 탈퇴하실거에요?..😢</span><br>
          <ul class="list">
            <li> 사용자는 모든 서비스에 대한 액세스 권한을 즉시 상실하게 됩니다.</li>
            <li>사용자가 만든 콘텐츠는 서비스 내에 남습니다.</li>
          </ul>

          <div v-if="success" style="text-align: center;">✔성공적으로 탈퇴되었습니다</div>
        </div>
        <div v-if="loading" class="d-flex justify-content-center mb-2">
          <v-progress-circular
            :size="25"
            color="orange"
            indeterminate
          ></v-progress-circular>
        </div>
        <div class="text-center">
          <v-btn text class="red lighten-1 white--text mx-2 mt-3" @click="withdrawal">탈퇴</v-btn>
          <v-btn text class="orange lighten-2 white--text mx-2 mt-3" @click="close">닫기</v-btn>
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
export default {

}
</script>

<style>

</style>