package com.dbms.georgia_express.repositories;

import com.dbms.georgia_express.model.Cart;
import com.dbms.georgia_express.model.CartItem;
import com.dbms.georgia_express.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndItem(Cart cart, Inventory item);
}
