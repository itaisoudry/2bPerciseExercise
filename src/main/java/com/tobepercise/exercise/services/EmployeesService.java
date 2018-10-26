package com.tobepercise.exercise.services;

import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesService {

    private EmployeeRepository empRepo;

    @Autowired
    public EmployeesService(EmployeeRepository empRepo) {
        this.empRepo = empRepo;
    }

    /**
     * Returns a summary of all the employees in the organization
     * Note: Currently, "summary" is the entire Employee object, but it might change.
     *
     * @return List of employees
     */
    public List<Employee> getEmployeesSummary() {
        return this.empRepo.findAll();
    }

    public Employee getEmployeeById(int employeeId) {
        return this.empRepo.getEmployeeById(employeeId);
    }

    public List<Employee> getManagerSubOrdinates(int managerId) {
        return this.empRepo.getEmployeeByManagerId(managerId);
    }

    public Employee create(Employee employee) {
        return this.empRepo.save(employee);
    }

    public Employee update(Employee employee) {
        return this.empRepo.save(employee);
    }

    public void delete(int employeeId) {
        this.empRepo.deleteById(employeeId);
    }
}
