package com.example.taskmanager.converter.dataTransferObjects;

import com.example.taskmanager.domain.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class TaskResponse {

    private Long id;
    private String status;
    private String severity;
    private String title;
    private String description;
    private String creationDate;
    private UserResponseDTO reporter;
    private UserResponseDTO assignee;
    private List<Comment> comments = new ArrayList<>();
}
