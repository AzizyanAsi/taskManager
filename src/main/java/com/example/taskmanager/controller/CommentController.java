package com.example.taskmanager.controller;

import com.example.taskmanager.converter.CommentConverter;
import com.example.taskmanager.converter.dataTransferObjects.CommentRequest;
import com.example.taskmanager.converter.dataTransferObjects.CommentResponse;
import com.example.taskmanager.converter.dataTransferObjects.TaskRequest;
import com.example.taskmanager.converter.dataTransferObjects.TaskResponse;
import com.example.taskmanager.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentConverter commentConverter;

    @PostMapping("/create")
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CommentRequest commentRequest) {
        return ResponseEntity.ok(commentConverter.convertToDTO(commentService.createComment(commentRequest)));
    }
}
