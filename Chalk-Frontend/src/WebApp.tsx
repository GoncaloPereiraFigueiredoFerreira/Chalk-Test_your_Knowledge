import { useState } from "react";
import { Outlet } from "react-router-dom";
import { Sidebar } from "./components/objects/Sidebar/Sidebar";

export function WebApp() {
  const [isOpen, toggle] = useState(true);

  return (
    <div className="flex flex-row h-full">
      <Sidebar isOpen={isOpen} toggle={toggle}></Sidebar>
      <div
        className={`z-10 h-full transition-all ${
          isOpen ? "w-[calc(100vw-256px)] ml-64" : "w-[calc(100vw-64px)] ml-16"
        }`}
      >
        <Outlet />
      </div>
      <div className="fixed top-0 left-0 h-screen w-screen bg-[#8ca1be] dark:bg-gray-900 -z-20"></div>
    </div>
  );
}
