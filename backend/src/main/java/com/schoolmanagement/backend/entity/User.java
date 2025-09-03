package com.schoolmanagement.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "please, write your first name")
    @Size(min = 3, max = 50)
    private String firstName;
    @NotBlank(message = "please, write your last name")
    @Size(min = 3, max = 50)
    private String  lastName;
    @Email
    private String email;
    @NotBlank(message = "please, write a password")
    @Size(min = 6, max = 100)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Size(min = 10, max = 14)
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
