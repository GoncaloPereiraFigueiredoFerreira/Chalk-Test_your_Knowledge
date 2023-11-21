import { useState } from "react";
import { DownArrowIcon, UpArrowIcon } from "../objects/SVGImages/SVGImages";
import "./Dropdown.css";

interface DropdownProps {
  className?: string;
  setChosenOption: (value: any) => void;
  chosenOption: any;
  options: any[];
  text: string;
  clearOption?: string;
}

export function Dropdown({
  className,
  setChosenOption,
  chosenOption,
  options,
  text,
  clearOption,
}: DropdownProps) {
  const [dropdownIsOpen, setDropdownIsOpen] = useState(false);
  return (
    <div className="relative h-full">
      <button
        className={`${
          dropdownIsOpen ? "bg-btn-2" : " bg-btn-2-hover"
        } dropdown ${className ? className : ""} border-gray-2-1 group`}
        type="button"
        onClick={() => setDropdownIsOpen(!dropdownIsOpen)}
      >
        <label> {chosenOption === null ? text : chosenOption} </label>
        {dropdownIsOpen ? UpArrowIcon() : DownArrowIcon()}
      </button>
      <div
        className={`${
          dropdownIsOpen ? "" : "hidden"
        } z-20 absolute top-12 w-40 min-w-max rounded-lg shadow-xl bg-3-2`}
      >
        <ul className="py-2">
          {chosenOption != null && clearOption ? (
            <li>
              <button
                type="button"
                onClick={() => {
                  setChosenOption(null);
                  setDropdownIsOpen(false);
                }}
                className={"bg-btn-3-1 inline-flex w-full px-4 py-2"}
              >
                {clearOption}
              </button>
            </li>
          ) : null}
          {options.map((value, index) =>
            chosenOption === value ? null : (
              <li key={index}>
                <button
                  type="button"
                  onClick={() => {
                    setChosenOption(value);
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
