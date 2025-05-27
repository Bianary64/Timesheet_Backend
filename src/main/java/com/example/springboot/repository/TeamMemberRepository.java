package com.example.springboot.repository;

import com.example.springboot.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    @Query(value = """
            SELECT id, name, role, email, phone, join_date AS joinDate
            FROM team_member WHERE tenant_id = :tenantId AND status = 1
            """, nativeQuery = true)
    List<Map<String, Object>> getAllDataByTenantId(@Param("tenantId") Long tenantId);
}
