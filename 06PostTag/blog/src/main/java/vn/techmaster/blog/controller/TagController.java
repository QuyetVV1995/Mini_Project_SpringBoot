package vn.techmaster.blog.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.techmaster.blog.DTO.UserInfo;
import vn.techmaster.blog.model.Tag;
import vn.techmaster.blog.service.IAuthenService;
import vn.techmaster.blog.service.ITagService;

@Controller
public class TagController {

    @Autowired
    private IAuthenService authenService;

    @Autowired
    private ITagService tagService;


    @GetMapping("/tag/{id}")
    public String getTagbyID(@PathVariable("id") long id, Model model, HttpServletRequest request ){
        Optional<Tag> optionTag =  tagService.getTagByID(id);
        if(optionTag.isPresent()){
            Tag tag = optionTag.get();
            model.addAttribute("tag", tag);
            
            
            UserInfo user = authenService.getLoginedUser(request); //Login user
            model.addAttribute("user", user);
            return "tag_post";
        }else{
            return Route.REDIRECT_HOME;
        }
  
    }
    
}
