package com.example.taskmanager.converter.dataTransferObjects;

import com.example.taskmanager.domain.Comment;
import com.example.taskmanager.validation.customAnnotations.ValidDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Getter
@Setter
@ToString
public class TaskRequest {

    @NotBlank(message = "status must not be blank")
    private String status;

    @NotBlank(message = "severity must not be blank")
    private String severity;

    @NotBlank(message = "title must not be blank")
    private String title;

    @NotBlank(message = "description must not be blank")
    private String description;

    @ValidDate
    @NotNull(message = "The creation date is required")
    private String creationDate;

    @Positive
    private Long reporterId;

    @Positive
    private Long assigneeId;

}
