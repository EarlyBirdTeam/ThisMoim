<template class="bg-light">
    <div class="container">
  <div class="py-5 text-center">
    <!-- <img class="d-block mx-auto mb-4" src="../assets/brand/bootstrap-solid.svg" alt="" width="72" height="72"> -->
    <h2>회원정보 수정</h2>
    <p class="lead">프로필 수정을 하실 수 있습니다.</p>
  </div>

  <div class="row">
    <div class="col-md-4 order-md-2 mb-4">
      <h4 class="d-flex justify-content-between align-items-center mb-3">
        <span class="text-muted">가입한 모임</span>
        <span class="badge badge-secondary badge-pill">3</span>
      </h4>
      <ul class="list-group mb-3">
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div>
            <h6 class="my-0">스프링부트 스터디</h6>
            <small class="text-muted">Spring Framework 모임입니다.</small>
          </div>
          <span class="text-muted">16/30(명)</span>
        </li>
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div>
            <h6 class="my-0">출사 모임</h6>
            <small class="text-muted">한강으로 출사~</small>
          </div>
          <span class="text-muted">12/30(명)</span>
        </li>
        <li class="list-group-item d-flex justify-content-between lh-condensed">
          <div>
            <h6 class="my-0">술 모임</h6>
            <small class="text-muted">소주 적셔</small>
          </div>
          <span class="text-muted">29/30(명)</span>
        </li>
        <li class="list-group-item d-flex justify-content-between bg-light">
          <div class="text-success">
            <h6 class="my-0">가장 최근에 참여한 모임</h6>
            <small>스프링부트</small>
          </div>
          <!-- <span class="text-success"></span> -->
        </li>
        <li class="list-group-item d-flex justify-content-between">
          <span>총 모임</span>
          <strong>3개</strong>
        </li>
      </ul>

      <form class="card p-2">
        <div class="input-group">
          <input type="text" class="form-control" placeholder="모임 검색">
          <div class="input-group-append">
            <button type="submit" class="btn btn-secondary">검색</button>
          </div>
        </div>
      </form>
    </div>
    <div class="col-md-8 order-md-1">
      <h4 class="mb-3">내 프로필</h4>
      <form class="needs-validation" novalidate>
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="firstName">이름</label>
            <input type="text" class="form-control" id="Name" placeholder="" value="" required>
            <div class="invalid-feedback">
              이름을 작성해주세요.
            </div>
          </div>
        </div>
        

        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="firstName">비밀번호</label>
            <input type="password" class="form-control" id="password" placeholder="" value="" required>
            <div class="invalid-feedback">
              비밀번호를 작성해주세요.
            </div>
          </div>
        </div>

        <div class="mb-3">
          <label for="username">닉네임</label>
          <div class="input-group">
            <div class="input-group-prepend">
            </div>
            <input type="text" class="form-control" id="username" placeholder="Nickname" required>
            <div class="invalid-feedback" style="width: 100%;">
              닉네임을 작성해주세요.
            </div>
          </div>
        </div>

        <div class="mb-3">
          <label for="email">Email <span class="text-muted"></span></label>
          <input type="email" class="form-control" id="email" placeholder="you@example.com">
          <div class="invalid-feedback">
            이메일을 작성해주세요.
          </div>
        </div>

        
        <button class="btn btn-primary btn-lg btn-block" type="submit" @click="submit">수정</button>
      </form>
    </div>
  </div>

  <footer class="my-5 pt-5 text-muted text-center text-small">
    <p class="mb-1">&copy; 2017-2020 이거모임!?</p>
  </footer>
</div>
<!-- <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
      <script>window.jQuery || document.write('<script src="../assets/js/vendor/jquery.slim.min.js"><\/script>')</script><script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
        <script src="form-validation.js"></script> -->
</template>



<script>
    import '../../assets/css/user.scss'
    import constants from '../../lib/constants'
    
    export default {
        components: {
        },
        created(){
        },
        methods: {         
            submit(){
                // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation')

    // Loop over them and prevent submission
    Array.prototype.filter.call(forms, function (form) {
      form.addEventListener('submit', function (event) {
        if (form.checkValidity() === false) {
          event.preventDefault()
          event.stopPropagation()
        }
        form.classList.add('was-validated')
      }, false)
    })
            }
        },
        computed:{
            
        },
        watch: {
        },
        data: () => {
            return {
                passwordConfirm: '',
                isTerm: false,
                passwordType:"password",
                passwordConfirmType:"password",
                constants
            }
        }

    }

</script>
<style scoped>

.container {
  max-width: 960px;
}

.lh-condensed { line-height: 1.25; }

</style>



