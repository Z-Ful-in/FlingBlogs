package org.example.flingblogs.controller;


import org.example.flingblogs.model.User;
import org.example.flingblogs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User register(@RequestBody User user){
        // TODO: 加密, 校验
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id){
        return userRepository.findById(id);
    }
}
