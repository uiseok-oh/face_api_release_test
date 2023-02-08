import axios from 'axios';
import port from './port';

export default {
    namespaced: true,
    state: {
        user: {},
        modifyUser: {}
    },
    getters: {
        // dataComputed : function(state){
        //     return state.user.money+1000
        // }
    },
    mutations: {
        getMyPageUser(state, payload) {
            state.user = payload;
        },
        getModifyUser(state, payload) {
            state.modifyUser = payload;
        }
    },
    actions: {
        async getMyPageUser({commit}) {
            await axios
                .get(port + 'member/mypage', {
                    withCredentials: true
                })
                .then(res => {
                    commit('getMyPageUser', res.data);
                });
        },

        async getModifyUser({commit}) {
            await axios
                .get(port + 'member/mypage/modify', {
                    withCredentials: true
                })
                .then(res => {
                    commit('getModifyUser', res.data);
                });
        },

        updateUser(_, {formData}) {
            axios.patch(port + 'member/mypage/modify', formData, {
                withCredentials: true,
                headers: {
                    'Content-Type': ' multipart/form-data'
                }
            });
        }
    }
};
