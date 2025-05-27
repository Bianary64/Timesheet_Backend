package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserService userService;

    @PostMapping("/getAllUsers")
    public List<User> getAllUsers(@RequestBody Map<String, String> payload) {
        return userService.getAllUsers(payload);
    }

    @PostMapping("/addNewUser")
    public Map<String, Object> addNewUser(@RequestBody Map<String, String> payload) {
        return userService.addNewUser(payload);
    }

    @PostMapping("/editUser")
    public Map<String, Object> editUser(@RequestBody Map<String, String> payload) {
        return userService.editUser(payload);
    }

    @PostMapping("/deleteUser")
    public Map<String, Object> deleteUser(@RequestBody Map<String, String> payload) {
        return userService.deleteUser(payload);
    }
}
