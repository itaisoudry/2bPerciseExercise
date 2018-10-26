package com.tobepercise.exercise.repositories;

import com.tobepercise.exercise.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer> {

    List<Report> getReportsByManagerId(int managerId);

    Report getReportsById(int reportId);

}
