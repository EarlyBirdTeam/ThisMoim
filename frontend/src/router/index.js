import Vue from 'vue'
import Vuex from 'vuex'
import Router from 'vue-router'
import VueCookie from 'vue-cookie'
import constants from '../lib/constants'

// 유저
import Login from '../page/user/Login.vue'
import Join from '../page/user/Join.vue'
import JoinDone from '../page/user/JoinDone.vue'
import MyPage from '../page/user/MyPage.vue'

// 포스트
import List from '../page/post/List.vue'
import Test from '../page/post/Test_Board.vue'
import Create from '../page/post/Create_Community.vue'
import Base from '../page/post/BaseBoard.vue'
import Chat from '../page/post/Chat.vue'
import Members from '../page/post/BoardMember.vue'
import Schedule from '../page/post/Schedule.vue'

Vue.use(Router) 
Vue.use(Vuex)
Vue.use(VueCookie)
 
export default new Router({
  mode: 'history',
  routes: [   
    // 로그인/가입
    // { 
    //   path: '/user/login',
    //   name: constants.URL_TYPE.USER.LOGIN,
    //   component: Login
    // },
    {
      path: '/user/join',
      name: constants.URL_TYPE.USER.JOIN,
      component: Join
    },
    {
      path:'/user/joinDone',
      name: constants.URL_TYPE.USER.JOINDONE,
      component: JoinDone
    },
    {
      path:'/user/MyPage',
      name: constants.URL_TYPE.USER.MYPAGE,
      component: MyPage
    },
    // 포스트
    { 
      path: '/',
      name: constants.URL_TYPE.POST.MAIN,
      component: List,
    },
    // { 
    //   path: '/board',
    //   name: constants.URL_TYPE.POST.BASE,
    //   component: Base,
    //   // beforeEnter: function (to,from,next){
    //   //   alert("로그인 후 이용가능한 서비스 입니다.")
    //   //   next('/');
    //   // }
    // },
    { 
      path: '/board/test',
      name: constants.URL_TYPE.POST.TEST_BOARD,
      component: Test,
   
    },
    { 
      path: '/board/create',
      name: constants.URL_TYPE.POST.CREATE,
      component: Create,
    },
    { 
      path: '/board/members',
      name: constants.URL_TYPE.POST.MEMBERS,
      component: Members,
    },
    { 
      path: '/board/chat',
      name: constants.URL_TYPE.POST.CHAT,
      component: Chat,
    },
    { 
      path: '/board/schedule',
      name: constants.URL_TYPE.POST.SCHEDULE,
      component: Schedule,
    },

    { 
      path: '/error',
      name: constants.ERROR.FRONT_ERROR,
      component: () => import('../page/etc/error.vue'),
    },


    // 그 외 페이지 (404, ERROR)
    {
      path: '*',
      name: 'e404',
      component: () => import('../page/etc/e404.vue')
    }

    
  ]
})
