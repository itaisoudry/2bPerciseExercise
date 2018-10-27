package com.tobepercise.exercise.controllers;

import com.tobepercise.exercise.exceptions.EmployeeControllerException;
import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeesService empService;

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployees() {

        return ResponseEntity.ok().body(this.empService.getEmployeesSummary());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int employeeId) throws
            EmployeeControllerException {
        if (employeeId <= 0)
            throw new EmployeeControllerException("Incorrect employeeId: Id should be bigger than 0");

        Employee result = this.empService.getEmployeeById(employeeId);
        if (result == null) {
            throw new EmployeeControllerException("Employee Id doesn't exists");
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/employee/{managerId}/subordinates")
    public ResponseEntity<List<Employee>> getManagerSubOrdinates(@PathVariable int managerId) throws
            EmployeeControllerException {
        if (managerId <= 0)
            throw new EmployeeControllerException("Incorrect employeeId: Id should be bigger than 0");

        return ResponseEntity.ok().body(this.empService.getManagerSubOrdinates(managerId));
    }

    @PostMapping("/employee")
    public ResponseEntity createEmployee(@Valid @RequestBody Employee employee) throws EmployeeControllerException {
        if (employee == null)
            throw new EmployeeControllerException("Employee must not be null");

        this.empService.create(employee);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/employee")
    public ResponseEntity updateEmployee(@Valid @RequestBody Employee employee) throws EmployeeControllerException {
        if (employee == null)
            throw new EmployeeControllerException("Employee must not be null");

        // update if exists else create
        this.empService.update(employee);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity deleteEmployee(@PathVariable int employeeId) throws EmployeeControllerException {
        if (employeeId <= 0)
            throw new EmployeeControllerException("Incorrect employeeId: Id should be bigger than 0");

        this.empService.delete(employeeId);

        return ResponseEntity.ok().build();
    }


}
