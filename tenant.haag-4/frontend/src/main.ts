import './styling/index.css'
import {ModulePromises, VueBootstrap} from '@jeroendruwe/ui-common';

const modulePromises: ModulePromises = {
    ['non-existing']: () =>
        Promise.reject('This is a non existing module'),
    ['menu']: () =>
        import('./modules/menu'),
    ['heading']: () =>
        import('./modules/heading')
};

new VueBootstrap(modulePromises)
    .init();

