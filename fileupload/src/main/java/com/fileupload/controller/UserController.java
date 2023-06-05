package com.fileupload.controller;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fileupload.entities.User;
import com.fileupload.repositories.UserRepository;
import com.fileupload.services.UserService;

@RestController
@RequestMapping("/api/")
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        if(users != null){
            return ResponseEntity.ok().body(users);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("users")
    public void createUser(@RequestParam("name") String name,
                           @RequestParam("profilePhoto") MultipartFile profilePhoto,
                           @RequestParam("resume") MultipartFile resume) throws IOException{
        User user = new User();
        user.setName(name);
        user.setResume(resume.getBytes());
        user.setProfilePhoto(profilePhoto.getBytes());
        userService.createUser(user);
    }

    @GetMapping("users/{id}/profile-photo")
    public ResponseEntity<byte[]> getEmployeeProfilePhoto(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user != null && user.getProfilePhoto() != null) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(user.getProfilePhoto());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("users/{id}/resume")
    public ResponseEntity<byte[]> getEmployeeResume(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user != null && user.getResume() != null) {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(user.getResume());
        }

        return ResponseEntity.notFound().build();
    }
}
