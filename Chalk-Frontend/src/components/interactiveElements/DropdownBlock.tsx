import { IoChevronDown } from "react-icons/io5";
import { IoChevronUp } from "react-icons/io5";
import "./DropdownBlock.css";
import { Dropdown } from "flowbite-react";

interface DropdownBlockProps {
  style?: string;
  setChosenOption: (value: any) => void;
  chosenOption: any;
  options: any[];
  text: string;
  clearOption?: string;
  placement?: "bottom" | "top";
}

export function DropdownBlock({
  style,
  setChosenOption,
  chosenOption,
  options,
  text,
  clearOption,
  placement,
}: DropdownBlockProps) {
  return (
    <Dropdown
      label=""
      placement={placement ? placement : "auto"}
      theme={{
        content: "py-1 rounded-lg focus:outline-none dark:bg-slate-700",
      }}
      renderTrigger={() => (
        <button
          className={`dropdown group text-black dark:text-white hover:bg-[#dddddd] dark:hover:bg-slate-700 border-[#dddddd] dark:border-slate-700
            ${style ? style : ""}`}
        >
          <label> {chosenOption === null ? text : chosenOption} </label>
          {placement === "top" ? (
            <IoChevronUp className="size-5 group-slate-icon dark:group-hover:text-white" />
          ) : (
            <IoChevronDown className="size-5 group-slate-icon dark:group-hover:text-white" />
          )}
        </button>
      )}
    >
      {chosenOption != null && clearOption ? (
        <Dropdown.Item
          as="button"
          theme={{
            base: "text-black dark:text-white hover:bg-[#dddddd] dark:hover:bg-slate-500 inline-flex px-4 py-2 w-full text-sm group",
          }}
          onClick={() => {
            setChosenOption(null);
          }}
        >
          <span>{clearOption}</span>
        </Dropdown.Item>
      ) : null}
      {options.map((value, index) =>
        chosenOption === value ? null : (
          <Dropdown.Item
            as="button"
            theme={{
              base: "text-black dark:text-white hover:bg-[#dddddd] dark:hover:bg-slate-500 inline-flex px-4 py-2 w-full text-sm group",
            }}
            key={index}
            onClick={() => {
              setChosenOption(value);
            }}
          >
            <span>{value}</span>
          </Dropdown.Item>
        )
      )}
    </Dropdown>
  );
}
