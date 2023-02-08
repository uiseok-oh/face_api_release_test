import Vue from 'vue';
import Vuex from 'vuex';

import moduleAdmin from './moduleAdmin';
import moduleCommunity from './moduleCommunity';
import moduleLogin from './moduleLogin';
import moduleMain from './moduleMain';
import moduleMyPage from './moduleMyPage';
import moduleSelfTest from './moduleSelfTest';
import moduleStudy from './moduleStudy';
import moduleTimer from './moduleTimer';
import moduleRanking from './moduleRanking';
import moduleWebRTC from './moduleWebRTC';

Vue.use(Vuex);

export default new Vuex.Store({
    // state: {
    // },
    // getters: {
    // },
    // mutations: {
    // },
    // actions: {
    // },
    modules: {
        moduleAdmin: moduleAdmin,

        moduleCommunity: moduleCommunity,
        moduleLogin: moduleLogin,
        moduleMain: moduleMain,
        moduleMyPage: moduleMyPage,
        moduleSelfTest: moduleSelfTest,
        moduleStudy: moduleStudy,
        moduleTimer: moduleTimer,
        moduleRanking: moduleRanking,
        moduleWebRTC: moduleWebRTC
    }
});
