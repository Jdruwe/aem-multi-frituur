import {ModulePromises, VueBootstrap} from '@jeroendruwe/ui-common';

const modulePromises: ModulePromises = {
    ['non-existing']: () =>
        Promise.reject('This is a non existing module'),
    ['menu']: () =>
        import('./modules/menu'),
    ['heading']: () =>
        import('./modules/heading'),
    ['opening-hours']: () =>
        import('./modules/openingHours')
};

new VueBootstrap(modulePromises)
    .init();

import './styling/index.css'
