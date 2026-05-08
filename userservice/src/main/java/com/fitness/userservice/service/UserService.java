package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponse register(@Valid RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exist");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));

        return modelMapper.map(user, UserResponse.class);
    }

    public Boolean existByUserId(String userId) {
        log.info("Calling User Validation API for userId: {}", userId);
        return userRepository.existsById(userId);
    }
}
