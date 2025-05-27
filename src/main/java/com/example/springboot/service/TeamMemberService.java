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
public class TeamMemberService {

    private static final Logger logger = LoggerFactory.getLogger(TeamMemberService.class);

    @Autowired
    TeamMemberRepository teamMemberRepository;

    public List<Map<String, Object>> getAllMembers(Map<String, String> payload) {
        Long tenantId = Long.parseLong(payload.get("tenantId"));

        List<Map<String, Object>> data = teamMemberRepository.getAllDataByTenantId(tenantId);

        return data;
    }

    public Map<String, Object> addMember(Map<String, String> payload) {
        Map<String, Object> result = new HashMap<>();

        LocalDate now = LocalDate.now();

        TeamMember newTeamMember = new TeamMember();
        newTeamMember.setTenant_id(Long.parseLong(payload.get("tenantId")));
        newTeamMember.setEmail(payload.get("email"));
        newTeamMember.setName(payload.get("name"));
        newTeamMember.setPhone(payload.get("phone"));
        newTeamMember.setRole(payload.get("role"));
        ZonedDateTime zonedDateTime1 = ZonedDateTime.parse(payload.get("joinDate"));
        LocalDate localStartDate = zonedDateTime1.toLocalDate();
        newTeamMember.setJoinDate(localStartDate);
        newTeamMember.setStatus(1);
        teamMemberRepository.save(newTeamMember);

        result.put("result", 1);

        return result;
    }
}