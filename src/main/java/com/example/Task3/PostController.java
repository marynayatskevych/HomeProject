package com.example.Task3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task3/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @PostMapping("/{userId}")
    public Post createPost(@PathVariable Long userId, @RequestBody Post post) {
        return userRepository.findById(userId)
                .map(user -> {
                    post.setAuthor(user);
                    return postRepository.save(post);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.setLikes(post.getLikes() + 1);
                    postRepository.save(post);
                    return "Post liked!";
                })
                .orElse("Post not found");
    }
}

