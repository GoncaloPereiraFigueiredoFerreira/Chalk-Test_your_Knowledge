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
    <div className="relative h-full">
      <button
        className={`${
          dropdownIsOpen ? "bg-btn-2" : " bg-btn-2-hover"
        } dropdown border-gray-2-1 group`}
        type="button"
        onClick={() => setDropdownIsOpen(!dropdownIsOpen)}
      >
        <label> {chosenOption < 0 ? text : options[chosenOption]} </label>
        {dropdownIsOpen ? UpArrowIcon() : DownArrowIcon()}
      </button>
      <div
        className={`${
          dropdownIsOpen ? "" : "hidden"
        } z-20 absolute top-12 w-40 min-w-max rounded-lg shadow-xl bg-3-2`}
      >
        <ul className="py-2">
          {chosenOption >= 0 ? (
            <li>
              <button
                type="button"
                onClick={() => {
                  setChosenOption(-1);
                  setDropdownIsOpen(false);
                }}
                className={"bg-btn-3-1 inline-flex w-full px-4 py-2"}
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
                  className={"bg-btn-3-1 inline-flex w-full px-4 py-2"}
                >
                  {value}
                </button>
              </li>
            )
          )}
        </ul>
      </div>
    </div>
  );
}
