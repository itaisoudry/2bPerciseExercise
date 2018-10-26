package com.tobepercise.exercise.services;

import com.tobepercise.exercise.exceptions.RepositoryException;
import com.tobepercise.exercise.model.Report;
import com.tobepercise.exercise.repositories.EmployeeRepository;
import com.tobepercise.exercise.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportsService {
    private ReportRepository reportRepo;

    private EmployeeRepository employeeRepo;

    @Autowired
    public ReportsService(ReportRepository reportRepo, EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
        this.reportRepo = reportRepo;
    }

    public List<Report> getManagerReports(int managerId) {
        return this.reportRepo.getReportsByManagerId(managerId);
    }

    public Report getReportById(int reportId) {
        return this.reportRepo.getReportsById(reportId);
    }

    public Report create(Report report) {

        int count = this.employeeRepo.isEmployeeAndManagerExists(report.getEmployeeId(), report.getManagerId());
        if (count != 2) {
            throw new RepositoryException("Incorrect employee or manager id");
        }

        return this.reportRepo.save(report);
    }

    public Report update(Report report) {
        return this.reportRepo.save(report);
    }

    public void delete(int reportId) {
        this.reportRepo.deleteById(reportId);
    }
}
