package com.example.user.service.service;

import com.example.user.service.exception.ResourceNotFoundException;
import com.example.user.service.model.User;
import com.example.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    // user operations

    private final UserRepository userRepository;

    //create

    public User saveUser(User user) {
        // generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    // get all user

    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    // get single user of given userId

    public User getUserById(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id si not found on server !! :" + userId));
    }


}
