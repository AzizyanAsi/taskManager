package com.example.taskmanager.converter.dataTransferObjects;

import com.example.taskmanager.domain.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CommentResponse {

    private Long id;
    private String description;
    private String creationDate;
    private Task task;
}
