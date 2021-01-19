package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post edit(long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }
}
