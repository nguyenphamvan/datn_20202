package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByVerificationCode(String code);
    @Modifying
    @Query("UPDATE User u SET u.enabled=:enabled where u.id=:id")
    void setActive(@Param("enabled") boolean enabled, @Param("id") Long id);
}
