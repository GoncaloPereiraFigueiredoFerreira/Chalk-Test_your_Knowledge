import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { UserProvider } from "./UserContext.tsx";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { APIProvider } from "./APIContext.tsx";
const GCLIENTID = import.meta.env.VITE_G_CLIENTID;

ReactDOM.createRoot(document.getElementById("root")!).render(
  <UserProvider>
    <APIProvider>
      <GoogleOAuthProvider clientId={GCLIENTID}>
        <App />
      </GoogleOAuthProvider>
    </APIProvider>
  </UserProvider>
);
