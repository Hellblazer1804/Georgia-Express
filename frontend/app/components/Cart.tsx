"use client";
import React, { useEffect, useState } from "react";
import style from "./Cart.module.css";
import { useSearchParams } from "next/navigation";
import { useRouter } from "next/navigation";

interface CartItem {
    item_id: number;
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
    const router = useRouter();

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
                const cartItems: CartItem[] = data.cartItems;
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

    const handleRemoveItem = async (itemId: number) => {
        try {
            const response = await fetch(`http://localhost:8080/api/cart/remove/${itemId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    "username": customerUsername || "",
                },
            });

            if (response.ok) {
                // Update the cart state to remove the item
                const updatedCartItems = cartItems.filter((item) => item.item_id !== itemId);
                setCartItems(updatedCartItems);

                // Recalculate the total cost
                const updatedTotalCost = updatedCartItems.reduce(
                    (acc, item) => acc + item.cart_item_cost * item.quantity,
                    0
                );
                setTotalCost(updatedTotalCost);

                alert("Item removed from cart.");
            } else {
                alert("Failed to remove item from cart.");
            }
        } catch (error) {
            console.error("Error removing item:", error);
            alert("An error occurred. Please try again.");
        }
    };

    const handleCheckout = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault(); // Prevent the form from reloading the page

        // Access form data using the FormData API
        const formData = new FormData(event.currentTarget);
        const creditCard = formData.get("creditCard") as string;
        const cvv = formData.get("cvv") as string;

        const cardInfo = {
            card_number: creditCard,
            cvv: parseInt(cvv, 10),
        };

        try {
            const response = await fetch(`http://localhost:8080/api/transaction/process`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "username": customerUsername || "",
                },
                body: JSON.stringify(cardInfo),
            });

            if (response.ok) {
                alert("Checkout successful!");
                router.push(`/overview?id=${customerId}&user=${customerUsername}`);
            } else {
                const errorData = await response.json();
                console.error("Error response:", errorData);
                alert(errorData.message || "Checkout failed.");
            }
        } catch (error) {
            console.error("Error checking out:", error);
            alert("An error occurred. Please try again.");
            router.push(`/overview?id=${customerId}&user=${customerUsername}`);
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
                        <div key={index} className={style.cartItem}>
                            {item.item_name} - ${item.cart_item_cost} x {item.quantity}
                            <button onClick={() => handleRemoveItem(item.item_id)}>Remove</button>
                        </div>
                    ))
                )}
                <div className={style.totalCost}>
                    Total: ${totalCost.toFixed(2)}
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

