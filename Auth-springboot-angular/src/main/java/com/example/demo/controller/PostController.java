package com.example.demo.controller;

import com.example.demo.models.Post;
import com.example.demo.models.Tag;
import com.example.demo.models.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/post/")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/allPost/{userId}", produces = "application/json")
    public ResponseEntity<List<Post>> getAllPostByUser(@PathVariable Long userId){
        List<Post> posts =  postRepository.findByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping(value = "/getTag", produces = "application/json")
    public ResponseEntity<List<Tag>> getAllTag(){
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @PostMapping(value = "/create", produces={"application/json"})
    public ResponseEntity<?> createPost(@RequestBody Post post, Principal principal){
        User user = userRepository.findByUsername(principal.getName()).get();
        post.setUser(user);
        return ResponseEntity.ok(postRepository.save(post));
    }

    @GetMapping(value = "/detail/{id}", produces = "application/json")
    public ResponseEntity<Post> getPostByID(@PathVariable Long id){
        Post post = postRepository.findById(id).get();
        return ResponseEntity.ok(post);
    }

    @PutMapping(value = "/edit/{id}", produces = "application/json")
    public ResponseEntity<Post> editPostById(@PathVariable Long id, @RequestBody Post updatePost){
        Post post = postRepository.findById(id).get();
        if(post != null){
            post.setTitle(updatePost.getTitle());
            post.setContent(updatePost.getContent());
            post.setTags(updatePost.getTags());
            return ResponseEntity.ok(postRepository.save(post));
        }else{
            return ResponseEntity.ok(postRepository.save(post));
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<?> deletePostById(@PathVariable Long id){
        postRepository.deleteById(id);
        return ResponseEntity.ok(200);
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<Post>> getAll(){
        return ResponseEntity.ok(postRepository.findAll());
    }

}
