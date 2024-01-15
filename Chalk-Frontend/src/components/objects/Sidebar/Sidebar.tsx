import "./Sidebar.css";
import {
  SidebarIcon,
  DownArrowIcon,
  CheckListIcon,
  GroupIcon,
  PenIcon,
  HelpIcon,
  TeacherIcon,
  GraduateIcon,
  UpArrowIcon,
  MoonIcon,
  SunIcon,
  LogoutIcon,
  PlusIcon,
  StarIcon,
  UserIcon,
  SearchIcon,
  WorldIcon,
  CircularGraficIcon,
} from "../SVGImages/SVGImages.tsx";
import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { MainLogo } from "../../MainLogo.tsx";

interface SidebarProps {
  isOpen: boolean;
  toggle: (value: boolean) => void;
}

import { Dropdown } from "flowbite-react";
import { Course, UserContext } from "../../../UserContext.tsx";
import { CreateGroupModal } from "../../pages/Groups/CreateGroup.tsx";

export function Sidebar({ isOpen, toggle }: SidebarProps) {
  const [showGroup, setShowGroup] = useState(false);
  const [dropUp, setDropUP] = useState(false);
  const [openCreateModal, setOpenCreateModal] = useState(false);
  const [selectedGroup, setSelectedGroup] = useState<Course>({
    id: "all",
    name: "Grupos",
  });
  const { user, logout } = useContext(UserContext);
  const [darkMode, setDarkMode] = useState(
    () => localStorage.getItem("dark-mode") === "true"
  );
  const navigate = useNavigate();

  useEffect(() => {
    document.documentElement.classList.toggle("dark", darkMode);
    localStorage.setItem("dark-mode", darkMode ? "true" : "false");
  }, [darkMode]);

  const toggleDarkMode = () => {
    setDarkMode((prevMode) => !prevMode);
  };

  function getGroup() {
    if (selectedGroup.id === "all" || showGroup) {
      return (
        <>
          <GroupIcon style={" group-hover:dark:text-black"} />
          <span className={`sidebar-dropdown-item  ${isOpen ? "" : "hidden"}`}>
            Grupos
          </span>
          {isOpen ? (showGroup ? UpArrowIcon() : DownArrowIcon()) : null}
        </>
      );
    } else {
      return (
        <>
          <TeacherIcon style={" group-hover:dark:text-black"} />
          <span className={`sidebar-dropdown-item ${isOpen ? "" : "hidden"}`}>
            {selectedGroup.name}
          </span>
          {isOpen ? (showGroup ? UpArrowIcon() : DownArrowIcon()) : null}
        </>
      );
    }
  }

  function showGrupOptions() {
    if (selectedGroup.id === "all") {
      return <></>;
    } else {
      return (
        <>
          <ul
            className={`${
              !showGroup && selectedGroup.id !== "all" ? "" : "hidden"
            } sidebar-dropdown transition-all`}
          >
            <li>
              <Link to={`groups/${selectedGroup.name}/alunos`}>
                <button className="sidebar-item bg-btn-1 group ">
                  <GraduateIcon style={" group-hover:dark:text-black"} />
                  <span className={isOpen ? "" : "hidden"}>Alunos</span>
                </button>
              </Link>
            </li>
            <li>
              <Link to={`groups/${selectedGroup.name}/testes`}>
                <button className="sidebar-item bg-btn-1 group items-center">
                  <WorldIcon style={" group-hover:dark:text-black"} />
                  <span className={isOpen ? "" : "hidden"}>
                    Testes Partilhados
                  </span>
                </button>
              </Link>
            </li>
            <li>
              <Link to={`groups/${selectedGroup.name}/avaliacoes`}>
                <button className="sidebar-item bg-btn-1 group">
                  <CircularGraficIcon style={" group-hover:dark:text-black"} />
                  <span className={isOpen ? "" : "hidden"}>Avaliações</span>
                </button>
              </Link>
            </li>
          </ul>
        </>
      );
    }
  }

  return (
    <>
      <aside
        className={`sidebar-background overflow-auto bg-1-3 dark:border-r-2 dark:border-[#dddddd] ${
          isOpen ? "" : "w-max"
        }`}
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
            <SidebarIcon style={" group-hover:text-black"} />
          </button>
          <div className={` ${isOpen ? "" : "hidden"}`}>
            <Link to="/webapp">
              <MainLogo></MainLogo>
            </Link>
          </div>
        </div>

        <ul className="sidebar-divisions border-t-0">
          <li>
            <button
              className="relative inline-block text-lg group mb-2"
              onClick={() => {
                toggle(false);
                navigate("/webapp/create-test");
              }}
            >
              {/*Bloco inicial*/}
              <span className="relative z-10 block px-3 py-3 overflow-hidden font-bold leading-tight transition-colors duration-500 ease-out border-2 text-black group-hover:text-white dark:text-[#dddddd] dark:group-hover:text-black bg-white group-hover:bg-gray-700 dark:bg-black dark:group-hover:bg-gray-500 border-gray-500 dark:border-gray-500  rounded-lg">
                {/*Bloco que surge*/}
                <span className="absolute overflow-auto left-0 w-64 h-64 -ml-2 ease-in-out transition-all duration-200 origin-top-right -rotate-90 -translate-x-full translate-y-12 bg-[#5555ce] dark:bg-[#ffd025] group-hover:-rotate-180"></span>
                {/*Conteudo*/}
                <span className="relative flex space-x-2 items-center overflow-auto">
                  <PlusIcon
                    style={
                      "group-hover:text-white text-black dark:text-white dark:group-hover:text-black transition-colors duration-500"
                    }
                  ></PlusIcon>

                  <p
                    className={`${
                      isOpen ? "" : "hidden"
                    } ease-linear duration-100`}
                  >
                    Novo Teste
                  </p>
                </span>
              </span>
              {/*Sombra*/}
              <span
                className={`${isOpen ? " h-12" : "h-11"}
                absolute bottom-0 right-0 w-full -mb-[1px] -mr-[1px] transition-all duration-200 ease-linear bg-gray-500 dark:bg-gray-400 rounded-lg group-hover:mb-0 group-hover:mr-0`}
                data-rounded="rounded-lg"
              ></span>
            </button>
          </li>
          <li>
            <button
              className="sidebar-item bg-btn-1 group"
              onClick={() => {
                toggle(false);
                navigate("/webapp/search");
              }}
            >
              <SearchIcon style={" ml-[1px] group-hover:dark:text-black"} />
              <span className={isOpen ? "" : "hidden"}>Procurar conteúdos</span>
            </button>
          </li>
          <li>
            <button
              className="sidebar-item bg-btn-1 group"
              onClick={() => {
                toggle(false);
                navigate("/webapp/tests");
              }}
            >
              <CheckListIcon style={" ml-[1px] group-hover:dark:text-black"} />
              <span className={isOpen ? "" : "hidden"}>Os meus testes</span>
            </button>
          </li>
          <li>
            <button
              className="sidebar-item bg-btn-1 group"
              onClick={() => {
                toggle(false);
                navigate("/webapp/exercise-bank");
              }}
            >
              <PenIcon style={"  ml-[1px] group-hover:dark:text-black"} />
              <span className={isOpen ? "" : "hidden"}>
                Banco de Exercícios
              </span>
            </button>
          </li>
        </ul>

        <div className="sidebar-divisions border-black dark:border-white transform transition-all duration-75">
          <div className="">
            <button
              className="sidebar-item bg-btn-1 group "
              onClick={() => setOpenCreateModal(true)}
            >
              <WorldIcon style={" ml-[2px] group-hover:dark:text-black"} />
              <span className={isOpen ? "" : "hidden"}>Criar Novo Grupo</span>
            </button>
            <CreateGroupModal
              open={openCreateModal}
              close={() => setOpenCreateModal(false)}
            />
          </div>
          <div className="">
            <button
              type="button"
              onClick={() => {
                toggle(true);
                setShowGroup(!showGroup);
              }}
              className="  bg-btn-1 sidebar-item group"
            >
              {getGroup()}
            </button>

            <div
              className={` transform transition-all ease-in-out duration-200  ${
                showGroup && isOpen
                  ? "max-h-[350px] h-auto opacity-100 translate-y-0"
                  : "max-h-0 opacity-0 -translate-y-[50px] overflow-hidden"
              } sidebar-dropdown  `}
            >
              <div className="overflow-auto">
                {user.user?.courses.map((item, index) => (
                  <div
                    key={index}
                    className={` ${showGroup && isOpen ? "" : "hidden"} `}
                  >
                    <button
                      onClick={() => {
                        setShowGroup(false);
                        setSelectedGroup(item);
                        navigate(`groups/${item.name}/alunos`);
                      }}
                      className={` sidebar-item ${
                        item === selectedGroup
                          ? "bg-btn-1-selected"
                          : "bg-btn-1"
                      } group`}
                    >
                      <TeacherIcon
                        style={"  ml-[1px]  group-hover:dark:text-black"}
                      />
                      <span>{item.name}</span>
                    </button>
                  </div>
                ))}
                <div
                  className={`z-[5] ${showGroup && isOpen ? "" : "hidden"} `}
                >
                  <button
                    onClick={() => {
                      setShowGroup(true);
                      setSelectedGroup({
                        id: "all",
                        name: "Grupos",
                      });
                      navigate(`groups`);
                    }}
                    className={`sidebar-item ${
                      "all" === selectedGroup.id
                        ? "bg-btn-1-selected"
                        : "bg-btn-1"
                    } group`}
                  >
                    <GroupIcon
                      style={"  ml-[2px] group-hover:dark:text-black"}
                    />
                    <span>Outros Grupos</span>
                  </button>
                </div>
              </div>
            </div>
            {showGrupOptions()}
          </div>
        </div>

        <div className="sidebar-divisions border-black dark:border-white mt-auto">
          <ul>
            <li
              onClick={() => {
                toggle(true), setDropUP(!dropUp);
              }}
              className="flex justify-center transition-all duration-100"
            >
              <Dropdown
                label=""
                placement="top"
                className={`transition-all ease-in-out duration-100 ${
                  dropUp
                    ? "opacity-100 translate-y-0"
                    : "opacity-0 -translate-y-10"
                }`}
                renderTrigger={() => (
                  <button className="sidebar-item bg-btn-1 group">
                    <img
                      src={user.user?.profilePic ?? ""}
                      className={`${
                        isOpen ? "w-10 h-10 " : "size-6"
                      } rounded-full ease-linear duration-75`}
                    />

                    <span className={isOpen ? "" : "hidden"}>
                      {user.user?.name}
                    </span>
                  </button>
                )}
              >
                <Dropdown.Header>
                  <span className="block text-sm">{user.user?.name}</span>
                  <span className="block truncate text-sm font-medium">
                    {user.user?.email}
                  </span>
                </Dropdown.Header>

                <Dropdown.Item
                  as="button"
                  className=" group"
                  onClick={() => navigate("profile")}
                >
                  <UserIcon style={" group-hover:dark:text-black"} />
                  <span className={`${isOpen ? "" : "hidden"} ml-2`}>
                    Profile Page
                  </span>
                </Dropdown.Item>
                <Dropdown.Item as="button" className=" group">
                  <StarIcon style={" group-hover:dark:text-black"} />
                  <span className={`${isOpen ? "" : "hidden"} ml-2`}>
                    Upgrade!
                  </span>
                </Dropdown.Item>
                <Dropdown.Item as="button" className=" group">
                  <HelpIcon style={" group-hover:dark:text-black"} />
                  <span className={`${isOpen ? "" : "hidden"} ml-2`}>Help</span>
                </Dropdown.Item>
                <Dropdown.Item as="button" className=" group" onClick={logout}>
                  <LogoutIcon style={" group-hover:dark:text-black"} />
                  <span
                    className={`${isOpen ? "" : "hidden"} text-red-700 ml-3`}
                  >
                    Log out
                  </span>
                </Dropdown.Item>
              </Dropdown>
            </li>

            <li>
              <button
                className="sidebar-item bg-btn-1 group"
                onClick={toggleDarkMode}
              >
                {darkMode ? (
                  <>
                    <SunIcon
                      style={
                        "  ml-[2px] dark:text-[#ffd025] group-hover:dark:text-black"
                      }
                    ></SunIcon>
                    <span className={isOpen ? "" : "hidden"}>
                      Light Mode Toogle
                    </span>
                  </>
                ) : (
                  <>
                    <MoonIcon style={" ml-[2px]"}></MoonIcon>{" "}
                    <span className={isOpen ? "" : "hidden"}>
                      Dark Mode Toogle
                    </span>
                  </>
                )}
              </button>
            </li>
          </ul>
        </div>
      </aside>
    </>
  );
}
