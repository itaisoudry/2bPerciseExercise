const instance = axios.create({
    timeout: 1000,
    headers: {'Content-Type': 'application/json'}
});

const MANAGER = 'MANAGER';

const employeeId = localStorage.currentEmployeeId;
const employeesData = JSON.parse(localStorage.employeesData);
const employee = employeesData[employeeId];
const manager = employeesData[employee.managerId];

if (employee.position === MANAGER) {
    displayManagerReportsAndSubOrdLabels();
    getManagerSubordinates();
    getManagerReports();
}

getEmployeeTasks();
populateEmployeeData();

function getManagerReports() {
    instance.get(`/report/${employee.id}`).then((response) => {
        populateReports(response.data);
    }).catch((error) => {
        alert('Error: Cannot get manager reports');
        console.log(error);
    });
}

function getEmployeeTasks() {
    instance.get(`/task/${employee.id}`).then((response) => {
        populateTasks(response.data);
    }).catch((error) => {
        alert("Error: Cannot get employee tasks");
        console.log(error);
    });
}

function getManagerSubordinates() {
    instance.get(`/employee/${employee.id}/subordinates`).then((response) => {
        populateSubordinates(response.data);
    }).catch((error) => {
        alert("Error: Cannot get manager subordinates");
        console.log(error);
    });
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
    const tbody = document.getElementById('tasksTableTbody');

    const body = [];
    tasks.forEach(task => {
        const dueDate = moment(task.dueDate).format("DD-MM-YYYY HH:MM");

        let row = '<tr>';
        row += `<td class="col-xs-8">${task.text}</td>`;
        row += `<td class="col-xs-8">${dueDate}</td>`;

        row += '</tr>';
        body.push(row);
    });

    tbody.innerHTML = body.join('');
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
    const tbody = document.getElementById('reportsTableTbody');

    const body = [];
    reports.forEach(report => {
        const reportDate = moment(report.reportDate).format("DD-MM-YYYY HH:MM");

        let row = '<tr>';
        row += `<td class="col-xs-8">${report.text}</td>`;
        row += `<td class="col-xs-8">${reportDate}</td>`;

        row += '</tr>';
        body.push(row);
    });

    tbody.innerHTML = body.join('');
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
    const tbody = document.getElementById('subordinatesTableTbody');
    const body = [];
    subordinates.forEach(subordinate => {
        const name = subordinate.firstName + ' ' + subordinate.lastName;

        let row = '<tr>';
        row += `<td class="col-xs-8">${name}</td>`;
        row += `<td class="col-xs-8 col-position">${subordinate.position.toLowerCase()}</td>`;
        row += `<td class="col-xs-4">
                <button class="btn btn-info" id="assignTask" data-toggle="modal" data-target="#taskModal" onclick="localStorage.clickedSubordinateId=${subordinate.id};">Assign Task</button>
            </td>`;

        row += '</tr>';
        body.push(row);
    });

    tbody.innerHTML = body.join('');
}

function populateEmployeeData() {
    const empName = employee.firstName + ' ' + employee.lastName;
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
    const dueDate = document.getElementById("dueDatepicker").value;
    const text = document.getElementById("taskModalText").value;

    const formattedDueDate = moment(dueDate).format("YYYY-MM-DD HH:MM:SS.SSS");
    const formattedAssignDate = moment().format("YYYY-MM-DD HH:MM:SS.SSS");

    if (text.length === 0 || formattedDueDate == null) {
        alert('Text must not be empty and date in format "20:10 10/04/2018" ');
        return;
    }

    const task = {
        text: text,
        assignDate: formattedAssignDate,
        dueDate: formattedDueDate,
        employeeId: localStorage.clickedSubordinateId
    };

    instance.post("/task", task).then((res) => {
        console.log(res);
    }).catch(error => {
        alert("Error: Cannot create task");
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
    const text = document.getElementById("reportModalText").value;
    const reportDate = moment().format("YYYY-MM-DD HH:MM:SS.SSS");

    if (text.length === 0) {
        alert('Text must not be empty.');
        return;
    }

    const report = {text: text, reportDate: reportDate, employeeId: employeeId, managerId: manager.id};

    instance.post("/report", report).then((res) => {
        console.log(res);
    }).catch(error => {
        alert("Error: Cannot create report");
        console.log(error);
    });

    $('#reportModal').modal('hide');
    document.getElementById("reportModalText").value = '';
}

function displayManagerReportsAndSubOrdLabels() {
    document.getElementById('reportsHeadline').style.display = 'inline';
    document.getElementById('subordinatesHeadline').style.display = 'inline';
}