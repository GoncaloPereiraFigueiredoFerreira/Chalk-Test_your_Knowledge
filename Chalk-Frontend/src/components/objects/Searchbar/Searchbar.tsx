import { useState } from "react";
import { DropdownBlock } from "../../interactiveElements/DropdownBlock";
import "./Searchbar.css";
import { SearchIcon } from "../SVGImages/SVGImages";

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
              <SearchIcon style="text-black" size="size-4"></SearchIcon>
              <button
                type="submit"
                className="font-medium rounded-lg text-sm px-4 py-2 pointer-events-auto bg-[#acacff] bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black"
              >
                Search
              </button>
            </div>
            <input
              type="text"
              className="py-2.5 pl-10 pr-24 rounded-r-lg w-full z-20 border border-l-0 text-black bg-white border-[#dddddd] dark:text-black dark:bg-gray-600 dark:border-gray-600 focus:ring-0 focus:border-[#dddddd] focus:dark:border-gray-600"
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
