const config = {
    headers: {'Content-Type': 'application/json'}
};

const employeeId = localStorage.currentEmployeeId;
const employeesData = JSON.parse(localStorage.employeesData);
let employee = employeesData[employeeId];
let manager = employeesData[employee.managerId];

if (employee.position === 'MANAGER') {
    displayManagerReportsAndSubOrdLabels();
    getManagerSubordinates();
    getManagerReports();
}

getEmployeeTasks();
populateEmployeeData();

function getManagerReports() {
    axios.get(`/report/${employee.id}`, config).then((response) => {
        populateReports(response.data);
    }).catch((error) => {
        console.log(error);
    });
}

function getEmployeeTasks() {
    axios.get(`/task/${employee.id}`, config).then((response) => {
        populateTasks(response.data);
    }).catch((error) => {
        console.log(error);
    });
}

function getManagerSubordinates() {
    if (employee.position === 'MANAGER') {
        axios.get(`/employee/${employee.id}/subordinates`, config).then((response) => {
            populateSubordinates(response.data);
        }).catch((error) => {
            console.log(error);
        })
    } else {
        console.error('Employee is not a manager.');
    }
}


function populateTasks(tasks) {
    if (tasks.length === 0) {
        document.getElementById('tasksLabel').innerText = 'You has no tasks!';
    } else {
        document.getElementById("tasksTable").style.display = '';
        populateTasksTable(tasks);
    }
}

function populateTasksTable(tasks) {
    let table = document.getElementById('tasksTable');
    let tbody = table.getElementsByTagName("tbody")[0];

    let body = [];
    for (let task of tasks) {
        let dueDate = moment(task.dueDate).format("DD-MM-YYYY HH:MM");

        let row = '<tr>';
        row += `<td class="col-xs-8">${task.text}</td>`;
        row += `<td class="col-xs-8">${dueDate}</td>`;

        row += '</tr>';
        body.push(row);
    }

    tbody.innerHTML = body;
}

function populateReports(reports) {
    console.log(reports);
    if (reports.length === 0) {
        document.getElementById('reportsLabel').outerText = 'You have no reports!';
    } else {
        document.getElementById('reportsTable').style.display = '';
        populateReportsTable(reports);
    }
}

function populateReportsTable(reports) {
    let table = document.getElementById('reportsTable');
    let tbody = table.getElementsByTagName("tbody")[0];

    let body = [];
    for (let report of reports) {
        let reportDate = moment(report.reportDate).format("DD-MM-YYYY HH:MM");

        let row = '<tr>';
        row += `<td class="col-xs-8">${report.text}</td>`;
        row += `<td class="col-xs-8">${reportDate}</td>`;

        row += '</tr>';
        body.push(row);
    }

    tbody.innerHTML = body;
}


function populateSubordinates(subordinates) {
    if (subordinates.length === 0) {
        console.log('No sub ordinates');
        document.getElementById('subordinatesLabel').innerText = 'You have no subordinates!';

    } else {
        document.getElementById('subordinatesTable').style.display = '';
        populateSubordinatesTable(subordinates);

    }
}

function populateSubordinatesTable(subordinates) {
    let table = document.getElementById('subordinatesTable').getElementsByTagName("tbody")[0];
    let body = [];
    for (let subordinate of subordinates) {
        let name = subordinate.firstName + ' ' + subordinate.lastName;

        let row = '<tr>';
        row += `<td class="col-xs-8">${name}</td>`;
        row += `<td class="col-xs-8 col-position">${subordinate.position.toLowerCase()}</td>`;
        row += `<td class="col-xs-4">
                <button class="btn btn-info" id="assignTask" data-toggle="modal" data-target="#taskModal" onclick="localStorage.clickedSubordinateId=${subordinate.id};">Assign Task</button>
            </td>`;

        row += '</tr>';
        body.push(row);
    }

    table.innerHTML = body;
}

function populateEmployeeData() {
    let empName = employee.firstName + ' ' + employee.lastName;
    let managerName = 'No Manager';

    // if manager==null, there is no manager.
    if (manager != null) {
        managerName = manager.firstName + ' ' + manager.lastName;
    }

    document.getElementById("empName").innerHTML = empName;
    document.getElementById("empPosition").innerHTML = employee.position.toLowerCase();
    document.getElementById("manager").innerHTML = managerName;
}

function assignTask() {
    let dueDate = document.getElementById("dueDatepicker").value;
    let text = document.getElementById("taskModalText").value;

    let formattedDueDate = moment(dueDate).format("YYYY-MM-DD HH:MM:SS.SSS");
    let formattedAssignDate = moment().format("YYYY-MM-DD HH:MM:SS.SSS");

    if (text.length === 0 || formattedDueDate == null) {
        alert('Text must not be empty and date in format "20:10 10/04/2018" ');
        return;
    }

    let task = {
        text: text,
        assignDate: formattedAssignDate,
        dueDate: formattedDueDate,
        employeeId: localStorage.clickedSubordinateId
    };

    axios.post("/task", task).then((res) => {
        console.log(res);
    }).catch(error => {
        console.log(error);
    });
    $('#taskModal').modal('hide');
    document.getElementById("dueDatepicker").value = '';
    document.getElementById("taskModalText").value = '';
}

$('#dueDatepicker').datetimepicker({
    uiLibrary: 'bootstrap4'
});


function sendReport() {
    let text = document.getElementById("reportModalText").value;
    let reportDate = moment().format("YYYY-MM-DD HH:MM:SS.SSS");

    if (text.length === 0) {
        alert('Text must not be empty.');
        return;
    }

    let report = {text: text, reportDate: reportDate, employeeId: employeeId, managerId: manager.id};
    console.log(report);
    axios.post("/report", report).then((res) => {
        console.log(res);
    }).catch(error => {
        console.log(error);
    });
    $('#reportModal').modal('hide');
    document.getElementById("reportModalText").value = '';
}

function displayManagerReportsAndSubOrdLabels() {
    document.getElementById('reportsHeadline').style.display = 'inline';
    document.getElementById('subordinatesHeadline').style.display = 'inline';
}