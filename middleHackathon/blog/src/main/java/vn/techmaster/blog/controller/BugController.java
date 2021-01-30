package vn.techmaster.blog.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.techmaster.blog.DTO.UserInfo;
import vn.techmaster.blog.controller.request.BugRequest;
import vn.techmaster.blog.controller.request.CommentRequest;
import vn.techmaster.blog.model.Bug;
import vn.techmaster.blog.model.Comment;
import vn.techmaster.blog.service.BugException;
import vn.techmaster.blog.service.IAuthenService;
import vn.techmaster.blog.service.IBugService;


@Controller
public class BugController {
    @Autowired 
    private IAuthenService authenService;

    @Autowired
    private IBugService bugService;

     //Save the uploaded file to this folder
     private static String UPLOADED_FOLDER = "Desktop";


    @GetMapping("/bug")  //Show form để tạo mới Post
    public String createBug(Model model, HttpServletRequest request) {
      UserInfo user = authenService.getLoginedUser(request);
      if (user != null) {
        BugRequest bugRequest= new BugRequest();
        bugRequest.setUser_id(user.getId());
        model.addAttribute("bug", bugRequest);
        model.addAttribute("user", user);

        return "bug";  
      } else {
        return Route.REDIRECT_HOME;
      }
    }


    @PostMapping(value = "/bug")
  public String createBug(@Valid @ModelAttribute("bug") BugRequest bugRequest,
  @RequestParam("file") MultipartFile file,@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, RedirectAttributes redirectAttributes,
  ModelMap modelMap, BindingResult bindingResult, Model model, HttpServletRequest request) throws IOException {

    UserInfo user = authenService.getLoginedUser(request);
    modelMap.addAttribute("bugRequest",bugRequest);

    if (file.isEmpty()) {
      return "redirect:/bugs";
    }

  
    if (user != null && user.getId() == bugRequest.getUser_id()) {
      try {
        if (bugRequest.getId() == null) {
            bugRequest.setPhoto(file.getBytes());
            bugRequest.setNamefile(file.getOriginalFilename());
            bugRequest.setNamefile1(file1.getOriginalFilename());
            bugRequest.setNamefile2(file2.getOriginalFilename());
            bugService.createNewBug(bugRequest); //Create
        } else {
            bugRequest.setPhoto(file.getBytes());
            bugRequest.setNamefile1(file1.getOriginalFilename());
            bugRequest.setNamefile2(file2.getOriginalFilename());
            bugRequest.setNamefile(file.getOriginalFilename());
            bugService.updateBug(bugRequest);  //Edit
        }
      } catch (BugException pe) {
        return Route.REDIRECT_HOME;
      }
      
      return "redirect:/bugs";
    } else {
      return Route.REDIRECT_HOME;
    }
    }


    @GetMapping("/bugs")  //Liệt kê các bug
    public String getAllBug(Model model, HttpServletRequest request) { 
    UserInfo user = authenService.getLoginedUser(request);
    if (user != null) {
      model.addAttribute("user", user);
      List<Bug> bugs = bugService.findAll();
      model.addAttribute("bugs", bugs);
      return "allBug";
    } else {
      return Route.REDIRECT_HOME;
    } 
  }

  @GetMapping("/bug/{id}")
  public String showBugAndComment(@PathVariable("id") long id,
  Model model, HttpServletRequest request){
    Optional<Bug> optionalBug = bugService.findById(id);
    if(optionalBug.isPresent()){
      Bug bug = optionalBug.get();
      model.addAttribute("bug", bug);
      List<Comment> comments = bug.getComments(); 
      Collections.reverse(comments); 
      model.addAttribute("comments", comments);

      UserInfo user = authenService.getLoginedUser(request); //Login user

      if (user != null) { //Nếu user login và xem post, cần bổ xung chức năng comment
        model.addAttribute("user", user); //Người dùng đang login
        model.addAttribute("commentRequest", new CommentRequest(bug.getId()));
      } else {
        model.addAttribute("commentRequest", new CommentRequest());
      }
      return "bug_comment";
    } else{
      return Route.REDIRECT_HOME;
    }
    
  }


}
