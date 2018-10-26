package com.tobepercise.exercise.repositories;

import com.tobepercise.exercise.enums.PositionEnum;
import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private EmployeeRepository empRepo;

    @Test
    public void getTasksByEmployeeIdTest() {
        Employee emp = new Employee(222222222, "Test", "Employee", 0, PositionEnum.EMPLOYEE);

        Timestamp timestamp = new Timestamp(new Date().getTime());
        Task task = new Task("Task!", timestamp, timestamp, emp.getId());
        Task taskTwo = new Task("Task 2", timestamp, timestamp, emp.getId());

        this.empRepo.save(emp);
        this.taskRepo.save(task);
        this.taskRepo.save(taskTwo);

        List<Task> found = this.taskRepo.getTasksByEmployeeId(emp.getId());
        Assert.notNull(found, "Result should not be null");
        Assert.isTrue(found.contains(task), "First task should be in the result");
        Assert.isTrue(found.contains(taskTwo), "Second task should be in the result");
        Assert.isTrue(found.size() == 2, "Result should contain only two elements");
    }

    @Test
    public void getTaskByIdTest() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        Task task = new Task("Task!", timestamp, timestamp, 303011308);

        task = this.taskRepo.save(task);

        Task found = this.taskRepo.getTasksById(task.getId());

        Assert.notNull(found,"Result should not be null");
        Assert.isTrue(found.equals(task),"Result and input should be equal");
    }
}
