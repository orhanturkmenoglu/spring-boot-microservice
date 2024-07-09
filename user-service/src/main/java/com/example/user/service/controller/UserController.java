package com.example.user.service.controller;

import com.example.user.service.model.User;
import com.example.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // create

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User saveUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
    }

    // single user get
    @GetMapping("/all")
    public ResponseEntity<List<User>> getUserAll() {
        List<User> getUserAll = userService.getAllUser();
        return ResponseEntity.ok(getUserAll);
    }

    // all user get
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        User getUserById = userService.getUserById(userId);
        return ResponseEntity.ok(getUserById);
    }
}
