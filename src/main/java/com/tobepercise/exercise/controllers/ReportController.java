package com.tobepercise.exercise.controllers;

import com.tobepercise.exercise.exceptions.ReportControllerException;
import com.tobepercise.exercise.model.Report;
import com.tobepercise.exercise.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ReportController {

    @Autowired
    private ReportsService reportService;

    @GetMapping("/report/{managerId}")
    public ResponseEntity getManagerReports(@PathVariable int managerId) throws ReportControllerException {
        if (managerId <= 0) {
            throw new ReportControllerException("managerId must be bigger than 0");
        }

        return ResponseEntity.ok().body(this.reportService.getManagerReports(managerId));
    }

    @PostMapping("/report")
    public ResponseEntity createReport(@Valid @RequestBody Report report) throws ReportControllerException {
        if (report == null) {
            throw new ReportControllerException("Report must not be null");
        }

        this.reportService.create(report);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/report")
    public ResponseEntity updateReport(@Valid @RequestBody Report report) throws ReportControllerException {
        if (report == null) {
            throw new ReportControllerException("Report must not be null");
        }

        this.reportService.create(report);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/report/{reportId}")
    public ResponseEntity deleteReport(@PathVariable int reportId) throws ReportControllerException {
        if (reportId <= 0) {
            throw new ReportControllerException("reportId must be bigger than 0");
        }

        this.reportService.delete(reportId);

        return ResponseEntity.ok().build();
    }
}
