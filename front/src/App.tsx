import { Link, Route, Routes } from "react-router-dom"
import "./App.css"
import { Login } from "./Login";
import { Chatroom } from "./ChatRoom";

const App = () => {
  return (
<div>
      <Link to="/">Login</Link>
      <Link to="/chatroom">Chatroom</Link>
      <Routes>
        <Route path="/">
          <Route index element={<Login />} />
          <Route path="chatroom" element={<Chatroom />} />
        </Route>
      </Routes>
    </div>
  )

}

export default App
