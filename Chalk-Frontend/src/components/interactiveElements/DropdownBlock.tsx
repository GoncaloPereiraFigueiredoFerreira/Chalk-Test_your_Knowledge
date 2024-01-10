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
      className="dark:bg-gray-600"
      renderTrigger={() => (
        <button
          className={`${
            style ? style : ""
          } dropdown bg-btn-2-hover border-gray-2-1 group`}
        >
          <label> {chosenOption === null ? text : chosenOption} </label>
          {placement === "bottom" ? <IoChevronDown className="size-5" /> : null}
          {placement === "top" ? <IoChevronUp className="size-5" /> : null}
        </button>
      )}
    >
      {chosenOption != null && clearOption ? (
        <Dropdown.Item
          as="button"
          theme={{
            base: "bg-btn-3-1 inline-flex px-4 py-2 w-full text-sm group",
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
              base: "bg-btn-3-1 inline-flex px-4 py-2 w-full text-sm group",
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
