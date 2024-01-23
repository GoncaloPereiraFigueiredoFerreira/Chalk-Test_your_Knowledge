import { useState } from "react";
import { Badge } from "flowbite-react";
import { IoSearch } from "react-icons/io5";
import { FaFilter } from "react-icons/fa";
import "./Searchbar.css";

export function FilterByTagsSearchBar({
  setSearch,
  setOpenModal,
  tagsList,
}: any) {
  const [searchText, setSearchText] = useState("");

  const setSearchs = (value: any) => {
    setSearchText(value);
    setSearch(value);
  };

  return (
    <div className="flex flex-col">
      <div className="flex w-full justify-center py-8 ">
        <div className="flex-col">
          <div className="flex w-full max-w-xl">
            <button
              onClick={() => setOpenModal(true)}
              className="py-2.5  w-64 rounded-l-lg  z-20 border-2  flex items-center justify-center"
            >
              Filter by Tags
              <div className="px-4">
                <FaFilter />{" "}
              </div>
            </button>
            <form className="relative w-full justify-center">
              <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1.5 pointer-events-none">
                <IoSearch className="size-5 text-slate-600 dark:text-slate-400" />
                <button
                  type="submit"
                  className="px-4 py-2 pointer-events-auto text-sm rounded-lg font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black transition-all duration-100"
                >
                  Search
                </button>
              </div>
              <input
                type="text"
                className="py-2.5 pl-10 pr-24 rounded-r-lg w-full z-20 border-2 border-l-0 bg-inherit text-black border-[#dddddd] dark:text-white placeholder:dark:text-gray-400 dark:border-slate-700 focus:ring-0 focus:border-[#dddddd] focus:dark:border-slate-700"
                placeholder="Procurar..."
                value={searchText}
                onChange={(text) => setSearchs(text.target.value)}
                required
              />
            </form>
          </div>
          <div className="flex items-center pt-2 ">
            <strong>Tags:</strong>
            <div className="flex ml-4 space-x-2">
              {tagsList.map((tag: any, index: any) => {
                return (
                  <Badge key={index} color={"blue"} className=" h-12  ">
                    {tag.name}
                  </Badge>
                );
              })}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
