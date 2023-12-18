import { useEffect, useState } from "react";
import {
  ExerciseJustificationKind,
  ExerciseType,
  OAResolutionData,
  TFResolutionData,
} from "../Exercise/Exercise";
import { Exercise, Resolution } from "../Exercise/Exercise";
import axios from "axios";
import { AnswerProps } from "./Answer";

interface TFRubric {
  description: string;
  type: ExerciseJustificationKind;
  id: string;
  choiceCotation: number;
  penalty: number;
}

export function TFAnswer({
  resolution,
  cotation,
  justifyKind,
  solution,
}: AnswerProps) {
  const [preview, setPreview] = useState(<></>);
  const [rubric, setRubric] = useState<TFRubric>();
  const cotationPerChoice =
    cotation / Object.entries((solution.data as TFResolutionData).items).length;

  const exeCotation = Array<number>(
    Object.entries((solution.data as TFResolutionData).items).length
  ).fill(0);

  function handleSubmit(e: any) {
    e.preventDefault();

    axios
      .post(
        "http://localhost:5173/exercises/resolutions/" +
          resolution?.id +
          "/manual-correction",
        { cotation: exeCotation.reduce((a, b) => a + b, 0) }
      )
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
  }

  useEffect(() => {
    /*  
    axios
      .get("http://localhost:5173/exercises/" + id + "/rubric")
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
  */
    if (
      resolution.data.type === ExerciseType.TRUE_OR_FALSE &&
      solution.data.type === ExerciseType.TRUE_OR_FALSE
    )
      switch (justifyKind) {
        case ExerciseJustificationKind.JUSTIFY_ALL:
          setPreview(
            <>
              <form className="flex flex-col" onSubmit={handleSubmit}>
                {Object.entries(
                  (resolution!.data as TFResolutionData).items
                ).map(
                  ([key, item]: [
                    string,
                    {
                      text: string;
                      justification: string;
                      value?: boolean;
                    }
                  ]) => (
                    <label
                      className={` ${
                        (solution.data as TFResolutionData).items[key].value ===
                        item.value
                          ? " text-green-400"
                          : "text-red-400"
                      } block mb-2 text-md font-medium  `}
                    >
                      {key + ": " + item.text}
                      {(solution.data as TFResolutionData).items[key].value ===
                      item.value ? (
                        <input
                          type="number"
                          id={key}
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={
                            "Max: " + rubric
                              ? rubric!.choiceCotation.toString()
                              : cotationPerChoice.toString()
                          }
                          pattern="[0-9]{3}.[0-9]{2}"
                          min={0}
                          max={
                            rubric ? rubric.choiceCotation : cotationPerChoice
                          }
                          onChange={(e) =>
                            (exeCotation[parseInt(key)] = parseFloat(
                              e.target.value
                            ))
                          }
                          required
                        />
                      ) : (
                        <input
                          type="number"
                          id={key}
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={
                            rubric ? rubric?.penalty.toString() : "0"
                          }
                          value={rubric ? rubric?.penalty : 0}
                          pattern="[0-9]{3}.[0-9]{2}"
                          disabled
                        />
                      )}
                    </label>
                  )
                )}
                <button
                  type="submit"
                  className="text-white m-2 w-fit h-fit float-right bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                >
                  Submit
                </button>
              </form>
            </>
          );
          break;
        case ExerciseJustificationKind.JUSTIFY_FALSE:
        case ExerciseJustificationKind.JUSTIFY_TRUE:
          setPreview(
            <>
              {Object.entries(resolution!.data.items!).map(([key, item]) => (
                <form className="flex flex-col" onSubmit={handleSubmit}>
                  {(justifyKind === ExerciseJustificationKind.JUSTIFY_FALSE &&
                    item.value === false) ||
                  (justifyKind === ExerciseJustificationKind.JUSTIFY_TRUE &&
                    item.value === true) ? (
                    <label
                      className={` ${
                        (solution.data as TFResolutionData).items[key].value ===
                        false
                          ? " text-green-400"
                          : "text-red-400"
                      } mb-2 text-md font-medium  `}
                    >
                      {key + ": " + item.text}
                      {(solution.data as TFResolutionData).items[key].value ===
                      false ? (
                        <input
                          type="number"
                          id="cotation"
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={
                            "Max: " + rubric
                              ? rubric!.choiceCotation.toString()
                              : cotationPerChoice.toString()
                          }
                          pattern="[0-9]{3}.[0-9]{2}"
                          min={0}
                          max={cotation}
                          onChange={(e) =>
                            (exeCotation[parseInt(key)] = parseFloat(
                              e.target.value
                            ))
                          }
                          required
                        />
                      ) : (
                        <input
                          type="number"
                          id={key}
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={
                            rubric ? rubric?.penalty.toString() : "0"
                          }
                          value={rubric ? rubric?.penalty : 0}
                          pattern="[0-9]{3}.[0-9]{2}"
                          disabled
                        />
                      )}
                    </label>
                  ) : (
                    <label
                      className={` ${
                        (solution.data as TFResolutionData).items[key].value ===
                        true
                          ? " text-green-400"
                          : "text-red-400"
                      } mb-2 text-md font-medium  `}
                    >
                      {key + ": " + item.text}
                      {(solution.data as TFResolutionData).items[key].value ===
                      item.value ? (
                        <input
                          type="number"
                          id="cotation"
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={
                            "Max: " + rubric
                              ? rubric!.choiceCotation.toString()
                              : cotationPerChoice.toString()
                          }
                          pattern="[0-9]{3}.[0-9]{2}"
                          min={0}
                          max={cotation}
                          onChange={(e) =>
                            (exeCotation[parseInt(key)] = parseFloat(
                              e.target.value
                            ))
                          }
                          required
                        />
                      ) : (
                        <input
                          type="number"
                          id={key}
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={
                            rubric ? rubric?.penalty.toString() : "0"
                          }
                          value={rubric ? rubric?.penalty : 0}
                          pattern="[0-9]{3}.[0-9]{2}"
                          disabled
                        />
                      )}
                    </label>
                  )}
                  <button
                    type="submit"
                    className="text-white m-2 w-fit h-fit float-right bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                  >
                    Submit
                  </button>
                </form>
              ))}
            </>
          );
          break;

        case ExerciseJustificationKind.NO_JUSTIFICATION:
          setPreview(
            <div>
              <form className="flex flex-col" onSubmit={handleSubmit}>
                {Object.entries(resolution!.data.items!).map(([key, item]) => (
                  <label
                    className={` ${
                      (solution.data as TFResolutionData).items[key].value ===
                      false
                        ? " text-green-400"
                        : "text-red-400"
                    } mb-2 text-md font-medium  `}
                  >
                    {key + ": " + item.text}
                    {(solution.data as TFResolutionData).items[key].value ===
                    item.value ? (
                      <input
                        type="number"
                        id={key}
                        value={cotationPerChoice}
                        className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                        placeholder={
                          rubric
                            ? rubric!.choiceCotation.toString()
                            : cotationPerChoice.toString()
                        }
                        pattern="[0-9]{3}.[0-9]{2}"
                        disabled
                      />
                    ) : (
                      <input
                        type="number"
                        id={key}
                        className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                        placeholder={rubric ? rubric?.penalty.toString() : "0"}
                        value={rubric ? rubric?.penalty : 0}
                        pattern="[0-9]{3}.[0-9]{2}"
                        disabled
                      />
                    )}
                  </label>
                ))}
              </form>
            </div>
          );
          break;
      }
  }, []);

  return (
    <>
      <div className=" border-b-2"></div>
      <div className="pt-2">{preview}</div>
    </>
  );
}
