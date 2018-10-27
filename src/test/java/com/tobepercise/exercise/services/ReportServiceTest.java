package com.tobepercise.exercise.services;

import com.tobepercise.exercise.enums.PositionEnum;
import com.tobepercise.exercise.exceptions.RepositoryException;
import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.model.Report;
import com.tobepercise.exercise.repositories.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private EmployeeRepository empRepo;

    @Before
    public void before() {
        Employee manager = new Employee(111111111, "Manager", "Employee", 0, PositionEnum.MANAGER);
        Employee employee = new Employee(111111112, "Employee", "Employee", 111111111, PositionEnum.EMPLOYEE);

        this.empRepo.save(manager);
        this.empRepo.save(employee);
    }

    @Test
    public void createReportTest() {
        Report report = generateReport(111111112, 111111111, "report");

        this.reportsService.create(report);

        Report found = this.reportsService.getReportById(report.getId());

        Assert.isTrue(report.equals(found), "Results should be equal");
    }

    @Test(expected = RepositoryException.class)
    public void createReportWithWrongEmployeeShouldNotCreate() {
        Report report = generateReport(1, 111111111, "report");

        this.reportsService.create(report);

        Report found = this.reportsService.getReportById(report.getId());
        Assert.isNull(found, "Report should be null");

        report.setManagerId(2);
        report.setEmployeeId(111111111);

        found = this.reportsService.getReportById(report.getId());
        Assert.isNull(found, "Report should be null");
    }


    @Test
    public void updateReportTest() {
        Report report = generateReport(111111112, 111111111, "report");

        this.reportsService.create(report);

        report.setText("Updated report text");

        this.reportsService.update(report);

        Report found = this.reportsService.getReportById(report.getId());
        System.out.println(this.reportsService.getManagerReports(report.getManagerId()));
        Assert.notNull(found, "Result should not be null");
        Assert.isTrue(found.equals(report), "Result should be equal to input");
    }

    @Test
    public void deleteReportTest() {
        Report report = generateReport(111111112, 111111111, "report");

        this.reportsService.create(report);
        this.reportsService.delete(report.getId());

        Report found = this.reportsService.getReportById(report.getId());
        Assert.isNull(found, "Result should be null");
    }

    private Report generateReport(int employeeId, int managerId, String text) {
        Timestamp reportDate = new Timestamp(new Date().getTime());


        return new Report(text, reportDate, employeeId, managerId);
    }

}
