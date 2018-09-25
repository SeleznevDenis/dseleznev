$(
    fillAuth()
);
$(
    fillForms()
);
$(
    fillAdverts()
);


function fillForms() {
    fillAdvertForm();
    fillModels();
}



const htmlGen = {
    auth: "<form class='form-inline'>" +
        "" +
        "<div class='form-group'>" +
        "  <label for='authLogin'> Login : </label>" +
        "  <input type='text' class='form-control' id='authLogin'>" +
        "</div>" +
        "<div class='form-group'>" +
        "  <label for='authPassword'>Password : </label>" +
        "  <input type='password' class='form-control' id='authPassword'>" +
        "</div>  " +
        "  <input type='hidden' value='signIn' id='authAction'>&#160" +
        "  <button type='button' class='btn btn-info' onclick='return sendAuthJson()'>Sign in</button>&#160" +
        "  <button type='button' class='btn btn-info' onclick='createUser()'>Create User</button>" +
        "</form>"
};

function fillAdverts() {
    $.ajax('./advert', {
        type: 'GET',
        complete: function(data) {
            var adverts = JSON.parse(data.responseText);
            var result = "";
            for(var i=0; i<adverts.length; ++i) {
                var photo = "";
                var advert = adverts[i];
                if (advert.photo) {
                    photo = "" +
                        "<div class='col-3'>" +
                        "  <img src='./upload/" + advert.photo + "' class='img-fluid' alt='Responsive image'>" +
                        "</div>";
                }
                result +=
                    "<div class='card'>" +
                    "  <div class='card-header'>" +
                    "    " + advert.model.brand.description + " " + advert.model.description +
                    "  </div>" +
                    "  <div class='card-body'>" +
                    "    <div class='row'>" +
                           photo +
                    "    " +
                    "      <div class='col'>" +
                    "        <p>Engine value: " + advert.engineValue + "</p>" +
                    "        <p>Cost: "+ advert.cost + "</p>" +
                    "        <p>Release date: " + advert.releaseDate + "</p>" +
                    "        <p>Mileage: " + advert.mileage + "</p>" +
                    "        <p>Body: " + advert.body.description + "</p>" +
                    "      </div>" +
                    "      <div class='col'>" +
                    "        <p>Drive unit: " + advert.driveUnit.description + "</p>" +
                    "        <p>Engine: " + advert.engine.description + "</p>" +
                    "        <p>Transmission: " + advert.transmission.description + "</p>" +
                    "        <p>Wheel: " + advert.wheel.description + "</p>" +
                    "      </div>" +
                    "    </div>" +
                    "    <div class='row'>" +
                    "        <p>" + advert.description + "</p>" +
                    "    </div>" +
                    "  </div>" +
                    "</div>";

            }
            document.getElementById("advertsShow").innerHTML = result;

        }
    })

}

function createUser() {
    alert('User create');
}

function divShow(id) {
    var element = document.getElementById(id);
    if (element.style.display === 'none') {
        element.style.display = '';
    } else {
        element.style.display = 'none';
    }
}



function fillAuth() {
    $.ajax('./auth', {
        type: 'GET',
        //response: 'text',
        complete: function(data) {
            var authDTO = JSON.parse(data.responseText);
            var result = "";
            if (!authDTO.login) {
                var message = authDTO.message;
                if(!message) {
                    message = "";
                }
                result += message + " " + htmlGen.auth;
            } else {
                result += fillSignOutHtml(authDTO.login)
            }
            document.getElementById("auth").innerHTML = result;
        }
    })
}

function fillSignOutHtml(login) {
    return "<label for='exit'>" +
        "You are authorized as: " + login + " &#160" +
        "</label>" +
        "<input type='hidden' value='signOut' id='authAction'>" +
        "<button type='button' class='btn btn-info' onclick='return sendAuthJson()'>Sign out</button>&#160" +
        "<button type='button' class='btn btn-info' onclick=\"divShow('adFormCard')\">Create advert</button>";
}

function sendAuthJson() {
    $.ajax('./auth', {
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify({
            'login': $('#authLogin').val(),
            'password': $('#authPassword').val(),
            'message': $('#authAction').val()
        }),
        complete: function (data) {
            var dto = JSON.parse(data.responseText);
            alert(dto.login);
            alert(dto.message);
            var result = "";
            var message = "";
            if (!dto.login) {
                if (dto.message) {
                    message = dto.message;
                }
                result += message + " " + htmlGen.auth;
            } else {
                result += fillSignOutHtml(dto.login);
            }
            document.getElementById("auth").innerHTML = result;
        }
    });
    return false;
}

function adAdvert() {
    var data = new FormData();
    jQuery.each(jQuery('#photo')[0].files, function(i, file) {
        data.append('file-'+i, file);
    });
    $.ajax('./file', {
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        method: 'POST',
        type: 'POST',
        complete: function(data){
            adAdvertDescription(data.responseText);
        }
    })
}

function adAdvertDescription(photoUrl) {
    $.ajax('./advert', {
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify({
            'action':'create',
            'advert': {
                description: $('#description').val(),
                engineValue: $('#engineValue').val(),
                cost: $('#cost').val(),
                releaseDate: $('#date').val(),
                mileage: $('#mileage').val(),
                body: {id : $('#carBody').val()},
                //color: $('#color').val(),
                driveUnit: {id : $('#driveUnit').val()},
                engine: {id :$('#MEngine').val()},
                transmission: {id : $('#MTransmission').val()},
                wheel: {id : $('#wheel').val()},
                model: {id : $('#model').val()},
                photo: photoUrl,
            }
        }),
        complete: function(data) {
            alert('Advert was add');
            divShow("adForm");
        }
    })
}

function fillAdvertForm() {
    $.ajax('./fillAdvert', {
        type: 'GET',
        dataType: 'json',
        complete: function(data) {
            var formsData = JSON.parse(data.responseText);
            //alert(formsData.brands.length);
            fillSelect("autoBrand", formsData.brands);
            fillSelect("carBody", formsData.bodies);
            fillSelect("driveUnit", formsData.driveUnits);
            fillSelect("MEngine", formsData.engines);
            fillSelect("MTransmission", formsData.transmissions);
            fillSelect("wheel", formsData.wheels);
        }
    })
}

function fillSelect(selectId, data) {
    //alert("inFillSelect");
    //alert(selectId);
    var result = "";
    for (var i = 0; i < data.length; ++i) {
        result += "" +
            "<option value='" + data[i].id + "'>" + data[i].description + "</option>";
    }
    document.getElementById(selectId).innerHTML = result;
}

function fillModels() {
    $.ajax('/fillAdvert', {
        type: 'POST',
        data: {"brandId" : $('#autoBrand').val()}, //$('#autoBrand').val()
        complete: function(data) {
            var models = JSON.parse(data.responseText);
            fillSelect("model", models);
        }
    })
}



