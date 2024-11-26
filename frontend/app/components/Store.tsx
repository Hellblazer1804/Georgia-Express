"use client"
import React, {useEffect, useState} from "react";
import ItemCard from "./ItemCard";
import {useRouter} from "next/navigation";
import style from "./Store.module.css";
import { useSearchParams } from "next/navigation";

interface Inventory {
    itemId: number;
    itemName: string;
    cost: number;
}

export default function Store() {
    const [items, setItems] = useState<Inventory[]>([]);
    const [cartId, setCartId] = useState<number | null>(null);
    const router = useRouter();
    const searchParams = useSearchParams();
    const customerId = searchParams.get("id");
    const customerUsername = searchParams.get("user");

    useEffect(() => {
        const fetchItems = async () => {
            try {
                const response = await fetch("http://localhost:8080/api/inventory");
                if (!response.ok) {
                    throw new Error("Failed to fetch inventory items");
                }
                const data: Inventory[] = await response.json();
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

        const Inventory = {
            item_id: itemId,
            quantity: 1
        };

        try {
            const response = await fetch(
                `http://localhost:8080/api/cart/add`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "username": customerUsername || "",
                    },
                    body: JSON.stringify(Inventory),
                }
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
            <div className={style.navButtons}>
                <button onClick={() => router.push(`/overview?id=${customerId}&user=${customerUsername}`)}>Home</button>
                <button onClick={() => router.push(`/cart?id=${customerId}&user=${customerUsername}`)}>Go to Cart</button>
            </div>
            <div className={style.gallery}>
                {items.map((item) => (
                    <ItemCard
                        key={item.itemId}
                        title={item.itemName}
                        price={item.cost}
                        onBuy={() => handleAddToCart(item.itemId)}
                    />
                ))}
            </div>
        </div>
    );
}

