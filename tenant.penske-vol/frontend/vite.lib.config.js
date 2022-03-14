const path = require('path');

const buildManifest = (name) => {
    return path.join(__dirname, 'dist', name, 'manifest.json');
};

const buildResourcesDir = (name) => {
    return path.join(
        __dirname,
        'dist',
        name,
        'etc.clientlibs',
        'penske-vol',
        'clientlibs',
        name,
        'resources'
    );
};

const buildClientlibDir = (name) => {
    return path.join(
        __dirname,
        '..',
        'apps',
        'src',
        'main',
        'content',
        'jcr_root',
        'apps',
        'penske-vol',
        'clientlibs',
        name
    );
};

const createLib = (name, categories) => {
    return {
        manifest: buildManifest(name),
        resourcesDir: buildResourcesDir(name),
        clientlibDir: buildClientlibDir(name),
        categories: [...categories],
        properties: {
            moduleIdentifier: 'vite',
        },
    };
};

module.exports = {
    libs: [
        createLib('clientlib-base', ['aem-multi-frituur.tenant.penske-vol.base']),
    ],
};
