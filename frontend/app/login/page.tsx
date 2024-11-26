"use client";
import React from 'react';
import {usePathname} from 'next/navigation';
import LoginForm from '../components/LoginForm'

export default function Page() {
    return (
        <div>
            <LoginForm />
        </div>
    )
}