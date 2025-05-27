package com.example.springboot.service;

import com.example.springboot.Config.PasswordUtil;
import com.example.springboot.entity.Project;
import com.example.springboot.entity.Tenant;
import com.example.springboot.entity.User;
import com.example.springboot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    ProjectRepository projectRepository;

    public List<Map<String, Object>> getAllProjects(Map<String, String> payload) {
        Long tenantId = Long.parseLong(payload.get("tenantId"));

        List<Map<String, Object>> data = projectRepository.getAllDataByTenantId(tenantId);

        return data;
    }

    public Map<String, Object> addProject(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        Project newProject = new Project();
        newProject.setTenant_id(Long.parseLong(payload.get("tenantId")));
        newProject.setName(payload.get("name"));
        newProject.setDescription(payload.get("description"));
        newProject.setProject_status(payload.get("status"));
        ZonedDateTime zonedDateTime1 = ZonedDateTime.parse(payload.get("startDate"));
        LocalDate localStartDate = zonedDateTime1.toLocalDate();
        newProject.setStart_date(localStartDate);
        ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(payload.get("endDate"));
        LocalDate localEndDate = zonedDateTime2.toLocalDate();
        newProject.setEnd_date(localEndDate);
        newProject.setTeam_size(Integer.parseInt(payload.get("teamSize")));
        newProject.setStatus(1);
        projectRepository.save(newProject);

        result.put("result", 1);

        return result;
    }

    public Map<String, Object> updateProject(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        Project existingProject = projectRepository.getDataById(Long.parseLong(payload.get("projectId")));
        existingProject.setProject_status(payload.get("status"));
        projectRepository.save(existingProject);

        result.put("result", 1);

        return result;
    }
}