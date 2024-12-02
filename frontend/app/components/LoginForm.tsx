"use client";

import React, { useState } from "react";
import style from "./LoginForm.module.css";
import { useRouter } from "next/navigation";

export default function LoginForm() {
    const router = useRouter();
    const [loginDetails, setLoginDetails] = useState({ username: "", password: "" });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setLoginDetails((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(loginDetails),
            });

            if (response.ok) {
                const data = await response.json();
                const token = data.token;
                console.log("token in login form", token);
                console.log("data in login form", data);
                const customerId = data.customerId;
                console.log("customerId in login form", customerId);

                if (token) {
                    localStorage.setItem("token", token);
                    router.push(`./overview?id=${customerId}`);
                } else {
                    alert("Login successful, but customer information is missing.");
                }
            } else {
                alert("Login failed. Please check your credentials.");
            }
        } catch (error) {
            console.error("Error during login:", error);
            alert("An error occurred during login. Please try again.");
        }
    };

    return (
        <section className={style.formContainer}>
            <div id={style.formSection}>
                <h2 id={style.formTitle}>Login</h2>
                <form onSubmit={handleSubmit}>
                    <div id={style.inputGroup}>
                        <label>Username</label>
                        <input
                            type="text"
                            name="username"
                            placeholder="Username"
                            value={loginDetails.username}
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
                            value={loginDetails.password}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <button type="submit" className={style.formButton}>
                        Login
                    </button>
                    <a href="./createAccount">Create account</a>
                </form>
            </div>
        </section>
    );
}