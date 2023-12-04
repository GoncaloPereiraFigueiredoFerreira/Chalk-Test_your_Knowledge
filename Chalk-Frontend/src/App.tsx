import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Login } from "./components/pages/Login/Login";
import { Register } from "./components/pages/Register/Register";
import { HomePage } from "./components/pages/HomePage/HomePage";
import "./App.css";
import { UserContext } from "./UserContext";
import { useContext } from "react";
import { FrontPage } from "./components/pages/FrontPage/FrontPage";
import { RealFrontPage } from "./components/pages/RealFrontPage/RealFrontPage";
import { WebApp } from "./WebApp";
import { TestPage } from "./components/pages/TestPage/TestPage";
import { Settings } from "./components/pages/Settings/Settings";
import { Subscription } from "./components/pages/Subscription/Subscription";
import { Correction } from "./components/pages/Correction/Correction";

function App() {
  const { user } = useContext(UserContext);
  return (
    <Router>
      <Routes>
        <Route index element={<HomePage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route path="/settings" element={<Settings />} />
        <Route path="/pricing" element={<Subscription />} />
        <Route
          path="/webapp"
          element={user.authenticated ? <WebApp /> : <WebApp /> /* <Login /> */}
        >
          <Route index element={<RealFrontPage />} />
          <Route path="exercise-bank" element={<FrontPage />} />
          <Route path="tests" element={<TestPage />}>
            <Route index path="" />
            <Route path="group/:id" />
          </Route>
          <Route path="profile" element={<Settings />} />

          <Route path="correction" element={<Correction />} />
          {/* <Route path="edit" element={<EditExercisePage />} /> */}
          {/*<Route path="catalog" element={<Catalog />} />*/}
          {/*<Route path="test"  element={<TestCreator />} />*/}
          {/*<Route path="group" element={<Group />} />*/}
          {/*<Route path="search" element={<Group />} />*/}

          {/*<Route path="upgrade" element={<Group />} />*/}
          {/*<Route path="history" element={<Group />} />*/}
          {/*<Route path="exercise-list" element={<Group />} />*/}
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
