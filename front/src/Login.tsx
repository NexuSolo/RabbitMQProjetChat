import React, { ChangeEvent, useContext, useState } from 'react';
import './Login.css';
import io, { Socket } from 'socket.io-client';
import { useNavigate } from 'react-router-dom';

export const Login = () =>  {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleUsernameChange = (e : ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value); // Met à jour le state 'username' avec la valeur de l'input
    };

    const handlePasswordChange = (e : ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value); // Met à jour le state 'password' avec la valeur de l'input
    };

    const handleLogin = () => {
        // console.log('http://localhost:5000?username=' + username);
        const socket : Socket = io('http://localhost:5000?username=' + username);
        const requestBody = JSON.stringify({ username, password });
        socket.on('connect', function() {
            console.log('Connected to the server');

            navigate('/Chatroom');
        });

        // socket.on('message', function (message) {
        //     console.log('Received a message: ', message);
        //     const messageElement = document.createElement('p');
        //     messageElement.textContent = `${message.username}: ${message.content}`;
        //     document.body.appendChild(messageElement);
        //   });
       
    };

    return (
        <div className='login'>
            <div className='login-logo'>
                <img className='logo-img' src="message.png" alt="Logo" />
            </div>
            <div className='login-input'>
                <div className='username-area'>
                    <h4>Nom d'utilisateur</h4>
                    <input type="text" placeholder="Exemple : Nexus" value={username} onChange={handleUsernameChange} />
                </div>
                <div className='password-area'>
                    <h4>Mot de passe</h4>
                    <input type="password" placeholder="··············" value={password} onChange={handlePasswordChange} />
                </div>
            </div>
            <div className='login-button-area'>
                <button className='login-button' onClick={handleLogin}>Se connecter</button>
            </div>
        </div>
    );
}