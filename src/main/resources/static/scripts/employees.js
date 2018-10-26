var config = {
    headers: { 'Content-Type': 'application/json' }
};

getEmployees();

function getEmployees() {
    axios.get('/employee', config)
        .then(function (response) {
            populateTable(response.data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

function populateTable(data) {
    var table = document.getElementById('employeesTable').getElementsByTagName("tbody")[0];
    var body = [];
    var dictionary = {};
    for (var employee of data) {

        dictionary[employee.id] = employee;

        var row = '<tr>';
        row += `<td class="col-xs-2">${employee.id}</td>`;
        row += `<td class="col-xs-8">${employee.firstName}</td>`;
        row += `<td class="col-xs-8">${employee.lastName}</td>`;
        row += `<td class="col-xs-4 col-position">${employee.position.toLowerCase()}</td>`;
        row += `<td class="col-xs-4">
                <button class="btn btn-info" onclick="goToProfile(${employee.id})">View</button>
            </td>`;

        row += '</tr>';
        body.push(row);
    }

    table.innerHTML = body;
    // save employees dictionary in localStorage
    localStorage.employeesData = JSON.stringify(dictionary);
}

function goToProfile(id) {
    localStorage.currentEmployeeId=id;
    document.location.href = "./profile.html";
}
