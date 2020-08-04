<template>
    <div style="height:200px">
        <div class="form-wrap">
            <div class="input-wrap">
                <input v-model="boardName"
                    id="boardName"
                    placeholder="모임 이름을 입력해주세요"
                    type="text"/>
            </div>


            <div class="input-wrap password-wrap">
                <input v-model="enterPassword"
                    id="password" 
                    :type="passwordType"
                    placeholder="비밀번호를 입력해주세요"/>
                <span :class="{active : passwordType==='text'}">
                        <i class="fas fa-eye"></i>
                    </span>
            </div>  
        </div>
        <button @click="createBoard"  class="btn">
            <span>
                작성완료
            </span>
        </button>
    </div>
  
</template>

<script>
    import constants from '../../lib/constants'
    export default {
        data: () => {
                return {
                    leader: '',
                    boardName: '',
                    enterPassword: '',
                    passwordType:"password",

                    constants
                }
            },
        methods: {
            createBoard: function(){
                const boardInfo = {
                    leader : user.nickname,
                    boardName: this.boardName,
                    enterPassword : this.enterPassword,
                }
                const API_CREATE_URL = API_BASE_URL + ''
              
                    axios.post(API_CREATE_URL, boardInfo)
                    .then(res => { 
                    
                    this.$router.push('')
                    alert("모임이 생성되었습니다.")
                    })
                    .catch(err => {
                    console.error(err.response.status)
                    this.$router.push('*')
                    this.$router.go(0)
                    })
                    }
                
                },
            }

</script>

<style>
    .btn{
        position: absolute;
        margin: 0 auto;
        bottom: 5px;
    }
</style>