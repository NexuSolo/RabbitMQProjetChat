import React, { useState, useEffect } from 'react';
import socketIOClient from 'socket.io-client';
import './ChatRoom.css';

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
  }, [messages, username]);

  const handleMessageSend = () => {
    socket.emit('sendMessage', { username, message: messageInput });
    setMessageInput('');
  };

  const handleLogout = () => {
    localStorage.removeItem('username');
    window.location.href = '/';
  };

  return (

    <div className='chatroom'>
      <button onClick={handleLogout} className="logout-button">Logout</button>
      <div className="chat-room-container">
        <div className="message-container">
          {messages.map((msg, index) => (
            <div key={index} className={`message ${msg.username === username ? 'own-message' : ''}`}>
              <span className="username">{msg.username}:</span>
              <span className="message-content">{msg.message}</span>
            </div>
          ))}
        </div>
        <div className="input-container">
          <input
            type="text"
            value={messageInput}
            onChange={(e) => setMessageInput(e.target.value)}
            placeholder="Ecrire un message..."
            className="message-input"
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
