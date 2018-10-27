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
@Table(name = "Tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @NotNull(message = "Task text must not be null")
    @Size(min = 1, max = 300, message = "Task text length can be between 1 and 300")
    private String text;

    @NotNull(message = "Task assign date must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp assignDate;

    @NotNull(message = "Task due date must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp dueDate;

    @NotNull(message = "Task employeeId must not be null")
    private int employeeId;

    public Task(String text, Timestamp assignDate, Timestamp dueDate, int employeeId) {
        this.text = text;
        this.assignDate = assignDate;
        this.dueDate = dueDate;
        this.employeeId = employeeId;
    }
}
