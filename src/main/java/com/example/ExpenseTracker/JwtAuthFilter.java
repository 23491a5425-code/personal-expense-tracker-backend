package com.example.ExpenseTracker;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
         HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
            String path = request.getServletPath();

if (path.equals("/") || path.equals("/login") || path.equals("/signup")) {
    filterChain.doFilter(request, response);
    return;
}
final String authHeader = request.getHeader("Authorization");
            String username= null;
            String token =null;

            if(authHeader!=null && authHeader.startsWith("Bearer ")){
                token =authHeader.substring(7);
                try{
                    username=jwtUtil.extractUsername(token);
                } catch(Exception e){
                    System.out.println("error extracting username");
                }
            }
            else{
                System.out.println("doesnt begin eith bearer");
            }

            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                var userdetails= userDetailsServiceImpl.loadUserByUsername(username);

                if(jwtUtil.validateToken(token, userdetails.getUsername())){
                    var authenticationToken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
            
    }
}
