package com.example.springboot.repository;

import com.example.springboot.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    @Query(value = """
            SELECT t.id, t.date, t.project_id AS projectId, p.name AS projectName, t.task_id AS taskId, s.title AS taskName,
            t.description, t.hours
            FROM timesheet t JOIN project p ON t.project_id = p.id JOIN task s ON t.task_id = s.id
            WHERE t.tenant_id = :tenantId AND t.status = 1
            """, nativeQuery = true)
    List<Map<String, Object>> getAllDataByTenantId(@Param("tenantId") Long tenantId);

    @Query(value = """
            SELECT t.id, t.date, t.project_id AS projectId, p.name AS projectName, t.task_id AS taskId, s.title AS taskName,
            t.description, t.hours
            FROM timesheet t JOIN project p ON t.project_id = p.id JOIN task s ON t.task_id = s.id
            WHERE t.tenant_id = :tenantId AND t.user_id = :userId AND t.status = 1
            """, nativeQuery = true)
    List<Map<String, Object>> getDataByUserIdAndTenantId(@Param("userId") Long userId, @Param("tenantId") Long tenantId);

    @Modifying
    @Transactional
    @Query(value= """
            UPDATE timesheet SET status = 0 WHERE id = :id AND status = 1
            """, nativeQuery = true)
    void deleteDataById(@Param("id") Long id);
}
