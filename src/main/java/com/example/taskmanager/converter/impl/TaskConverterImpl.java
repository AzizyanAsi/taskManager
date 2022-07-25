package com.example.taskmanager.converter.impl;

import com.example.taskmanager.converter.TaskConverter;
import com.example.taskmanager.converter.UserConverter;
import com.example.taskmanager.converter.dataTransferObjects.TaskRequest;
import com.example.taskmanager.converter.dataTransferObjects.TaskResponse;
import com.example.taskmanager.domain.enums.SeverityEnum;
import com.example.taskmanager.domain.enums.StatusEnum;
import com.example.taskmanager.domain.Task;
import com.example.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.taskmanager.validation.DateValidatorCustom.DATE_FORMAT_PATTERN;

@Component
@RequiredArgsConstructor
public class TaskConverterImpl implements TaskConverter {

    private final UserService userService;
    private final UserConverter userConverter;

    @Override
    public Task convertToEntity(TaskRequest taskRequest) {
        if (taskRequest == null) {
            return null;
        }
        Date creationDate = null;
        try {
            creationDate = new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(taskRequest.getCreationDate());
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to convert creation date");
        }
        Task task = new Task();
        task.setStatus(StatusEnum.valueOf(taskRequest.getStatus()));
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setSeverity(SeverityEnum.valueOf(taskRequest.getSeverity()));
        task.setCreationDate(creationDate);
        task.setAssignee(userService.findById(taskRequest.getAssigneeId()));
        task.setReporter(userService.findById(taskRequest.getReporterId()));
        return task;
    }

    @Override
    public TaskResponse convertToDTO(Task task) {
        TaskResponse response = new TaskResponse();
        response.setId(task.getId());
        response.setStatus(String.valueOf(task.getStatus()));
        response.setDescription(task.getDescription());
        response.setTitle(task.getTitle());
        response.setSeverity(String.valueOf(task.getSeverity()));
        response.setCreationDate(String.valueOf(task.getCreationDate()));
        response.setReporter(userConverter.convertToDTO(task.getReporter()));
        response.setAssignee(userConverter.convertToDTO(task.getAssignee()));
        response.setComments(task.getComments());
        return response;
    }

    @Override
    public List<TaskResponse> bulkConvertToDTO(List<Task> tasks) {
        return tasks.stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> bulkConvertToEntity(List<TaskRequest> taskRequest) {
        return taskRequest.stream().map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
