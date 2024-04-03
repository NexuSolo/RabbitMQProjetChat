import React, { useState } from 'react';
import './Login.css';

const Login = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = () => {
        const requestBody = JSON.stringify({ username, password });

        fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: requestBody
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Login failed.');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            onLogin();
        })
        .catch(error => {
            console.error('Error:', error);
        });
    };

    return (
        <div className='login'>
            <div className='login-logo'>
                <img className='logo-img' src="message.png" alt="Logo" />
            </div>
            <div className='login-input'>
                <div className='username-area'>
                    <h4>Nom d'utilisateur</h4>
                    <input type="text" placeholder="Exemple : Nexus" value={username} onChange={(e) => setUsername(e.target.value)} />
                </div>
                <div className='password-area'>
                    <h4>Mot de passe</h4>
                    <input type="password" placeholder="··············" value={password} onChange={(e) => setPassword(e.target.value)} />
                </div>
            </div>
            <div className='login-button-area'>
                <button className='login-button' onClick={handleLogin}>Se connecter</button>
            </div>
        </div>
    );
};

export default Login;
