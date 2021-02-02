package vn.techmaster.blog.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.techmaster.blog.DTO.UserInfo;
import vn.techmaster.blog.controller.request.CommentRequest;
import vn.techmaster.blog.model.Comment;
import vn.techmaster.blog.service.CommentService;
import vn.techmaster.blog.service.IAuthenService;
import vn.techmaster.blog.service.ICommentService;
import vn.techmaster.blog.service.IPostService;
import vn.techmaster.blog.service.PostException;

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

  @GetMapping("/comment/delete/{id}")
  public String deleteComment(@PathVariable("id") Long id, HttpServletRequest request, Model model){
    UserInfo user = authenService.getLoginedUser(request);
    if (user != null) {
      model.addAttribute("user", user);
      commentService.deleteComment(id);
      return Route.REDIRECT_HOME;
    } else {
      return Route.REDIRECT_HOME;
    } 
  }
  
}
