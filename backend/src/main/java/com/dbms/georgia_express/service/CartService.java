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
import com.dbms.georgia_express.security.JwtTokenUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomerLoginService customerLoginService;


    public void addToCart(String token, Long itemId, Integer quantity) {
        CustomerLogin customerLogin = customerLoginService.getCustomerLoginFromToken(token);

        Inventory item = inventoryService.findItemById(Math.toIntExact(itemId))
                .orElseThrow(() -> new NotFoundException("Item not found"));

        Cart cart = cartRepository.findByCustomerLogin(customerLogin)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomerLogin(customerLogin);
                    newCart.setCartAmount(BigDecimal.ZERO);
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
        cartItem.setCost(calculateCartItemCost(cartItem));
        cartItemRepository.save(cartItem);

        // Update cart amount
        updateCartAmount(cart);
    }

    public void removeFromCart(String token, Long itemId) {
        CustomerLogin customerLogin = customerLoginService.getCustomerLoginFromToken(token);
        System.out.println("Customer Login: " + customerLogin.getUsername()); // Log the customer login
        Inventory item = inventoryService.findItemById(Math.toIntExact(itemId))
                .orElseThrow(() -> new NotFoundException("Item not found"));

        Cart cart = cartRepository.findByCustomerLogin(customerLogin)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        CartItem cartItem = cartItemRepository.findByCartAndItem(cart, item)
                .orElseThrow(() -> new NotFoundException("Item not found in cart"));

        cart.removeCartItem(cartItem);
        cartRepository.save(cart);

        // Update cart amount
        updateCartAmount(cart);
    }

    public List<CartItem> getCart(String token) {
        CustomerLogin customerLogin = customerLoginService.getCustomerLoginFromToken(token);

        Cart cart = cartRepository.findByCustomerLogin(customerLogin)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        return cart.getCartItems();
    }

    public void clearCart(String token) {
        CustomerLogin customerLogin = customerLoginService.getCustomerLoginFromToken(token);

        Cart cart = cartRepository.findByCustomerLogin(customerLogin)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        // Remove all cart items
        cart.getCartItems().clear();

        // Reset cart amount
        cart.setCartAmount(BigDecimal.ZERO);

        // Save the empty cart
        cartRepository.save(cart);
    }

    public BigDecimal getTotalAmount(List<CartItem> cartItems) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem cartItem : cartItems) {
            totalAmount = totalAmount.add(BigDecimal.valueOf(cartItem.getItem().getCost())
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        return totalAmount;
    }

    private void updateCartAmount(Cart cart) {
        BigDecimal cartAmount = getTotalAmount(cart.getCartItems());
        cart.setCartAmount(cartAmount);
        cartRepository.save(cart);
    }

    private int calculateCartItemCost(CartItem cartItem) {
        int cartItemCost = cartItem.getQuantity() * cartItem.getItem().getCost();
        return cartItemCost;
    }
}