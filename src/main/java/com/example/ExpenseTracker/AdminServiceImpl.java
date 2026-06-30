package com.example.ExpenseTracker;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    public AdminServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public List<AppUser> getAllUsers(){
        return userRepository.findAll();
    }
    
}
