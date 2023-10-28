import { useState } from 'react';
import './Login.css';
import { Link } from 'react-router-dom';

export function Login({
}) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorState, setErrorState] = useState(false);

    const validateForm = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()

        let login = {
            "email": email,
            "password": password
        }
        console.log(login);
    };

    return (
        <div className='bg_color'>
            <div className='whitebox'>
                <form className='form_login' onSubmit={validateForm}>
                    <div className='form_title_login'>
                        <img className={'button_image'} src={"images/preto_no_branco.png"} alt={""} />
                        <h1> Login </h1>
                    </div>
                    <div className='form_content_login'>
                        <h3> Email </h3>
                        <input 
                            className={errorState?'error_login':''}    
                            type='email'
                            placeholder='email'
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                        <h3> Password </h3>
                        <input
                            className={errorState?'error_login':''}    
                            type='password'
                            placeholder='password'
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    {errorState?
                        <div className='register_label_login'>
                            <label className='error_login'>*Your login credentials don’t match </label> <label className='error_login'>an account in our system</label>
                        </div>
                        :
                        null
                    }
                    <div className='register_label_login'>
                        <label>Don’t have an account?</label>
                        <Link to='/register' className='link'>
                            Sign up
                        </Link>
                    </div>
                    
                    <div className='buttons_login'>
                        <button type='submit'>Login</button>
                    </div>
                </form>
            </div>
        </div>
    );
}