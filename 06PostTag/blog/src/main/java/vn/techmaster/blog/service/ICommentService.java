package vn.techmaster.blog.service;

import java.util.Optional;

import vn.techmaster.blog.model.Comment;

public interface ICommentService {

    public Optional<Comment> getCommentById(Long id);
    public void deleteComment(Long id);
    
}
