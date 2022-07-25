package com.example.taskmanager.converter;

import com.example.taskmanager.converter.dataTransferObjects.TaskRequest;
import com.example.taskmanager.converter.dataTransferObjects.TaskResponse;
import com.example.taskmanager.domain.Task;

import java.util.List;

public interface TaskConverter {

    Task convertToEntity(TaskRequest taskRequest);

    TaskResponse convertToDTO(Task task);

    List<TaskResponse> bulkConvertToDTO(List<Task> tasks);

    List<Task> bulkConvertToEntity(List<TaskRequest> taskRequest);
}
