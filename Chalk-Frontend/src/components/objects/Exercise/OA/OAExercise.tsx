import { Exercise } from "../Exercise";
import { OASolve } from "./OASolve";

interface ExerciseProps {
  position: string;
  contexto: string;
  exercise: Exercise;
}

export function OAExercise({ position, contexto, exercise }: ExerciseProps) {
  let exerciseDisplay = <></>;
  switch (contexto) {
    case "solve":
      exerciseDisplay = <OASolve statement={exercise.statement}></OASolve>;
      break;

    case "preview":
      exerciseDisplay = <OASolve statement={exercise.statement}></OASolve>;
      break;

    case "correct":
      exerciseDisplay = <></>;
      break;

    case "psolution":
      exerciseDisplay = <></>;
      break;
  }
  return (
    <div className="">
      <div className="m-5 text-title-2">{position + ") " + exercise.title}</div>
      <div className="m-5 text-lg">{exerciseDisplay}</div>
    </div>
  );
}
