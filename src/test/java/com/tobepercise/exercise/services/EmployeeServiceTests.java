package com.tobepercise.exercise.services;

import com.tobepercise.exercise.enums.PositionEnum;
import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.repositories.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTests {
    @Autowired
    private EmployeesService empService;

    @Autowired
    private EmployeeRepository empRepo;

    @Test
    public void findEmployeeByIdShouldReturnEmployeeWithSameId() {
        Employee emp = this.empService.getEmployeeById(303011308);

        Assert.notNull(emp, "Result should not be null");
        Assert.isTrue(emp.getId() == 303011308, "Employee Id should be 303011308");
    }

    @Test
    public void creatingNewEmployeeShouldAddNewEmployee() {
        Employee dani = new Employee(303011310, "dani", "cohen", 0, PositionEnum.EMPLOYEE);

        this.empService.create(dani);

        Employee found = this.empService.getEmployeeById(dani.getId());

        Assert.notNull(found, "Result should not be null");
        Assert.isTrue(dani.equals(found), "Employees must be equal");
    }

    @Test
    public void deleteEmployeeTest() {
        Employee empOne = new Employee(232124543,"First","Employee",0,PositionEnum.MANAGER);
        this.empRepo.save(empOne);

        this.empService.delete(empOne.getId());

        Assert.isNull(this.empService.getEmployeeById(empOne.getId()), "Result should be null");
    }

    @Test
    public void updateEmployeeTests() {
        Employee itai = this.empService.getEmployeeById(303011308);
        itai.setLastName("Sudri");

        this.empService.create(itai);

        Employee found = this.empService.getEmployeeById(303011308);

        Assert.notNull(found, "Result should not be null");
        Assert.isTrue(found.getLastName().equals("Sudri"), "Last name should be 'Sudri' ");
    }

    @Test
    public void getEmployeesSummaryShouldReturnSevenEmployeesSummary() {
        this.empRepo.deleteAll();

        Employee empOne = new Employee(123456789,"First","Employee",0,PositionEnum.MANAGER);
        Employee empTwo = new Employee(123456788,"Second","Employee",0,PositionEnum.EMPLOYEE);

        this.empService.create(empOne);
        this.empService.create(empTwo);

        List<Employee> employees = this.empService.getEmployeesSummary();

        Assert.notNull(employees, "Employees list should not  be null");
        Assert.isTrue(employees.size() == 2, "List should contain only two objects");
    }

}
