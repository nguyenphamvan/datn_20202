package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIdIsNot(long currentUserId);
    User findByEmail(String email);
    User findByVerificationCode(String code);
    public User findByResetPasswordToken(String token);
    @Modifying
    @Query("UPDATE User u SET u.enabled=:enabled where u.id=:id")
    void setActive(@Param("enabled") boolean enabled, @Param("id") Long id);
}
