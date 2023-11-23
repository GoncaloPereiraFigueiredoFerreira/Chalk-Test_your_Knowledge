import { useContext, createContext } from "react";

import { UserAction, UserState } from "./UserInterface";

export const UserContext3 = createContext<
  { userState: UserState; dispatch: React.Dispatch<UserAction> } | undefined
>(undefined);

export function useUserContext() {
  const user = useContext(UserContext3);
  if (user === undefined) {
    throw new Error("useUserContext must be used with a UserContext.Provider");
  }
  return user;
}
