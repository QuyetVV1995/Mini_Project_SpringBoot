package com.example.demo.controller;

import com.example.demo.models.Post;
import com.example.demo.models.Tag;
import com.example.demo.models.User;
import com.example.demo.models.uploadForm;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.TagRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.services.PostService;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.security.services.UserDetailsServiceImpl;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/post/")
public class PostController {
    private static String UPLOAD_DIR = "image";

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;

    @GetMapping("/file/{filename}")
    public ResponseEntity<?> download(@PathVariable String filename) throws NotFoundException {
        File file = new File(UPLOAD_DIR + "/" + filename);
        if (!file.exists()) {
            throw new NotFoundException("File not found");
        }

        UrlResource resource;
        try {
            resource = new UrlResource(file.toURI());
        } catch (MalformedURLException e) {
            throw new NotFoundException("File not found");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@ModelAttribute("uploadForm") uploadForm form, Principal principal) {
        // Create folder to save file if not exist
        // TODO: edit UPLOAD_DIR = principal.getName() + "image"
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        MultipartFile fileData = form.getFileData();
        String name = fileData.getOriginalFilename();
        if (name != null && name.length() > 0) {
            try {
                // Create file
                File serverFile = new File(UPLOAD_DIR + "/" + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(fileData.getBytes());
                stream.close();
                return ResponseEntity.ok("/file/"+name);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when uploading");
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
    }


    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<Page<Post>> getAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "limit", defaultValue = "10") int limit){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("id"));
        Page<Post> pageResult = postRepository.findAll(pageRequest);
        return ResponseEntity.ok(pageResult);
    }

    @GetMapping(value = "/allPost/{userId}", produces = "application/json")
    public ResponseEntity<List<Post>> getAllPostByUser(@PathVariable Long userId){
        List<Post> posts =  postRepository.findByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping(value = "/getTag", produces = "application/json")
    public ResponseEntity<List<Tag>> getAllTag(){
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @PostMapping(value = "/create", produces={"application/json"})
    public ResponseEntity<?> createPost(@RequestBody Post post, Principal principal){
        User user = userRepository.findByUsername(principal.getName()).get();
        post.setUser(user);
        return ResponseEntity.ok(postRepository.save(post));
    }

    @GetMapping(value = "/detail/{id}", produces = "application/json")
    public ResponseEntity<Post> getPostByID(@PathVariable Long id){
        Post post = postRepository.findById(id).get();
        return ResponseEntity.ok(post);
    }

    @PutMapping(value = "/edit/{id}", produces = "application/json")
    public ResponseEntity<Post> editPostById(@PathVariable Long id, @RequestBody Post updatePost){
        Post post = postRepository.findById(id).get();
        if(post != null){
            post.setTitle(updatePost.getTitle());
            post.setContent(updatePost.getContent());
            post.setTags(updatePost.getTags());
            return ResponseEntity.ok(postRepository.save(post));
        }else{
            return ResponseEntity.ok(postRepository.save(post));
        }
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<?> deletePostById(@PathVariable Long id){
        postRepository.deleteById(id);
        return ResponseEntity.ok(200);
    }

}
