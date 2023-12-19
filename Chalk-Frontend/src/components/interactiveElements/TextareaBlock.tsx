import { useRef, useState } from "react";
import { FileUploadIcon } from "../objects/SVGImages/SVGImages";

interface TextareaBlockProps {
  toolbar?: boolean;
  className?: string;
  placeholder?: string;
  value?: string;
  onChange?: (text: string) => void;
}
export function TextareaBlock({
  toolbar,
  className,
  placeholder,
  value,
  onChange,
}: TextareaBlockProps) {
  let hasToolbar = toolbar ? toolbar : false;
  const [text, setText] = useState(value ? value : "");
  const [isFocused, setIsFocused] = useState(false);
  const spanRef = useRef<HTMLDivElement>(null);

  const handleFocus = () => {
    setIsFocused(true);
  };

  const handleBlur = () => {
    setIsFocused(false);
    if (onChange != undefined) onChange(text);
  };

  const handleInput = () => {
    if (spanRef.current) {
      // 1: get htmlContent
      var htmlContent = spanRef.current.innerHTML.toString();
      const indexOfFirstDiv = htmlContent.indexOf("<div>");

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

      // 2: parse htmlContent -> parseContent
      const regex = /<(\/?)(\w+)(?: \w+="[^"]*")*>|([^<>]+)/g;
      let parseContent = htmlContent.matchAll(regex);

      // 3: concat parseContent -> modifiedContent
      let modifiedContent = "";
      if (parseContent !== null)
        for (const match of parseContent) {
          if (match[2] != undefined) {
            switch (match[2]) {
              case "b":
                modifiedContent += `<${match[1] ? "/" : ""}strong>`;
                break;
              case "i":
                modifiedContent += `<${match[1] ? "/" : ""}em>`;
                break;
              case "div":
                modifiedContent += `<${match[1] ? "/" : ""}p>`;
                break;
              default:
                modifiedContent += `<${match[1] ? "/" : ""}${match[2]}>`;
                break;
            }
          } else {
            modifiedContent += match[3];
          }
        }

      // 4: save modifiedContent
      if (modifiedContent === "<p></p>") setText("");
      else setText(modifiedContent);
    }
  };

  function toggleBold() {
    document.execCommand("bold");
    spanRef.current?.focus();
  }

  function toggleItalic() {
    document.execCommand("italic");
    spanRef.current?.focus();
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
                B
              </button>
              <button
                type="button"
                className="p-2 rounded cursor-pointer light-mode-gray-icon focus:outline-none"
                onClick={() => toggleItalic()}
              >
                I
              </button>
            </div>
            <div className="flex flex-wrap items-center space-x-1 sm:pl-4">
              <button
                type="button"
                className="p-2 rounded cursor-pointer light-mode-gray-icon focus:outline-none"
              >
                <FileUploadIcon></FileUploadIcon>
              </button>
            </div>
          </div>
        </div>
        <div
          className="flex flex-row w-full px-4 py-2"
          onClick={() => spanRef.current?.focus()}
        >
          <p
            ref={spanRef}
            id="textblock"
            className="block w-max focus:outline-none"
            onFocus={handleFocus}
            onBlur={handleBlur}
            onInput={handleInput}
            contentEditable
          >
            {value}
          </p>
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
  const pattern =
    /^(?:(?:<(\w+)(?: class="([^"]*)")*>((?:.|\n)+?)<\/\1>)|([^<]+)|(<br>))/;
  const match = stringHTML.match(pattern);
  let element = <></>;
  let restElement = <></>;

  if (match) {
    let rest = stringHTML.slice(match[0].length);

    if (match[1] != undefined)
      switch (match[1]) {
        case "p":
          element = <p className={match[2]}>{textToHTML(match[3])}</p>;
          break;
        case "strong":
          element = (
            <strong className={match[2]}>{textToHTML(match[3])}</strong>
          );
          break;
        case "em":
          element = <em className={match[2]}>{textToHTML(match[3])}</em>;
          break;
        default:
          element = <div className={match[2]}>{textToHTML(match[3])}</div>;
          break;
      }
    else if (match[4] != undefined)
      element = <>{decodeHtmlEntities(match[4])}</>;
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
