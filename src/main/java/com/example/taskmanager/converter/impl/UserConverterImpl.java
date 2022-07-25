package com.example.taskmanager.converter.impl;

import com.example.taskmanager.converter.UserConverter;
import com.example.taskmanager.converter.dataTransferObjects.UserRequestDTO;
import com.example.taskmanager.converter.dataTransferObjects.UserResponseDTO;
import com.example.taskmanager.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverterImpl implements UserConverter {


    @Override
    public User convertToEntity(UserRequestDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User entity = new User();
        entity.setFirstName(userDTO.getFirstName());
        entity.setLastName(userDTO.getLastName());
        entity.setUsername(userDTO.getUsername());
        entity.setPassword(userDTO.getPassword());
        entity.setEmail(userDTO.getEmail());
        return entity;
    }

    @Override
    public UserResponseDTO convertToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }

    @Override
    public List<UserResponseDTO> bulkConvertToDTO(List<User> users) {
        return users.stream().map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> bulkConvertToEntity(List<UserRequestDTO> userRequest) {
        return userRequest.stream().map(this::convertToEntity)
                .collect(Collectors.toList());
    }
}

