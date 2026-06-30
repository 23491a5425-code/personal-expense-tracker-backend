package com.example.ExpenseTracker;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService=authService;
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@RequestBody AppUserDTO appUserDTO)
    {
        AuthResponseDTO authResponseDTO = authService.registerUser(appUserDTO);
        if("success".equals(authResponseDTO.getMessage()))
        {
            return ResponseEntity.ok(authResponseDTO);
        }else{
            return ResponseEntity.badRequest().body(authResponseDTO);
        }
        
    }

     @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO authDTO)
    {
        AuthResponseDTO authResponseDTO = authService.loginUser(authDTO);
        if("success".equals(authResponseDTO.getMessage()))
        {
            return ResponseEntity.ok(authResponseDTO);
        }else{
            return ResponseEntity.badRequest().body(authResponseDTO);
        }
        
    }
}
