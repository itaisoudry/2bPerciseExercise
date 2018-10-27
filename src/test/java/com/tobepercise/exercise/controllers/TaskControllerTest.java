package com.tobepercise.exercise.controllers;

import com.tobepercise.exercise.enums.PositionEnum;
import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.model.Task;
import com.tobepercise.exercise.repositories.EmployeeRepository;
import com.tobepercise.exercise.repositories.TaskRepository;
import com.tobepercise.exercise.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    private Timestamp assignDate;
    private Timestamp dueDate;

    private Employee employee;

    @Before
    public void before() {
        this.assignDate = new Timestamp(new Date().getTime());
        this.dueDate = new Timestamp(new Date().getTime());

        this.employee = new Employee(212121212, "Tasks", "Employee", 0, PositionEnum.EMPLOYEE);
        this.employeeRepo.save(employee);
    }

    @Test
    public void getEmployeeTasksShouldReturnOk() throws Exception {
        Task task = new Task("Test task", this.assignDate, this.dueDate, employee.getId());

        this.taskRepo.save(task);

        this.mockMvc.perform(get("/task/" + employee.getId())).andExpect(status().isOk());
    }

    @Test
    public void createTaskShouldReturnOk() throws Exception {
        Task task = new Task("Test task", this.assignDate, this.dueDate, employee.getId());

        this.mockMvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(task))).andDo(print())
                .andExpect(status().isOk());

        System.out.println(this.taskRepo.findAll());
    }

    @Test
    public void updateTaskShouldReturnOk() throws Exception {
        Task task = new Task("Test task", this.assignDate, this.dueDate, employee.getId());

        this.taskRepo.save(task);

        task.setText("Updated Text");

        this.mockMvc.perform(put("/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(task)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTaskShouldReturnOk() throws Exception {
        Task task = new Task("Test task", this.assignDate, this.dueDate, employee.getId());

        task = this.taskRepo.save(task);

        this.mockMvc.perform(delete("/task/" + task.getId())).andExpect(status().isOk());
    }
}
