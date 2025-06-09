package com.example.springboot.controller;

import com.example.springboot.entity.User;
import com.example.springboot.service.ProjectService;
import com.example.springboot.service.TaskService;
import com.example.springboot.service.TimesheetService;
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
@RequestMapping("/api/timesheet")
public class TimesheetController {

    private static final Logger logger = LoggerFactory.getLogger(TimesheetController.class);

    @Autowired
    TimesheetService timesheetService;

    @PostMapping("/getTimeEntries")
    public List<Map<String, Object>> getTimeEntries(@RequestBody Map<String, String> payload) {
        return timesheetService.getTimeEntries(payload);
    }

    @PostMapping("/getTimeEntriesOverview")
    public Map<String, Object> getTimeEntriesOverview(@RequestBody Map<String, String> payload) {
        return timesheetService.getTimeEntriesOverview(payload);
    }

    @PostMapping("getTimeEntriesReview")
    public Map<String, Object> getTimeEntriesReview(@RequestBody Map<String, String> payload) {
        return timesheetService.getTimeEntriesReview(payload);
    }

    @PostMapping("/addTimeEntry")
    public Map<String, Object> addTimeEntry(@RequestBody Map<String, String> payload) {
        return timesheetService.addTimeEntry(payload);
    }

    @PostMapping("/deleteTimeEntry")
    public Map<String, Object> deleteTimeEntry(@RequestBody Map<String, String> payload) {
        return timesheetService.deleteTimeEntry(payload);
    }
}
