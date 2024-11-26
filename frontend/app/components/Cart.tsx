"use client"
import React, {useEffect, useState} from "react";
import style from "./Cart.module.css";
import {useSearchParams} from "next/navigation";
import {useRouter} from "next/navigation";

interface CartItem {
    cart_item_id: number;
    item_name: string;
    cart_item_cost: number;
    quantity: number;
}

export default function Cart() {
    const [cartItems, setCartItems] = useState<CartItem[]>([]);
    const searchParams = useSearchParams();
    const customerId = searchParams.get("id");
    const customerUsername = searchParams.get("user");
    const [totalCost, setTotalCost] = useState<number>(0);

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
                const totalCost = data.cart_amount;
                console.log("cart items", cartItems);
                setCartItems(cartItems);
                setTotalCost(totalCost);
            } catch (error) {
                console.error("Error fetching cart items:", error);
            }
        };

        fetchCartItems();
    }, [customerUsername]);

    const handleCheckout = async () => {
        const creditCard = (document.querySelector('input[name="creditCard"]') as HTMLInputElement).value;
        const cvv = (document.querySelector('input[name="cvv"]') as HTMLInputElement).value;
        const cardInfo = {
            card_number: creditCard.toString(),
            cvv: parseInt(cvv, 10),
        }

        try {
            const response = await fetch(`http://localhost:8080/api/transaction/process`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "username": customerUsername || "",
                },
                body: JSON.stringify(cardInfo),
            });
            console.log("response", response);
            if (response.ok) {
                alert("Checkout successful!");
                useRouter().push(`/overview?id=${customerId}&user=${customerUsername}`);
            } else {
                const errorData = await response.json();
                console.error("Error response:", errorData);
                alert(errorData);
            }
        } catch (error) {
            alert(error);
            console.error("Error checking out:", error);
        }
    };

    return (
        <div className={style.cartPage}>
        <div className={style.cart}>
            <h1>Your Cart</h1>
            {cartItems.length === 0 ? (
                <p>Your cart is empty.</p>
            ) : (
                cartItems.map((item, index) => (
                    <div key={index}>
                        {item.item_name} - ${item.cart_item_cost} x {item.quantity}
                    </div>
                ))
            )}
            <div className={style.totalCost}>
                Total: ${totalCost}
            </div>
            <a href={`/store?id=${customerId}&user=${customerUsername}`}>Back to store</a>
        </div>
            <div className={style.checkout}>
                <form onSubmit={handleCheckout}>
                    <div className={style.inputBox}>
                        <input name="creditCard" type="text" placeholder="Credit Card Number" required />
                    </div>
                    <div className={style.inputBox}>
                        <input name={"cvv"} type="text" placeholder="CVV" required />
                    </div>
                    <button type="submit">Checkout</button>
                </form>
            </div>
            </div>
    );
}