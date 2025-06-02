package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.ProjectService;
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
@RequestMapping("/api/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    ProjectService projectService;

    @PostMapping("/getAllProjects")
    public List<Map<String, Object>> getAllProjects(@RequestBody Map<String, String> payload) {
        return projectService. getAllProjects(payload);
    }

    @PostMapping("/addProject")
    public Map<String, Object> addProject(@RequestBody Map<String, String> payload) {
        return projectService.addProject(payload);
    }

    @PostMapping("/updateProjectStatus")
    public Map<String, Object> updateProjectStatus(@RequestBody Map<String, String> payload) {
        return projectService.updateProjectStatus(payload);
    }

    @PostMapping("/updateProject")
    public Map<String, Object> updateProject(@RequestBody Map<String, String> payload) {
        return projectService.updateProject(payload);
    }

    @PostMapping("/deleteProject")
    public Map<String, Object> deleteProject(@RequestBody Map<String, String> payload) {
        return projectService.deleteProject(payload);
    }
}
