package com.schoolmanagement.backend.Services;

import com.schoolmanagement.backend.entity.Role;
import com.schoolmanagement.backend.entity.User;
import com.schoolmanagement.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Array;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setFirstName("Ousama");
        user.setLastName("Lasri");
        user.setEmail("ousama@example.com");
        user.setPassword("password");
        user.setRole(Role.ADMIN);
        user.setPhoneNumber("12345689");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testcreateUser() {
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Ousama", savedUser.getFirstName());
        assertEquals("Lasri", savedUser.getLastName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testgetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testgetUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals("Ousama", foundUser.getFirstName());
        assertEquals("Lasri", foundUser.getLastName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByIdNotFound(){
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->{
            userService.getUserById(2L);
        });

        assertEquals("User not found with id: 2", exception.getMessage());
        verify(userRepository, times(1)).findById(2L);
    }
}