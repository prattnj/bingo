export const Register = () => {
    return (
        <div className={'auth-all'}>
            <input type={'text'} placeholder={'Email'} className={'input-box'}/>
            <input type={'text'} placeholder={'Username'} className={'input-box'}/>
            <input type={'text'} placeholder={'Password'} className={'input-box'}/>
            <input type={'text'} placeholder={'Repeat Password'} className={'input-box'}/>
            <div className={'button'}><b>REGISTER</b></div>
        </div>
    );
}