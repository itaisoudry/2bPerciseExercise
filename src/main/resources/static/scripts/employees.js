const instance = axios.create({
    timeout: 1000,
    headers: {'Content-Type': 'application/json'}
});

getEmployees();

function getEmployees() {
    instance.get('/employee')
        .then(function (response) {
            populateTable(response.data);
        })
        .catch(function (error) {
            alert("Error: Cannot get list of employees");
            console.log(error);
        });
}

function populateTable(employees) {
    const tbody = document.getElementById('employeesTableTbody');
    const body = [];
    const dictionary = {};

    employees.forEach(employee => {

        dictionary[employee.id] = employee;

        let row = '<tr>';
        row += `<td class="col-xs-2">${employee.id}</td>`;
        row += `<td class="col-xs-8">${employee.firstName}</td>`;
        row += `<td class="col-xs-8">${employee.lastName}</td>`;
        row += `<td class="col-xs-4 col-position">${employee.position.toLowerCase()}</td>`;
        row += `<td class="col-xs-4">
                <button class="btn btn-info" onclick="goToProfile(${employee.id})">View</button>
            </td>`;

        row += '</tr>';
        body.push(row);
    });

    tbody.innerHTML = body.join('');
    // save employees dictionary in localStorage
    localStorage.employeesData = JSON.stringify(dictionary);
}

function goToProfile(id) {
    localStorage.currentEmployeeId = id;
    document.location.href = "./profile.html";
}
