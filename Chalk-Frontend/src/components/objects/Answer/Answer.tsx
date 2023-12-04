import { ExerciseJustificationKind, ExerciseType } from "../Exercise/Exercise";
import { useState, useEffect } from "react";
import { TFAnswer } from "./TFAnswer";
import { OAAnswer } from "./OAAnswer";
import { MCAnswer } from "./MCAnswer";

export interface Answer {
  type: ExerciseType;
  justification?: ExerciseJustificationKind;
  answers: string[];
  correction: string[];
}

export interface AnswerProps {
  id: string;
  name: string;
  email: string;
  answer: {
    type: ExerciseType;
    justification?: ExerciseJustificationKind;
    answers: string[];
    correction: string[];
  };
  remCorrection?: (value: string) => void;
}

export function Answer({ name, id, email, answer }: AnswerProps) {
  console.log(answer);

  const [preview, setPreview] = useState(<></>);
  const [selectedAnswer, setSelectedAnswer] = useState(false);

  useEffect(() => {
    switch (answer.type) {
      case "multiple-choice":
        setPreview(
          <MCAnswer
            type={answer.type}
            answers={answer.answers}
            correction={answer.correction}
            justification={answer.justification}
          ></MCAnswer>
        );
        break;
      case "open-answer":
        setPreview(
          <OAAnswer
            type={answer.type}
            answers={answer.answers}
            correction={answer.correction}
          ></OAAnswer>
        );
        break;
      case "true-or-false":
        setPreview(
          <TFAnswer
            type={answer.type}
            answers={answer.answers}
            correction={answer.correction}
            justification={answer.justification}
          ></TFAnswer>
        );
        break;
      case "fill-in-the-blank":
        setPreview(
          <></>
          // <FillBlankExercise
          //   statement={exercise.statement}
          //   problem={exercise.problem}
          //   contexto="solve"
          //   name={name}
          // ></FillBlankExercise>
        );
        break;
      case "code":
        setPreview(
          <></>
          // <CodeExercise
          //   statement={statement}
          //   problem={problem}
          //   contexto="solve"
          //   name={name}
          // ></CodeExercise>
        );
        break;
    }
  }, [answer]);

  return (
    <div
      className={` ${selectedAnswer ? "max-h-full" : "max-h-[60px]"} ${
        answer.type === "multiple-choice"
          ? JSON.stringify(answer.answers) === JSON.stringify(answer.correction)
            ? " bg-green-400"
            : " bg-red-400"
          : ""
      } transition-[max-height] bg-white text-black duration-1000 rounded-lg mb-4 overflow-hidden`}
    >
      <div
        className=" items-center text-md font-medium cursor-pointer p-4"
        onClick={() =>
          selectedAnswer ? setSelectedAnswer(false) : setSelectedAnswer(true)
        }
      >
        {name + " (" + email + ")"}
      </div>
      <div
        className={`${selectedAnswer ? "" : "scale-y-0"} p-4 rounded-lg ex-1`}
      >
        {preview}
      </div>
    </div>
  );
}
