package vn.techmaster.blog.service;

import java.util.Optional;

import vn.techmaster.blog.model.Comment;

public interface ICommentService {
    Optional<Comment> findCommentByID(Long id);
    void deleteCommentByID(Long id);
}
