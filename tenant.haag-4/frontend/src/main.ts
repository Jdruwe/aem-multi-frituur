import {createApp} from 'vue'
import App from './App.vue'
import sayHello from '@jeroendruwe/ui-common';

console.log('>>> main called!');

createApp(App).mount('#app');

sayHello();
