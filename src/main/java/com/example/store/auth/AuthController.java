package com.example.store.auth;

import com.example.store.users.UserDto;
import com.example.store.users.User;
import com.example.store.users.UserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final UserMapper userMapper;
    private final JwtConfiguration jwtConfiguration;
    private final AuthService authService;

    @PostMapping("/login")
    public JwtResponse login(@Valid @RequestBody LoginRequest request,
                                             HttpServletResponse response){

        LoginResponse loginResult = authService.login(request);

        var cookie = new Cookie("refreshToken", loginResult.getRefreshToken().toString());
        cookie.setHttpOnly(true);
        cookie.setPath("/auth/refresh");
        cookie.setMaxAge(jwtConfiguration.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);

        return new JwtResponse(loginResult.getAccessToken().toString());
    }

   @PostMapping("/refresh")
   public ResponseEntity<JwtResponse> refresh(
           @CookieValue(name = "refreshToken") String refreshToken){
       Jwt accessToken = authService.refreshAccessToken(refreshToken);

       return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
   }
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(){
        User currentUser = authService.getCurrentUser();
        if(currentUser == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userMapper.toDto(currentUser));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentials(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
