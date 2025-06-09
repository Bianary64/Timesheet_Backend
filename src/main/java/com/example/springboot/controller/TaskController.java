package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.ProjectService;
import com.example.springboot.service.TaskService;
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
@RequestMapping("/api/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @PostMapping("/getAllTasks")
    public List<Map<String, Object>> getAllTasks(@RequestBody Map<String, String> payload) {
        return taskService.getAllTasks(payload);
    }

    @PostMapping("/addTask")
    public Map<String, Object> addTask(@RequestBody Map<String, String> payload) {
        return taskService.addTask(payload);
    }

    @PostMapping("/updateTask")
    public Map<String, Object> updateTask(@RequestBody Map<String, String> payload) {
        return taskService.updateTask(payload);
    }

    @PostMapping("/updateTaskStatus")
    public Map<String, Object> updateTaskStatus(@RequestBody Map<String, String> payload) {
        return taskService.updateTaskStatus(payload);
    }

    @PostMapping("/getTasksByProject")
    public List<Map<String, Object>> getTasksByProject(@RequestBody Map<String, String> payload) {
        return taskService.getTasksByProject(payload);
    }

    @PostMapping("/deleteTask")
    public Map<String, Object> deleteTask(@RequestBody Map<String, String> payload) {
        return taskService.deleteTask(payload);
    }
}
