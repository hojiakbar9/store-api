package com.example.store.users;

import com.example.store.common.ErrorDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(
            @RequestParam(required = false, defaultValue = "",name = "sort")
            String sort) {
        return userService.getAllUsers(sort);
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder builder){
        UserDto userDto = userService.registerUser(request);
        UriComponents uri = builder.path("users/{id}").buildAndExpand(userDto.getId());
        return ResponseEntity.created(uri.toUri()).body(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request){
       return userService.updateUser(id, request);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/change-password")
    public void changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request){
        userService.changePassword(request, id);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("User not found"));
    }
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorDto> handleDuplicateUserException(){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto("User already exists"));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException(Exception ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(ex.getMessage()));
    }
}