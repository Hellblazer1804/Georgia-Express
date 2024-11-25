"use client";

import React, { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import style from "./AccountOverview.module.css";

interface Customer {
    customerId: number;
    name: string;
    ssn: string;
    email: string;
    phone: string;
    address: string;
    creditScore: number;
    salary: number;
    dateOfBirth: string;
}

interface Card {
    card_number: string;
    expiration_date: string;
    recommended_credit_limit: number;
    card_status: string;
    approved: boolean;
    card_balance: number;
    minimum_payment: number;
    reward_points: number;
}

export default function AccountOverview() {
    const searchParams = useSearchParams();
    const customerId = searchParams.get("id");
    const customerUsername = searchParams.get("user");
    const [customer, setCustomer] = useState<Customer | null>(null);
    const [cards, setCards] = useState<Card[]>([]);

    useEffect(() => {
        if (!customerId) return;

        const fetchCustomerAndCards = async () => {
            try {
                const customerResponse = await fetch(`http://localhost:8080/api/customer/${customerId}`);
                if (!customerResponse.ok) {
                    throw new Error(`Failed to fetch customer: ${customerResponse.status}`);
                }
                const customerData = await customerResponse.json();
                setCustomer(customerData);

                const cardsResponse = await fetch(`http://localhost:8080/api/card/customer/${customerId}`);
                if (!cardsResponse.ok) {
                    throw new Error(`Failed to fetch cards: ${cardsResponse.status}`);
                }
                const cardsData = await cardsResponse.json();
                setCards(cardsData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchCustomerAndCards();
    }, [customerId]);

    if (!customer) {
        return <div>Loading customer information...</div>;
    }

    return (
        <div className={style.container}>
            <h1 className={style.title}>Account Overview</h1>
            <div className={style.cardInfo}>
                <p><strong>Name:</strong> {customer.name}</p>
                <p><strong>Email:</strong> {customer.email}</p>
                <p><strong>Phone Number:</strong> {customer.phone}</p>
                <p><strong>Address:</strong> {customer.address}</p>
                <p><strong>Credit Score:</strong> {customer.creditScore}</p>
                <p><strong>Salary:</strong> ${customer.salary.toLocaleString()}</p>
                <p><strong>Date of Birth:</strong> {customer.dateOfBirth}</p>
            </div>

            <h2 className={style.title}>Card Information</h2>
            <hr className={style.line}/>
            {cards.length > 0 ? (
                cards.map((card, index) => (
                    <div key={index} className={style.card}>
                        <p><strong>Card Number:</strong> **** **** **** {card.card_number.slice(-4)}</p>
                        <p><strong>Expiry Date:</strong> {card.expiration_date}</p>
                        <p><strong>Credit Limit:</strong> ${card.recommended_credit_limit.toLocaleString()}</p>
                        <p><strong>Card Balance:</strong> ${card.card_balance.toLocaleString()}</p>
                        <p><strong>Minimum Payment:</strong> ${card.minimum_payment.toLocaleString()}</p>
                        <p><strong>Reward Points:</strong> {card.reward_points}</p>
                        <p><strong>Status:</strong> {card.card_status}</p>
                        <hr className={style.line}/>
                    </div>

                ))

            ) : (
                <p>Loading card information...</p>
            )}
            <a href={`/store?id=${customerId}&user=${customerUsername}`}>Store</a>
        </div>
    );
}