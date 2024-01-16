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
    <div className="flex flex-col w-full h-screen py-24 overflow-auto bg-2-1">
      <button
        className="bg-white w-64 self-end"
        onClick={() => setViewMode(viewMode === "grid" ? "row" : "grid")}
      >
        Switch to {viewMode === "grid" ? "Row" : "Grid"} View
      </button>

      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Todos os grupos</label>
        </div>
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
