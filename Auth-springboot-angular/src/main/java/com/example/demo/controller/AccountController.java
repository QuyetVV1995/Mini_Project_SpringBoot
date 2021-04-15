package com.example.demo.controller;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/account/")
public class AccountController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<?> createAccount(@RequestBody User user){
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping(value = "/get-roles", produces = "application/json")
    public ResponseEntity<List<Role>> getRole(){
        return ResponseEntity.ok(roleRepository.findAll());
    }
}
