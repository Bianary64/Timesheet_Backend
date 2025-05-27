package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.ProjectService;
import com.example.springboot.service.TaskService;
import com.example.springboot.service.TeamMemberService;
import com.example.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/team")
public class TeamMemberController {

    private static final Logger logger = LoggerFactory.getLogger(TeamMemberController.class);

    @Autowired
    TeamMemberService teamMemberService;

    @PostMapping("/getAllMembers")
    public List<Map<String, Object>> getAllMembers(@RequestBody Map<String, String> payload) {
        return teamMemberService.getAllMembers(payload);
    }

    @PostMapping("/addMember")
    public Map<String, Object> addMember(@RequestBody Map<String, String> payload) {
        return teamMemberService.addMember(payload);
    }
}
