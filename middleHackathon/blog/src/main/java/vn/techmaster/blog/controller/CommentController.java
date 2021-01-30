package vn.techmaster.blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.blog.DTO.UserInfo;
import vn.techmaster.blog.controller.request.CommentRequest;
import vn.techmaster.blog.service.BugException;
import vn.techmaster.blog.service.IAuthenService;
import vn.techmaster.blog.service.IBugService;

@Controller
public class CommentController {
  @Autowired
  private IAuthenService authenService;
  @Autowired 
  private IBugService bugService;

  @PostMapping("/comment")
  public String handlePostComment(@ModelAttribute CommentRequest commentRequest, HttpServletRequest request) {
    UserInfo userLogin = authenService.getLoginedUser(request);
    if (userLogin != null) {
      try {
        bugService.addCommentforBug(commentRequest, userLogin.getId());
      } catch (BugException e) {
        e.printStackTrace();
      }

      return "redirect:/bug/" + commentRequest.getBug_id();

    } else {
      return Route.HOME;
    }
  }
  
}
