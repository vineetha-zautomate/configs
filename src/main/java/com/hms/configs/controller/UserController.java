package com.hms.configs.controller;

import com.hms.configs.dto.CreateUserRequest;
import com.hms.configs.dto.UpdateUserRequest;
import com.hms.configs.service.impl.KeycloakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private KeycloakService keycloakService;

    // API to create a user with phone number and password
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest createUserRequest) {
        String response = keycloakService.createUser(createUserRequest);
        return ResponseEntity.ok(response);
    }

    // API to create a user with phone number and password
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody CreateUserRequest createUserRequest) {
        String response = keycloakService.getTokens(createUserRequest.getPhoneNumber(), createUserRequest.getPassword());
        return ResponseEntity.ok(response);
    }

    // API to update user details like first name, last name, and email
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUserDetails(@PathVariable("userId") String userId, @RequestBody UpdateUserRequest updateUserRequest) {
        updateUserRequest.setUserId(userId);
        String response = keycloakService.updateUserDetails(updateUserRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        keycloakService.deleteUser(userId);
        return "User Deleted Successfully.";
    }

}

