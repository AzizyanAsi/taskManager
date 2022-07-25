package com.example.taskmanager.converter.dataTransferObjects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class UpdateTaskRequest {

    private String status;

    @Positive
    private Long updaterId;

    @Positive
    private Long assigneeId;
}
