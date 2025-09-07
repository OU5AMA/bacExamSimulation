package com.schoolmanagement.backend.Services;

import com.schoolmanagement.backend.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User saveUser(@Valid User user);

    List<User> getAllUsers();

    User getUserById(Long id);


}
