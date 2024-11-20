package com.dbms.georgia_express.repositories;

import com.dbms.georgia_express.model.CustomerLogin;
import com.dbms.georgia_express.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerLogin(CustomerLogin customerLogin);
}
