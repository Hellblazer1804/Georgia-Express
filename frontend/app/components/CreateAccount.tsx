"use client";
import { useSearchParams } from "next/navigation";
import React, { useState } from "react";
import style from "@/app/components/LoginForm.module.css";
import { useRouter } from "next/navigation";

export default function CreateAccount() {
    const searchParams = useSearchParams();
    const customerId = searchParams.get("id");
    const router = useRouter();

    // Form state
    const [formData, setFormData] = useState({
        username: "",
        password: "",
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (!customerId) {
            alert("Customer ID is missing.");
            return;
        }

        const registrationData = {
            username: formData.username,
            password: formData.password,
            customer_id: customerId,
        };

        try {
            // Step 1: Register the user
            const registerResponse = await fetch("http://localhost:8080/api/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(registrationData),
            });

            if (!registerResponse.ok) {
                const errorData = await registerResponse.json();
                throw new Error(errorData.message || "Failed to create account");
            }
            const data = await registerResponse.json();
            const token = data.token;


            const cardResponse = await fetch(`http://localhost:8080/api/card/generate`, {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                }
            });

            console.log("Card response:", cardResponse);
            if (!cardResponse.ok) {
                throw new Error("Customer is not qualified for a credit card. Please contact customer support.");
            }
            const cardData = await cardResponse.json();
            console.log("Card data:", cardData);
            router.push(`/login`);
        } catch (error: any) {
            alert(error.message);
            router.push("/");
        }
    };

    return (
        <section className={style.formContainer}>
            <div id={style.formSection}>
                <h2 id={style.formTitle}>Create Account</h2>
                <form onSubmit={handleSubmit}>
                    <div id={style.inputGroup}>
                        <label>Username</label>
                        <input
                            type="text"
                            name="username"
                            placeholder="Username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div id={style.inputGroup}>
                        <label>Password</label>
                        <input
                            type="password"
                            name="password"
                            placeholder="Password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                        />
                    </div>

                    <button type="submit" className={style.formButton}>
                        Submit
                    </button>
                </form>
            </div>
        </section>
    );
}
