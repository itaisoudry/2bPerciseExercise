package com.tobepercise.exercise.repositories;

import com.tobepercise.exercise.enums.PositionEnum;
import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.model.Report;
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
public class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepo;

    @Autowired
    private EmployeeRepository empRepo;

    @Test
    public void getReportsByManagerIdTest() {
        Employee manager = new Employee(222222222, "Test", "Manager", 0, PositionEnum.MANAGER);
        this.empRepo.save(manager);

        Report reportOne = new Report("Report to manager", new Timestamp(new Date().getTime()), 111111111, 222222222);
        Report reportTwo = new Report("Report 2 to manager", new Timestamp(new Date().getTime()), 111111111, 222222222);

        reportOne = this.reportRepo.save(reportOne);
        reportTwo = this.reportRepo.save(reportTwo);

        List<Report> found = this.reportRepo.getReportsByManagerId(manager.getId());

        Assert.notNull(found, "Result should not be null");
        Assert.isTrue(found.contains(reportOne), "First report should be in the results");
        Assert.isTrue(found.contains(reportTwo), "Second report should be in the results");
        Assert.isTrue(found.size() == 2, "Reports list should contain only two elements");
    }

    @Test
    public void getReportsByIdTest() {
        Report report = new Report("Report", new Timestamp(new Date().getTime()), 303011308, 303011309);

        report = this.reportRepo.save(report);

        Report found = this.reportRepo.getReportsById(report.getId());

        Assert.notNull(found, "Result should not be null");
        Assert.isTrue(found.equals(report), "Result should be equal to input");

    }
}
