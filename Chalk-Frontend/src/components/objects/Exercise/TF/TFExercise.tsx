import { ExerciseComponentProps, ExerciseContext } from "../Exercise";
import { TFPreview } from "./TFPreview";
import { TFSolve } from "./TFSolve";

export function TFExercise({
  position,
  context,
  exercise,
}: ExerciseComponentProps) {
  let exerciseDisplay = <></>;
  if (exercise.items && exercise.justifyKind)
    switch (context.context) {
      case ExerciseContext.SOLVE:
        exerciseDisplay = (
          <TFSolve
            id={exercise.id}
            position={position}
            items={exercise.items}
            statement={exercise.statement}
            justifyKind={exercise.justifyKind}
            resolution={context.resolutionData}
            setResolution={context.setExerciseSolution}
          />
        );
        break;

      case ExerciseContext.PREVIEW:
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

      case ExerciseContext.EDIT:
        exerciseDisplay = <></>;
        break;

      case ExerciseContext.GRADING:
        exerciseDisplay = <></>;
        break;

      case ExerciseContext.REVIEW:
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
