import "./Sidebar.css";
import { LuAlignJustify } from "react-icons/lu";
import { IoChevronDown } from "react-icons/io5";
import { IoChevronUp } from "react-icons/io5";
import { HiClipboardList } from "react-icons/hi";
import { HiUserGroup } from "react-icons/hi";
import { FaChalkboardTeacher } from "react-icons/fa";
import { FaPencil } from "react-icons/fa6";
import { FaUserGraduate } from "react-icons/fa";
import { MdPublic } from "react-icons/md";
import { TbChartPieFilled } from "react-icons/tb";
import { TiPlus } from "react-icons/ti";
import { IoSearch } from "react-icons/io5";
import { FaMoon } from "react-icons/fa";
import { IoIosSunny } from "react-icons/io";
import { FaUser } from "react-icons/fa6";
import { TbLogout2 } from "react-icons/tb";
import { IoHelpOutline } from "react-icons/io5";
import { MdStars } from "react-icons/md";
import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { MainLogo } from "../../MainLogo.tsx";

interface SidebarProps {
  isOpen: boolean;
  toggle: (value: boolean) => void;
}

import { Dropdown } from "flowbite-react";
import { Course, UserContext, UserRole } from "../../../UserContext.tsx";
import { CreateGroupModal } from "../../pages/Groups/CreateGroup.tsx";
import { APIContext } from "../../../APIContext.tsx";
import { TagsFilterModal, TagsList } from "../Tags/TagsFilterModal.tsx";

