import { useEffect, useState } from "react";
import {
  Exercise,
  Item,
  ExerciseJustificationKind,
} from "../Exercise/Exercise";
import axios from "axios";

interface MCRubric {
  description: string;
  type: ExerciseJustificationKind;
  id: string;
  choiceCotation: number;
  penalty: number;
}

export function MCAnswer({
  id,
  cotation,
  solution,
  resolution,
  justifyKind,
}: Exercise) {
  const [preview, setPreview] = useState(<></>);
  const [blank, setBlank] = useState(true);
  const [exeCotation, setCotation] = useState(cotation);
  const [rubric, setRubric] = useState<MCRubric>();

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

  console.log(cotation);
  console.log(solution);
  console.log(resolution);
  console.log(justifyKind);

  useEffect(() => {
    /* axios
      .get("http://localhost:5173/exercises/" + id + "/rubric")
      .then((response) => console.log(response))
      .catch((error) => console.log(error)); */

    if (solution)
      switch (justifyKind) {
        case ExerciseJustificationKind.JUSTIFY_MARKED:
          setPreview(
            <>
              <div className="pt-2">
                {resolution ? (
                  Object.entries(resolution!.data.items!).map(
                    ([key, item]: [string, Item]) =>
                      item.value ? (
                        <form className="flex">
                          <label
                            className={` ${
                              solution.data.items[key] !== undefined
                                ? " text-green-400"
                                : "text-red-400"
                            }  mb-2 text-md font-medium  `}
                          >
                            {key + ": " + item.text}
                          </label>
                          {solution.data.items[key] ? (
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
                          <button
                            type="submit"
                            className="text-white m-2 w-fit h-fit float-right bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                          >
                            Submit
                          </button>
                        </form>
                      ) : (
                        <></>
                      )
                  )
                ) : (
                  <></>
                )}
              </div>
            </>
          );
          break;
        case ExerciseJustificationKind.NO_JUSTIFICATION:
          setPreview(
            <>
              {Object.entries(resolution!.data.items!).map(
                ([key, item]: [string, Item]) =>
                  item.value ? (
                    <div className="pt-2">
                      <form className="flex flex-col">
                        <label
                          className={` ${
                            solution.data.items[key]
                              ? " text-green-400"
                              : "text-red-400"
                          }  mb-2 text-md font-medium  `}
                        >
                          {key + ": " + item.text}
                        </label>
                        {solution.data.items[key] ? (
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
                        <button
                          type="submit"
                          className="text-white m-2 w-fit h-fit float-right bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                        >
                          Submit
                        </button>
                      </form>
                    </div>
                  ) : (
                    <></>
                  )
              )}
            </>
          );
          break;
      }
    else
      switch (justifyKind) {
        case ExerciseJustificationKind.JUSTIFY_MARKED:
          setPreview(
            <>
              <div className="pt-2">
                {resolution ? (
                  Object.entries(resolution!.data.items!).map(
                    ([key, item]: [string, Item]) =>
                      item.value ? (
                        <form className="flex">
                          <label
                            className={` text-black mb-2 text-md font-medium  `}
                          >
                            {key + ": " + item.text}
                          </label>
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
                          <button
                            type="submit"
                            className="text-white m-2 w-fit h-fit float-right bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                          >
                            Submit
                          </button>
                        </form>
                      ) : (
                        <></>
                      )
                  )
                ) : (
                  <></>
                )}
              </div>
            </>
          );
          break;
        case ExerciseJustificationKind.NO_JUSTIFICATION:
          setPreview(
            <>
              {Object.entries(resolution!.data.items!).map(
                ([key, item]: [string, Item]) =>
                  item.value ? (
                    <div className="pt-2">
                      <form className="flex flex-col">
                        <label
                          className={` text-black mb-2 text-md font-medium  `}
                        >
                          {key + ": " + item.text}
                        </label>

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
                        <button
                          type="submit"
                          className="text-white m-2 w-fit h-fit float-right bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                        >
                          Submit
                        </button>
                      </form>
                    </div>
                  ) : (
                    <></>
                  )
              )}
            </>
          );
          break;
      }
  }, []);

  return (
    <>
      {/* box will become green if right, red if wrong */}
      {preview}
    </>
  );
}
