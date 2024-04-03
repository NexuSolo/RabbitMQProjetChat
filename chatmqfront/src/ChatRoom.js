import React, { useState, useEffect } from 'react';
import socketIOClient from 'socket.io-client';

const ChatRoom = ({ username }) => {
  const [messages, setMessages] = useState([]);
  const [messageInput, setMessageInput] = useState('');
  const [socket, setSocket] = useState(null);

  useEffect(() => {
    const newSocket = socketIOClient('http://localhost:3001');
  
    newSocket.on('message', (newMessage) => {
      setMessages([...messages, newMessage]);
    });
  
    newSocket.emit('join', { username });
  
    setSocket(newSocket);
  
    return () => {
      newSocket.disconnect();
    };
  }, [messages, username]); // Ajoutez messages dans le tableau des dépendances
  

  const handleMessageSend = () => {
    socket.emit('sendMessage', { username, message: messageInput });
    setMessageInput('');
  };

  const handleLogout = () => {
    // Déconnectez-vous en supprimant le nom d'utilisateur stocké
    localStorage.removeItem('username');
    // Redirigez vers la page de connexion
    window.location.href = '/';
  };

  return (
    <div>
      <button onClick={handleLogout}>Logout</button> {/* Bouton de déconnexion */}
      <div>
        {messages.map((msg, index) => (
          <div key={index}>
            <span>{msg.username}:</span>
            <span>{msg.message}</span>
          </div>
        ))}
      </div>
      <input type="text" value={messageInput} onChange={(e) => setMessageInput(e.target.value)} />
      <button onClick={handleMessageSend}>Send</button>
    </div>
  );
};

export default ChatRoom;
