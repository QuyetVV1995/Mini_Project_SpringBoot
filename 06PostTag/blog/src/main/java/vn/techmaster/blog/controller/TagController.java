package vn.techmaster.blog.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.techmaster.blog.model.Tag;
import vn.techmaster.blog.service.TagService;

@Controller
public class TagController {
    
    @Autowired
    private TagService tagService;

    @GetMapping("/tag/{id}")
    public String listPostByTag(@PathVariable("id") long id, Model model){

        Optional<Tag> tag = tagService.findById(id);
        if(tag.isPresent()){
            // Giá trị nhận về là Optional Tag nên giá trị truyền vào model phải là tag.get()
            
            model.addAttribute("tag_post", tag.get());
       
            return "searchPostByTag";
        }else{
            return Route.REDIRECT_HOME;
        }
       
        
    }
}
