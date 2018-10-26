package com.tobepercise.exercise.model;

import com.tobepercise.exercise.enums.PositionEnum;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employees")
public class Employee implements Serializable {
    @Id
    @Setter(AccessLevel.NONE)
    private int id;

    @NotNull(message = "First name must not be null")
    @Size(min = 2, max = 20, message = "First name should contain between 2 and 20 characters")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    @Size(min = 2, max = 20, message = "Last name should contain between 2 and 20 characters")
    private String lastName;

    private int managerId;

    @NotNull(message = "Position must be 0 (Employee) or 1 (Manager)")
    private PositionEnum position;

}
