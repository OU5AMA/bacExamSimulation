package com.schoolmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT', 'TEACHER')")
    public ResponseEntity<String> getStudentDashboard(){
        return ResponseEntity.ok("Welcome to student DashBoard! All autheticated users can see this.");
    }
}
