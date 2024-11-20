package com.dbms.georgia_express.service;

import com.dbms.georgia_express.exception.NotFoundException;
import com.dbms.georgia_express.exception.UnauthorizedException;
import com.dbms.georgia_express.model.Cart;
import com.dbms.georgia_express.model.CartItem;
import com.dbms.georgia_express.model.CustomerLogin;
import com.dbms.georgia_express.model.Inventory;
import com.dbms.georgia_express.repositories.CartItemRepository;
import com.dbms.georgia_express.repositories.CartRepository;
import com.dbms.georgia_express.repositories.CustomerLoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CustomerLoginRepository customerLoginRepository;
    @Autowired
    private InventoryService inventoryService;

    public void addToCart(String username, Long itemId, Integer quantity) {
        CustomerLogin customerLogin = customerLoginRepository.findById(username)
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        Inventory item = inventoryService.findItemById(Math.toIntExact(itemId))
                .orElseThrow(() -> new NotFoundException("Item not found"));

        Cart cart = cartRepository.findByCustomerLogin(customerLogin)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerLogin(customerLogin);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item)
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setCart(cart);
                    newCartItem.setItem(item);
                    newCartItem.setQuantity(0);
                    return newCartItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);
    }

    public void removeFromCart(String username, Long itemId) {
        CustomerLogin customerLogin = customerLoginRepository.findById(username)
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        Inventory item = inventoryService.findItemById(Math.toIntExact(itemId))
                .orElseThrow(() -> new NotFoundException("Item not found"));

        Cart cart = cartRepository.findByCustomerLogin(customerLogin)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item)
                .orElseThrow(() -> new NotFoundException("Item not found in cart"));

        cart.removeCartItem(cartItem);
        cartRepository.save(cart);
    }

    public List<CartItem> getCart(String username) {
        CustomerLogin customerLogin = customerLoginRepository.findById(username)
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        Cart cart = cartRepository.findByCustomerLogin(customerLogin)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        return cart.getCartItems();
    }
}

