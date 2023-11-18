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
  MoonIcon,
  SunIcon,
} from "../SVGImages/SVGImages.tsx";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { MainLogo } from "../../MainLogo.tsx";

const groups: { [key: string]: string } = {
  "1": "Professores da escola AFS Gualtar",
  "2": "Turma A",
  "3": "Turma F",
};

interface SidebarProps {
  isOpen: boolean;
  toggle: (value: boolean) => void;
  selected: number;
  setSelected: (value: number) => void;
}

export function Sidebar({
  isOpen,
  toggle,
  selected,
  setSelected,
}: SidebarProps) {
  const [group, setGroup] = useState("0");
  const [showGroup, setShowGroup] = useState(false);
  const [darkMode, setDarkMode] = useState(
    () => localStorage.getItem("dark-mode") === "true"
  );

  useEffect(() => {
    document.documentElement.classList.toggle("dark", darkMode);
    localStorage.setItem("dark-mode", darkMode ? "true" : "false");
  }, [darkMode]);

  const toggleDarkMode = () => {
    setDarkMode((prevMode) => !prevMode);
  };

  function getGroup(num: string) {
    if (num === "0" || showGroup) {
      return (
        <>
          <GroupIcon style={"group-gray-icon"} />
          <span className={`sidebar-dropdown-item ${isOpen ? "" : "hidden"}`}>
            Grupos
          </span>
          {isOpen ? (showGroup ? UpArrowIcon() : DownArrowIcon()) : null}
        </>
      );
    } else if (num in groups) {
      return (
        <>
          <GraduateIcon style={"group-gray-icon"} />
          <span className={`sidebar-dropdown-item ${isOpen ? "" : "hidden"}`}>
            {groups[num]}
          </span>
          {isOpen ? (showGroup ? UpArrowIcon() : DownArrowIcon()) : null}
        </>
      );
    }
  }

  return (
    <aside
      className={`sidebar-background bg-1-3 ${isOpen ? "" : "w-max"}`}
      aria-label="Sidebar"
    >
      <div className="flex flex-row gap-3">
        <button
          type="button"
          className="sidebar-item bg-btn-1 w-auto"
          onClick={() => {
            toggle(!isOpen);
            setShowGroup(false);
          }}
        >
          <SidebarIcon style={"group-gray-icon"} />
        </button>
        <div className={` ${isOpen ? "" : "hidden"}`}>
          <MainLogo></MainLogo>
        </div>
      </div>
      <ul className="sidebar-divisions border-t-0">
        <li>
          <button
            type="button"
            onClick={() => {
              toggle(true);
              setShowGroup(!showGroup);
            }}
            className="sidebar-item bg-btn-1 group"
          >
            {getGroup(group)}
          </button>
          <ul className={`${showGroup ? "" : "hidden"} sidebar-dropdown`}>
            <li>
              <button
                onClick={() => setGroup("0")}
                className="sidebar-item bg-btn-1 group"
              >
                <GraduateIcon style={"group-gray-icon"} />
                <span className={isOpen ? "" : "hidden"}>Geral</span>
              </button>
            </li>
            {Object.entries(groups).map(([key, item]) => (
              <li key={key}>
                <button
                  onClick={() => setGroup(key)}
                  className="sidebar-item bg-btn-1 group"
                >
                  <GraduateIcon style={"group-gray-icon"} />
                  <span className={isOpen ? "" : "hidden"}>{item}</span>
                </button>
              </li>
            ))}
          </ul>
        </li>
      </ul>

      <ul className="sidebar-divisions border-gray-3">
        <li>
          <Link to={"/user/test"}>
            <button className="sidebar-item bg-btn-1 group">
              <CheckListIcon style={"group-gray-icon"} />
              <span className={isOpen ? "" : "hidden"}>Avaliações</span>
            </button>
          </Link>
        </li>
        <li>
          <Link to={"/user"}>
            <button className="sidebar-item bg-btn-1 group">
              <PenIcon style={"group-gray-icon"} />
              <span className={isOpen ? "" : "hidden"}>Exercicos</span>
            </button>
          </Link>
        </li>
        <li>
          <button className="sidebar-item bg-btn-1 group">
            <FoldersIcon style={"group-gray-icon"} />
            <span className={isOpen ? "" : "hidden"}>Rubricas</span>
          </button>
        </li>
      </ul>
      <ul className="sidebar-divisions border-gray-3">
        <li>
          <button className="sidebar-item bg-btn-1 group">
            <HelpIcon style={"group-gray-icon"} />
            <span className={isOpen ? "" : "hidden"}>Ajuda</span>
          </button>
        </li>
        <li>
          <button className="sidebar-item bg-btn-1 group">
            <TeacherIcon style={"group-gray-icon"} />
            <span className={isOpen ? "" : "hidden"}>Tutoriais</span>
          </button>
        </li>
        <li>
          <button className="sidebar-item bg-btn-1 group">
            <MessageBoxIcon style={"group-gray-icon"} />
            <span className={isOpen ? "" : "hidden"}>Sugestões</span>
          </button>
        </li>
        <li>
          <button
            className="sidebar-item bg-btn-1 group"
            onClick={toggleDarkMode}
          >
            {darkMode ? (
              <>
                <SunIcon style={"group-gray-icon"}></SunIcon>
                <span className={isOpen ? "" : "hidden"}>
                  Light Mode Toogle
                </span>
              </>
            ) : (
              <>
                <MoonIcon style={"group-gray-icon"}></MoonIcon>{" "}
                <span className={isOpen ? "" : "hidden"}>Dark Mode Toogle</span>
              </>
            )}
          </button>
        </li>
        <li>
          <Link to={"/user/catalog"}>
            <button className="sidebar-item bg-btn-1 group">
              <SettingsIcon style={"group-gray-icon"} />
              <span className={isOpen ? "" : "hidden"}>Definições</span>
            </button>
          </Link>
        </li>
      </ul>
    </aside>
  );
}
