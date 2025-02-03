package com.example.Task3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task3/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/{userId}/follow/{followId}")
    public String followUser(@PathVariable Long userId, @PathVariable Long followId) {
        return userRepository.findById(userId)
                .flatMap(user -> userRepository.findById(followId)
                        .map(follow -> {
                            user.getFollowing().add(follow);
                            follow.getFollowers().add(user);
                            userRepository.save(user);
                            userRepository.save(follow);
                            return "User " + userId + " followed User " + followId;
                        }))
                .orElse("One of the users not found");
    }
}

