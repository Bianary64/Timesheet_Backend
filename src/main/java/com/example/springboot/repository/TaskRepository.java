package com.example.springboot.repository;

import com.example.springboot.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query(value = """
            SELECT t.id, t.title, p.name AS project, t.task_status AS status, p.start_date AS startDate, p.end_date AS dueDate,
            p.id AS projectId
            FROM task t JOIN project p ON t.project_id = p.id
            WHERE t.tenant_id = :tenantId AND t.status = 1
            """, nativeQuery = true)
    List<Map<String, Object>> getAllDataByTenantId(@Param("tenantId") Long tenantId);

    @Query(value = """
            SELECT * FROM task WHERE id = :id AND status = 1
            """, nativeQuery = true)
    Task getDataById(@Param("id") Long id);

    @Query(value = """
            SELECT id, title AS name, project_id AS projectId
            FROM task WHERE tenant_id = :tenantId AND project_id = :projectId AND status = 1
            """, nativeQuery = true)
    List<Map<String, Object>> getDataByProject(@Param("tenantId") Long tenantId, @Param("projectId") Long projectId);
}
