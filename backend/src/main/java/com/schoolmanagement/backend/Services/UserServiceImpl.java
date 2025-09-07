package com.schoolmanagement.backend.Services;

import com.schoolmanagement.backend.entity.User;
import com.schoolmanagement.backend.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;


    @Override
    public User saveUser(User user) {
        // Any additional business logic or validation can go here
        return userRepository.save(user);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found with id: " + id));
    }
}
