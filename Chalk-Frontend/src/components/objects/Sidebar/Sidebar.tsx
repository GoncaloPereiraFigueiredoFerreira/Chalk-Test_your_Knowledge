import "./Sidebar.css";
import {
  SidebarIcon,
  DownArrowIcon,
  FoldersIcon,
  CheckListIcon,
  SettingsIcon,
  GroupIcon,
  PenIcon,
  MessageBoxIcon,
  HelpIcon,
  TeacherIcon,
  GraduateIcon,
  UpArrowIcon,
} from "../SVGImages/SVGImages.tsx";
import { useState } from "react";
import { Outlet } from "react-router-dom";

const groups: { [key: string]: string } = {
  "1": "Professores da escola AFS Gualtar",
  "2": "Turma A",
  "3": "Turma F",
};

export function Sidebar() {
  // const [selected,setSelected] = useState(0)
  const [sidebarIsOpen, setSidebarIsOpen] = useState(true);
  const [group, setGroup] = useState("0");
  const [showGroup, setShowGroup] = useState(false);

  function getGroup(num: string) {
    console.log(groups[num]);

    if (num === "0" || showGroup) {
      return (
        <>
          <GroupIcon style={"sidebar-icon"} />
          <span
            className={`sidebar-dropdown-item ${sidebarIsOpen ? "" : "hidden"}`}
          >
            Grupos
          </span>
          {sidebarIsOpen ? (showGroup ? UpArrowIcon() : DownArrowIcon()) : null}
        </>
      );
    } else if (num in groups) {
      return (
        <>
          <GraduateIcon style={"sidebar-icon"} />
          <span
            className={`sidebar-dropdown-item ${sidebarIsOpen ? "" : "hidden"}`}
          >
            {groups[num]}
          </span>
          {sidebarIsOpen ? (showGroup ? UpArrowIcon() : DownArrowIcon()) : null}
        </>
      );
    }
  }

  return (
    <div className="flex flex-row h-auto dark:bg-gray-800 bg-gray-200">
      <aside
        className={`sidebar-background ${sidebarIsOpen ? "" : "w-max"}`}
        aria-label="Sidebar"
      >
        <div className="flex flex-row gap-3">
          <button
            type="button"
            className="sidebar-item w-auto"
            onClick={() => {
              setSidebarIsOpen(!sidebarIsOpen);
              setShowGroup(false);
            }}
          >
            <SidebarIcon style={"white-icon"} />
          </button>
          <div className={`flex mr-4 ${sidebarIsOpen ? "" : "hidden"}`}>
            <img
              src="https://flowbite.s3.amazonaws.com/logo.svg"
              className="mr-3 h-8"
              alt="FlowBite Logo"
            />
            <span className="self-center text-2xl font-semibold whitespace-nowrap dark:text-white">
              Flowbite
            </span>
          </div>
        </div>
        <ul className="sidebar-divisions border-t-0">
          <li>
            <button
              type="button"
              onClick={() => {
                setSidebarIsOpen(true);
                setShowGroup(!showGroup);
              }}
              className="sidebar-item group"
            >
              {getGroup(group)}
            </button>
            <ul className={`${showGroup ? "" : "hidden"} sidebar-dropdown`}>
              <li>
                <button
                  onClick={() => setGroup("0")}
                  className="sidebar-item group"
                >
                  <GraduateIcon style={"sidebar-icon"} />
                  <span className={sidebarIsOpen ? "" : "hidden"}>Geral</span>
                </button>
              </li>
              {Object.entries(groups).map(([key, item]) => (
                <li key={key}>
                  <button
                    onClick={() => setGroup(key)}
                    className="sidebar-item group"
                  >
                    <GraduateIcon style={"sidebar-icon"} />
                    <span className={sidebarIsOpen ? "" : "hidden"}>
                      {item}
                    </span>
                  </button>
                </li>
              ))}
            </ul>
          </li>
        </ul>

        <ul className="sidebar-divisions">
          <li>
            <button className="sidebar-item group">
              <CheckListIcon style={"sidebar-icon"} />
              <span className={sidebarIsOpen ? "" : "hidden"}>Avaliações</span>
            </button>
          </li>
          <li>
            <button className="sidebar-item group">
              <PenIcon style={"sidebar-icon"} />
              <span className={sidebarIsOpen ? "" : "hidden"}>Exercicos</span>
            </button>
          </li>
          <li>
            <button className="sidebar-item group">
              <FoldersIcon style={"sidebar-icon"} />
              <span className={sidebarIsOpen ? "" : "hidden"}>Rubricas</span>
            </button>
          </li>
        </ul>
        <ul className="sidebar-divisions">
          <li>
            <button className="sidebar-item group">
              <HelpIcon style={"sidebar-icon"} />
              <span className={sidebarIsOpen ? "" : "hidden"}>Ajuda</span>
            </button>
          </li>
          <li>
            <button className="sidebar-item group">
              <TeacherIcon style={"sidebar-icon"} />
              <span className={sidebarIsOpen ? "" : "hidden"}>Tutoriais</span>
            </button>
          </li>
          <li>
            <button className="sidebar-item group">
              <MessageBoxIcon style={"sidebar-icon"} />
              <span className={sidebarIsOpen ? "" : "hidden"}>Sugestões</span>
            </button>
          </li>
          <li>
            <button className="sidebar-item group">
              <SettingsIcon style={"sidebar-icon"} />
              <span className={sidebarIsOpen ? "" : "hidden"}>Defenições</span>
            </button>
          </li>
        </ul>
      </aside>
      <div
        className={`w-full z-10 transition-all ${
          sidebarIsOpen ? "ml-64" : "ml-16"
        }`}
      >
        <Outlet />
      </div>
    </div>
  );
}
