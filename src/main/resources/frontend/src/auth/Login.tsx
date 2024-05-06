import {Link} from "react-router-dom";
import './auth.css'

export const Login = () => {
    return (
        <div className={'auth-all'}>
            <input type={'text'} placeholder={'Username'} className={'input-box'}/>
            <input type={'text'} placeholder={'Password'} className={'input-box'}/>
            <div className={'button'}><b>LOGIN</b></div>
            <p>New user? <Link to={'/register'}><u>Register</u></Link> instead.</p>
        </div>
    );
}