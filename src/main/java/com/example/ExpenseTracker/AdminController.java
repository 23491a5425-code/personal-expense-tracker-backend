package com.example.ExpenseTracker;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    private final AdminService adminService;
    public AdminController (AdminService adminService){
        this.adminService=adminService;
    }
    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAllUsers(){
        List<AppUser> user= adminService.getAllUsers();
        return ResponseEntity.ok(user);
    }
}
