"use client"
import React, {useEffect, useState} from "react";
import style from "./Cart.module.css";
import {useSearchParams} from "next/navigation";

interface CartItem {
    cart_item_id: number;
    item_name: string;
    cost: number;
    quantity: number;
}

export default function Cart() {
    const [cartItems, setCartItems] = useState<CartItem[]>([]);
    const searchParams = useSearchParams();
    const customerId = searchParams.get("id");
    const customerUsername = searchParams.get("user");

    useEffect(() => {
        const fetchCartItems = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/cart`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        "username": customerUsername || "",
                    },
                });
                if (!response.ok) {
                    throw new Error("Failed to fetch cart items");
                }
                const data = await response.json();
                const cartItems : CartItem[] = data.cartItems;
                console.log("cart items", cartItems);
                setCartItems(cartItems);
            } catch (error) {
                console.error("Error fetching cart items:", error);
            }
        };

        fetchCartItems();
    }, [customerUsername]);

    const handleCheckout = async () => {
        // Handle checkout logic here
    };

    return (
        <div className={style.cart}>
            <h1>Your Cart</h1>
            {cartItems.length === 0 ? (
                <p>Your cart is empty.</p>
            ) : (
                cartItems.map((item, index) => (
                    <div key={index}>
                        {item.item_name}
                    </div>
                ))
            )}
        </div>
    );
}