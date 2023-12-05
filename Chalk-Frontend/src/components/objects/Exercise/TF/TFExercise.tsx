import { Exercise } from "../Exercise";
import { TFPreview } from "./TFPreview";
import { TFSolve } from "./TFSolve";

interface ExerciseProps {
  position: string;
  contexto: string;
  exercise: Exercise;
}

export function TFExercise({ position, contexto, exercise }: ExerciseProps) {
  let exerciseDisplay = <></>;
  if (exercise.items && exercise.justifyKind)
    switch (contexto) {
      case "solve":
        exerciseDisplay = (
          <TFSolve
            id={exercise.id}
            position={position}
            items={exercise.items}
            statement={exercise.statement}
            justifyKind={exercise.justifyKind}
          />
        );
        break;

      case "preview":
        exerciseDisplay = (
          <TFPreview
            id={exercise.id}
            position={position}
            items={exercise.items}
            statement={exercise.statement}
            justifyKind={exercise.justifyKind}
          />
        );
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
