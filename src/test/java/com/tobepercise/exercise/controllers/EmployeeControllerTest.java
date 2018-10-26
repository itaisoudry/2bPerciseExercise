package com.tobepercise.exercise.controllers;

import com.tobepercise.exercise.enums.PositionEnum;
import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.repositories.EmployeeRepository;
import com.tobepercise.exercise.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository empRepo;

    @Test
    public void getEmployeesShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/employee")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getEmployeeByIdShouldReturnOk() throws Exception {
        Employee employee = new Employee(123456789, "Test", "Employee", 0, PositionEnum.EMPLOYEE);
        this.empRepo.save(employee);

        this.mockMvc.perform(get("/employee/" + employee.getId())).andExpect(status().isOk());
    }

    @Test
    public void getEmployeeByIdWithBadIdShouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(get("/employee/123123123")).andExpect(status().isBadRequest());
    }

    @Test
    public void getSubordinatesShouldReturnOk() throws Exception {
        Employee manager = new Employee(123456799, "Test", "Manager", 0, PositionEnum.MANAGER);
        Employee employee = new Employee(123456789, "Test", "Employee", 123456799, PositionEnum.MANAGER);
        this.empRepo.save(employee);
        this.empRepo.save(manager);

        this.mockMvc.perform(get("/employee/" + manager.getId() + "/subordinates")).andExpect(status().isOk());
    }

    @Test
    public void getSubordinatesWithBadIdShouldReturnOk() throws Exception {
        this.mockMvc.perform(get("/employee/-1/subordinates")).andExpect(status().isBadRequest());
    }

    @Test
    public void createEmployeeShouldReturnOk() throws Exception {
        Employee employee = new Employee(123456789, "Test", "Employee", 123456799, PositionEnum.MANAGER);
        this.mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(employee)))
                .andExpect(status().isCreated()).andDo(print());
    }

    @Test
    public void createNullEmployeeShouldReturnBadRequest() throws Exception {
        this.mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createMissingParameterEmployeeShouldReturnBadRequest() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Test");
        this.mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateEmployeeShouldReturnOk() throws Exception {
        Employee employee = new Employee(123456789, "Test", "Employee", 123456799, PositionEnum.MANAGER);
        this.empRepo.save(employee);

        employee.setFirstName("Updated");
        this.mockMvc.perform(put("/employee").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.asJsonString(employee)))
                .andExpect(status().isOk());

    }


    @Test
    public void deleteEmployeeShouldReturnOk() throws Exception {
        Employee employee = new Employee(123456789, "Test", "Employee", 123456799, PositionEnum.MANAGER);
        this.empRepo.save(employee);

        this.mockMvc.perform(delete("/employee/" + employee.getId())).andExpect(status().isOk());
    }
}
