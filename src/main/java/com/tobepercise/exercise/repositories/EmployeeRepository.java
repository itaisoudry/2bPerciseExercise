package com.tobepercise.exercise.repositories;

import com.tobepercise.exercise.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee getEmployeeById(int employeeId);

    @Query(value = "SELECT count(id) FROM employees WHERE (id=:employeeId) OR (id=:managerId AND position=1)",
            nativeQuery = true)
    int isEmployeeAndManagerExists(@Param("employeeId") int employeeId, @Param("managerId") int managerId);

    List<Employee> getEmployeeByManagerId(int managerId);
}
