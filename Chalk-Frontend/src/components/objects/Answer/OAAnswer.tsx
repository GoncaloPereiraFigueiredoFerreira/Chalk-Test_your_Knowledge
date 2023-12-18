import { useEffect, useState } from "react";
import { ExerciseJustificationKind, ExerciseType } from "../Exercise/Exercise";
import axios from "axios";
import { AnswerProps } from "./Answer";

interface Standards {
  cotation: number;
  description: string;
  title: string;
}

interface OARubric {
  description: string;
  type: ExerciseJustificationKind;
  id: string;
  criteria: {
    standards: Standards[];
    title: string;
  };
}

export function OAAnswer({ resolution, cotation }: AnswerProps) {
  const [rubric, setRubric] = useState<OARubric>();

  const standCotation = Array<number>(
    rubric ? Object.entries(rubric.criteria.standards).length : 1
  ).fill(0);

  function handleSubmit(e: any) {
    e.preventDefault();
    axios
      .post(
        "http://localhost:5173/exercises/resolutions/" +
          resolution!.id +
          "/manual-correction",
        { cotation: standCotation.reduce((a, b) => a + b, 0) }
      )
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
  }

  /*   useEffect(() => {
    axios
      .get("http://localhost:5173/exercises/" + id + "/rubric")
      .then((response) => console.log(response))
      .catch((error) => console.log(error)); 
  }, []); */

  return (
    <div className="flex flex-col">
      <div className=" border-b-2"></div>
      {resolution!.data.type === ExerciseType.OPEN_ANSWER &&
      resolution!.data.text ? (
        <div>{resolution!.data.text}</div>
      ) : (
        <></>
      )}
      <form onSubmit={handleSubmit} className="flex ">
        {rubric ? (
          Object.entries(rubric.criteria.standards).map(([key, standard]) => (
            <>
              <label className=" mb-2 text-md font-medium text-gray-900 dark:text-white">
                {standard.description}
                <input
                  type="number"
                  id={key}
                  className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                  placeholder={"Max: " + standard.cotation?.toString()}
                  pattern="[0-9]{3}.[0-9]{2}"
                  min={0}
                  max={standard.cotation}
                  onChange={(e) =>
                    (standCotation[parseInt(key)] = parseFloat(e.target.value))
                  }
                  required
                />
              </label>
            </>
          ))
        ) : (
          <>
            <label className=" mb-2 text-md font-medium text-gray-900 dark:text-white">
              <input
                type="number"
                id="cotation"
                className="bg-gray-50 border w-fit h-fit float-left border-gray-300  text-md rounded-lg block m-2 p-2 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white"
                placeholder={"Max: " + cotation?.toString()}
                pattern="[0-9]{3}.[0-9]{2}"
                min={0}
                max={cotation}
                onChange={(e) =>
                  (standCotation[0] = parseFloat(e.target.value))
                }
                required
              />
            </label>
          </>
        )}
        <button
          type="submit"
          className="text-white m-2 w-fit h-fit float-right bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          Submit
        </button>
      </form>
    </div>
  );
}
