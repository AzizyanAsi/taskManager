package com.example.taskmanager.converter;

import com.example.taskmanager.converter.dataTransferObjects.UserRequestDTO;
import com.example.taskmanager.converter.dataTransferObjects.UserResponseDTO;
import com.example.taskmanager.domain.User;

import java.util.List;

public interface UserConverter {

    User convertToEntity(UserRequestDTO userDTO);

    UserResponseDTO convertToDTO(User user);

    List<UserResponseDTO> bulkConvertToDTO(List<User> users);

    List<User> bulkConvertToEntity(List<UserRequestDTO> users);
}
