const validationMessages = {
    login: {
        required: 'Enter login. '
    }, name: {
        required: 'Enter name. '
    }, address: {
        required: 'Enter email. '
    }, password: {
        required: 'Enter password. '
    }, role: {
        required: 'Choose country. '
    }, type: {
        required: 'Chose music type'
    }
};
var up = [];

const htmlGeneration = {
    auth: {
        signInForm:
        "<form class='form-inline'>" +
        "Sign in." +
        "<div class='form-group'>" +
        "  <label for='authLogin'> Login : </label>" +
        "  <input type='text' class='form-control' id='authLogin'>" +
        "</div>" +
        "<div class='form-group'>" +
        "  <label for='authPassword'>Password : </label>" +
        "  <input type='password' class='form-control' id='authPassword'>" +
        "</div>  " +
        "  <input type='hidden' value='signIn' id='authAction'>" +
        "  <button type='button' class='btn btn-default' onclick='return sendAuthJson()'>Sign in</button>" +
        "</form>"
    },
    users:
    "<caption>All users</caption>" +
    "        <thead>" +
    "        <tr>" +
    "            <th>id</th>" +
    "            <th>Name</th>" +
    "            <th>Login</th>" +
    "            <th>Role</th>" +
    "            <th>Address</th>" +
    "            <th>Music type</th>" +
    "            <th colspan=\"2\">Action</th>" +
    "        </tr>" +
    "        </thead>" +
    "        <tbody>",
    createUserHtml:
    "<h2>Create user : </h2>" +
    "<form>" +
    "  <div class='form-group'>" +
    "    <label for='createLogin'>Login : </label>" +
    "    <input type='text' class='form-control' name='login' id='createLogin'>" +
    "  </div>" +
    "  <div class='form-group'>" +
    "    <label for='createName'>Name : </label>" +
    "    <input type='text' class='form-control' name='name' id='createName'>" +
    "  </div>" +
    "  <div class='form-group'>" +
    "    <label for='createPassword'>Password : </label>" +
    "    <input type='password' class='form-control' name='password' id='createPassword'>" +
    "  </div>" +
    "  <div class='form-group'>" +
    "    <label for='createSelectRole'>Role : </label>" +
    "    <select class='form-control' name='role' id='createSelectRole'></select>" +
    "  </div>" +
    "  <div class='form-group'>" +
    "    <label for='createSelectType'>Music type : </label>" +
    "    <select class='custom-select' name='musicType' id='createSelectType' multiple='multiple'></select>" +
    "  </div>" +
    "  <div class='form-group'>" +
    "    <label for='createAddress'>Address : </label>" +
    "    <input type='text' class='form-control' name='address' id='createAddress'>" +
    "  </div>" +
    "  <button type='button' class='btn btn-default' onclick='createUser()'>Create</button>" +
    "</form>"
};

function createUser() {
    if (createCheck()) {
        $.ajax('./create', {
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify({
                'name': $('#createName').val(),
                'password': $('#createPassword').val(),
                'login': $('#createLogin').val(),
                'role': {role:$('#createSelectRole').val()},
                'address': $('#createAddress').val(),
                'musicType': computeTypes($('#createSelectType'))
            }),
            complete: function(data) {
                var loginRole = JSON.parse(data.responseText);
                fillUsers(loginRole.login, loginRole.role);
            }
        })
    }
}

function createCheck() {
    var done = true;
    var message =
        checkAndGetMessage($('#createName').val(), validationMessages.name.required) +
        checkAndGetMessage( $('#createPassword').val(), validationMessages.password.required) +
        checkAndGetMessage($('#createSelectRole').val(), validationMessages.role.required) +
        checkAndGetMessage($('#createLogin').val(), validationMessages.login.required) +
        checkAndGetMessage($('#createAddress').val(), validationMessages.address.required) +
        checkAndGetMessage($('#createSelectType').val()[0], validationMessages.type.required);
    if (message) {
        done = false;
        alert(message);
    }
    return done;
}

function checkAndGetMessage(el, message) {
    return !el ? message : '';
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
            fillAuth();
        }
    });
    return false;
}

