package com.example.demo.service;

import com.example.demo.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    public List<Post> getAllPost();
    public void save(Post post);

    public Post edit(long id);

    public void deletePost(long id);


}
