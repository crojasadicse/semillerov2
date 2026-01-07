package com.semillero.semillero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.semillero.semillero.jwt.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    
    private AuthenticationManager authenticationManager;    

    private JwtUtil jwtUtil;
    
    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {


        System.out.println("========== LOGIN ==========");
        System.out.println("Username: " + username  + " - Password: " + password);


        try {
            Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            List<String> roles = auth.getAuthorities().stream()
                .map(r -> r.getAuthority())
                .filter(role -> !"FACTOR_PASSWORD".equals(role))
                .toList();

            return jwtUtil.generateToken(username, roles);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            //throw new ValidatedRequestException("Credenciales inválidas");
            return "Credenciales inválidas";
        }        

    }

}
