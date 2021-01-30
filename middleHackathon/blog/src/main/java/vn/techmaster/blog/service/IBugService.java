package vn.techmaster.blog.service;

import java.util.List;
import java.util.Optional;


import vn.techmaster.blog.controller.request.BugRequest;
import vn.techmaster.blog.controller.request.CommentRequest;
import vn.techmaster.blog.model.Bug;

public interface IBugService {
    public List<Bug> findAll();
    public void createNewBug(BugRequest bugRequest) throws BugException;
    public void updateBug(BugRequest bugRequest) throws BugException;
    public Optional<Bug> findById(Long id);
    public void addCommentforBug(CommentRequest commentRequest, long user_id) throws BugException;

}
