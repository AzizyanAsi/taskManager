package com.example.taskmanager.service.impl;

import com.example.taskmanager.converter.UserConverter;
import com.example.taskmanager.converter.dataTransferObjects.UpdateRequest;
import com.example.taskmanager.converter.dataTransferObjects.UserRequestDTO;
import com.example.taskmanager.domain.User;
import com.example.taskmanager.exception.UserNotFoundException;
import com.example.taskmanager.firebase.FirebaseMessagingService;
import com.example.taskmanager.firebase.Note;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseMessagingService firebaseService;

    @Value("${notification.client.token}")
    private String clientToken;

    @Override
    @Transactional
    public User register(UserRequestDTO userDTO) {
        LOGGER.debug("Requested to create user for email : {}", userDTO.getEmail());
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(userConverter.convertToEntity(userDTO));
    }

    @Override
    public void sendNotificationToUsers(Map<String, String> data, String noteType) {
        Note note = new Note();
        note.setContent(noteType);
        note.setData(data);
        try {
            firebaseService.sendNotification(note, clientToken);
            LOGGER.info("notifications with note {} was successfully sent", note);

        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public User update(String username, UpdateRequest userRequest) {
        LOGGER.info("Requested to update a user with username {}", username);
        final User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("No user found by this username {}", username));

        if (user.isProfileUpdated()) {
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setEmail(userRequest.getEmail());
            user.setUsername(userRequest.getUsername());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        LOGGER.info("In update User, user with username {} successfully updated", username);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        LOGGER.info("In findByUsername requested to get the user with username {}", username);
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("No user found by this username", username));
    }

    @Override
    @Transactional
    public Boolean userNameExists(String name) {
        return userRepository.findByUsername(name).isPresent();
    }

    @Override
    public Boolean emailExists(String name) {
        return userRepository.findByEmail(name).isPresent();
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        LOGGER.debug("In find all : {} user found", users.size());
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        LOGGER.debug("In findById  requested to get the user with  id: {}", id);
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Not found user by this id: {}", id));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        LOGGER.info("Requested to delete a user with id {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("No user found by this id", id);
        }
        userRepository.deleteById(id);
        LOGGER.info(" User with id {} successfully deleted", id);
        return true;
    }

}
