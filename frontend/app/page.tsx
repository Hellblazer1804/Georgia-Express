"use client";
import React from 'react';
import {usePathname} from 'next/navigation';
import LoginSignupForm from './components/LoginSignupForm';
import {useRouter} from "next/navigation";

const Page: React.FC = () => {
    const pathname = usePathname();
    console.log("current path:" + pathname);
    const router = useRouter();

    const handleLogin = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        //login logic will go here

        router.push('./home');
    };

    return (
        <div>
            <LoginSignupForm
                formType="Login"
                onSubmit={handleLogin}
                buttonText="Login"
                linkText="Sign up"
                linkAction={() => router.push('./signup')}
                isLogin={true}
            />
        </div>
    );
};

export default Page;
