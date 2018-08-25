var tasks;

$(
    loadTasks()
);

function loadTasks() {
    $.ajax('./tasks', {
        type: 'GET',
        complete: function(data) {
            tasks = JSON.parse(data.responseText);
            fillUsers();
        }
    })
}

function fillUsers() {
    var result = "<caption>Tasks</caption>" +
        "<thead>" +
        "<tr>" +
        "  <th>Id</th>" +
        "  <th>Description</th>" +
        "  <th>Date</th>" +
        "  <th>" +
        "    <div class='form-check'>" +
        "      <input class='form-check-input' checked='checked' type='checkbox' value='true' onclick='filterTable()' id='checkBoxTrue'>" +
        "      <label class='form-check-label' for='checkBoxTrue'>Show done</label>" +
        "    </div>" +
        "    <div class='form-check'>" +
        "      <input class='form-check-input' checked='checked' type='checkbox' value='true' onclick='filterTable()' id='checkBoxFalse'>" +
        "      <label class='form-check-label' for='checkBoxFalse'>Show don't done</label>" +
        "    </div>" +
        "  </th>" +
        "</tr>" +
        "</thead>" +
        "<tbody>";
    for (var i=0; i!==tasks.length; ++i) {
        result += "<tr>" +
            "        <td>" + tasks[i].id + "</td>" +
            "        <td>" + tasks[i].description + "</td>" +
            "        <td>" + new Date(tasks[i].created).toString()+ "</td>" +
            "        <td>" +
            "<div class='form-check'>" +
            "  <input class='form-check-input' ";
        if (tasks[i].done === true) {
            result += " checked='checked' ";
        }
        result += "type='checkbox' value='"+ i +"' onclick='update(this.value)' name='done' id='done'>" +
            "  <label class='form-check-label' for='done'>" +
            "    Done." +
            "  </label>" +
            "</div>" +
            "</td>" +
            "</tr>" +
            "</tbody>";
    }
    document.getElementById("table").innerHTML = result;
}

function filterTable() {
    var filterTrue = document.getElementById("checkBoxTrue").checked;
    var filterFalse = document.getElementById("checkBoxFalse").checked;
    var rows = document.getElementsByTagName("tr");
    for (var i = 1; i < rows.length; ++i) {
        var row = rows.item(i);
        var selectChecked = row.querySelector('[name="done"]').checked;
        if (filterTrue && selectChecked || filterFalse&& !selectChecked) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    }
}

function update(i) {
    tasks[i].done = !tasks[i].done;
    $.ajax('./update', {
        type: 'POST',
        data: JSON.stringify(tasks[i]),
        complete: function(data) {
            filterTable();
        }
    })
}
function addTask() {
    $.ajax('./tasks', {
        type: 'POST',
        data: JSON.stringify({
            'created': new Date(),
            'description': $('#description').val()
        }),
        complete: function() {
            loadTasks();
            filterTable();
        }
    })
}