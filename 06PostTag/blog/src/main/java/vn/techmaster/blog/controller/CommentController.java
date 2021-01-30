package vn.techmaster.blog.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.blog.DTO.UserInfo;
import vn.techmaster.blog.controller.request.CommentRequest;
import vn.techmaster.blog.model.Comment;
import vn.techmaster.blog.service.IAuthenService;
import vn.techmaster.blog.service.ICommentService;
import vn.techmaster.blog.service.IPostService;
import vn.techmaster.blog.service.PostException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CommentController {
  @Autowired
  private IAuthenService authenService;
  @Autowired
  private IPostService postService;
  @Autowired
  private ICommentService commentService;

  @PostMapping("/comment")
  public String handlePostComment(@ModelAttribute CommentRequest commentRequest, HttpServletRequest request) {
    UserInfo userLogin = authenService.getLoginedUser(request);
    if (userLogin != null) {
      try {
        postService.addComment(commentRequest, userLogin.getId());
      } catch (PostException e) {
        e.printStackTrace();
      }

      return "redirect:/post/" + commentRequest.getPost_id();

    } else {
      return Route.HOME;
    }
  }

  @GetMapping(value="/delete/comment/{id}")
  public String deleteCommentByID(@PathVariable("id") long id,HttpServletRequest request, Model model) {
    UserInfo userLogin = authenService.getLoginedUser(request);
    Optional<Comment> comment = commentService.findCommentByID(id);
    if(comment.isPresent()){
        if(userLogin.getId() == comment.get().getUser().getId() ){
          commentService.deleteCommentByID(id);
          return "redirect:/post/" + id; 
      }
    }else{
      return "redirect:/post/" + id;
    }
    return "redirect:/post/" + id;
  }
  
  
}
