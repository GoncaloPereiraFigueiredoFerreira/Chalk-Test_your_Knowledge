import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Login } from "./components/pages/Login/Login.js";
import { Register } from "./components/pages/Register/Register.js";
import { HomePage } from "./components/pages/HomePage/HomePage.js";
import { FrontPage } from "./components/pages/FrontPage/FrontPage.js";
import { Sidebar } from "./components/objects/Sidebar/Sidebar.js";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/" element={<HomePage />} />
        <Route path="/user" element={<Sidebar />}>
          <Route index element={<FrontPage />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
