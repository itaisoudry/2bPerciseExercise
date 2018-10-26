package com.tobepercise.exercise.services;

import com.tobepercise.exercise.exceptions.RepositoryException;
import com.tobepercise.exercise.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceTest {
    //TODO - change all tests to tests newly added data, and not from data.sql
    @Autowired
    private TasksService taskService;

    @Test
    public void getTaskByIdShouldReturnCorrectTask() {
        Task task = this.taskService.getTaskById(1);

        Assert.notNull(task, "Task should not be null");
        Assert.isTrue(task.getId() == 1, "Task ID should be 1");
    }

    @Test
    public void getEmployeeTasksShouldReturnListOfTasks() {
        List<Task> tasks = this.taskService.getEmployeeTasks(111111112);

        Assert.isTrue(tasks.size() == 1, "Tasks list should contain only one task");
        Assert.notNull(tasks.get(0), "Task should not be null");
        Assert.isTrue(tasks.get(0).getEmployeeId() == 111111112, "Id must match to 111111112");
    }


    @Test
    public void createTaskTest() {
        Task task = generateTask(111111112, "Test Task");
        task = this.taskService.create(task);

        Task found = this.taskService.getTaskById(task.getId());
        Assert.notNull(found, "Task cannot be null");
        Assert.isTrue(found.equals(task), "Input and Result should be equal");

        task.setText("Changed");
        Assert.isTrue(!found.equals(task), "Results should not be equal");
    }

    @Test(expected = RepositoryException.class)
    public void createTaskWithBadEmployeeIdShouldNotBeCreated() {
        Task task = generateTask(2341, "Task");

        this.taskService.create(task);
    }

    @Test
    public void updateTaskTest() {
        Task task = generateTask(111111111, "Task to be updated");

        task = this.taskService.create(task);

        task.setText("Updated text");

        this.taskService.update(task);

        Task updatedTask = this.taskService.getTaskById(task.getId());

        Assert.isTrue(updatedTask.getText().equals(task.getText()), "Text should be updated");
    }

    @Test
    public void deleteTaskTest() {
        Task task = generateTask(111111111, "Task to be deleted");

        task = this.taskService.create(task);

        this.taskService.delete(task.getId());

        Task found = this.taskService.getTaskById(task.getId());
        Assert.isNull(found, "Task Should be null");
    }

    private Task generateTask(int employeeId, String text) {

        Calendar calendar = Calendar.getInstance();

        Timestamp assignDate = new Timestamp(calendar.getTimeInMillis());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        Timestamp dueDate = new Timestamp(calendar.getTimeInMillis());


        return new Task(text, assignDate, dueDate, employeeId);
    }
}
