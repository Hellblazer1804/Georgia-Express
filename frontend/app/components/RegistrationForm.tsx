"use client"
import React, { useState } from "react";
import style from "./RegistrationForm.module.css";
import { useRouter } from "next/navigation";

export default function RegistrationForm() {
    const router = useRouter();
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        dateOfBirth: '',
        ssn: '',
        salary: '',
        creditScore: '',
        email: '',
        address: '',
        phoneNumber: ''
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const customer = {
        name: `${formData.firstName} ${formData.lastName}`,
        ssn: formData.ssn,
        email: formData.email,
        phoneNumber: formData.phoneNumber,
        address: formData.address,
        creditScore: parseInt(formData.creditScore, 10),
        salary: parseInt(formData.salary, 10),
        dateOfBirth: formData.dateOfBirth,
    };

    try {
        const response = await fetch('http://localhost:8080/api/customer', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(customer),
        });

        if (response.ok) {
            const responseData = await response.json();
            const customerId = responseData.customerId;
            router.push(`./createAccount?id=${customerId}`);



        } else {
            alert("Failed to add customer. Please try again.");
        }
    } catch (error) {
        console.error("Error adding customer:", error);
        alert("An error occurred. Please try again.");
    }
};



    return (
        <div className={style.container}>
            <div className={style.sidebar}>
                <h1 className={style.logo}>Georgia<br/>Express</h1>
            </div>
            <div className={style.formContainer}>
                <h2 className={style.formTitle}>Apply</h2>
                <form className={style.form} onSubmit={handleSubmit}>
                    <div className={style.inputGroup}>
                        <input
                            type="text"
                            name="firstName"
                            placeholder="First Name"
                            required
                            value={formData.firstName}
                            onChange={handleChange}
                        />
                        <input
                            type="text"
                            name="lastName"
                            placeholder="Last Name"
                            required
                            value={formData.lastName}
                            onChange={handleChange}
                        />
                    </div>
                    <div className={style.inputGroup}>
                        <input
                            type="date"
                            name="dateOfBirth"
                            placeholder="Date Of Birth"
                            required
                            value={formData.dateOfBirth}
                            onChange={handleChange}
                        />
                        <input
                            type="text"
                            name="ssn"
                            placeholder="SSN"
                            required
                            value={formData.ssn}
                            onChange={handleChange}
                        />
                    </div>
                    <div className={style.inputGroup}>
                        <input
                            type="number"
                            name="salary"
                            placeholder="Salary"
                            required
                            value={formData.salary}
                            onChange={handleChange}
                        />
                        <input
                            type="number"
                            name="creditScore"
                            placeholder="Credit Score"
                            required
                            value={formData.creditScore}
                            onChange={handleChange}
                        />
                    </div>
                    <div className={style.inputGroupSingle}>
                        <input
                            type="email"
                            name="email"
                            placeholder="Email"
                            required
                            value={formData.email}
                            onChange={handleChange}
                        />
                    </div>
                    <div className={style.inputGroupSingle}>
                        <input
                            type="text"
                            name="address"
                            placeholder="Address"
                            required
                            value={formData.address}
                            onChange={handleChange}
                        />
                    </div>
                    <div className={style.inputGroupSingle}>
                        <input
                            type="tel"
                            name="phoneNumber"
                            placeholder="Phone Number"
                            required
                            value={formData.phoneNumber}
                            onChange={handleChange}
                        />
                    </div>
                    <button type="submit" className={style.submitButton}>Submit</button>
                </form>
                <div className={style.homeButton}>
                    <button>
                        <a href='./login'>Login</a>
                    </button>
                </div>
            </div>

        </div>
    );
}
