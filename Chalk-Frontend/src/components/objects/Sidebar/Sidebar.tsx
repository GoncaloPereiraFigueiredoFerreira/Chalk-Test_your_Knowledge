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
import { UserActionKind } from "../../../UserInterface.tsx";
import { useUserContext } from "../../../context.ts";

const groups = ["Professores da escola AFS Gualtar", "Turma A", "Turma F"];

interface SidebarProps {
  isOpen: boolean;
  toggle: (value: boolean) => void;
}

export function Sidebar({ isOpen, toggle }: SidebarProps) {
  const [showGroup, setShowGroup] = useState(false);
  const [darkMode, setDarkMode] = useState(
    () => localStorage.getItem("dark-mode") === "true"
  );

  const { userState, dispatch } = useUserContext();

  useEffect(() => {
    document.documentElement.classList.toggle("dark", darkMode);
    localStorage.setItem("dark-mode", darkMode ? "true" : "false");
  }, [darkMode]);

  const toggleDarkMode = () => {
    setDarkMode((prevMode) => !prevMode);
  };

  function getGroup() {
    if (userState.selectedGroup === "all" || showGroup) {
      return (
        <>
          <GroupIcon style={"group-gray-icon"} />
          <span className={`sidebar-dropdown-item ${isOpen ? "" : "hidden"}`}>
            Grupos
          </span>
          {isOpen ? (showGroup ? UpArrowIcon() : DownArrowIcon()) : null}
        </>
      );
    } else {
      return (
        <>
          <GraduateIcon style={"group-gray-icon"} />
          <span className={`sidebar-dropdown-item ${isOpen ? "" : "hidden"}`}>
            {userState.selectedGroup}
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
            {getGroup()}
          </button>
          <ul className={`${showGroup ? "" : "hidden"} sidebar-dropdown`}>
            <li>
              <button
                onClick={() =>
                  dispatch({
                    type: UserActionKind.SET_SELECTED_GROUP,
                    payload: {
                      selectedGroup: "all",
                    },
                  })
                }
                className={`sidebar-item ${
                  "all" === userState.selectedGroup
                    ? "bg-btn-1-selected"
                    : "bg-btn-1"
                } group`}
              >
                <GroupIcon style={"group-gray-icon"} />
                <span className={isOpen ? "" : "hidden"}>Geral</span>
              </button>
            </li>
            {groups.map((item, index) => (
              <li key={index}>
                <button
                  onClick={() =>
                    dispatch({
                      type: UserActionKind.SET_SELECTED_GROUP,
                      payload: {
                        selectedGroup: item,
                      },
                    })
                  }
                  className={`sidebar-item ${
                    item === userState.selectedGroup
                      ? "bg-btn-1-selected"
                      : "bg-btn-1"
                  } group`}
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
