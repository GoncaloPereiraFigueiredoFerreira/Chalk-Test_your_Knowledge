import { useEffect, useRef, useState } from "react";
import { MdOutlineAttachFile } from "react-icons/md";
import { FaBold } from "react-icons/fa";
import { FaItalic } from "react-icons/fa";
import { ImUnderline } from "react-icons/im";

interface TextareaBlockProps {
  className?: string;
  toolbar?: boolean;
  rows?: number;
  placeholder?: string;
  value?: string;
  onChange?: (text: string) => void;
  disabled?: boolean;
}
export function TextareaBlock({
  disabled,
  className,
  toolbar,
  rows = 1,
  placeholder,
  value,
  onChange,
}: TextareaBlockProps) {
  const hasToolbar = toolbar ? toolbar : false;
  const [text, setText] = useState(value ? value : "");
  const [isFocused, setIsFocused] = useState(false);
  const divRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (divRef.current) {
      divRef.current.innerHTML = value ?? "";

      setText(value ? value : "");
    }
  }, [value]);

  const handleFocus = () => {
    setIsFocused(true);
  };

  const handleBlur = () => {
    setIsFocused(false);
    if (onChange != undefined)
      onChange(
        text
          .replace("<i>", "<em>")
          .replace("</i>", "</em>")
          .replace("<b>", "<strong>")
          .replace("</b>", "</strong>")
      );
  };

  const handleInput = () => {
    if (divRef.current) {
      // 1: get htmlContent
      let htmlContent = divRef.current.innerHTML.toString();

      const indexOfFirstP = htmlContent.indexOf("<p>");
      const indexOfFirstDiv = htmlContent.indexOf("<div>");

      if (indexOfFirstP !== 0) {
        if (indexOfFirstDiv > 0) {
          // Split the string at the first <div>
          htmlContent =
            "<div>" +
            htmlContent.substring(0, indexOfFirstDiv) +
            "</div>" +
            htmlContent.substring(indexOfFirstDiv);
        } else if (indexOfFirstDiv !== 0) {
          htmlContent = "<div>" + htmlContent + "</div>";
        }
      }

      // 2: Remove the initial space and format the string
      const regex_2 =
        /^(<(?:p|div)>(?:<\/?div>|<\/?p>|<\/?br\/?>)*<\/(?:p|div)>)*/;
      htmlContent = htmlContent.replace(regex_2, "");

      // 3: parse htmlContent -> parseContent
      const regex_3 = /<(\/?)(\w+)(?: (?:\w|-)+(?:="[^"]*")?)*>|([^<>]+)/g;
      const parseContent = htmlContent.matchAll(regex_3);

      // 4: concat parseContent -> modifiedContent
      let modifiedContent = "";
      if (parseContent !== null)
        for (const match of parseContent) {
          if (match[2] != undefined) {
            modifiedContent += `<${match[1] ? "/" : ""}${match[2]}>`;
          } else {
            modifiedContent += match[3];
          }
        }

      // 5: save modifiedContent
      setText(modifiedContent);
    }
  };

  function toggleBold() {
    document.execCommand("bold");
    divRef.current?.focus();
  }

  function toggleItalic() {
    document.execCommand("italic");
    divRef.current?.focus();
  }

  function toggleUnderline() {
    document.execCommand("underline");
    divRef.current?.focus();
  }

  return (
    <>
      <div
        className={
          "w-full mb-4 border-2 rounded-lg dark:text-white bg-inherit border-[#bdcee6] dark:border-slate-700 overflow-hidden" +
          (className !== undefined ? className : "")
        }
      >
        <div
          className={`${
            hasToolbar ? "" : "hidden"
          } flex items-center rounded-t-md justify-between px-3 py-2 border-b-2 bg-[#d8e3f1] border-[#bdcee6] dark:border-slate-700 dark:bg-[#2e3c50]`}
        >
          <div className="flex flex-wrap items-center sm:divide-x-2 divide-[#bdcee6] dark:divide-slate-600">
            <div className="flex items-center space-x-1 sm:pr-4">
              <button
                type="button"
                className="p-2 rounded cursor-pointer slate-icon focus:outline-none"
                onClick={() => toggleBold()}
              >
                <FaBold className="size-4" />
              </button>
              <button
                type="button"
                className="p-2 rounded cursor-pointer slate-icon focus:outline-none"
                onClick={() => toggleItalic()}
              >
                <FaItalic className="size-4" />
              </button>
              <button
                type="button"
                className="p-2 rounded cursor-pointer slate-icon focus:outline-none"
                onClick={() => toggleUnderline()}
              >
                <ImUnderline className="size-4" />
              </button>
            </div>
            <div className="flex flex-wrap items-center space-x-1 sm:pl-4">
              <button
                type="button"
                className="p-2 rounded cursor-pointer slate-icon focus:outline-none"
              >
                <MdOutlineAttachFile className="size-5" />
              </button>
            </div>
          </div>
        </div>
        <div
          className="flex flex-row w-full px-4 py-2"
          style={{ minHeight: 24 * rows + "px" }}
          onClick={() => divRef.current?.focus()}
        >
          <div
            ref={divRef}
            className="focus:outline-none"
            onFocus={handleFocus}
            onBlur={handleBlur}
            onInput={handleInput}
            contentEditable={!disabled}
            suppressContentEditableWarning={true}
          ></div>
          {text === "" && !isFocused && placeholder !== undefined ? (
            <span className="text-slate-500 pointer-events-none">
              {placeholder}
            </span>
          ) : null}
        </div>
      </div>
    </>
  );
}
