package com.schoolmanagement.backend.controller;

import com.schoolmanagement.backend.Services.UserService;
import com.schoolmanagement.backend.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // POST /api/v1/users => create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        return userService.createUser(user);
    }

    // GET /api/v1/users => List all users
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        return userService.getAllUsers();
    }

    // GET /api/v1/users => get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
}
