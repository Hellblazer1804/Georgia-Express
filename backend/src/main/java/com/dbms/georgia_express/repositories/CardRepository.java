package com.dbms.georgia_express.repositories;


import com.dbms.georgia_express.model.Card;
import com.dbms.georgia_express.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByCardNumber(String cardNumber);
    List<Card> findByCardStatus(String status);
    List<Card> findByCustomer(Customer customerId);
}