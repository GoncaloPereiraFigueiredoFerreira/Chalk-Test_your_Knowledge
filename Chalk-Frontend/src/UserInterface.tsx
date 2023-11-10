import { useState } from "react";
import { Outlet } from "react-router-dom";
import { Sidebar } from "./components/objects/Sidebar/Sidebar";
import { UserContext } from "./context";

export interface User {
  id: number;
}

interface UserInterfaceProps {
  userData: User;
}

export function UserInterface({ userData }: UserInterfaceProps) {
  const [selected, setSelected] = useState(0);
  const [isOpen, toggle] = useState(true);

  const [user] = useState(userData);

  return (
    <div className="flex flex-row h-96 dark:bg-gray-800 bg-gray-200">
      <Sidebar
        isOpen={isOpen}
        toggle={toggle}
        selected={selected}
        setSelected={setSelected}
      ></Sidebar>
      <div
        className={`w-full z-10 transition-all ${isOpen ? "ml-64" : "ml-16"}`}
      >
        <UserContext.Provider value={user}>
          <Outlet />
        </UserContext.Provider>
      </div>
    </div>
  );
}
