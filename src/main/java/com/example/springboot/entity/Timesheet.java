package com.example.springboot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "timesheet")
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tenant_id;

    private Long project_id;

    private Long task_id;

    private Long user_id;

    private LocalDate date;

    private Integer hours;

    private String description;

    private Integer status;

    public Timesheet() {
    }

    public Timesheet(Long tenant_id, Long project_id, Long task_id, Long user_id, LocalDate date, Integer hours, String description,
                     Integer status) {
        this.tenant_id = tenant_id;
        this.project_id = project_id;
        this.task_id = task_id;
        this.date = date;
        this.user_id = user_id;
        this.hours = hours;
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(Long tenant_id) {
        this.tenant_id = tenant_id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public Long getTask_id() {
        return task_id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
