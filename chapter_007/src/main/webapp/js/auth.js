function validate() {
    const validationMessages = {
        login: {
            required: 'Enter login. '
        },
        password: {
            required: 'Enter password. '
        }
    };
    var done = true;
    var message =
        checkAndGetMessage($('#login'), validationMessages.login.required) +
        checkAndGetMessage($('#password'), validationMessages.password.required);
    if (message) {
        done = false;
        alert(message);
    }
    return done;
}

function checkAndGetMessage(el, message) {
    return !el.val() ? message : '';
}