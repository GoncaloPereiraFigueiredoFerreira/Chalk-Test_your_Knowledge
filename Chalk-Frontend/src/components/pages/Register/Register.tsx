import { useState } from 'react';
import './Register.css';
import '../Login/Login.css';

export function Register({
    
}) {
    const [sex, setSex] = useState(true);
    const [email, setEmail] = useState('');
    const [name, setName] = useState('');
    const [nif, setNIF] = useState('');
    const [telemovel, setTelemovel] = useState('');
    const [password, setPassword] = useState('');
    const [cpass, setCPass] = useState('');
    const [error, setError] = useState(0);

    const validateForm = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        if(password === cpass){

        } else {
            console.log("error register");
        }
    };

    return (
        <div className='bg_color'>
            <div className='whitebox'>
                <form className='form_login' onSubmit={validateForm}>
                    <div className='form_title_register'>
                        <div className='form_title_image_register'>
                            <img className={'button_image'} src={'images/preto_no_branco.png'} alt={''} />
                        </div>
                        <div className='form_title_desc_register'>
                            <label> Register </label>
                        </div>
                    </div>
                    <div className='form_content_login'>
                        <div className='form_sex_register'>
                            <b> Sex: </b>
                            <div className='form_sex_buttons_register'>
                                <button type='button' onClick={() => setSex(true)}>Mr.</button>
                                <button type='button' onClick={() => setSex(false)}>Mrs.</button>
                            </div>
                        </div>
                        <b> Name </b>
                        <input
                            className='input_register'
                            placeholder='Name'
                            onChange={(e) => setName(e.target.value)}
                            required/>
                        <b> Email </b>
                        <input
                            className='input_register'
                            type='email'
                            placeholder='Email'
                            onChange={(e) => setEmail(e.target.value)}
                            required/>
                        <b> Telemovel </b>
                        <input
                            className='input_register'
                            type='number'
                            placeholder='Telemovel'
                            onChange={(e) => setTelemovel(e.target.value)}
                            required/>
                        <b> NIF </b>
                        <input
                            className='input_register'
                            type='number'
                            placeholder='NIF'
                            onChange={(e) => setNIF(e.target.value)}
                            required/>
                        <b> Password </b>
                        <input
                            className='input_register'
                            type='password'
                            placeholder='Password'
                            onChange={(e) => setPassword(e.target.value)}
                            required/>
                        <b> Confirm Password </b>
                        <input
                            className={'input_register' + (error===1? ' security_input_error':'')}
                            type='password'
                            placeholder='Password'
                            onChange={(e) => setCPass(e.target.value)}
                            required/>
                        <label className={error===1? 'scurity_error':'disabled_selected'}>*password são diferentes</label>
                    </div>
                    <div className='register_label_login'>
                        <label>By creating an account, you agree </label>
                        <div>
                            <label> to ParkFinder's </label><label className='Link'>Conditions</label>
                        </div>
                        <div>
                            <label className='Link'> of Use</label>
                            <label> and </label><label className='Link'>Privacy Notice.</label>
                        </div>
                    </div>
                    <div className='buttons_login'>
                        <button type='submit'>Submit</button>
                    </div>
                </form>
            </div>
        </div>
    );
}