const initDevTrigger = () => {
    const element = document.getElementsByClassName('editor-GlobalBar-devTrigger')[0];
    if (element) {
        updateDevStatus(element);
        listenToDevStatusToggle(element);
    }
}

const updateDevStatus = (element) => {
    fetch('/bin/devServer/status')
        .then(response => response.json())
        .then(data => data.enabled && element.classList.add('is-selected'));
}

const listenToDevStatusToggle = (element) => {
    element.addEventListener("click", async () => {
        toggleDevStatus();
    });
}

const toggleDevStatus = async () => {
    const token = await getToken();
    fetch('/bin/devServer/toggle', {
        method: 'POST',
        headers: {
            'CSRF-Token': token
        },
    }).then(() => location.reload());
}

const getToken = async () => {
    const response = await fetch('/libs/granite/csrf/token.json');
    const data = await response.json();
    return data.token;
}

initDevTrigger();
