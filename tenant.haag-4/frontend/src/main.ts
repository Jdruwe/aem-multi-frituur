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
    ['derp']: () =>
        Promise.reject('Derp'),
    ['bar']: () =>
        import('./bar'),
    ['foo']: () =>
        import('./foo'),
};

const bootstrap = new VueBootstrap(modulePromises);

