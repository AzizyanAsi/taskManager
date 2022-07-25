package com.example.taskmanager.converter.dataTransferObjects;

import com.example.taskmanager.validation.customAnnotations.UniqueEmail;
import com.example.taskmanager.validation.customAnnotations.UniqueUserName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class UserRequestDTO {

    @NotBlank(message = "firstName must not be blank")
    private String firstName;

    @NotBlank(message = "lastName must not be blank")
    private String lastName;

    @UniqueEmail
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "email must have correct syntax")
    private String email;

    @Size(min = 3, message = "username must have minimum 3 symbol")
    @UniqueUserName
    private String username;

    @Size(min = 6, message = "password must have minimum 6 symbol")
    private String password;



//    private List<Task> reportedTasks = new ArrayList<>();//TODO think how get user all reported tasks
//
//    private List<Task> assignedTasks = new ArrayList<>();


//    private List<Long> roleId = new ArrayList<>();

}
