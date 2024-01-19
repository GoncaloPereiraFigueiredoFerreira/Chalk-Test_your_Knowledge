import { useState, useContext, useEffect } from "react";

import { useNavigate } from "react-router-dom";
import { Course } from "../../../UserContext.tsx";
import { APIContext } from "../../../APIContext.tsx";

export function GroupsPage() {
  const [viewMode, setViewMode] = useState<"grid" | "row">("grid");
  const navigate = useNavigate();
  const { contactBACK } = useContext(APIContext);
  const [courses, setCourses] = useState<Course[]>([]);

  useEffect(() => {
    contactBACK(
      "courses",
      "GET",
      { page: "0", itemsPerPage: "50" },
      undefined
    ).then((response) => {
      response.json().then((groups) => {
        const tmpL: Course[] = [];
        groups.map((group: any) => {
          tmpL.push({ id: group.id, name: group.name } as Course);
        });
        setCourses(tmpL);
      });
    });
  }, []);

  return (
    <div className="flex flex-col w-full h-screen py-16 overflow-auto bg-white dark:bg-black">
      <div className="flex flex-row w-11/12 self-center gap-4 min-h-max px-16 pb-4 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd] justify-between">
        <div className="flex w-fit float-left justify-between p-4 pt-0 ">
          <label className="flex text-4xl text-gray-600 dark:text-white">
            Todos os grupos
          </label>
        </div>
        <button
          className=" px-2 w-fit float-right bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black bg-[#acacff] rounded-md p-1"
          onClick={() => setViewMode(viewMode === "grid" ? "row" : "grid")}
        >
          Switch to {viewMode === "grid" ? "Row" : "Grid"} View
        </button>
      </div>

      <div
        className={
          viewMode === "grid"
            ? "grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 px-10"
            : "flex flex-col gap-4 px-10"
        }
      >
        {courses.map((item, index) => (
          <button
            key={index}
            type="button"
            onClick={() => navigate(`${item.id}/alunos`)}
          >
            <div className="bg-white p-6 rounded-lg shadow-md">
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
