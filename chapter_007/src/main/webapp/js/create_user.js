function validate() {
    const validationMessages = {
        login: {
            required: 'Enter login. '
        }, name: {
            required: 'Enter name. '
        }, email: {
            required: 'Enter email. '
        }, password: {
            required: 'Enter password. '
        }, country: {
            required: 'Choose country. '
        }
    };
    var done = true;
    var message =
        checkAndGetMessage($('#login'), validationMessages.login.required) +
        checkAndGetMessage($('#name'), validationMessages.name.required) +
        checkAndGetMessage($('#email'), validationMessages.email.required) +
        checkAndGetMessage($('#password'), validationMessages.password.required) +
        checkAndGetMessage($('#selectCountry'), validationMessages.country.required);
    if (message) {
        done = false;
        alert(message);
    }
    return done;
}

function checkAndGetMessage(el, message) {
    return !el.val() ? message : '';
}
function loadCities() {
    $.ajax('./address', {
        type: 'POST',
        dataType: 'json',
        data: {"country":$('#selectCountry').val()},
        complete: function(data) {
            var cities = JSON.parse(data.responseText);
            var result = "";
            for (i = 0; i !== cities.length; ++i) {
                result += "<option value="+cities[i]+" >"+cities[i]+"</option>";
            }
            var citiesDiv = document.getElementById("selectCity");
            citiesDiv.innerHTML = result;
        }
    })
}

$(
    $.ajax('./address', {
        type: 'GET',
        complete: function(data) {
            var countries = JSON.parse(data.responseText);
            var result = "<option value=\"\">Choose country:</option>";
            for (var i = 0; i !== countries.length; ++i) {
                result += "<option value=\""+countries[i]+"\">"+countries[i]+"</option>";
            }
            var countriesDiv = document.getElementById("selectCountry");
            countriesDiv.innerHTML = result;
        }
    })
);