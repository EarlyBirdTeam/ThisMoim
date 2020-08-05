import Vue from 'vue';
import axios from 'axios'
import Vuex from 'vuex';
import cookies from 'vue-cookie';
import constants from './lib/constants.js'
import router from './router/index.js'
import http from './http-common.js';
import * as Cookies from 'js-cookie'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

export const store = new Vuex.Store({
    plugins: [createPersistedState({
        storage: {
          getItem: key => Cookies.get(key),
          setItem: (key, value) => Cookies.set(key, value, { expires: 3, secure: true }),
          removeItem: key => Cookies.remove(key)
        }
      })],
    state:{
        host: 'http://127.0.0.1:3000',
        token: '',
        role:'',
        userData:{
            email:'',
            name:'',
            password:'',
            nickname:'',
        },
        errorcode:'',
        accessToken:'',
        modal:false,
        
        joining:{
            canIUseIt:"",
        }
    },
    actions:{
        [constants.METHODS.DELETE_USER] : (store, payload) =>{
            alert(email.value + " and " + password.value);
            http.put('/account/delete',
                    {
                        email: payload.email,
                        password: payload.password,
                        nickname: payload.nickName,
                        name: payload.realName
                    }
                )
            .then(() => {
                console.log("delete req success");
                cookies.delete('Logged');
                store.commit(constants.METHODS.DELETE_USER);
                alert("탈퇴되었습니다. 메인 화면으로 돌아갑니다.");
                router.push('/');
                })
            .catch(exp => {
                router.push('/error');
                store.commit(constants.METHODS.ERROR, exp)
                console.log(exp);
            });

        },
        [constants.METHODS.UPDATE_USER] : (store, payload) =>{
            http.put('http://localhost:8080/account',
                    {
                        email: payload.email,
                        password: payload.password,
                        nickname: payload.nickName,
                        name: payload.realName
                    }
                )
                .then(() => {
                    console.log("update req success");
                    alert("수정되었습니다.");
            })
                .catch(exp => {
                    router.push('/error');
                    console.log("error is");
                    console.log(exp);
                    store.commit(constants.METHODS.ERROR, exp)
                });
        },
        /*
            로그인
        */
        [constants.METHODS.LOGIN_USER] : (_store, payload) =>{
            const url = "api/auth/login";
            const data = {
                "email": payload.email,
                "password": payload.password
            }   
                http
                .post(url, data)
                .then(res => {
                    console.log(res);
                    if (res.status == 200) {
                        cookies.set('AccessToken', res.data.accessToken);
                        cookies.set('AcesssData', res.data.userData);
                        store.commit(constants.METHODS.LOGIN_USER, [data, res.data.accessToken]);
                        store.dispatch(constants.METHODS.GET_USER, data.email);
                        console.log(store.state);
                        return true;
                    }
                })
                .catch(err => {
                    console.log(err.message);
                     alert("로그인 정보가 잘못되었습니다.");
                    return false;
                });
                
                return false;
        },
        [constants.METHODS.LOGOUT_USER] : (store) =>{
            store.commit(constants.METHODS.LOGOUT_USER);
               
        },
        [constants.METHODS.CREATE_USER] : (_store, payload) =>{
            store.commit(constants.METHODS.EMAILCHECK, "reset");
            const url = 'api/auth/register';
            const data = {
                "email": payload.email.value,
                "password": payload.password.value,
                "registerAsAdmin": false,
                "username": payload.realName.value,
                    "nickname": payload.nickName.value,
            };
            console.log(data);
           
            http.post(url, data)
            .then(() => console.log("create req success"))
            .catch(exp => {
                router.push('/error');
                store.commit(constants.METHODS.ERROR, exp)
                console.log(exp);
            });
        },
        [constants.METHODS.GET_USER] : (store, payload) =>{
            console.log("data : " + payload);

            const data = payload;
            const url = `/api/user/userInfo?email=${data}`;
            http.get(url, {
                headers: {
                    Authorization: 'Bearer ' + store.getters.accessToken
                }
            })
                .then(res => {
                    const dataWhatINeed = res.data  ;
                    console.log("In store, dataWhatINeed is : ", dataWhatINeed);
                    store.commit(constants.METHODS.GET_USER, {
                        dataWhatINeed
                    });
                })
                .catch(exp => {
                    router.push('/error');
                    store.commit(constants.METHODS.ERROR, exp)
                    console.log(exp);
                });
        },
        [constants.METHODS.EMAILCHECK] : (store, payload) =>{
            const checkEmail = payload;
            if(checkEmail==""){         
                store.commit(constants.METHODS.EMAILCHECK, "nothing");
                return;
            }
            var exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
            if(exptext.test(checkEmail)==false){
                //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우            
                store.commit(constants.METHODS.EMAILCHECK, "invaild");
                return;
            }



            const url = `/api/auth/checkEmailInUse?email=${checkEmail}`;
            http.get(url)
                .then(res => {
                    console.log(res.data.data);
                    store.commit(constants.METHODS.EMAILCHECK, res.data.data);
                })
                .catch(exp => {
                    router.push('/error');
                    store.commit(constants.METHODS.ERROR, exp)
                    console.log(exp);
                    store.commit(constants.METHODS.EMAILCHECK, 0);
                })
        },

    },
    mutations:{
        [constants.METHODS.LOGIN_USER] : (state, payload) =>{
            // state.password = payload.password;
            console.log("In Store, payload is : ", payload);
            state.userData.email = payload[0].email  ;
            state.accessToken = payload[1];
            state.modal = !state.modal;
        },
        [constants.METHODS.LOGOUT_USER] : (state) =>{
            state.userData.email = '';
            state.userData.password = '';
            state.accessToken = '';
            
            cookies.delete('AccessToken');
            cookies.delete('AccessData');
        },
        [constants.METHODS.GET_USER] : (state, payload) =>{
            console.log(payload.dataWhatINeed);
            state.userData.email = payload.dataWhatINeed.email;
            state.userData.password = payload.dataWhatINeed.password;
            state.userData.nickname = payload.dataWhatINeed.nickname;
            state.userData.name = payload.dataWhatINeed.username;
        },
        [constants.METHODS.DELETE_USER] : (state) =>{
            state.userData.email = "";
            state.userData.password = "";
            state.userData.nickname = "";
            state.userData.name = "";
        },
        [constants.METHODS.ERROR] : (state, exp) =>{
            state.errorcode = exp;
        },
        [constants.METHODS.EMAILCHECK] : (state, result) =>{
            console.log("In store, result is : ", result);
            switch(result){
                case "true":
                    state.joining.canIUseIt = "사용할 수 없는 이메일입니다.";
                    break;
                case "false":
                    state.joining.canIUseIt = "사용할 수 있는 이메일입니다.";
                    break;
                case "invaild":
                    state.joining.canIUseIt = "이메일형식이 올바르지 않습니다.";
                    break;
                default:
                    state.joining.canIUseIt = "";
                    break;
            }
            console.log("In store, canIUseIt is : ", state.joining.canIUseIt);
        }
    },
    getters:{
        userData: function(state){
            return state.userData;
        },
        accessToken: function(state){
            return state.accessToken;
        },
        canIUseIt: function(state){
            return state.joining.canIUseIt;
        }
    }
});