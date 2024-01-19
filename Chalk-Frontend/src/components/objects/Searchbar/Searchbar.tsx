import { useState } from "react";
import { DropdownBlock } from "../../interactiveElements/DropdownBlock";
import { IoSearch } from "react-icons/io5";
import "./Searchbar.css";

const options = ["Exercicios", "Testes"];

export function Searchbar({ setSearch }: any) {
  const [chosenOption, setChosenOption] = useState(null);
  const [searchText, setSearchText] = useState("");

  const setSearchs = (value: any) => {
    setSearchText(value);
    setSearch(value);
  };

  return (
    <>
      <div className="flex w-full justify-center py-8">
        <div className="flex w-full max-w-xl">
          <DropdownBlock
            options={options}
            text="Categoria"
            chosenOption={chosenOption}
            setChosenOption={setChosenOption}
            clearOption="Todas as categorias"
            style="rounded-l-lg"
            placement="bottom"
          ></DropdownBlock>
          <form className="relative w-full justify-center">
            <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1 pointer-events-none">
              <IoSearch className="size-5 text-gray-600 dark:text-gray-400" />
              <button
                type="submit"
                className="font-medium rounded-lg text-sm px-4 py-2 pointer-events-auto bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white"
              >
                Search
              </button>
            </div>
            <input
              type="text"
              className="py-2.5 pl-10 pr-24 rounded-r-lg w-full z-20 border border-l-0 text-black bg-inherit border-[#dddddd] dark:text-white placeholder:dark:text-gray-400 dark:border-gray-600 focus:ring-0 focus:border-[#dddddd] focus:dark:border-gray-600"
              placeholder="Search..."
              value={searchText}
              onChange={(text) => setSearchs(text.target.value)}
              required
            />
          </form>
        </div>
      </div>
    </>
  );
}
