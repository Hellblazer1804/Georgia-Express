"use client"
import React, { useEffect, useState } from "react";
import { useCart } from "../CartContext";
import style from "./Cart.module.css";

interface CartItem {
    cart_item_id: number;
    item_name: string;
    cost: number;
    quantity: number;
}

export default function Cart() {
    const [cartItems, setCartItems] = useState<CartItem[]>([]);
    const [totalAmount, setTotalAmount] = useState<number>(0);
    const { cartId, username } = useCart();

    useEffect(() => {
        if (!cartId) return;

        const fetchCartItems = async () => {
            try {
                const response = await fetch(`/api/cart/${cartId}`);
                if (!response.ok) {
                    throw new Error("Failed to fetch cart items");
                }
                const data: CartItem[] = await response.json();
                setCartItems(data);

                // Calculate total amount
                const total = data.reduce((sum, item) => sum + item.cost * item.quantity, 0);
                setTotalAmount(total);
            } catch (error) {
                console.error("Error fetching cart items:", error);
            }
        };

        fetchCartItems();
    }, [cartId]);

    const handleCheckout = async () => {
        try {
            const response = await fetch(`/api/transaction/process`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    username,
                    totalAmount,
                }),
            });

            if (response.ok) {
                const result = await response.json();
                alert(result.message || "Transaction processed successfully!");
                setCartItems([]); // Clear cart after successful checkout
                setTotalAmount(0); // Reset total amount
            } else {
                const error = await response.json();
                alert(`Transaction failed: ${error.message}`);
            }
        } catch (error) {
            console.error("Error during checkout:", error);
            alert("An error occurred during checkout. Please try again.");
        }
    };

    return (
        <div className={style.cart}>
            <h1>Your Cart</h1>
            {cartItems.length === 0 ? (
                <p>Your cart is empty.</p>
            ) : (
                <div>
                    <ul>
                        {cartItems.map((item) => (
                            <li key={item.cart_item_id}>
                                {item.item_name} - ${item.cost.toFixed(2)} x {item.quantity}
                            </li>
                        ))}
                    </ul>
                    <h3>Total: ${totalAmount.toFixed(2)}</h3>
                    <button onClick={handleCheckout} className={style.checkoutButton}>
                        Checkout
                    </button>
                </div>
            )}
        </div>
    );
}
