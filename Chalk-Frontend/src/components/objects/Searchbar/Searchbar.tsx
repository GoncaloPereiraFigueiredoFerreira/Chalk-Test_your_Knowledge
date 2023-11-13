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
        <div className="flex w-full max-w-xl">
          <Dropdown
            options={options}
            text="Categoria"
            chosenOption={chosenOption}
            setChosenOption={setChosenOption}
          ></Dropdown>
          <div className="relative w-full justify-center">
            <div className="absolute h-full w-full flex justify-between items-center pl-4 pr-1 pointer-events-none">
              <SearchIcon style="text-gray-400" size="size-4"></SearchIcon>
              <button
                type="submit"
                className="font-medium rounded-lg text-sm px-4 py-2 pointer-events-auto btn-selected"
              >
                Search
              </button>
            </div>
            <input
              type="text"
              className="py-2.5 pl-10 pr-24 rounded-r-lg w-full z-20 border-l-0 border bg-input-1"
              placeholder="Search..."
              value={searchText}
              onChange={(text) => setSearchText(text.target.value)}
              required
            />
          </div>
        </div>
      </form>
    </>
  );
}
