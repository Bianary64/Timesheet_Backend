package com.example.springboot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenant_id;

    private Long user_id;

    private String name;

    private String description;

    private String project_status;

    private LocalDate start_date;

    private LocalDate end_date;

    private Integer team_size;

    private Integer status;

    public Project() {
    }

    public Project(String tenant_id, Long user_id, String name, String description, String project_status, LocalDate start_date, LocalDate end_date,
                   Integer team_size, Integer status) {
        this.tenant_id = tenant_id;
        this.user_id = user_id;
        this.name = name;
        this.description = description;
        this.project_status = project_status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.team_size = team_size;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProject_status() {
        return project_status;
    }

    public void setProject_status(String project_status) {
        this.project_status = project_status;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public Integer getTeam_size() {
        return team_size;
    }

    public void setTeam_size(Integer team_size) {
        this.team_size = team_size;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
