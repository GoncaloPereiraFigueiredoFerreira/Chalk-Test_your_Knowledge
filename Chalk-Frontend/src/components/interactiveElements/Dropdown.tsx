import { useState } from "react";
import { DownArrowIcon, UpArrowIcon } from "../objects/SVGImages/SVGImages";
import "./Dropdown.css";

interface DropdownProps {
  setChosenOption: (value: any) => void;
  chosenOption: any;
  options: any[];
  text: string;
}

export function Dropdown({
  setChosenOption,
  chosenOption,
  options,
  text,
}: DropdownProps) {
  const [dropdownIsOpen, setDropdownIsOpen] = useState(false);
  return (
    <>
      <button
        className={`${
          dropdownIsOpen
            ? "bg-gray-200 dark:bg-gray-600"
            : "hover:bg-gray-200 dark:hover:bg-gray-600"
        } dropdown group`}
        type="button"
        onClick={() => setDropdownIsOpen(!dropdownIsOpen)}
      >
        <label> {chosenOption < 0 ? text : options[chosenOption]} </label>
        {dropdownIsOpen ? UpArrowIcon() : DownArrowIcon()}
      </button>
      <div
        className={`${
          dropdownIsOpen ? "" : "hidden"
        } z-20 absolute mt-12 w-40 min-w-max bg-gray-200 divide-y divide-gray-100 rounded-lg shadow-xl dark:bg-gray-600`}
      >
        <ul className="py-2 text-gray-700 dark:text-white">
          {chosenOption >= 0 ? (
            <li>
              <button
                type="button"
                onClick={() => {
                  setChosenOption(-1);
                  setDropdownIsOpen(false);
                }}
                className={
                  "dark:hover:bg-gray-500 hover:bg-gray-300 inline-flex w-full px-4 py-2"
                }
              >
                {"Todos os conteudos"}
              </button>
            </li>
          ) : null}
          {options.map((value, index) =>
            chosenOption === index ? null : (
              <li key={index}>
                <button
                  type="button"
                  onClick={() => {
                    setChosenOption(index);
                    setDropdownIsOpen(false);
                  }}
                  className={
                    "dark:hover:bg-gray-500 hover:bg-gray-300 inline-flex w-full px-4 py-2"
                  }
                >
                  {value}
                </button>
              </li>
            )
          )}
        </ul>
      </div>
    </>
  );
}
