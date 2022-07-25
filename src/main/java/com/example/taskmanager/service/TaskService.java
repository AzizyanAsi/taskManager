package com.example.taskmanager.service;

import com.example.taskmanager.converter.dataTransferObjects.TaskRequest;
import com.example.taskmanager.converter.dataTransferObjects.UpdateTaskRequest;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;


@Service
public interface TaskService {

    Task createTask(TaskRequest taskRequest);

    Task updateAssignee(Long id, UpdateTaskRequest taskRequest);

    Task updateTaskStatus(Long id, UpdateTaskRequest taskRequest);

    Task findById(Long id) throws TaskNotFoundException;

}
