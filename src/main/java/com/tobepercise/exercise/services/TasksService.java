package com.tobepercise.exercise.services;

import com.tobepercise.exercise.exceptions.RepositoryException;
import com.tobepercise.exercise.model.Employee;
import com.tobepercise.exercise.model.Task;
import com.tobepercise.exercise.repositories.EmployeeRepository;
import com.tobepercise.exercise.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService {

    private TaskRepository taskRepo;

    private EmployeeRepository employeeRepo;

    @Autowired
    public TasksService(TaskRepository taskRepo, EmployeeRepository employeeRepo) {
        this.taskRepo = taskRepo;
        this.employeeRepo = employeeRepo;
    }

    public List<Task> getEmployeeTasks(int employeeId) {
        return this.taskRepo.getTasksByEmployeeId(employeeId);
    }


    public Task getTaskById(int taskId) {
        return this.taskRepo.getTasksById(taskId);
    }

    public Task create(Task task) {
        Employee employee = this.employeeRepo.getEmployeeById(task.getEmployeeId());
        if (employee == null || employee.getId() != task.getEmployeeId()) {
            throw new RepositoryException("Employee Id doesn't exists");
        }
        return this.taskRepo.save(task);
    }

    public void update(Task task) {
        this.taskRepo.save(task);
    }

    public void delete(int taskId) {
        this.taskRepo.deleteById(taskId);
    }

}
