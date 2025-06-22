package com.example.springboot.controller;

import com.example.springboot.Config.JwtUtil;
import com.example.springboot.Config.PasswordUtil;
import com.example.springboot.entity.Tenant;
import com.example.springboot.entity.User;
import com.example.springboot.repository.TenantRepository;
import com.example.springboot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    TenantRepository tenantRepository;

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        logger.info("---" + payload.get("email"));
        // Find the user by email
        User existingUser = userRepository.findByEmail(payload.get("email"));

        if (existingUser != null) {
            result.put("result", 0);
            result.put("msg", "The user already exists.");
            result.put("user", null);
        } else {
            Tenant newTenant = new Tenant();
            newTenant.setStatus(1);
            newTenant.setCompany_name("");
            newTenant.setEmail("");
            newTenant.setTax_id("");
            newTenant.setPhone("");
            newTenant.setAddress("");
            newTenant.setWebsite("");
            newTenant.setCity("");
            newTenant.setState("");
            newTenant.setZip_code("");
            newTenant.setFiscal_start_day("");
            newTenant.setCurrency("");
            newTenant.setTimezone("");
            Tenant tenant = tenantRepository.save(newTenant);

            String name = payload.get("name");
            String password = PasswordUtil.hashPassword(payload.get("password"));

            User newUser = new User();
            newUser.setTenant_id(tenant.getId());
            newUser.setName(name);
            newUser.setPassword(password);
            newUser.setEmail(payload.get("email"));
            newUser.setRole(payload.get("role"));
            newUser.setStatus(1);

            User user = userRepository.save(newUser);

            result.put("result", 1);
            result.put("msg", "The user has been created successfully!");
            result.put("user", user);
        }

        return result;
    }

    @PutMapping("/user")
    public ResponseEntity<Map<String, Object>> updateUser(
            @RequestParam("id") Long id,
            @RequestBody Map<String, Object> updateBody) {

        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(id).orElse(null);

        if (user == null || user.getStatus() == 0) {
            result.put("result", 0);
            result.put("msg", "User not found or inactive");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        // Example: update name, email, role if present in body
        if (updateBody.containsKey("name")) {
            user.setName((String) updateBody.get("name"));
        }
        if (updateBody.containsKey("email")) {
            user.setEmail((String) updateBody.get("email"));
        }
        if (updateBody.containsKey("role")) {
            user.setRole((String) updateBody.get("role"));
        }

        userRepository.save(user);

        result.put("result", 1);
        result.put("msg", "User updated successfully");
        result.put("user", user);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/user")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestParam("id") Long id) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(id).orElse(null);

        if (user == null || user.getStatus() == 0) {
            result.put("result", 0);
            result.put("msg", "User not found or already inactive");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }

        user.setStatus(0);
        userRepository.save(user);


        result.put("result", 1);
        result.put("msg", "User has been deactivated (status set to 0)");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        User existingUser = userRepository.findByEmail(payload.get("email"));

        // Check if the user exists
        if (existingUser == null || existingUser.getStatus() == 0) {
            result.put("result", 0);
            result.put("msg", "User not found");
        } else {
            // Validate the password
            if (existingUser.getPassword().equals(PasswordUtil.hashPassword(payload.get("password")))) {
                LocalDateTime now = LocalDateTime.now();
                existingUser.setLast_login(now);
                userRepository.save(existingUser);

                String token = JwtUtil.generateToken(payload.get("email"));
                logger.info(">>>>>>>>>>>> {}", token);
                result.put("result", 1);
                result.put("token", token);
                result.put("userId", existingUser.getId());
                result.put("userEmail", existingUser.getEmail());
                result.put("userName", existingUser.getName());
                result.put("tenantId", existingUser.getTenant_id());
                result.put("userRole", existingUser.getRole());
            } else {
                result.put("result", 0);
                result.put("msg", "Invalid password");
            }
        }

        return result;
    }

    @GetMapping("/email")
    public Map<String, Object> loginByEmail(@RequestParam("email") String email) {
        Map<String, Object> result = new HashMap<>();

        User existingUser = userRepository.findByEmail(email);

        // Check if the user exists and is active
        if (existingUser == null || existingUser.getStatus() == 0) {
            result.put("result", 0);
            result.put("msg", "User not found");
        } else {
            // Update last login timestamp
            LocalDateTime now = LocalDateTime.now();
            existingUser.setLast_login(now);
            userRepository.save(existingUser);

            // Generate JWT token
            String token = JwtUtil.generateToken(email);
            logger.info("Login token generated for email {}: {}", email, token);

            // Prepare response
            result.put("result", 1);
            result.put("token", token);
            result.put("userId", existingUser.getId());
            result.put("userEmail", existingUser.getEmail());
            result.put("userName", existingUser.getName());
            result.put("tenantId", existingUser.getTenant_id());
            result.put("userRole", existingUser.getRole());
        }

        return result;
    }
}
