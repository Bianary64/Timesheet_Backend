package com.example.springboot.repository;

import com.example.springboot.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    @Query(value = """
            SELECT * FROM tenant WHERE id = :tenantId AND status = 1
            """, nativeQuery = true)
    Tenant getTenanatDataById(@Param("tenantId") Long tenantId);
}
