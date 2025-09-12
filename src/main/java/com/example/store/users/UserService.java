package com.example.store.users;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers(String sort){
        if (!Set.of("name", "email").contains(sort)) {
            sort = "name";
        }
        List<User> users = userRepository.findAll(Sort.by(sort));
        return users.stream().map(userMapper::toDto).toList();
    }
    public UserDto getUser(Long id){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }

    public UserDto registerUser(RegisterUserRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException();
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public UserDto updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userMapper.updateUser(request, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    public void changePassword(ChangePasswordRequest request, Long id){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if(!user.getPassword().equals(request.getOldPassword()))
            throw new AccessDeniedException("Passwords don't match");
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }
}