export function Sidebar({ isOpen, toggle }: SidebarProps) {
  const [showGroup, setShowGroup] = useState(false);
  const [openGroupModal, setGroupModal] = useState(false);
  const [openCreatModal, setCreatModal] = useState(false);
  const [tagList, setTagList] = useState<TagsList>([]);

  const { contactBACK } = useContext(APIContext);
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

  useEffect(() => {
    if (tagList.length > 0) {
      contactBACK(
        "tests/autoEvaluation",
        "POST",
        {
          tags: tagList
            .map((tag) => {
              return tag.id;
            })
            .toString(),
          nrExercises: "10",
        },
        undefined,
        "string"
      ).then((id) => {
        navigate("/webapp/tests/" + id + "/solve");
      });
    }
  }, [tagList]);

  const toggleDarkMode = () => {
    setDarkMode((prevMode) => !prevMode);
  };

  function GetGroup() {
    if (selectedGroup.id === "all" || showGroup) {
      return (
        <>
          <HiUserGroup className="size-6 group-slate-icon" />
          <span className={`sidebar-dropdown-item ${isOpen ? "" : "hidden"}`}>
            Grupos
          </span>
          {isOpen ? (
            showGroup ? (
              <IoChevronUp className="size-5 group-slate-icon" />
            ) : (
              <IoChevronDown className="size-5 group-slate-icon" />
            )
          ) : null}
        </>
      );
    } else {
      return (
        <>
          <FaChalkboardTeacher className="size-6 group-slate-icon" />
          <span className={`sidebar-dropdown-item ${isOpen ? "" : "hidden"}`}>
            {selectedGroup.name}
          </span>
          {isOpen ? (
            showGroup ? (
              <IoChevronUp className="size-5 group-slate-icon" />
            ) : (
              <IoChevronDown className="size-5 group-slate-icon" />
            )
          ) : null}
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
              selectedGroup.id !== "all" ? "" : "hidden"
            } gap-2 transition-all sidebar-divisions border-slate-300 dark:border-slate-600 pb-4`}
          >
            <li>
              <Link to={`groups/${selectedGroup.id}/alunos`}>
                <button className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group">
                  <FaUserGraduate className="size-6 group-slate-icon" />
                  <span className={isOpen ? "" : "hidden"}>Alunos</span>
                </button>
              </Link>
            </li>
            <li>
              <Link to={`groups/${selectedGroup.id}/testes`}>
                <button className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group">
                  <MdPublic className="size-6 group-slate-icon" />
                  <span className={isOpen ? "" : "hidden"}>
                    Testes Partilhados
                  </span>
                </button>
              </Link>
            </li>
            <li>
              <Link to={`groups/${selectedGroup.id}/avaliacoes`}>
                <button className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group">
                  <TbChartPieFilled className="size-6 group-slate-icon" />
                  <span className={isOpen ? "" : "hidden"}>Avaliações</span>
                </button>
              </Link>
            </li>
          </ul>
        </>
      );
    }
  }

  function handleLogout() {
    contactBACK("users/logout", "POST", undefined, undefined, "none").then(
      () => {
        logout();
      }
    );
  }

  return (
    <>
      <div
        className={`sidebar-background bg-[#8ca1be] dark:bg-slate-800 ${
          isOpen ? "" : "w-max"
        }`}
        aria-label="Sidebar"
      >
        <div className="flex flex-row gap-3 h-[42px]">
          <button
            type="button"
            className="sidebar-item w-auto hover:bg-white hover:dark:bg-slate-500 group"
            onClick={() => {
              toggle(!isOpen);
              setShowGroup(false);
            }}
          >
            <LuAlignJustify className="size-6 group-slate-icon" />
          </button>
          <div className={` ${isOpen ? "" : "hidden"}`}>
            <Link to="/webapp">
              <MainLogo></MainLogo>
            </Link>
          </div>
        </div>

        <ul className="sidebar-divisions border-t-0">
          <li>
            <TagsFilterModal
              setTagsList={setTagList}
              openModal={openCreatModal}
              setOpenModal={setCreatModal}
              header={
                "Selecione as tags que pretende ver nos exercícios do teste"
              }
            />
            <button
              className="relative inline-block text-lg group"
              onClick={() => {
                toggle(false);
                if (user.user?.role == UserRole.SPECIALIST)
                  navigate("/webapp/create-test");
                else {
                  setCreatModal(true);
                }
              }}
            >
              {/*Bloco inicial*/}
              <span className="relative z-10 block p-2 overflow-hidden font-bold leading-tight transition-all duration-200 ease-out border-2 text-slate-700 group-hover:text-white dark:text-slate-300 dark:group-hover:text-slate-800 bg-white group-hover:bg-slate-700 dark:bg-slate-800 dark:group-hover:bg-slate-500 border-slate-700 dark:border-slate-600 rounded-lg">
                {/*Bloco que surge*/}
                <span className="absolute left-0 w-64 h-64 -ml-2 transition-all duration-200 origin-top-right -rotate-90 -translate-x-full translate-y-12 bg-slate-700 dark:bg-slate-300 group-hover:-rotate-180 ease"></span>
                {/*Conteudo*/}
                <span className="relative flex space-x-2 items-center">
                  <TiPlus className="group-hover:text-white text-slate-700 dark:text-slate-300 dark:group-hover:text-black transition-all duration-500" />
                  <p className={isOpen ? "" : "hidden"}>Novo Teste</p>
                </span>
              </span>
              {/*Sombra*/}
              <span
                className={`${
                  isOpen ? "h-10" : "h-9"
                } absolute bottom-0 right-0 w-full -mb-1 -mr-1 transition-all duration-200 ease-linear bg-slate-500 dark:bg-slate-400 rounded-lg group-hover:mb-0 group-hover:mr-0 pointer-events-none`}
                data-rounded="rounded-lg"
              ></span>
            </button>
          </li>
          <li>
            <button
              className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group"
              onClick={() => {
                toggle(false);
                navigate("/webapp/search");
              }}
            >
              <IoSearch className="size-6 group-slate-icon scale-110" />
              <span className={isOpen ? "" : "hidden"}>Procurar conteúdos</span>
            </button>
          </li>
          <li>
            <button
              className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group"
              onClick={() => {
                toggle(false);
                navigate("/webapp/tests");
              }}
            >
              <HiClipboardList className="size-6 group-slate-icon scale-125" />
              <span className={isOpen ? "" : "hidden"}>Os meus testes</span>
            </button>
          </li>
          <li>
            <button
              className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group"
              onClick={() => {
                toggle(false);
                navigate("/webapp/exercise-bank");
              }}
            >
              <FaPencil className="size-6 group-slate-icon" />
              <span className={isOpen ? "" : "hidden"}>
                Banco de Exercícios
              </span>
            </button>
          </li>
        </ul>

        <ul className="sidebar-divisions border-slate-300 dark:border-slate-600  transition-all">
          <li>
            <button
              type="button"
              onClick={() => {
                toggle(true);
                setShowGroup(!showGroup);
              }}
              className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group"
            >
              <GetGroup />
            </button>

            <ul
              className={`${
                showGroup && isOpen ? "" : "hidden"
              } sidebar-dropdown`}
            >
              {user.user?.courses?.map((item, index) => (
                <li key={index}>
                  <button
                    onClick={() => {
                      setSelectedGroup(item);
                      navigate(`groups/${item.id}/alunos`);
                    }}
                    className={`sidebar-item ${
                      item === selectedGroup
                        ? "text-black bg-white dark:text-white dark:bg-slate-600"
                        : "text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 hover:dark:text-white"
                    } group`}
                  >
                    <FaChalkboardTeacher className="size-6 group-slate-icon" />
                    <span className={isOpen ? "" : "hidden"}>{item.name}</span>
                  </button>
                </li>
              ))}
              <li>
                <button
                  onClick={() => {
                    setSelectedGroup({
                      id: "all",
                      name: "Grupos",
                    });
                    navigate(`groups`);
                  }}
                  className={`sidebar-item ${
                    "all" === selectedGroup.id
                      ? "text-black bg-white dark:text-white dark:bg-slate-600"
                      : "text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 hover:dark:text-white"
                  } group`}
                >
                  <HiUserGroup className="size-6 group-slate-icon" />
                  <span className={isOpen ? "" : "hidden"}>
                    Todos os Grupos
                  </span>
                </button>
              </li>
            </ul>
          </li>
          {user.user?.role === UserRole.SPECIALIST && (
            <li>
              <button
                className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group"
                onClick={() => setGroupModal(true)}
              >
                <MdPublic className="size-6 group-slate-icon" />
                <span className={isOpen ? "" : "hidden"}>Criar Novo Grupo</span>
              </button>
              <CreateGroupModal
                open={openGroupModal}
                close={() => setGroupModal(false)}
              />
            </li>
          )}
        </ul>
        {showGrupOptions()}

        <div className="sidebar-divisions border-slate-300 dark:border-slate-600 mt-auto  transition-all">
          <ul>
            <li onClick={() => toggle(true)}>
              <Dropdown
                label=""
                placement="top"
                theme={{
                  content:
                    "py-1 rounded-lg focus:outline-none dark:bg-slate-700",
                }}
                renderTrigger={() => (
                  <button className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group">
                    <img
                      src={user.user?.photoPath ?? ""}
                      className={`${
                        isOpen ? "w-10 h-10 " : "size-6"
                      } rounded-full ease-linear duration-75`}
                      alt="user photo"
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
                  theme={{
                    base: "text-black dark:text-white hover:bg-[#dddddd] dark:hover:bg-slate-500 inline-flex px-4 py-2 w-full text-sm group",
                  }}
                  onClick={() => navigate("profile")}
                >
                  <FaUser className="size-6 group-slate-icon" />
                  <span className={`${isOpen ? "" : "hidden"} ml-2`}>
                    Profile Page
                  </span>
                </Dropdown.Item>
                <Dropdown.Item
                  as="button"
                  className=" group"
                  theme={{
                    base: "text-black dark:text-white hover:bg-[#dddddd] dark:hover:bg-slate-500 inline-flex px-4 py-2 w-full text-sm group",
                  }}
                >
                  <MdStars className="size-6 group-slate-icon" />
                  <span className={`${isOpen ? "" : "hidden"} ml-2`}>
                    Upgrade!
                  </span>
                </Dropdown.Item>
                <Dropdown.Item
                  as="button"
                  className=" group"
                  theme={{
                    base: "text-black dark:text-white hover:bg-[#dddddd] dark:hover:bg-slate-500 inline-flex px-4 py-2 w-full text-sm group",
                  }}
                >
                  <IoHelpOutline className="size-6 group-slate-icon" />
                  <span className={`${isOpen ? "" : "hidden"} ml-2`}>Help</span>
                </Dropdown.Item>
                <Dropdown.Item
                  as="button"
                  className=" group"
                  theme={{
                    base: "text-black dark:text-white hover:bg-red-300 dark:hover:bg-red-900 inline-flex px-4 py-2 w-full text-sm group",
                  }}
                  onClick={() => {
                    handleLogout();
                  }}
                >
                  <TbLogout2 className="size-6 group-slate-icon" />
                  <span
                    className={`${
                      isOpen ? "" : "hidden"
                    } text-red-600 dark:group-hover:text-red-500 font-bold ml-3`}
                  >
                    Log out
                  </span>
                </Dropdown.Item>
              </Dropdown>
            </li>

            <li>
              <button
                className="sidebar-item text-black dark:text-white hover:bg-white hover:dark:bg-slate-500 group"
                onClick={toggleDarkMode}
              >
                {darkMode ? (
                  <>
                    <IoIosSunny className="size-6 group-slate-icon" />
                    <span className={isOpen ? "" : "hidden"}>
                      Light Mode Toogle
                    </span>
                  </>
                ) : (
                  <>
                    <FaMoon className="size-6 group-slate-icon" />{" "}
                    <span className={isOpen ? "" : "hidden"}>
                      Dark Mode Toogle
                    </span>
                  </>
                )}
              </button>
            </li>
          </ul>
        </div>
      </div>
    </>
  );
}
