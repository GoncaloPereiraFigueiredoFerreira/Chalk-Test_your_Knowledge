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
            style="rounded-l-lg border-slate-400"
            placement="bottom"
          ></DropdownBlock>
          <form className="relative w-full justify-center">
            <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1.5 pointer-events-none">
              <IoSearch className="size-5 text-slate-600 dark:text-slate-400" />
              <button
                type="submit"
                className="px-4 py-2 pointer-events-auto text-sm rounded-lg font-medium btn-base-color"
              >
                Search
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
      </div>
    </>
  );
}
