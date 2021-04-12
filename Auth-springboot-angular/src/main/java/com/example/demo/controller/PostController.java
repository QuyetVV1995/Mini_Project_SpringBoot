package com.example.demo.controller;

import com.example.demo.models.Post;
import com.example.demo.models.Tag;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;

    @GetMapping(value = "/allPostOfUser/{userId}", produces = "application/json")
    public ResponseEntity<List<Post>> getAllPostByUser(@PathVariable Long userId){
        List<Post> posts =  postRepository.findByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping(value = "/getAllTag", produces = "application/json")
    public ResponseEntity<List<Tag>> getAllTag(){
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @PostMapping(value = "/create-post", produces={"application/json"})
    public ResponseEntity<?> createPost(@RequestBody Post post){
        System.out.println(post.getTags());
        return null;
    }
}
