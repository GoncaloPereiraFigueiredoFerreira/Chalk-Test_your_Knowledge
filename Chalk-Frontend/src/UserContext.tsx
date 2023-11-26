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

interface User {
  email: string;
  name: string;
  profilePic: string;
  role: UserRole;
  courses: Course[];
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

  const [cookies, setCookie] = useCookies(["chalkauthtoken"]);

  const login = (user: User) => {
    let newState: UserState = { authenticated: true, user: { ...user } };
    setUserState(newState);
    localStorage.setItem("user", JSON.stringify(newState));
  };

  const logout = () => {
    setUserState({ authenticated: false });
    setCookie("chalkauthtoken", "");
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
