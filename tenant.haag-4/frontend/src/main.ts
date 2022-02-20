// import {createApp} from 'vue'
// import App from './App.vue'
//
// console.log('>>> main called!');
//
// createApp(App).mount('#app');
//
// init({});

import {ModulePromises, VueBootstrap} from '@jeroendruwe/ui-common';

const modulePromises: ModulePromises = {
    ['foo']: () =>
        import('./foo'),
    ['bar']: () =>
        import('./bar')
};

const bootstrap = new VueBootstrap(modulePromises);

