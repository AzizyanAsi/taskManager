package com.example.taskmanager.converter.dataTransferObjects;

import com.example.taskmanager.validation.customAnnotations.ValidDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class CommentRequest {

    @NotBlank(message = "description must not be blank")
    private String description;

    @ValidDate
    @NotNull(message = "The creation date is required")
    private String creationDate;

    @NotNull
    private Long taskId;

    @NotNull
    private Long creatorId;
}
