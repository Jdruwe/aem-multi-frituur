const setupNamespace = () => {
    window.AemMultiFrituur = window.AemMultiFrituur || {};
}

const listenToUpdate = () => {
    window.AemMultiFrituur.AFTER_EDIT = () => {
        requestRender();
    }
}

const requestRender = () => {
    window.postMessage(
        {
            type: 'render-request',
        },
        '*'
    );
}

setupNamespace();
listenToUpdate();
