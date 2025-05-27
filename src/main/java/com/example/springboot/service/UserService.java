package com.example.springboot.service;

import com.example.springboot.Config.PasswordUtil;
import com.example.springboot.entity.Tenant;
import com.example.springboot.entity.User;
import com.example.springboot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(Map<String, String> payload) {
        List<User> users = userRepository.getAllUsers(Long.parseLong(payload.get("tenantId")));

        return users;
    }

    public Map<String, Object> addNewUser(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        // Find the user by email
        User existingUser = userRepository.findByEmail(payload.get("email"));

        if (existingUser != null) {
            result.put("result", 0);
            result.put("msg", "The user already exists.");
        } else {
            String password = PasswordUtil.hashPassword(payload.get("password"));

            User newUser = new User();
            newUser.setTenant_id(Long.parseLong(payload.get("tenantId")));
            newUser.setName(payload.get("name"));
            newUser.setPassword(password);
            newUser.setEmail(payload.get("email"));
            newUser.setRole(payload.get("role"));
            newUser.setStatus(1);

            User user = userRepository.save(newUser);

            result.put("result", 1);
            result.put("msg", "The user has been created successfully!");
        }

        return result;
    }

    public Map<String, Object> editUser(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        // Find the user by email
        User user = userRepository.checkUserByEmail(payload.get("email"), Long.parseLong(payload.get("userId")));

        if (user != null) {
            result.put("result", 0);
            result.put("msg", "The user already exists.");
        } else {
            User existingUser = userRepository.getUserById(Long.parseLong(payload.get("userId")));

            if (!"".equals(payload.get("password"))) {
                String password = PasswordUtil.hashPassword(payload.get("password"));
                existingUser.setPassword(password) ;
            }

            existingUser.setTenant_id(Long.parseLong(payload.get("tenantId")));
            existingUser.setName(payload.get("name"));
            existingUser.setEmail(payload.get("email"));
            existingUser.setRole(payload.get("role"));
            existingUser.setStatus(1);

            userRepository.save(existingUser);

            result.put("result", 1);
            result.put("msg", "The user has been updated successfully!");
        }
        return result;
    }

    public Map<String, Object> deleteUser(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        try {
            Long id = Long.parseLong(payload.get("userId"));
            Long tenantId = Long.parseLong(payload.get("tenantId"));
            userRepository.deleteUserById(id, tenantId);

            result.put("result", 1);
        } catch (Exception e) {
            result.put("result", 0);
        }

        return result;
    }
}