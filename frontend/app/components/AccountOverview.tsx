"use client";

import React, { useEffect, useState } from "react";
import { useSearchParams } from "next/navigation";
import style from "./AccountOverview.module.css";

interface Customer {
    customerId: number;
    name: string;
    ssn: string;
    email: string;
    phoneNumber: string;
    address: string;
    creditScore: number;
    salary: number;
    dateOfBirth: string;
}

interface Card {
    cardNumber: string;
    expiryDate: string;
    creditLimit: number;
    cardStatus: string;
    approved: boolean;
    cardBalance: number;
    minimumPayment: number;
    rewardPoints: number;
}

export default function AccountOverview() {
    const searchParams = useSearchParams();
    const customerId = searchParams.get("customerId");
    const cardNumber = searchParams.get("cardNumber")
    const [customer, setCustomer] = useState<Customer | null>(null);
    const [card, setCard] = useState<Card | null>(null);

    useEffect(() => {
        if (!customerId) return;

        const fetchCustomerAndCard = async () => {
            try {
                const customerResponse = await fetch(`http://localhost:8080/api/customer/${customerId}`);
                if (!customerResponse.ok) {
                    throw new Error(`Failed to fetch customer: ${customerResponse.status}`);
                }
                const customerData = await customerResponse.json();
                setCustomer(customerData);

                const generateCardResponse = await fetch(`http://localhost:8080/api/card/${customerId}/generate`);
                if (!generateCardResponse.ok) {
                    throw new Error(`Failed to generate card: ${generateCardResponse.status}`);
                }


                const cardResponse = await fetch(`http://localhost:8080/api/card/${customerId}`);
                if (!cardResponse.ok) {
                    throw new Error(`Failed to fetch card: ${cardResponse.status}`);
                }
                const cardData = await cardResponse.json();
                setCard(cardData);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchCustomerAndCard();
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
                <p><strong>Phone Number:</strong> {customer.phoneNumber}</p>
                <p><strong>Address:</strong> {customer.address}</p>
                <p><strong>Credit Score:</strong> {customer.creditScore}</p>
                <p><strong>Salary:</strong> ${customer.salary.toLocaleString()}</p>
                <p><strong>Date of Birth:</strong> {customer.dateOfBirth}</p>
            </div>

            <h2 className={style.title}>Card Information</h2>
            {card ? (
                <div className={style.card}>
                    <p><strong>Card Number:</strong> **** **** **** {card.cardNumber.slice(-4)}</p>
                    <p><strong>Expiry Date:</strong> {card.expiryDate}</p>
                    <p><strong>Credit Limit:</strong> ${card.creditLimit.toLocaleString()}</p>
                    <p><strong>Card Balance:</strong> ${card.cardBalance.toLocaleString()}</p>
                    <p><strong>Minimum Payment:</strong> ${card.minimumPayment.toLocaleString()}</p>
                    <p><strong>Reward Points:</strong> {card.rewardPoints}</p>
                    <p><strong>Status:</strong> {card.cardStatus}</p>
                    <p><strong>Approved:</strong> {card.approved ? "Yes" : "No"}</p>
                </div>
            ) : (
                <p>Loading card information...</p>
            )}
        </div>
    );
}

