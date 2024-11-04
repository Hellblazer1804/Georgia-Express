package com.dbms.georgia_express.repositories;

import com.dbms.georgia_express.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> getCustomersByDateOfBirth(String dateOfBirth);
    List<Customer> getCustomerByAddress(String address);
}
