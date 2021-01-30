package vn.techmaster.blog.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import vn.techmaster.blog.DTO.BugMapper;
import vn.techmaster.blog.controller.request.BugRequest;
import vn.techmaster.blog.controller.request.CommentRequest;
import vn.techmaster.blog.model.Bug;
import vn.techmaster.blog.model.Comment;
import vn.techmaster.blog.model.User;
import vn.techmaster.blog.model.Bug.status;
import vn.techmaster.blog.repository.BugRepository;
import vn.techmaster.blog.repository.UserRepository;

@Service
public class BugService implements IBugService {

    @Autowired
    private BugRepository bugRepository;

    @Autowired
    UserRepository userRepo;

    @Override
    public List<Bug> findAll() {
        return bugRepository.findAll();
    }

    @Override
    public void createNewBug(BugRequest bugRequest) throws BugException {
        Optional<User> optionalUser = userRepo.findById(bugRequest.getUser_id());
        if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        Bug bug = BugMapper.INSTANCE.bugRequestToBug(bugRequest);
        bug.setStatus(status.NOT_FIX);
         
        bugRepository.saveAndFlush(bug);
        user.addBug(bug);
        userRepo.flush();
        } else {
        throw new BugException("Cannot add bug");
        }

    }

    @Override
    public void updateBug(BugRequest bugRequest) throws BugException {
        Optional<Bug> optionalBug = bugRepository.findById(bugRequest.getId());
        if (optionalBug.isPresent()) {
          Bug bug = optionalBug.get();
          bug.setTitle(bugRequest.getTitle());
          bug.setContent(bugRequest.getContent());
          bug.setStatus(status.NOT_FIX);
         
          bugRepository.saveAndFlush(bug);
        } else {
          createNewBug(bugRequest);
        }

    }

    @Override
    public Optional<Bug> findById(Long id) {
      return bugRepository.findById(id);
    }

    @Override
    public void addCommentforBug(CommentRequest commentRequest, long user_id) throws BugException {
      Optional<Bug> optionalBug = bugRepository.findById(commentRequest.getBug_id());
      Optional<User> oUser = userRepo.findById(user_id);
      if (optionalBug.isPresent() && oUser.isPresent()) {
        Bug bug = optionalBug.get();
        Comment comment = new Comment(commentRequest.getContent());
        comment.setUser(oUser.get());
        bug.addComment(comment);
        bugRepository.flush();
      } else {
        throw new BugException("Bug or User is missing");
      }
    }
    
}
