package com.example.ExpenseTracker;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        AppUser appUser= userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException(username));

            SimpleGrantedAuthority authority= new 
                    SimpleGrantedAuthority("ROLE_" + appUser.getRole().name());

            return new User(appUser.getUsername(), appUser.getPassword(), 
                Collections.singleton(authority));
    }
}
 