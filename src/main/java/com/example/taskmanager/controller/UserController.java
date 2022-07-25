package com.example.taskmanager.controller;

import com.example.taskmanager.converter.UserConverter;
import com.example.taskmanager.converter.dataTransferObjects.UpdateRequest;
import com.example.taskmanager.converter.dataTransferObjects.UserRequestDTO;
import com.example.taskmanager.converter.dataTransferObjects.UserResponseDTO;
import com.example.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserConverter userConverter;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO userRequest) {
        return ResponseEntity.ok(userConverter.convertToDTO(userService.register(userRequest)));

    }

    @PutMapping("/{username}")
    public ResponseEntity<UserResponseDTO> updateUserInfo(@PathVariable String username,
                                                          @RequestBody @Valid UpdateRequest userRequest) {
        return ResponseEntity.ok(userConverter.convertToDTO(userService.update(username, userRequest)));
    }

    @GetMapping
    public List<? extends UserResponseDTO> getAllUsers() {
        return userConverter.bulkConvertToDTO(userService.getAllUsers());
    }

    @GetMapping("/search")
    public ResponseEntity<UserResponseDTO> searchUserByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userConverter.convertToDTO(userService.findByUsername(username)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userConverter.convertToDTO(userService.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteById(id));
    }

}
