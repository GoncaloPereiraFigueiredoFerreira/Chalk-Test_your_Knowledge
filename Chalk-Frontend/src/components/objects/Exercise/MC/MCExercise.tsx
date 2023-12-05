import { Exercise } from "../Exercise";
import { MCSolve } from "./MCSolve";

interface ExerciseProps {
  position: string;
  contexto: string;
  exercise: Exercise;
}

export function MCExercise({ exercise, position, contexto }: ExerciseProps) {
  let exerciseDisplay = <></>;
  if (exercise.items && exercise.justifyKind)
    switch (contexto) {
      case "solve":
        exerciseDisplay = (
          <MCSolve
            id={exercise.id}
            position={position}
            items={exercise.items}
            statement={exercise.statement}
            justify={exercise.justifyKind}
          ></MCSolve>
        );
        break;

      case "preview":
        exerciseDisplay = <></>;
        break;

      case "correct":
        exerciseDisplay = <></>;
        break;

      case "psolution":
        exerciseDisplay = <></>;
        break;
    }
  return (
    <>
      <div className="m-5 text-title-2">{position + ") " + exercise.title}</div>
      <div className="m-5 text-lg">{exerciseDisplay}</div>
    </>
  );
}
