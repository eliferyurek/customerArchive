package com.examples.customerArchive.controller;

import com.examples.customerArchive.model.User;
import com.examples.customerArchive.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<User> GetUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User GetUser(@PathVariable Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public User PostUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
