package com.schoolmanagement.backend.Services;

import com.schoolmanagement.backend.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<User> createUser(@Valid User user);

    ResponseEntity<List<User>> getAllUsers();

    ResponseEntity<User> getUserById(Long id);


}
