import { Route, Router, Routes } from "react-router-dom";
import "./App.css";

function App() {
  return (
   <Router>
    <Routes>
      <Route path="/" element={<div>Home</div>} />
      <Route path="/login" element={<div>Login</div>} />
      <Route path="/signup" element={<div>Signup</div>} />
    </Routes>
   </Router>
  );
}

export default App;
