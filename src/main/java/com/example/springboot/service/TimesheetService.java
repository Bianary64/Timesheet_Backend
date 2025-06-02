package com.example.springboot.service;

import com.example.springboot.Config.PasswordUtil;
import com.example.springboot.entity.*;
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
public class TimesheetService {

    private static final Logger logger = LoggerFactory.getLogger(TimesheetService.class);

    @Autowired
    TimesheetRepository timesheetRepository;

    @Autowired
    UserRepository userRepository;

    public List<Map<String, Object>> getTimeEntries(Map<String, String> payload) {
        Long tenantId = Long.parseLong(payload.get("tenantId"));
        Long userId = Long.parseLong(payload.get("userId"));

        User user = userRepository.getUserById(userId);
        if ("User".equals(user.getRole())) {
            List<Map<String, Object>> data = timesheetRepository.getDataByUserIdAndTenantId(userId, tenantId);

            return data;
        } else {
            List<Map<String, Object>> data = timesheetRepository.getAllDataByTenantId(tenantId);

            return data;
        }
    }

    public Map<String, Object> getTimeEntriesOverview(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        Long tenantId = Long.parseLong(payload.get("tenantId"));
        LocalDate startDate = LocalDate.parse(payload.get("startDate"));
        LocalDate endDate = LocalDate.parse(payload.get("endDate"));

        List<Map<String, Object>> entries = timesheetRepository.getEntries(tenantId, startDate, endDate);
        List<Map<String, Object>> projects = timesheetRepository.getProjects(tenantId, startDate, endDate);
        List<Map<String, Object>> users = timesheetRepository.getUsers(tenantId, startDate, endDate);

        result.put("entries", entries);
        result.put("projects", projects);
        result.put("users", users);

        return result;
    }

    public Map<String, Object> addTimeEntry(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        LocalDate now = LocalDate.now();

        Timesheet newTimesheet = new Timesheet();
        newTimesheet.setTenant_id(Long.parseLong(payload.get("tenantId")));
        newTimesheet.setUser_id(Long.parseLong(payload.get("userId")));
        newTimesheet.setProject_id(Long.parseLong(payload.get("projectId")));
        newTimesheet.setTask_id(Long.parseLong(payload.get("taskId")));
        newTimesheet.setDescription(payload.get("description"));
        newTimesheet.setHours(Integer.parseInt(payload.get("hours")));
//        ZonedDateTime zonedDateTime1 = ZonedDateTime.parse(payload.get("date"));
//        LocalDate localStartDate = zonedDateTime1.toLocalDate();
        newTimesheet.setDate(LocalDate.parse(payload.get("date")));
        newTimesheet.setStatus(1);
        timesheetRepository.save(newTimesheet);

        result.put("result", 1);

        return result;
    }

    public Map<String, Object> deleteTimeEntry(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        Long timeEntryId = Long.parseLong(payload.get("timeEntryId"));
        timesheetRepository.deleteDataById(timeEntryId);

        result.put("result", 1);

        return result;
    }
}