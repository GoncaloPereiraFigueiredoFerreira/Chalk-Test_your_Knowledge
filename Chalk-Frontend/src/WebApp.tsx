import { useState } from "react";
import { Outlet } from "react-router-dom";
import { Sidebar } from "./components/objects/Sidebar/Sidebar";

export function WebApp() {
  const [isOpen, toggle] = useState(true);

  return (
    <div className="flex flex-row h-full">
      <Sidebar isOpen={isOpen} toggle={toggle}></Sidebar>
      <div
        className={`w-full z-10 h-full transition-all ${
          isOpen ? "ml-64" : "ml-16"
        }`}
      >
        <Outlet />
      </div>
      <div className="fixed top-0 left-0 h-screen w-screen bg-1-1 -z-10"></div>
    </div>
  );
}
