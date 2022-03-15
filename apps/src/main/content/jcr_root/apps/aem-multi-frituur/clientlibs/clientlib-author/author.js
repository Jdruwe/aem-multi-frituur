const setupNamespace = () => {
    window.AemMultiFrituur = window.AemMultiFrituur || {};
}

const listenToUpdate = () => {
    window.AemMultiFrituur.requestRender = (parent) => {
        if (parent) {
            window.Granite.author.responsive.EditableActions.REFRESH.execute(parent).then(() => {
                sendRenderRequest();
            });
        } else {
            sendRenderRequest();
        }
    }
}

const sendRenderRequest = () => {
    window.postMessage(
        {
            type: 'render-request',
        },
        '*'
    );
}

setupNamespace();
listenToUpdate();
