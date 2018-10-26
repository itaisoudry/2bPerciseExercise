package com.tobepercise.exercise.controllers;


import com.tobepercise.exercise.enums.PositionEnum;
import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.model.Report;
import com.tobepercise.exercise.repositories.EmployeeRepository;
import com.tobepercise.exercise.repositories.ReportRepository;
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
public class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public ReportRepository reportRepo;

    @Autowired
    public EmployeeRepository empRepo;

    @Before
    public void before() {
        Employee manager = new Employee(222222222, "Manager", "Employee", 0, PositionEnum.MANAGER);
        Employee employee = new Employee(222222223, "Employee", "Employee", manager.getId(), PositionEnum.EMPLOYEE);

        this.empRepo.save(manager);
        this.empRepo.save(employee);
    }

    @Test
    public void getManagerReportsShouldReturnOk() throws Exception {
        Report report = new Report("Report", new Timestamp(new Date().getTime()), 222222223, 222222222);
        this.reportRepo.save(report);

        this.mockMvc.perform(get("/report/" + report.getManagerId())).andExpect(status().isOk());
    }

    @Test
    public void createReportShouldReturnOk() throws Exception {
        Report report = new Report("Report", new Timestamp(new Date().getTime()), 222222223, 222222222);

        System.out.println(TestUtils.asJsonString(report));
        this.mockMvc.perform(post("/report").contentType(MediaType.APPLICATION_JSON).
                content(TestUtils.asJsonString(report))).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void createReportWithMissingParamsShouldReturnBadRequest() throws Exception {
        Report report = new Report();
        report.setText("Report");

        this.mockMvc.perform(post("/report").contentType(MediaType.APPLICATION_JSON).
                content(TestUtils.asJsonString(report))).
                andExpect(status().isBadRequest());
    }

    @Test
    public void updateReportShouldReturnOk() throws Exception {
        Report report = new Report("Report", new Timestamp(new Date().getTime()), 222222223, 222222222);

        this.reportRepo.save(report);

        report.setText("Updated!");

        this.mockMvc.perform(post("/report").contentType(MediaType.APPLICATION_JSON).
                content(TestUtils.asJsonString(report))).
                andExpect(status().isOk());
    }

    @Test
    public void deleteReportShouldReturnOk() throws Exception {
        Report report = new Report("Report", new Timestamp(new Date().getTime()), 222222223, 222222222);

        report = this.reportRepo.save(report);

        this.mockMvc.perform(delete("/report/" + report.getId())).andExpect(status().isOk());
    }

}
