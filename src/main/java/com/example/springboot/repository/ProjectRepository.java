package com.example.springboot.repository;

import com.example.springboot.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query(value = """
            SELECT id, name, description, project_status AS status, start_date AS startDate, end_date AS endDate, 
            team_size AS teamSize
            FROM project WHERE tenant_id = :tenantId AND status = 1
            """, nativeQuery = true)
    List<Map<String, Object>> getAllDataByTenantId(@Param("tenantId") Long tenantId);

    @Query(value = """
            SELECT * FROM project WHERE id = :id AND status = 1
            """, nativeQuery = true)
    Project getDataById(@Param("id") Long id);
}
