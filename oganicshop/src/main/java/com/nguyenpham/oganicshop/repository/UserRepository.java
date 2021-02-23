package com.nguyenpham.oganicshop.repository;

import com.nguyenpham.oganicshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
