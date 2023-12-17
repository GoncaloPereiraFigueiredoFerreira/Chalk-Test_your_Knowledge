import { useEffect, useState } from "react";
import {
  ExerciseJustificationKind,
  ExerciseType,
  OAResolutionData,
  TFResolutionData,
} from "../Exercise/Exercise";
import { Exercise, Resolution, Item } from "../Exercise/Exercise";
import axios from "axios";

interface TFRubric {
  description: string;
  type: ExerciseJustificationKind;
  id: string;
  choiceCotation: number;
  penalty: number;
}

export function TFAnswer(
  resolution: Resolution,
  cotation: number,
  justifyKind: ExerciseJustificationKind,
  solution: Resolution
) {
  const [preview, setPreview] = useState(<></>);
  const [exeCotation, setCotation] = useState(cotation);
  const [rubric, setRubric] = useState<TFRubric>();

  function handleSubmit() {
    axios
      .post(
        "http://localhost:5173/exercises/resolutions/" +
          resolution?.id +
          "/manual-correction",
        { cotation: exeCotation }
      )
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
  }

  function setExeCotation(e) {
    setCotation(e.target.value);
  }

  useEffect(() => {
    /*  
    axios
      .get("http://localhost:5173/exercises/" + id + "/rubric")
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
  */
    if (resolution.data.type === ExerciseType.TRUE_OR_FALSE)
      switch (justifyKind) {
        case ExerciseJustificationKind.JUSTIFY_ALL:
          setPreview(
            <>
              {Object.entries((resolution!.data as TFResolutionData).items).map(
                ([key, item]: [
                  string,
                  {
                    text: string;
                    justification: string;
                    value?: boolean;
                  }
                ]) => (
                  <form className="flex flex-col">
                    <label
                      className={` ${
                        solution.data.items[key].value === item.value
                          ? " text-green-400"
                          : "text-red-400"
                      } block mb-2 text-md font-medium  `}
                    >
                      {key + ": " + item.text}
                    </label>
                    {solution.data.items[key].value === item.value ? (
                      <input
                        type="number"
                        id="cotation"
                        value={exeCotation}
                        className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                        placeholder={"Max: " + resolution?.cotation?.toString()}
                        pattern="[0-9]{3}.[0-9]{2}"
                        min={0}
                        max={cotation}
                        onChange={setExeCotation}
                        required
                      />
                    ) : (
                      <input
                        type="value"
                        id="grade"
                        className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                        placeholder={"0"}
                        pattern="[0-9]{3}.[0-9]{2}"
                        disabled
                      />
                    )}

                    <button
                      type="submit"
                      className="text-white m-2 w-fit h-fit float-right bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                    >
                      Submit
                    </button>
                  </form>
                )
              )}
            </>
          );
          break;
        case ExerciseJustificationKind.JUSTIFY_FALSE ||
          ExerciseJustificationKind.JUSTIFY_TRUE:
          setPreview(
            <>
              {Object.entries(resolution!.data.items!).map(([key, item]) => (
                <form className="flex flex-col">
                  {(justifyKind === ExerciseJustificationKind.JUSTIFY_FALSE &&
                    item.value === false) ||
                  (justifyKind === ExerciseJustificationKind.JUSTIFY_TRUE &&
                    item.value === true) ? (
                    <>
                      <label
                        className={` ${
                          solution.data.items[key].value === false
                            ? " text-green-400"
                            : "text-red-400"
                        } mb-2 text-md font-medium  `}
                      >
                        {key + ": " + item.text}
                      </label>
                      {solution.data.items[key].value === false ? (
                        <input
                          type="number"
                          id="cotation"
                          value={exeCotation}
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={
                            "Max: " + resolution?.cotation?.toString()
                          }
                          pattern="[0-9]{3}.[0-9]{2}"
                          min={0}
                          max={cotation}
                          onChange={setExeCotation}
                          required
                        />
                      ) : (
                        <input
                          type="value"
                          id="grade"
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={"0"}
                          pattern="[0-9]{3}.[0-9]{2}"
                          disabled
                        />
                      )}
                    </>
                  ) : (
                    <>
                      <label
                        className={` ${
                          solution.data.items[key].value === true
                            ? " text-green-400"
                            : "text-red-400"
                        } mb-2 text-md font-medium  `}
                      >
                        {key + ": " + item.text}
                      </label>
                      {solution.data.items[key].value === item.value ? (
                        <input
                          type="number"
                          id="cotation"
                          value={exeCotation}
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={
                            "Max: " + resolution?.cotation?.toString()
                          }
                          pattern="[0-9]{3}.[0-9]{2}"
                          min={0}
                          max={cotation}
                          onChange={setExeCotation}
                          required
                        />
                      ) : (
                        <input
                          type="value"
                          id="grade"
                          className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                          placeholder={"0"}
                          pattern="[0-9]{3}.[0-9]{2}"
                          disabled
                        />
                      )}
                    </>
                  )}
                </form>
              ))}
            </>
          );
          break;

        case ExerciseJustificationKind.NO_JUSTIFICATION:
          setPreview(
            <div>
              {Object.entries(resolution!.data.items!).map(([key, item]) => (
                <form className="flex flex-col">
                  <label
                    className={` ${
                      solution.data.items[key].value === false
                        ? " text-green-400"
                        : "text-red-400"
                    } mb-2 text-md font-medium  `}
                  >
                    {key + ": " + item.text}
                  </label>
                  {solution.data.items[key].value === item.value ? (
                    <input
                      type="value"
                      id="grade"
                      className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                      placeholder={"Max: " + cotation?.toString()}
                      pattern="[0-9]{3}.[0-9]{2}"
                      required
                    />
                  ) : (
                    <input
                      type="value"
                      id="grade"
                      className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                      placeholder={"0"}
                      pattern="[0-9]{3}.[0-9]{2}"
                      disabled
                    />
                  )}
                </form>
              ))}
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
