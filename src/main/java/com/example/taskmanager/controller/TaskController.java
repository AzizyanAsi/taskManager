package com.example.taskmanager.controller;

import com.example.taskmanager.converter.TaskConverter;
import com.example.taskmanager.converter.UserConverter;
import com.example.taskmanager.converter.dataTransferObjects.*;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final TaskConverter taskConverter;

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskRequest taskRequest) {
        return ResponseEntity.ok(taskConverter.convertToDTO(taskService.createTask(taskRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateAssignee(@PathVariable Long id,
                                                       @RequestBody @Valid UpdateTaskRequest userRequest) {
        return ResponseEntity.ok(taskConverter.convertToDTO(taskService.updateAssignee(id, userRequest)));
    }

    @PutMapping("updateStatus/{id}")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long id,
                                                     @RequestBody @Valid UpdateTaskRequest userRequest) {
        return ResponseEntity.ok(taskConverter.convertToDTO(taskService.updateTaskStatus(id, userRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskConverter.convertToDTO(taskService.findById(id)));
    }

}
