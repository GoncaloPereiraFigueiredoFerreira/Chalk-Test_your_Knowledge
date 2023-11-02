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
        } z-10 absolute mt-12 w-40 bg-white divide-y divide-gray-100 rounded-lg shadow-xl dark:bg-gray-600`}
      >
        <ul className="py-2 text-gray-700 dark:text-gray-200">
          {options.map((value, index) => (
            <li key={index}>
              <button
                type="button"
                onClick={() => {
                  setChosenOption(index);
                  setDropdownIsOpen(false);
                }}
                className={`${
                  index === chosenOption
                    ? "dark:bg-gray-500 dark:text-white bg-gray-200"
                    : "dark:hover:bg-gray-500 dark:hover:text-white hover:bg-gray-200"
                } inline-flex w-full px-4 py-2`}
              >
                {value}
              </button>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
}
