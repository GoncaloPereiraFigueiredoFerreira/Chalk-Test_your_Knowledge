import { createContext } from "react";

export const APIContext = createContext<{
  backendAPI: string;
  authAPI: string;
  chalkyAPI: string;
}>({ backendAPI: "", authAPI: "", chalkyAPI: "" });

export function APIProvider({ children }: any) {
  const AUTHSERVER = import.meta.env.VITE_AUTH;
  const BACKSERVER = import.meta.env.VITE_BACKEND;
  const CHALKYSERVER = import.meta.env.VITE_AI_API;

  return (
    <APIContext.Provider
      value={{
        backendAPI: BACKSERVER,
        authAPI: AUTHSERVER,
        chalkyAPI: CHALKYSERVER,
      }}
    >
      {children}
    </APIContext.Provider>
  );
}
