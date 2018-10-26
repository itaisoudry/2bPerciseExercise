package com.tobepercise.exercise.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "Reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @NotNull(message="Report text must not be null")
    @Size(min = 1, max = 300,message="Report text length can be between 1 and 300")
    private String text;

    @NotNull(message="Report date must not be null")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp reportDate;

    @NotNull(message="Report employeeId must not be null")
    private int employeeId;

    @NotNull(message="Report managerId must not be null")
    private int managerId;

    public Report(String text, Timestamp reportDate, int employeeId, int managerId) {
        this.text = text;
        this.reportDate = reportDate;
        this.employeeId = employeeId;
        this.managerId = managerId;
    }

    public Report(){

    }

}
