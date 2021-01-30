package vn.techmaster.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.techmaster.blog.model.Comment;
import vn.techmaster.blog.repository.CommentRepository;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Optional<Comment> findCommentByID(Long id) {
        
        return commentRepository.findById(id);
    }

    @Override
    public void deleteCommentByID(Long id) {
       commentRepository.deleteById(id);

    }
    
}
