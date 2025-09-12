package com.example.store.auth;

import com.example.store.users.User;
import com.example.store.users.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var id = (Long)authentication.getPrincipal();

        return userRepository.findById(id).orElse(null);
    }

    public LoginResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user  = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return new LoginResponse(accessToken, refreshToken);
    }
    public Jwt refreshAccessToken(String refreshToken){
        Jwt jwt = jwtService.parse(refreshToken);

        if (jwt == null || jwt.isExpired()) {
            throw new BadCredentialsException("Invalid Refresh Token");
        }
        Long userId = jwt.getUserId();
        User user = userRepository.findById(userId).orElseThrow();
        return jwtService.generateAccessToken(user);
    }
}
