import React, { useState } from 'react';
import Login from './Login';
import ChatRoom from './ChatRoom';

const App = () => {
  const [loggedIn, setLoggedIn] = useState(false);
  const [username, setUsername] = useState('');

  const handleLogin = (username) => {
    setLoggedIn(true);
    setUsername(username);
  };

  return (
    <div>
      {!loggedIn ? <Login onLogin={handleLogin} /> : <ChatRoom username={username} />}
    </div>
  );
};

export default App;
