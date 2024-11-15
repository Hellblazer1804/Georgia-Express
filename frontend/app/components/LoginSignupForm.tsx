import React from "react";
import style from './LoginSignupForm.module.css';

interface FormComponentProps {
    formType: string;
    onSubmit: (e: React.FormEvent<HTMLFormElement>) => void;
    buttonText: string;
    linkText: string;
    linkAction: () => void;
    isLogin: boolean;
}


const LoginSignupForm: React.FC<FormComponentProps> = ({
   formType,
   onSubmit,
   buttonText,
   linkText,
   linkAction,
   isLogin
 }) => {
    return (
      
           
      
        <section className={style.formContainer}>
            <div id = {style.formSection} >
                <h2 id = {style.formTitle}>{formType}</h2>
                <form onSubmit={onSubmit}>
                    <div id={style.inputGroup}>
                        <label>Username</label>
                        <input type="text" placeholder="UserName" required/>
                    </div>
                    <div id={style.inputGroup}>
                        <label>Password</label>
                        <input type="password" placeholder="Password" required/>
                    </div>
       
                    {!isLogin && (
                        <div id={style.inputGroup}>
                            <div className= {style.footerLinks}>
                                <a href="#">Forgot Password</a>
                                <a href = "#" onClick={(e) => {
                                    e.preventDefault();
                                    linkAction();
                                }}>{linkText}</a>
                            </div>
                        </div>
                    )}

                    {isLogin && (
                        <div className= {style.footerLinks}>
                            <a href="#">Forgot Password</a>
                            <a href = "#" onClick={(e) => {
                                e.preventDefault();
                                linkAction();
                            }}>{linkText}</a>
                        </div>
                    )}
                    <button type="submit" className={style.formButton}>{buttonText}</button>

                </form>
           
            </div>
        </section>
    );
};

export default LoginSignupForm;
