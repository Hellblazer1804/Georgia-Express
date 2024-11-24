"use client"
import React, { useEffect, useState } from "react";
import ItemCard from "./ItemCard";
import {useRouter} from "next/navigation";
import style from "./Store.module.css";

interface InventoryItem {
    itemId: number;
    itemName: string;
    cost: number;
}

export default function Store() {
    const [items, setItems] = useState<InventoryItem[]>([]);
    const [cartId, setCartId] = useState<number | null>(null);
    const router = useRouter();

    useEffect(() => {
        const fetchItems = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/inventory");
                if (!response.ok) {
                    throw new Error("Failed to fetch inventory items");
                }
                const data: InventoryItem[] = await response.json();
                setItems(data);
            } catch (error) {
                console.error("Error fetching inventory items:", error);
            }
        };

        const fetchCartId = async () => {
            setCartId(1);
        };

        fetchItems();
        fetchCartId();
    }, []);

    const handleAddToCart = async (itemId: number) => {
        if (!cartId) {
            alert("Cart not found. Please try again.");
            return;
        }

        try {
            const response = await fetch(
                `http://localhost:8080/api/cart/${cartId}/addItem?itemId=${itemId}&quantity=1`,
                { method: "POST" }
            );
            if (response.ok) {
                alert("Item added to cart!");
            } else {
                alert("Failed to add item to cart. Please try again.");
            }
        } catch (error) {
            console.error("Error adding item to cart:", error);
            alert("An error occurred. Please try again.");
        }
    };

    return (
        <div className={style.container}>
            <div className={style.cartLink}>
                <button onClick={() => router.push("/cart")}>Go to Cart</button>
            </div>
            <div className={style.gallery}>
                {items.map((item) => (
                    <ItemCard
                        key={item.itemId}
                        image=""
                        title={item.itemName}
                        price={item.cost}
                        onBuy={() => handleAddToCart(item.itemId)}
                    />
                ))}
            </div>
        </div>
    );
}

