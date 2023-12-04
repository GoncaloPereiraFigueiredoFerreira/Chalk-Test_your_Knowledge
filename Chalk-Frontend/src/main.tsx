import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { UserProvider } from "./UserContext.tsx";
import { GoogleOAuthProvider } from "@react-oauth/google";
import { APIProvider } from "./APIContext.tsx";
const GCLIENTID = import.meta.env.VITE_G_CLIENTID;

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <APIProvider>
      <UserProvider>
        <GoogleOAuthProvider clientId={GCLIENTID}>
          <App />
        </GoogleOAuthProvider>
      </UserProvider>
    </APIProvider>
  </React.StrictMode>
);
