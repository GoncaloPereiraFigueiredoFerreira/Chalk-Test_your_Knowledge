import { useState } from "react";
import { ExerciseHeader } from "../Exercise/Header/ExHeader";
import { Answer } from "./Answer";

export function OAAnswer({ type, answers, correction }: Answer) {
  const [valor, setValor] = useState("");
  return (
    <>
      <div className=" border-b-2"></div>
      <div>
        {answers.map((str: String) => (
          <p>{str}</p>
        ))}
      </div>
      <div>
        <label className="hidden mb-2 text-sm font-medium text-gray-900 dark:text-white">
          Grading :
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
    </>
  );
}
