package com.example.ExpenseTracker;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    @Override
    public AppUser saveUser(AppUser user){
        return userRepository.save(user);

    }

    @Override
    public AppUser findByUsername(String username)
    {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Optional<AppUser> findUserById(Long id){
        return userRepository.findById(id);
    }

}
