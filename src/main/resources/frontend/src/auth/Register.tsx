import {register} from "./actions";
import {useState} from "react";

export const Register = () => {
    const [email, setEmail] = useState<string>('');
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [repeatPassword, setRepeatPassword] = useState<string>('');
    const [errorMessage, setErrorMessage] = useState<string>('');

    const validateFields = () => {
        if (!username || !password || !repeatPassword || !email) {
            setErrorMessage('All fields are required.');
            return false;
        }
        if (repeatPassword !== password) {
            setErrorMessage('Passwords don\'t match.');
            return false;
        }
        setErrorMessage('');
        return true;
    };

    const validateAndRegister = () => {
        if (!validateFields()) return;
        register(username, password, email).then(r => {
            if (!r) {
                // server error
                setErrorMessage('Server error, try again later.');
            } else if (r.message) {
                // 4xx
                setErrorMessage(r.message);
            } else {
                // success
                window.location.href = '/';
            }
        });
    };

    return (
        <div className={'auth-all'}>
            <input
                type={'text'}
                placeholder={'Email'}
                className={'input-box'}
                onChange={(event) => setEmail(event.target.value)}
            />
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
            <input
                type={'password'}
                placeholder={'Repeat Password'}
                className={'input-box'}
                onChange={(event) => setRepeatPassword(event.target.value)}
            />
            <div className={'error'}>{errorMessage}</div>
            <div className={'button'} onClick={validateAndRegister}><b>REGISTER</b></div>
        </div>
    );
}