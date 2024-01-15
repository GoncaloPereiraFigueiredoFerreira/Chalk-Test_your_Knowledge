import { createContext, useEffect, useState } from "react";
import { CookiesProvider, useCookies } from "react-cookie";

export enum UserRole {
  SPECIALIST = "SPECIALIST",
  STUDENT = "STUDENT",
}

export interface Course {
  id: string;
  name: string;
}

export interface User {
  id: string;
  email: string;
  name: string;
  photoPath: string;
  role: UserRole;
  courses?: Course[];
}

interface UserState {
  authenticated: boolean;
  user?: User;
}

export const UserContext = createContext<{
  user: UserState;
  login: (user: User) => void;
  logout: () => void;
}>({ user: { authenticated: false }, login: () => {}, logout: () => {} });

export function UserProvider({ children }: any) {
  const [userState, setUserState] = useState<UserState>({
    authenticated: false,
  });

  const AUTHSERVER = import.meta.env.VITE_AUTH;

  const [cookies, setCookie] = useCookies(["chalkauthtoken"]);

  const login = (user: User) => {
    let newState: UserState = {
      authenticated: true,
      user: { ...user },
    };
    setUserState(newState);
    localStorage.setItem("user", JSON.stringify(newState));
  };

  const logout = () => {
    setUserState({ authenticated: false });
    setCookie("chalkauthtoken", "", { path: "/" });
    localStorage.removeItem("user");
  };

  useEffect(() => {
    // Se existe a cookie
    if ("chalkauthtoken" in cookies && cookies.chalkauthtoken !== "") {
      let user = localStorage.getItem("user");
      // Se estiver no local storage
      if (user != null) {
        login(JSON.parse(user).user);
      } else {
        // Ir buscar à autenticação e fazer login
        fetch(AUTHSERVER + "user", {
          mode: "cors",
          credentials: "include",
          headers: {
            "Content-type": "application/json",
            Authorization: "Bearer " + cookies.chalkauthtoken,
          },
        }).then((response) => {
          response.json().then((json) => {
            login({
              ...json,
              courses: [],
              photoPath: "",
            });
          });
        });
      }
    } else {
      logout();
    }
  }, []);

  return (
    <UserContext.Provider value={{ user: userState, login, logout }}>
      <CookiesProvider>{children}</CookiesProvider>
    </UserContext.Provider>
  );
}
