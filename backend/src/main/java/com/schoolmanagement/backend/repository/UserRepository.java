package com.schoolmanagement.backend.repository;

import com.schoolmanagement.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
