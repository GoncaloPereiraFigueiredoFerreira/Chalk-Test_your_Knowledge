import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Login } from "./components/pages/Login/Login.js";
import { Register } from "./components/pages/Register/Register.js";
import { HomePage } from "./components/pages/HomePage/HomePage.js";
import { FrontPage } from "./components/pages/FrontPage/FrontPage.js";
import { Sidebar } from "./components/objects/Sidebar/Sidebar.js";
import { Settings } from "./components/pages/Settings/Settings.js";
import { Subscription } from "./components/pages/Subsciption/Subscription.js";
import { Correction } from "./components/pages/Correction/Correction.js";
import "flowbite";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/" element={<HomePage />} />
        <Route path="/pricing" element={<Subscription />} />
        <Route path="/settings" element={<Settings />} />
        <Route path="/user" element={<Sidebar />}>
          <Route index element={<FrontPage />} />
          <Route index element={<Correction />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
