package com.dbms.georgia_express.repositories;

import com.dbms.georgia_express.model.CustomerLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerLoginRepository extends JpaRepository<CustomerLogin, String> {
    boolean existsByUsername(String username);
}