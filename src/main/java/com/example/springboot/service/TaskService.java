package com.example.springboot.service;

import com.example.springboot.Config.PasswordUtil;
import com.example.springboot.entity.Project;
import com.example.springboot.entity.Task;
import com.example.springboot.entity.Tenant;
import com.example.springboot.entity.User;
import com.example.springboot.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
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
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    public List<Map<String, Object>> getAllTasks(Map<String, String> payload) {
        Long tenantId = Long.parseLong(payload.get("tenantId"));
        Long userId = Long.parseLong(payload.get("userId"));

        User user = userRepository.getUserById(userId);
        if ("User".equals(user.getRole())) {
            List<Map<String, Object>> data = taskRepository.getDataByUserIdAndTenantId(userId, tenantId);

            return data;
        } else {
            List<Map<String, Object>> data = taskRepository.getAllDataByTenantId(tenantId);

            return data;
        }
    }

    public Map<String, Object> addTask(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        LocalDate now = LocalDate.now();

        Task newTask = new Task();
        newTask.setTenant_id(Long.parseLong(payload.get("tenantId")));
        newTask.setProject_id(Long.parseLong(payload.get("projectId")));
        newTask.setTitle(payload.get("title"));
        newTask.setUser_id(Long.parseLong(payload.get("userId")));
        newTask.setTask_status(payload.get("status"));
        newTask.setStart_date(LocalDate.parse(payload.get("startDate")));
        newTask.setEnd_date(LocalDate.parse(payload.get("dueDate")));
        newTask.setCreated_at(now);
        newTask.setUpdated_at(now);
        newTask.setStatus(1);
        taskRepository.save(newTask);

        result.put("result", 1);

        return result;
    }

    public Map<String, Object> updateTask(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();
        LocalDate now = LocalDate.now();

        Task existingTask = taskRepository.getDataById(Long.parseLong(payload.get("taskId")));
        existingTask.setTask_status(payload.get("status"));
        existingTask.setStart_date(LocalDate.parse(payload.get("startDate")));
        existingTask.setEnd_date(LocalDate.parse(payload.get("dueDate")));
        existingTask.setUpdated_at(now);
        taskRepository.save(existingTask);

        result.put("result", 1);

        return result;
    }

    public Map<String, Object> updateTaskStatus(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();
        LocalDate now = LocalDate.now();

        Task existingTask = taskRepository.getDataById(Long.parseLong(payload.get("taskId")));
        existingTask.setTask_status(payload.get("status"));
        existingTask.setUpdated_at(now);
        taskRepository.save(existingTask);

        result.put("result", 1);

        return result;
    }

    public List<Map<String, Object>> getTasksByProject(Map<String, String> payload) {
        Long tenantId = Long.parseLong(payload.get("tenantId"));
        Long projectId = Long.parseLong(payload.get("projectId"));
        Long userId = Long.parseLong(payload.get("userId"));

        List<Map<String, Object>> result = taskRepository.getDataByProject(tenantId, projectId, userId);

        return result;
    }

    public Map<String, Object> deleteTask(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        Long taskId = Long.parseLong(payload.get("taskId"));
        taskRepository.deleteDataById(taskId);

        result.put("result", 1);
        return result;
    }
}