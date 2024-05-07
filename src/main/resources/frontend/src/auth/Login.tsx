import {Link} from "react-router-dom";
import './auth.css'
import {useState} from "react";
import {login} from "./actions";

export const Login = () => {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [errorMessage, setErrorMessage] = useState<string>('');

    const validateFields = () => {
        if (!username || !password) {
            setErrorMessage('All fields are required.');
            return false;
        }
        setErrorMessage('');
        return true;
    };

    const validateAndLogin = () => {
        if (!validateFields()) return;
        login(username, password);
    };

    return (
        <div className={'auth-all'}>
            <input
                type={'text'}
                placeholder={'Username'}
                className={'input-box'}
                onChange={(event) => setUsername(event.target.value)}
            />
            <input
                type={'password'}
                placeholder={'Password'}
                className={'input-box'}
                onChange={(event) => setPassword(event.target.value)}
            />
            <div className={'error'}>{errorMessage}</div>
            <div className={'button'} onClick={validateAndLogin}><b>LOGIN</b></div>
            <p>New user? <Link to={'/register'}><u>Register</u></Link> instead.</p>
        </div>
    );
}