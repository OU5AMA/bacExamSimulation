package com.schoolmanagement.service;

import com.schoolmanagement.dto.request.LoginRequest;
import com.schoolmanagement.dto.request.RegisterRequest;
import com.schoolmanagement.dto.response.AuthenticationResponse;
import com.schoolmanagement.dto.response.UserResponseDTO;
import com.schoolmanagement.entity.Role;
import com.schoolmanagement.entity.User;
import com.schoolmanagement.exception.UserAlreadyExistsException;
import com.schoolmanagement.exception.ValidationException;
import com.schoolmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // Validate business rules
        validateRegistrationRequest(request);

        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        // Build user entity
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phoneNumber(request.getPhoneNumber()) // Will be null for non-teachers
                .build();

        // Save user
        var savedUser = userRepository.save(user);

        // Generate tokens
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .Token(jwtToken)
                .refreshToken(refreshToken)
                .user(mapToUserResponseDTO(savedUser))
                .build();
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user from database
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate tokens
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        return AuthenticationResponse.builder()
                .Token(jwtToken)
                .refreshToken(refreshToken)
                .user(mapToUserResponseDTO(user))
                .build();
    }

    private void validateRegistrationRequest(RegisterRequest request) {
        // Only teachers can have phone numbers
        if (request.getRole() != Role.TEACHER && request.getPhoneNumber() != null) {
            throw new ValidationException("Only teachers can have phone numbers");
        }
    }

    private UserResponseDTO mapToUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getRole() == Role.TEACHER ? user.getPhoneNumber() : null)
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}