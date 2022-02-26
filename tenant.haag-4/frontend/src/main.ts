import {ModulePromises, VueBootstrap} from '@jeroendruwe/ui-common';

const modulePromises: ModulePromises = {
    ['derp']: () =>
        Promise.reject('Derp'),
    ['bar']: () =>
        import('./bar'),
    ['foo']: () =>
        import('./foo'),
};

new VueBootstrap(modulePromises)
    .init();

