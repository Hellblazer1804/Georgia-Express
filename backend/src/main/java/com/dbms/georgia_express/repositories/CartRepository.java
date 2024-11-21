package com.dbms.georgia_express.repositories;

import com.dbms.georgia_express.model.Cart;
import com.dbms.georgia_express.model.CustomerLogin;
import com.dbms.georgia_express.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerLogin(CustomerLogin customerLogin);
}
