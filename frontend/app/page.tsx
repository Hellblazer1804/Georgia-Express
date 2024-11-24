"use client";
import React from "react";
import { usePathname, useRouter } from "next/navigation";
import RegistrationForm from "./components/RegistrationForm";

const Page: React.FC = () => {
    const pathname = usePathname();
    console.log("current path:" + pathname);
    const router = useRouter();

    const handleLogin = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        // login logic will go here

        router.push("./overview");
    };

    return <RegistrationForm />;
};

export default Page;

