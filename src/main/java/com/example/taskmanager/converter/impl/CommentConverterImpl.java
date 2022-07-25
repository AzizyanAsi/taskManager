package com.example.taskmanager.converter.impl;

import com.example.taskmanager.converter.dataTransferObjects.CommentResponse;
import com.example.taskmanager.converter.CommentConverter;
import com.example.taskmanager.converter.dataTransferObjects.CommentRequest;
import com.example.taskmanager.domain.Comment;
import com.example.taskmanager.service.TaskService;
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
public class CommentConverterImpl implements CommentConverter {

    private final TaskService taskService;

    @Override
    public Comment convertToEntity(CommentRequest commentRequest) {
        Date creationDate = null;
        try {
            creationDate = new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(commentRequest.getCreationDate());
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to convert creation date");
        }
        Comment comment = new Comment();
        comment.setDescription(commentRequest.getDescription());
        comment.setCreationDate(creationDate);
        comment.setTask(taskService.findById(commentRequest.getTaskId()));
        return comment;
    }

    @Override
    public CommentResponse convertToDTO(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setDescription(comment.getDescription());
        response.setCreationDate(String.valueOf(comment.getCreationDate()));
        response.setTask(comment.getTask());
        return response;
    }

    @Override
    public List<CommentResponse> bulkConvertToDTO(List<Comment> comments) {
        return comments.stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> bulkConvertToEntity(List<CommentRequest> commentRequests) {
        return commentRequests.stream().map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}
