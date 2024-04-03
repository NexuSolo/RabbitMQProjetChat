import React, { useState } from 'react';
import socketIOClient from 'socket.io-client';
import './Login.css';

const Login = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = () => {
        const socket = socketIOClient('http://localhost:8080');
        socket.emit('login', { username });
        onLogin();
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
