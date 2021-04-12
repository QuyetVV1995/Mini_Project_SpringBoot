package com.example.demo.controller;

import com.example.demo.models.Comment;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CommentController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;


    @PostMapping(value = "/new-comment/{postId}/{userId}", produces={"application/json"})
    public ResponseEntity<Post> createComment(@RequestBody Comment comment, @PathVariable Long postId, @PathVariable Long userId){
        Post post = postRepository.findById(postId).get();
        User user = userRepository.findById(userId).get();

        if(user != null){
            comment.setUser(user);
            commentRepository.saveAndFlush(comment);
            if(post != null){
                post.setComments(comment);
                postRepository.save(post);
            }else {
                System.out.println("Post not found");
            }
        }else{
            System.out.println("User not found");
        }
        return ResponseEntity.ok(post);
    }

    @DeleteMapping(value = "comment/delete/{postId}/{commentId}", produces = "application/json")
    public ResponseEntity<Post> deleteCommentByID(@PathVariable Long postId, @PathVariable Long commentId){
        Post post = postRepository.findById(postId).get();
        Comment comment = commentRepository.findById(commentId).get();
        if(post != null){
           post.removeComment(comment);
           commentRepository.deleteById(commentId);
        }
        return ResponseEntity.ok(post);
    }
}
