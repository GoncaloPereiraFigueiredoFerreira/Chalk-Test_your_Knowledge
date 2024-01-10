import { useEffect, useRef, useState } from "react";
import { Root } from "react-dom/client";
import { MdOutlineAttachFile } from "react-icons/md";
import { FaBold } from "react-icons/fa";
import { FaItalic } from "react-icons/fa";
import { ImUnderline } from "react-icons/im";

interface TextareaBlockProps {
  id?: string;
  className?: string;
  toolbar?: boolean;
  rows?: number;
  placeholder?: string;
  value?: string;
  onChange?: (text: string) => void;
  disabled?: boolean;
}
export function TextareaBlock({
  id,
  className,
  toolbar,
  rows = 1,
  placeholder,
  value,
  onChange,
  disabled,
}: TextareaBlockProps) {
  let hasToolbar = toolbar ? toolbar : false;
  const [text, setText] = useState(value ? value : "");
  const [isFocused, setIsFocused] = useState(false);
  const pRef = useRef<HTMLDivElement>(null);
  const rootRef = useRef<Root | null>(null);

  useEffect(() => {
    if (pRef.current) {
      pRef.current.innerHTML = value ?? "";

      setText(value ? value : "");
    }
  }, [value]);

  const handleFocus = () => {
    setIsFocused(true);
  };

  const handleBlur = () => {
    setIsFocused(false);
    if (onChange != undefined) onChange(text);
  };

  const handleInput = () => {
    if (pRef.current) {
      // 1: get htmlContent
      var htmlContent = pRef.current.innerHTML.toString();

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
      const regex_3 = /<(\/?)(\w+)(?: \w+="[^"]*")*>|([^<>]+)/g;
      let parseContent = htmlContent.matchAll(regex_3);

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
    pRef.current?.focus();
  }

  function toggleItalic() {
    document.execCommand("italic");
    pRef.current?.focus();
  }

  function toggleUnderline() {
    document.execCommand("underline");
    pRef.current?.focus();
  }

  return (
    <>
      <div
        className={
          "w-full mb-4 border-2 rounded-lg ex-1" +
          (className !== undefined ? className : "")
        }
      >
        <div
          className={`${
            hasToolbar ? "" : "hidden"
          } flex items-center justify-between px-3 py-2 border-b-2 rounded-t-lg ex-2`}
        >
          <div className="flex flex-wrap items-center sm:divide-x-2 ex-division">
            <div className="flex items-center space-x-1 sm:pr-4">
              <button
                type="button"
                className="p-2 rounded cursor-pointer light-mode-gray-icon focus:outline-none"
                onClick={() => toggleBold()}
              >
                <FaBold className="size-4" />
              </button>
              <button
                type="button"
                className="p-2 rounded cursor-pointer light-mode-gray-icon focus:outline-none"
                onClick={() => toggleItalic()}
              >
                <FaItalic className="size-4" />
              </button>
              <button
                type="button"
                className="p-2 rounded cursor-pointer light-mode-gray-icon focus:outline-none"
                onClick={() => toggleUnderline()}
              >
                <ImUnderline className="size-4" />
              </button>
            </div>
            <div className="flex flex-wrap items-center space-x-1 sm:pl-4">
              <button
                type="button"
                className="p-2 rounded cursor-pointer light-mode-gray-icon focus:outline-none"
              >
                <MdOutlineAttachFile className="size-5" />
              </button>
            </div>
          </div>
        </div>
        <div
          className="flex flex-row w-full px-4 py-2"
          style={{ minHeight: 24 * rows + "px" }}
          onClick={() => pRef.current?.focus()}
        >
          <div
            ref={pRef}
            className="block w-max focus:outline-none"
            onFocus={handleFocus}
            onBlur={handleBlur}
            onInput={handleInput}
            contentEditable={!disabled}
            suppressContentEditableWarning={true}
          ></div>
          {text === "" && !isFocused && placeholder !== undefined ? (
            <span className="text-gray-500 pointer-events-none">
              {placeholder}
            </span>
          ) : null}
        </div>
      </div>
    </>
  );
}

function decodeHtmlEntities(encodedString: string): string {
  const textarea = document.createElement("textarea");
  textarea.innerHTML = encodedString;
  return textarea.value;
}

export function textToHTML(stringHTML: string) {
  const pattern = /^(?:(?:<(\w+)>((?:.|\n)+?)<\/\1>)|([^<]+)|(<br>))/;
  const match = stringHTML.match(pattern);
  let element = <></>;
  let restElement = <></>;

  if (match) {
    let rest = stringHTML.slice(match[0].length);

    if (match[1] != undefined)
      switch (match[1]) {
        case "div":
        case "p":
        case "span":
          element = <p>{textToHTML(match[2])}</p>;
          break;
        case "b":
        case "strong":
          element = <strong>{textToHTML(match[2])}</strong>;
          break;
        case "i":
        case "em":
          element = <em>{textToHTML(match[2])}</em>;
          break;
        case "u":
          element = <u>{textToHTML(match[2])}</u>;
          break;
        case "code":
          element = <code>{textToHTML(match[2])}</code>;
          break;
        case "pre":
          element = <pre>{textToHTML(match[2])}</pre>;
          break;
        default:
          element = <div>{textToHTML(match[2])}</div>;
          break;
      }
    else if (match[3] != undefined)
      element = <>{decodeHtmlEntities(match[3])}</>;
    else element = <br></br>;

    if (rest != "") restElement = textToHTML(rest);
  }
  return (
    <>
      {element}
      {restElement}
    </>
  );
}