function fillSignOutHtml(login, role) {
    return "<label for='exit'>" +
        "You are authorized as: " + login + ". Your role: " + role + " " +
        "</label>" +
        "<input type='hidden' value='signOut' id='authAction'>" +
        "<button type='button' class='btn btn-default' onclick='return sendAuthJson()'>Sign out</button>";
}

function fillAuth() {
    $.ajax('./auth', {
        type: 'GET',
        complete: function(data) {
            var auth = JSON.parse(data.responseText);
            var result = "";
            if (!auth.login || !auth.role) {
                var message = auth.message;
                if (!auth.message) {
                    message = "";
                }
                result += message + " " + htmlGeneration.auth.signInForm;
            } else {
                result += fillSignOutHtml(auth.login, auth.role);
                fillUsers(auth.login, auth.role);
                if (auth.role === "admin" || auth.role === "moderator" || auth.role === "user") {
                    fillUserCreate();
                }
            }
            document.getElementById("authDiv").innerHTML = result;
        }
    })
}

function fillUserCreate() {
    document.getElementById("divAuto").innerHTML = htmlGeneration.createUserHtml;
    fillRoles("createSelectRole");
    fillTypes("createSelectType");
}

function fillRoles(elId, roleSelected) {
    $.ajax('./roles_types', {
        type: 'GET',
        data: {action : "role"},
        complete: function(data) {
            var roles = JSON.parse(data.responseText);
            var result = "";
            for (i = 0; i !== roles.length; ++i) {
                result += "<option";
                if(elId === "updateRole" && roles[i].role === roleSelected) {
                    result += " selected";
                }
                result += " value=\"" + roles[i].role + "\">" + roles[i].role + "</option>";
            }
            document.getElementById(elId).innerHTML = result;
        }
    })
}

function fillTypes(elId, typeSelected) {
    $.ajax('./roles_types', {
        type: 'GET',
        data: {action : "type"},
        complete: function(data) {
            var types = JSON.parse(data.responseText);
            var result = "";
            for (i = 0; i !== types.length; ++i) {
                result += "<option";
                if(elId === "updateType" && ~typeSelected.indexOf(types[i].type)) {
                    result += " selected";
                }
                result += " value=\"" + types[i].type + "\">"+types[i].type+"</option>";
            }
            document.getElementById(elId).innerHTML = result;
        }
    })
}

function fillUsers(userLogin, userRole) {
    $.ajax('./users', {
        type: 'GET',
        complete: function(data) {
            var users = JSON.parse(data.responseText);
            if (!users) {
                alert("nodata");
            } else {
                var result = htmlGeneration.users;
                for (var i=0; i !== users.length; ++i) {
                    var musicType = users[i].musicType;
                    var musicTypeText = "";
                    for (var j=0; j!==musicType.length; ++j) {
                        musicTypeText += musicType[j].type;
                        if (j === musicType.length - 1) {
                            musicTypeText += ".";
                        } else {
                            musicTypeText += ", ";
                        }
                    }
                    var userId = users[i].id;
                    up[userId] = users[i].password;
                    result += "<tr>" +
                        "       <td>" + userId + "</td>" +
                        "       <td id='"+userId+"name'>" + users[i].name + "</td>" +
                        "       <td id='"+userId+"login'>" + users[i].login + "</td>" +
                        "       <td id='"+userId+"role'>" + users[i].role.role + "</td>" +
                        "       <td id='"+userId+"address'>" + users[i].address.address + "</td>" +
                        "       <td id='"+userId+"type'>" + musicTypeText + "</td>" +
                        "       <td id='"+userId+"update'>"+ updateButton(users[i].id, userLogin, users[i].login, userRole, users[i].role.role)+"</td>" +
                        "       <td id='"+userId+"delete'>"+ deletionButton(users[i].id, userLogin, users[i].login, userRole, users[i].role.role) +"</td>" +
                        "      </tr>"
                }
                result += "</tbody>";
                document.getElementById("userTableBody").innerHTML = result;
            }
        }
    })
}

function updateButton(id, userLogin, tableUserLogin, userRole, tableUserRole) {
    var htmlText = "";
    if (userRole === "admin" || (userRole === "moderator" && tableUserRole !== "admin") || userLogin === tableUserLogin) {
        htmlText =
            "<button type='button' value='" + id + "' class='btn btn-default' onclick='updateUser(this.value)'>Update</button>";
    }
    return htmlText;
}

