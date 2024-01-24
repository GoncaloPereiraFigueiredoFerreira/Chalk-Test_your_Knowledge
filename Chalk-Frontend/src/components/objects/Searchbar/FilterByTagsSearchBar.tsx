import { useState } from "react";
import { IoSearch } from "react-icons/io5";
import { FaFilter } from "react-icons/fa";
import "./Searchbar.css";
import "../../interactiveElements/DropdownBlock.css";
import { TagBlock } from "../../interactiveElements/TagBlock";

export function FilterByTagsSearchBar({
  setSearch,
  setOpenModal,
  tagsList,
  noOutterPadding,
}: any) {
  const [searchText, setSearchText] = useState("");

  const setSearchs = (value: any) => {
    setSearchText(value);
    setSearch(value);
  };

  return (
    <>
      <div
        className={`${
          noOutterPadding !== undefined ? "" : "py-8"
        } flex flex-col w-full items-center`}
      >
        <div className="flex w-full max-w-xl">
          <button
            onClick={() => setOpenModal(true)}
            className="dropdown rounded-l-lg text-black dark:text-white hover:bg-slate-300 dark:hover:bg-slate-700 border-slate-400 dark:border-slate-700 group"
          >
            Filtrar por Tópicos
            <FaFilter className="size-4 group group-slate-icon" />
          </button>
          <form className="relative w-full justify-center">
            <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1.5 pointer-events-none">
              <IoSearch className="size-5 text-slate-600 dark:text-slate-400" />
              <button
                type="submit"
                className="px-4 py-2 pointer-events-auto text-sm rounded-lg font-medium btn-base-color active:scale-90"
              >
                Procurar
              </button>
            </div>
            <input
              type="text"
              className="py-2.5 pl-10 pr-24 rounded-r-lg w-full z-20 border-2 border-l-0 bg-inherit text-black border-slate-400 dark:text-white placeholder:dark:text-gray-400 dark:border-slate-700 focus:ring-0 focus:border-slate-400 focus:dark:border-slate-700"
              placeholder="Procurar..."
              value={searchText}
              onChange={(text) => setSearchs(text.target.value)}
              required
            />
          </form>
        </div>
        <div
          className={`${
            tagsList.length > 0 ? "" : "hidden"
          } flex items-center pt-2 max-w-xl`}
        >
          <p className="font-semibold text-black dark:text-white">Tópicos:</p>
          <div className="flex flex-wrap pl-4 gap-4">
            {tagsList.map((tag: any, index: any) => {
              return <TagBlock key={index}>{tag.name}</TagBlock>;
            })}
          </div>
        </div>
      </div>
    </>
  );
}
