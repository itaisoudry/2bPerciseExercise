package com.tobepercise.exercise.repositories;

import com.tobepercise.exercise.enums.PositionEnum;
import com.tobepercise.exercise.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    public void getEmployeeByIdShouldReturnCorrectEmployee() {
        Employee employee = new Employee(444444444, "Test", "Employee", 0, PositionEnum.EMPLOYEE);

        employee = this.employeeRepository.save(employee);

        Employee found = this.employeeRepository.getEmployeeById(employee.getId());

        Assert.notNull(found, "Result should not be null");
        Assert.isTrue(found.equals(employee), "Result and input should be equal");
    }

    @Test
    public void getManagerSubOrdinatesReturnsSubOrdinates() {
        Employee manager = new Employee(111111117, "Manager", "Manager", 0, PositionEnum.MANAGER);

        Employee subOne = new Employee(111111118, "First", "Employee", manager.getId(), PositionEnum.EMPLOYEE);
        Employee subTwo = new Employee(111111119, "Second", "Employee", manager.getId(), PositionEnum.EMPLOYEE);

        this.employeeRepository.save(manager);
        this.employeeRepository.save(subOne);
        this.employeeRepository.save(subTwo);

        List<Employee> subOrdinates = this.employeeRepository.getEmployeeByManagerId(manager.getId());
        Assert.notNull(subOrdinates, "List should not be null");
        Assert.isTrue(subOrdinates.size() == 2, "List should contain only two elements");
        Assert.isTrue(subOrdinates.contains(subOne), "First employee should be in the result");
        Assert.isTrue(subOrdinates.contains(subTwo), "Second employee should be in the result");
    }

    @Test
    public void isEmployeeAndManagerExistsTest() {
        Employee manager = new Employee(111111117, "Manager", "Manager", 0, PositionEnum.MANAGER);
        Employee subOne = new Employee(111111118, "First", "Employee", manager.getId(), PositionEnum.EMPLOYEE);

        this.employeeRepository.save(manager);
        this.employeeRepository.save(subOne);

        int count = this.employeeRepository.isEmployeeAndManagerExists(subOne.getId(), manager.getId());

        Assert.isTrue(count == 2, "Count should be 2");
    }


}
