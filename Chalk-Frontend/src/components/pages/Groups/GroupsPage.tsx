import { useState, useContext } from "react";

import { Link, useNavigate } from "react-router-dom";
import { Course, UserContext } from "../../../UserContext.tsx";
interface Class {
  id: string;
  className: string;
  teacherName: string;
}
export function GroupsPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(false);
  const [viewMode, setViewMode] = useState<"grid" | "row">("grid");
  const navigate = useNavigate();
  const { user, logout } = useContext(UserContext);

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
        {user.user?.courses.map((item, index) => (
          <button
            key={index}
            type="button"
            onClick={() => navigate(`${item.name}/alunos`)}
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
