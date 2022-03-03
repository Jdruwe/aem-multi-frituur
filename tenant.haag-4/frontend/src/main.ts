import {ModulePromises, VueBootstrap} from '@jeroendruwe/ui-common';

const modulePromises: ModulePromises = {
    ['derp']: () =>
        Promise.reject('Derp'),
    ['menu']: () =>
        import('./modules/menu')
};

new VueBootstrap(modulePromises)
    .init();
