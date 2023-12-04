import { useEffect, useState } from "react";
import { ExerciseJustificationKind } from "../Exercise/Exercise";
import { Answer } from "./Answer";

export function TFAnswer({ type, answers, correction, justification }: Answer) {
  const [collapsed, setCollapsed] = useState(true);
  const [preview, setPreview] = useState(<></>);

  useEffect(() => {
    switch (justification) {
      case ExerciseJustificationKind.JUSTIFY_ALL:
        setPreview(
          <>
            <div>
              {answers.map((str: String) => (
                <div>
                  <label className="hidden mb-2 text-sm font-medium text-gray-900 dark:text-black">
                    {str}
                  </label>
                  <input
                    type="value"
                    id="grade"
                    className="bg-gray-50 border w-fit h-fit float-left border-gray-300 text-gray-900 text-sm rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                    placeholder="grade"
                    pattern="[0-9]{3}.[0-9]{2}"
                    required
                  />
                </div>
              ))}
            </div>
          </>
        );
        break;
      case ExerciseJustificationKind.JUSTIFY_FALSE:
        setPreview(
          <>
            {answers.map((str: String) => (
              <div>
                {str === "V" ? (
                  <>
                    <label className="hidden mb-2 text-sm font-medium text-gray-900 dark:text-black">
                      {str}
                    </label>
                    <input
                      type="value"
                      id="grade"
                      className="bg-gray-50 border w-fit h-fit float-left border-gray-300 text-gray-900 text-sm rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                      placeholder="grade"
                      pattern="[0-9]{3}.[0-9]{2}"
                      required
                    />
                  </>
                ) : (
                  <></>
                )}
              </div>
            ))}
          </>
        );
        break;
      case ExerciseJustificationKind.JUSTIFY_TRUE:
        setPreview(
          <>
            {answers.map((str: String) => (
              <div>
                {str === "V" ? (
                  <>
                    <label className="hidden mb-2 text-sm font-medium text-gray-900 dark:text-black">
                      {str}
                    </label>
                    <input
                      type="value"
                      id="grade"
                      className="bg-gray-50 border w-fit h-fit float-left border-gray-300 text-gray-900 text-sm rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                      placeholder="grade"
                      pattern="[0-9]{3}.[0-9]{2}"
                      required
                    />
                  </>
                ) : (
                  <></>
                )}
              </div>
            ))}
          </>
        );
        break;
      case ExerciseJustificationKind.NO_JUSTIFICATION:
        setPreview(
          <div>
            {answers.map((str: String) => (
              <ul className="hidden mb-2 text-sm font-medium text-gray-900 dark:text-black">
                {str}
              </ul>
            ))}
          </div>
        );
        break;
    }
  }, [type]);

  return (
    <>
      <div className=" border-b-2"></div>
      <div>{preview}</div>
    </>
  );
}
