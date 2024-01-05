import { useState } from "react";

import { useNavigate } from "react-router-dom";
import {
  GridIcon,
  ListIcon,
  SearchIcon,
} from "../../objects/SVGImages/SVGImages";
export function AvaliacoesPage() {
  const [viewMode, setViewMode] = useState<"grid" | "row">("grid");
  const [searchKey, setSearch] = useState("");
  const [examList, setExamList] = useState([
    "1 História 2023",
    "2 História 2023",
  ]);
  const addEvaluation = () => {
    setExamList([...examList, `${examList.length + 1} História 2023`]);
  };
  const navigate = useNavigate();
  return (
    <div className="flex flex-col w-full h-screen py-24 overflow-auto bg-2-1">
      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <div className="">
            <div className="relative w-full justify-center">
              <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1 pointer-events-none">
                <SearchIcon style="text-gray-400" size="size-4"></SearchIcon>
              </div>
              <input
                type="text"
                className="py-2.5 pl-10 pr-24 rounded-lg w-full z-20 border bg-input-1"
                placeholder="Search..."
                value={searchKey}
                onChange={(text) => setSearch(text.target.value)}
                required
              />
            </div>
          </div>
          <div className="flex  ">
            <button
              className=" items-center justify-center w-24 h-10 text-sm font-medium text-gray-900 focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10   dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
              onClick={addEvaluation}
            >
              Adicionar Avaliacao
            </button>
            <button
              className="px-2 w-12"
              onClick={() => setViewMode(viewMode === "grid" ? "row" : "grid")}
            >
              {viewMode === "grid" ? (
                <ListIcon size="size-8" />
              ) : (
                <GridIcon size="size-8" />
              )}
            </button>
          </div>
        </div>
        {examList.map((item, index) => (
          <div
            key={index}
            className="flex justify-between bg-white p-6 rounded-lg shadow-md"
          >
            <div className=" text-xl font-semibold text-gray-800 m-2 p-4">
              {item}
            </div>
            <div>
              <button
                className=" self-end w-32 lg:w-64 h-20 text-sm font-medium text-gray-900 focus:outline-none bg-gray-100 rounded-lg border border-gray-900 hover:bg-gray-500 hover:text-white focus:z-10 focus:ring-4 focus:ring-gray-200 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700"
                onClick={() => navigate(`${item}`)}
              >
                Notas
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
