import React from "react";
import style from "./ItemCard.module.css";

interface ItemCardProps {
    title: string;
    price: number;
    onBuy: () => void; // New prop for the Buy button
}

export default function ItemCard({title, price, onBuy }: ItemCardProps) {
    return (
        <div className={style.card}>
            <h3 className={style.title}>{title}</h3>
            <p className={style.price}>${price.toFixed(2)}</p>
            <button className={style.buyButton} onClick={onBuy}>
                Add to Cart
            </button>
        </div>
    );
}

