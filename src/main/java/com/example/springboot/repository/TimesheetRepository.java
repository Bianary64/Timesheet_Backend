package com.example.springboot.repository;

import com.example.springboot.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    @Query(value = """
            SELECT t.id, t.user_id AS userId, u.name AS user, t.date, t.project_id AS projectId, p.name AS projectName, t.task_id AS taskId, s.title AS taskName,
            t.description, t.hours
            FROM timesheet t JOIN project p ON t.project_id = p.id JOIN task s ON t.task_id = s.id
            JOIN user u ON t.user_id = u.id
            WHERE t.tenant_id = :tenantId AND t.status = 1
            """, nativeQuery = true)
    List<Map<String, Object>> getAllDataByTenantId(@Param("tenantId") Long tenantId);

    @Query(value = """
            SELECT t.id, t.user_id AS userId, u.name AS user, t.date, t.project_id AS projectId, p.name AS projectName, t.task_id AS taskId, s.title AS taskName,
            t.description, t.hours
            FROM timesheet t JOIN project p ON t.project_id = p.id JOIN task s ON t.task_id = s.id
            JOIN user u ON t.user_id = u.id
            WHERE t.tenant_id = :tenantId AND t.user_id = :userId AND t.status = 1
            """, nativeQuery = true)
    List<Map<String, Object>> getDataByUserIdAndTenantId(@Param("userId") Long userId, @Param("tenantId") String tenantId);

    @Modifying
    @Transactional
    @Query(value= """
            UPDATE timesheet SET status = 0 WHERE id = :id AND status = 1
            """, nativeQuery = true)
    void deleteDataById(@Param("id") Long id);

    @Query(value = """
            SELECT\s
                t.id,
                t.date,
                t.project_id as projectId,
                p.name as projectName,
                t.task_id as taskId,
                tk.title as taskName,
                t.description,
                t.hours,
                u.name AS userName,
                u.id AS userId
            FROM timesheet t
            JOIN project p ON t.project_id = p.id
            JOIN task tk ON t.task_id = tk.id
            JOIN user u ON t.user_id = u.id
            WHERE t.tenant_id = :tenantId
                AND t.date BETWEEN :startDate AND :endDate AND t.status = 1
            ORDER BY t.date DESC
            """, nativeQuery = true)
    List<Map<String, Object>> getEntries(@Param("tenantId") String tenantId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = """
            SELECT\s
                t.id,
                t.date,
                t.project_id as projectId,
                p.name as projectName,
                t.task_id as taskId,
                tk.title as taskName,
                t.description,
                t.hours,
                u.name AS userName,
                u.id AS userId
            FROM timesheet t
            JOIN project p ON t.project_id = p.id
            JOIN task tk ON t.task_id = tk.id
            JOIN user u ON t.user_id = u.id
            WHERE t.tenant_id = :tenantId
                AND t.date BETWEEN :startDate AND :endDate AND t.status = 1 AND t.user_id = :userId
            ORDER BY t.date DESC
            """, nativeQuery = true)
    List<Map<String, Object>> getEntriesWithUserId(@Param("tenantId") String tenantId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                                   @Param("userId") Long userId);

    @Query(value = """
            SELECT\s
                p.user_id AS userId,
                COALESCE(SUM(t.hours), 0) as totalHours,
                p.id,
                p.name
            FROM project p
            LEFT JOIN timesheet t ON p.id = t.project_id
                AND t.tenant_id = :tenantId
                AND t.date BETWEEN :startDate AND :endDate
            WHERE p.tenant_id = :tenantId AND p.status = 1 AND t.status = 1
            GROUP BY p.id, p.name
            HAVING COALESCE(SUM(t.hours), 0) > 0
            """, nativeQuery = true)
    List<Map<String, Object>> getProjects(@Param("tenantId") String tenantId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = """
            SELECT\s
                p.user_id AS userId,
                COALESCE(SUM(t.hours), 0) as totalHours,
                p.id,
                p.name
            FROM project p
            LEFT JOIN timesheet t ON p.id = t.project_id
                AND t.tenant_id = :tenantId
                AND t.date BETWEEN :startDate AND :endDate
            WHERE p.tenant_id = :tenantId AND p.status = 1 AND p.user_id = :userId AND t.status = 1
            GROUP BY p.id, p.name
            HAVING COALESCE(SUM(t.hours), 0) > 0
            """, nativeQuery = true)
    List<Map<String, Object>> getProjectsWithUserId(@Param("tenantId") String tenantId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                                    @Param("userId") Long userId);

    @Query(value = """
            SELECT\s
                u.id,
                u.name,
                COALESCE(SUM(t.hours), 0) as totalHours
            FROM user u
            LEFT JOIN timesheet t ON t.user_id = u.id
                AND t.tenant_id = :tenantId
                AND t.date BETWEEN :startDate AND :endDate
            WHERE u.tenant_id = :tenantId AND u.status = 1 AND t.status = 1
            GROUP BY u.id, u.name
            HAVING COALESCE(SUM(t.hours), 0) > 0
            """, nativeQuery = true)
    List<Map<String, Object>> getUsers(@Param("tenantId") String tenantId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
