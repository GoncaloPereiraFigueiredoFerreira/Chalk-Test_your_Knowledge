import { useState } from "react";
import { Dropdown } from "../../interactiveElements/Dropdown";
import "./Searchbar.css";
import { SearchIcon } from "../SVGImages/SVGImages";

const options = ["Exercicios", "Testes"];

export function Searchbar() {
  const [chosenOption, setChosenOption] = useState(-1);
  const [searchText, setSearchText] = useState("");
  return (
    <>
      <form className="flex w-full justify-center">
        <div className="flex w-full max-w-lg">
          <Dropdown
            options={options}
            text="Categoria"
            chosenOption={chosenOption}
            setChosenOption={setChosenOption}
          ></Dropdown>
          <div className="relative w-full">
            <input
              type="search"
              className="block outline-none py-2.5 px-4 rounded-r-lg w-full z-20 text-black border-l-0 border border-gray-300 focus:border-gray-300 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white focus:ring-0 focus:dark:border-gray-600"
              placeholder="Search..."
              value={searchText}
              onChange={(text) => setSearchText(text.target.value)}
              required
            />
            <button
              type="submit"
              className="absolute top-0 right-0 py-2.5 px-4 text-sm font-medium h-full text-white bg-blue-700 rounded-r-lg border border-blue-700 hover:bg-blue-800 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
            >
              <SearchIcon size={"size-4"}></SearchIcon>
            </button>
          </div>
        </div>
      </form>
    </>
  );
}
