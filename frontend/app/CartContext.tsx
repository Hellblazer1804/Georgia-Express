"use client";

import React, { createContext, useContext, useState } from "react";

interface CartContextProps {
    cartId: number | null;
    username: string;
    setCartId: (id: number | null) => void;
    setUsername: (username: string) => void;
}

const CartContext = createContext<CartContextProps | undefined>(undefined);

export const CartProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [cartId, setCartId] = useState<number | null>(null);
    const [username, setUsername] = useState<string>("");

    return (
        <CartContext.Provider value={{ cartId, username, setCartId, setUsername }}>
            {children}
        </CartContext.Provider>
    );
};

export const useCart = () => {
    const context = useContext(CartContext);
    if (!context) {
        throw new Error("useCart must be used within a CartProvider");
    }
    return context;
};

