import { useState, useContext, useEffect } from "react";
import { ViewType } from "../../objects/ListTests/ListTest.tsx";
import { useNavigate } from "react-router-dom";
import { Course } from "../../../UserContext.tsx";
import { APIContext } from "../../../APIContext.tsx";
import { FaListUl } from "react-icons/fa";
import { HiViewGrid } from "react-icons/hi";

export function GroupsPage() {
  const navigate = useNavigate();
  const { contactBACK } = useContext(APIContext);
  const [courses, setCourses] = useState<Course[]>([]);
  const [view, setViewType] = useState(ViewType.GRID);

  useEffect(() => {
    contactBACK(
      "courses",
      "GET",
      { page: "0", itemsPerPage: "50" },
      undefined
    ).then((page) => {
      let groups = page.items;
      const tmpL: Course[] = [];
      groups.map((group: any) => {
        tmpL.push({ id: group.id, name: group.name } as Course);
      });
      setCourses(tmpL);
    });
  }, []);

  return (
    <div className="flex flex-col w-full h-screen py-16 overflow-auto bg-white dark:bg-slate-900">
      <div className="flex flex-row w-11/12 self-center gap-4 min-h-max px-16 pb-4 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd] justify-between">
        <div className="flex w-fit float-left justify-between p-4 pt-0 ">
          <label className="flex text-4xl text-gray-600 dark:text-white">
            Todos os grupos
          </label>
        </div>

        <div className="flex gap-4">
          {view === ViewType.GRID ? (
            <button
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
              onClick={() => setViewType(ViewType.LIST)}
            >
              <FaListUl className="size-5 scale-90" />
              <p>Lista</p>
            </button>
          ) : (
            <button
              className="flex w-fit items-center gap-2 py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90 ease-in-out"
              onClick={() => setViewType(ViewType.GRID)}
            >
              <HiViewGrid className="size-5 scale-110" />
              <p>Grelha</p>
            </button>
          )}
        </div>
      </div>

      <div
        className={
          view === ViewType.GRID
            ? "grid pt-4 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 px-10"
            : "flex pt-4 flex-col gap-4 px-10"
        }
      >
        {courses.map((item, index) => (
          <button
            key={index}
            type="button"
            onClick={() => navigate(`${item.id}/alunos`)}
          >
            <div className=" p-6 btn-base-color rounded-lg shadow-md">
              <h2 className="text-xl font-semibold text-gray-800 mb-2">
                {item.name}
              </h2>
            </div>
          </button>
        ))}
      </div>
    </div>
  );
}
