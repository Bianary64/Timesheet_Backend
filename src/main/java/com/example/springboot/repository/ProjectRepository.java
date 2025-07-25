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
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = """
            SELECT\s
                p.id,\s
                p.name,\s
                p.description,\s
                p.project_status AS status,\s
                p.start_date AS startDate,\s
                p.end_date AS endDate,\s
                p.team_size AS teamSize,\s
                p.user_id AS userId,
                u.name AS user,
                COUNT(t.id) AS taskCount
            FROM\s
                project p
            LEFT JOIN\s
                task t ON p.id = t.project_id
            LEFT JOIN
                user u ON p.user_id = u.id
            WHERE\s
                p.tenant_id = :tenantId\s
                AND p.status = 1
            GROUP BY\s
                p.id
            """, nativeQuery = true)
    List<Map<String, Object>> getAllDataByTenantId(@Param("tenantId") String tenantId);

    @Query(value = """
            SELECT\s
                p.id,\s
                p.name,\s
                p.description,\s
                p.project_status AS status,\s
                p.start_date AS startDate,\s
                p.end_date AS endDate,\s
                p.team_size AS teamSize,\s
                p.user_id AS userId,
                u.name AS user,
                COUNT(t.id) AS taskCount
            FROM\s
                project p
            LEFT JOIN\s
                task t ON p.id = t.project_id
            LEFT JOIN
                user u ON p.user_id = u.id
            WHERE\s
                p.tenant_id = :tenantId\s
                AND p.status = 1 AND p.user_id = :userId
            GROUP BY\s
                p.id
            """, nativeQuery = true)
    List<Map<String, Object>> getDataByUserIdAndTenantId(@Param("userId") Long userId, @Param("tenantId") String tenantId);

    @Query(value = """
            SELECT * FROM project WHERE id = :id AND status = 1
            """, nativeQuery = true)
    Project getDataById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = """
            UPDATE project SET status = 0 WHERE id = :projectId AND status = 1
            """, nativeQuery = true)
    void deleteDataById(@Param("projectId") Long projectId);
}
