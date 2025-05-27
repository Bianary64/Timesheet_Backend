package com.example.springboot.repository;

import com.example.springboot.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT * FROM user WHERE email = :email AND status = 1
            """, nativeQuery = true)
    User findByEmail(@Param("email") String email);

    @Query(value = """
            SELECT * FROM user WHERE email = :email AND status = 1 AND id != :userId
            """, nativeQuery = true)
    User checkUserByEmail(@Param("email") String email, @Param("userId") Long userId);

    @Query(value = """
            SELECT * FROM user WHERE tenant_id = :tenantId AND status = 1
            """, nativeQuery = true)
    List<User> getAllUsers(@Param("tenantId") Long tenantId);

    @Modifying
    @Transactional
    @Query(value= """
            UPDATE user SET status = 0 WHERE id = :id AND tenant_id = :tenantId AND status = 1
            """, nativeQuery = true)
    void deleteUserById(@Param("id") Long id, @Param("tenantId") Long tenantId);

    @Query(value = """
            SELECT * FROM user WHERE id = :id AND status = 1
            """, nativeQuery = true)
    User getUserById(@Param("id") Long id);
}
