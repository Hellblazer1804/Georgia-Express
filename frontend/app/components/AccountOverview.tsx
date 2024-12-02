"use client";

import React, {useEffect, useState} from "react";
import style from "./AccountOverview.module.css";
import {useRouter} from "next/navigation";
import {useSearchParams} from "next/navigation";

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
    cvv: number;
    expiration_date: string;
    recommended_credit_limit: number;
    card_status: string;
    approved: boolean;
    card_balance: number;
    minimum_payment: number;
    reward_points: number;
}

export default function AccountOverview() {
    const [customer, setCustomer] = useState<Customer | null>(null);
    const [cards, setCards] = useState<Card[]>([]);
    const [showDetails, setShowDetails] = useState<{ [key: number]: boolean }>({});
    const router = useRouter();
    const searchParams = useSearchParams();
    const customerId = searchParams.get("id");
    const token = localStorage.getItem("token");

    const handleApplyForCard = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/card/generate`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            if (!response.ok) {
                throw new Error("Failed to generate card");
            }
            const data = await response.json();
            console.log("Card generated:", data);
            alert("Card generated successfully!");

            // Fetch the updated list of cards
            const updatedCardsResponse = await fetch(`http://localhost:8080/api/card/customer`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            if (!updatedCardsResponse.ok) {
                throw new Error("Failed to fetch updated cards");
            }
            const updatedCards = await updatedCardsResponse.json();
            setCards(updatedCards); // Update the state with the new cards
        } catch (error) {
            console.error("Error generating card:", error);
            alert("Failed to generate card. Please try again.");
        }
    };


    const makePayment = (cardNumber: string) => async (event: React.FormEvent) => {
        event.preventDefault();
        const form = event.target as HTMLFormElement;
        const formData = new FormData(form);
        const amount = formData.get("amount") as string;

        try {
            const response = await fetch(
                `http://localhost:8080/api/card/${cardNumber}/payment?paymentAmount=${amount}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify({amount: parseFloat(amount)}),
                }
            );
            if (!response.ok) {
                throw new Error("Failed to make payment");
            }
            const data = await response.json();
            console.log("Payment made:", data);
            alert("Payment made successfully!");

            // Re-fetch the updated card data
            const updatedCardsResponse = await fetch(`http://localhost:8080/api/card/customer`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            if (!updatedCardsResponse.ok) {
                throw new Error("Failed to fetch updated card info");
            }
            const updatedCards = await updatedCardsResponse.json();
            setCards(updatedCards); // Update the state with the latest card info
        } catch (error) {
            console.error("Error making payment:", error);
            alert("Failed to make payment. Please try again.");
        }
    };

    const toggleShowDetails = (index: number) => {
        setShowDetails((prevState) => ({
            ...prevState,
            [index]: !prevState[index],
        }));
    };

    useEffect(() => {
        if (!customerId || !token) return;

        const fetchCustomerAndCards = async () => {
            try {
                const id = parseInt(customerId);
                console.log("id", id);
                const customerResponse = await fetch(`http://localhost:8080/api/customer/${id}`, {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                if (!customerResponse.ok) {
                    throw new Error(`Failed to fetch customer: ${customerResponse.status}`);
                }
                const customerData = await customerResponse.json();
                setCustomer(customerData);

                const cardsResponse = await fetch(`http://localhost:8080/api/card/customer`, {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                if (!cardsResponse.ok) {
                    throw new Error(`Failed to fetch cards: ${cardsResponse.status}`);
                }
                const cardsData = await cardsResponse.json();
                console.log("Cards data:", cardsData);
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
            <div className={style.topBar}>
                <button onClick={() => router.push("./login")} className={style.signOutButton}>
                    Sign Out
                </button>
            </div>

            <h1 className={style.title}>Account Overview</h1>
            <div className={style.cardInfo}>
                <p><strong>Name:</strong> {customer.name}</p>
                <p><strong>Email:</strong> {customer.email}</p>
                <p><strong>Phone Number:</strong> {customer.phone}</p>
                <p><strong>Address:</strong> {customer.address}</p>
                <p><strong>Credit Score:</strong> {customer.creditScore}</p>
                <p><strong>Salary:</strong> ${customer.salary}</p>
                <p><strong>Date of Birth:</strong> {customer.dateOfBirth}</p>
            </div>

            <h2 className={style.title}>Card Information</h2>
            <hr className={style.line}/>
            {cards.length > 0 ? (
                cards.map((card, index) => (
                    <div key={index} className={style.card}>
                        <p>
                            <strong>Card Number:</strong>{" "}
                            {showDetails[index] ? card.card_number : `**** **** **** ${card.card_number.slice(-4)}`}
                            <button onClick={() => toggleShowDetails(index)} className={style.show}>
                                {showDetails[index] ? "Hide" : "Show"}
                            </button>
                        </p>
                        <p>
                            <strong>CVV:</strong> {showDetails[index] ? card.cvv : "***"}
                        </p>
                        <p><strong>Expiry Date:</strong> {card.expiration_date}</p>
                        <p><strong>Credit Limit:</strong> ${card.recommended_credit_limit.toLocaleString()}</p>
                        <p><strong>Card Balance:</strong> ${card.card_balance.toLocaleString()}</p>
                        <p><strong>Minimum Payment:</strong> ${card.minimum_payment.toLocaleString()}</p>
                        <p><strong>Reward Points:</strong> {card.reward_points}</p>
                        <p><strong>Status:</strong> {card.card_status}</p>
                        <form className={style.paymentForm} onSubmit={makePayment(card.card_number)}>
                            <div className={style.payment}>
                                <input name="amount"/>
                                <button type="submit">Make Payment</button>
                            </div>
                        </form>
                        <hr className={style.line}/>
                    </div>
                ))
            ) : (
                <p>Loading card information...</p>
            )}
            <div className={style.buttons}>
                <button onClick={() => router.push(`/store?id=${customerId}`)}>Store</button>
                <button onClick={handleApplyForCard}>Apply for a Card</button>
            </div>
        </div>
    );
}
