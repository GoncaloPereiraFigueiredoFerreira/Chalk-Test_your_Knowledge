import { useState } from "react";
import { Outlet } from "react-router-dom";
import { Sidebar } from "./components/objects/Sidebar/Sidebar";

export function WebApp() {
  const [isOpen, toggle] = useState(true);

  return (
    <div className="flex flex-row h-full w-full">
      <div
        className={`${
          isOpen ? "min-w-[256px]" : "min-w-[64px]"
        } flex overflow-hidden transition-all duration-700`}
      >
        <Sidebar isOpen={isOpen} toggle={toggle}></Sidebar>
      </div>
      <div className="z-10 h-full w-full">
        <Outlet />
      </div>
      <div className="fixed top-0 left-0 h-screen w-screen bg-gray-300 dark:bg-gray-800 -z-10"></div>
    </div>
  );
}