function deletionButton(id, userLogin, tableUserLogin, userRole, tableUserRole) {
    var htmlText = "";
    if (userRole === "admin" || (userRole === "moderator" && tableUserRole !== "admin")) {
        htmlText =
            "<button type='button' value='"+ id +"' class='btn btn-default' onclick='deleteUser(this.value)'>Delete</button>";
    }
    return htmlText;
}

function updateUser(id) {
    var login = document.getElementById(id+"login");
    login.innerHTML =
        "<div class='form-group'>" +
        "  <label for='updateLogin'>Login : </label>" +
        "  <input type='text' class='form-control' value='" + login.textContent + "' id='updateLogin'>" +
        "</div>";
    var name = document.getElementById(id+"name");
    name.innerHTML =
        "<div class='form-group'>" +
        "  <label for='updateName'>Name : </label>" +
        "  <input type='text' class='form-control' value='"+ name.textContent + "' id='updateName'>" +
        "</div>";
    var role = document.getElementById(id+"role");
    role.innerHTML =
        "<div class='form-group'>" +
        "  <label for='updateRole'>Role : </label>" +
        "  <select class='form-control' id='updateRole'></select>" +
        "</div>";
    fillRoles("updateRole", role.textContent);
    var address = document.getElementById(id+"address");
    address.innerHTML =
        "<div class='form-group'>" +
        "  <label for='updateAddress'>Address : </label>" +
        "  <input type='text' class='form-control' value='"+ address.textContent +"' id='updateAddress'>" +
        "</div>";
    var musicType = document.getElementById(id+"type");
    musicType.innerHTML =
        "<div class='form-group'>" +
        "  <label for='updateType'>Music type : </label>" +
        "  <select class='custom-select' id='updateType' multiple='multiple'>" +
        "  </select>" +
        "</div>";
    fillTypes("updateType", musicType.textContent);
    var updateButton = document.getElementById(id+"update");
    updateButton.innerHTML =
        "<button type='button' value='"+id+"' class='btn btn-default' onclick='sendUpdate(this.value)'>Save</button>";
    var deleteButton = document.getElementById(id+"delete");
    deleteButton.innerHTML =
        "<div class='form-group'>" +
        "  <label for='updatePassword'>Password : </label>" +
        "  <input type='password' class='form-control' value='"+ up[id].password +"' id='updatePassword'>" +
        "</div>";
}

function sendUpdate(id) {
    if(checkUpdate()) {
        $.ajax('./update', {
            type: 'POST',
            data: JSON.stringify({
                'id': parseInt(id),
                'name': $('#updateName').val(),
                'password': $('#updatePassword').val(),
                'login': $('#updateLogin').val(),
                'role': {role:$('#updateRole').val()},
                'address': $('#updateAddress').val(),
                'musicType': computeTypes($('#updateType'))
            }),
            complete: function(data) {
                var loginRole = JSON.parse(data.responseText);
                fillUsers(loginRole.login, loginRole.role);
            }
        })
    }
}

function checkUpdate() {
    var done = true;
    var message =
        checkAndGetMessage($('#updateName').val(), validationMessages.name.required) +
        checkAndGetMessage( $('#updatePassword').val(), validationMessages.password.required) +
        checkAndGetMessage($('#updateRole').val(), validationMessages.role.required) +
        checkAndGetMessage($('#updateLogin').val(), validationMessages.login.required) +
        checkAndGetMessage($('#updateAddress').val(), validationMessages.address.required) +
        checkAndGetMessage($('#updateType').val()[0], validationMessages.type.required);
    if (message) {
        done = false;
        alert(message);
    }
    return done;
}

function computeTypes(el) {
    var arr = [];
    for (var i=0; i<el.val().length; ++i) {
        arr.push({type: el.val()[i]})
    }
    return arr;
}

function deleteUser(id) {
    alert(id);
    $.ajax('./delete', {
        type: 'POST',
        data: {id: id},
        complete: function(data) {
            var loginRole = JSON.parse(data.responseText);
            fillUsers(loginRole.login, loginRole.role);
        }
    })
}

$(
    fillAuth()
);
