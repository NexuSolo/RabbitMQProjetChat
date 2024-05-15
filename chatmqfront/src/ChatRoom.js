import React, { useState, useRef } from 'react';
import './ChatRoom.css';
import io from 'socket.io-client';

const ChatRoom = ({ username }) => {
  const [messages, setMessages] = useState([]);
  const messageInputRef = useRef(null); // Créer une référence pour l'élément input
  const socket = io.connect('http://localhost:5000?username=' + encodeURIComponent(username));

  const handleMessageSend = () => {
    const message = messageInputRef.current.value; // Accéder à la valeur de l'input à travers la référence
    socket.emit('message', message);
    messageInputRef.current.value = ''; // Efface le contenu de l'input après l'envoi
  };

  const handleLogout = () => {
    localStorage.removeItem('username');
    window.location.href = '/';
  };

  return (
    <div className='chatroom'>
      <button onClick={handleLogout} className="logout-button">Logout</button>
      <div className="chat-room-container">
        <div className="input-container">
          <input
            type="text"
            placeholder="Ecrire un message..."
            className="message-input"
            ref={messageInputRef} // Utilise la référence pour l'input
          />
          <div className="send-button-container">
            <img onClick={handleMessageSend} src="send.png" alt="Send" className="send-button" />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatRoom;
