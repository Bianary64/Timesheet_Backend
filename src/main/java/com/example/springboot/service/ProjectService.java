package com.example.springboot.service;

import com.example.springboot.entity.Project;
import com.example.springboot.entity.User;
import com.example.springboot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    public List<Map<String, Object>> getAllProjects(Map<String, String> payload) {
        String tenantId = payload.get("tenantId");
        Long userId = Long.parseLong(payload.get("userId"));

        User user = userRepository.getUserById(userId);
        if ("User".equals(user.getRole())) {
            List<Map<String, Object>> data = projectRepository.getDataByUserIdAndTenantId(userId, tenantId);

            return data;
        } else {
            List<Map<String, Object>> data = projectRepository.getAllDataByTenantId(tenantId);

            return data;
        }
    }

    public Map<String, Object> addProject(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        Project newProject = new Project();
        newProject.setTenant_id(payload.get("tenantId"));
        newProject.setName(payload.get("name"));
        newProject.setDescription(payload.get("description"));
        newProject.setProject_status(payload.get("status"));
        newProject.setStart_date(LocalDate.parse(payload.get("startDate")));
        newProject.setEnd_date(LocalDate.parse(payload.get("endDate")));
        newProject.setTeam_size(Integer.parseInt(payload.get("teamSize")));
        newProject.setUser_id(Long.parseLong(payload.get("userId")));
        newProject.setStatus(1);
        projectRepository.save(newProject);

        result.put("result", 1);

        return result;
    }

    public Map<String, Object> updateProjectStatus(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        Project existingProject = projectRepository.getDataById(Long.parseLong(payload.get("projectId")));
        existingProject.setProject_status(payload.get("status"));
        existingProject.setUser_id(Long.parseLong(payload.get("userId")));
        projectRepository.save(existingProject);

        result.put("result", 1);

        return result;
    }

    public Map<String, Object> updateProject(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        Project existingProject = projectRepository.getDataById(Long.parseLong(payload.get("projectId")));
        existingProject.setTenant_id(payload.get("tenantId"));
        existingProject.setName(payload.get("name"));
        existingProject.setDescription(payload.get("description"));
        existingProject.setProject_status(payload.get("status"));
        existingProject.setStart_date(LocalDate.parse(payload.get("startDate")));
        existingProject.setEnd_date(LocalDate.parse(payload.get("endDate")));
        existingProject.setTeam_size(Integer.parseInt(payload.get("teamSize")));
        existingProject.setUser_id(Long.parseLong(payload.get("userId")));
        existingProject.setStatus(1);
        projectRepository.save(existingProject);

        result.put("result", 1);

        return result;
    }

    public Map<String, Object> deleteProject(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        Long projectId = Long.parseLong(payload.get("projectId"));
        projectRepository.deleteDataById(projectId);

        return result;
    }
}