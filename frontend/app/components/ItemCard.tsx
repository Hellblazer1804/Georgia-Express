import React from "react";
import style from "./ItemCard.module.css";

interface ItemCardProps {
    image: string;
    title: string;
    price: number;
    onBuy: () => void; // New prop for the Buy button
}

export default function ItemCard({ image, title, price, onBuy }: ItemCardProps) {
    return (
        <div className={style.card}>
            <img src={image || "/placeholder.png"} alt={title} className={style.image} />
            <h3 className={style.title}>{title}</h3>
            <p className={style.price}>${price.toFixed(2)}</p>
            <button className={style.buyButton} onClick={onBuy}>
                Buy
            </button>
        </div>
    );
}

