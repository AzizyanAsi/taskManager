

package com.example.taskmanager.service;

import com.example.taskmanager.converter.dataTransferObjects.UpdateRequest;
import com.example.taskmanager.converter.dataTransferObjects.UserRequestDTO;
import com.example.taskmanager.domain.User;
import com.example.taskmanager.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface UserService {

    User register(UserRequestDTO userRequest);

    User update(String username, UpdateRequest userRequest);

    User findByUsername(String username);

    Boolean userNameExists(String name);

    Boolean emailExists(String name);

    List<User> getAllUsers();

    User findById(Long id) throws UserNotFoundException;

    boolean deleteById(Long id);

    void sendNotificationToUsers(Map<String, String> data, String noteType);
}
