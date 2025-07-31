package com.schoolmanagement.dto.response;

import com.schoolmanagement.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;  // Only populated for teachers
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
