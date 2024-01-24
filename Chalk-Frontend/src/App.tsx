import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Login } from "./components/pages/Login&Register/Login";
import { Register } from "./components/pages/Login&Register/Register";
import { HomePage } from "./components/pages/HomePage/HomePage";
import { UserContext } from "./UserContext";
import { useContext } from "react";
import { ExerciseBankPage } from "./components/pages/ExerciseBankPage/ExerciseBankPage";
import { FrontPage } from "./components/pages/FrontPage/FrontPage";
import { WebApp } from "./WebApp";
import { TestPage } from "./components/pages/Tests/TestList/TestPage";
import { Settings } from "./components/pages/Settings/Settings";
import { Subscription } from "./components/pages/HomePage/Subscription/Subscription";
import { SearchList } from "./components/pages/SearchList/SearchList";
import { SolveTest } from "./components/pages/Tests/SolveTest/SolveTest";
import { GroupsPage } from "./components/pages/Groups/GroupsPage";
import { AvaliacoesPage } from "./components/pages/Groups/AvaliacoesPage";
import { TestesPartilhadosPage } from "./components/pages/Groups/TestesPartilhadosPage";
import { AlunosPage } from "./components/pages/Groups/AlunosPage";
import { GroupNavBar } from "./components/pages/Groups/GroupNavBar";
import { EvaluationPage } from "./components/pages/Groups/EvaluationPage";
import { Correction } from "./components/pages/Tests/Correction/Correction";
import { PreviewTest } from "./components/pages/Tests/Preview/PreviewTest";
import "./App.css";
import "./components/interactiveElements/Icon.css";
import { EditTest } from "./components/pages/Tests/EditTest/EditTest";
import { CreateTestPage } from "./components/pages/Tests/EditTest/CreateTest";
import { UnderMaintenance } from "./components/UnderMaintenence";

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
          element={user.authenticated ? <WebApp /> : <Login />}
          // element={<WebApp />}
        >
          <Route index element={<FrontPage />} />
          <Route path="search" element={<SearchList />} />
          <Route path="exercise-bank" element={<ExerciseBankPage />} />
          <Route path="create-test" element={<CreateTestPage />} />
          <Route path="tests">
            <Route path="" element={<TestPage />} />
            <Route path=":testID">
              <Route path="edit" element={<EditTest />} />
              <Route path="preview" element={<PreviewTest />} />
              <Route path="solve" element={<SolveTest />} />
              <Route path="correction" element={<Correction />} />
            </Route>
          </Route>
          <Route path="profile" element={<Settings />} />
          <Route path="groups" element={<GroupsPage />} />
          <Route path="groups/:id" element={<GroupNavBar />}>
            <Route path="alunos" element={<AlunosPage />} />
            <Route path="testes" element={<TestesPartilhadosPage />} />
            <Route path="definitions" element={<UnderMaintenance />} />
            <Route path="avaliacoes">
              <Route index path="" element={<AvaliacoesPage />} />
              <Route path=":results_id" element={<EvaluationPage />} />
            </Route>
          </Route>
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
