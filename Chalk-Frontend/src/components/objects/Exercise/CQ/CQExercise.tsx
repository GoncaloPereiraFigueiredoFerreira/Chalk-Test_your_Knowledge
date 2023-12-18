import { ExerciseComponentProps, ExerciseContext } from "../Exercise";
import { CQPreview } from "./CQPreview";
import { CQSolve } from "./CQSolve";

export function CQExercise({
  exercise,
  position,
  context,
}: ExerciseComponentProps) {
  let exerciseDisplay = <></>;
  switch (context.context) {
    case ExerciseContext.SOLVE:
      exerciseDisplay = (
        <CQSolve
          id={exercise.id}
          position={position}
          statement={exercise.statement}
          resolution={context.resolutionData}
          setResolution={context.setExerciseSolution}
        ></CQSolve>
      );
      break;

    case ExerciseContext.PREVIEW:
      exerciseDisplay = (
        <CQPreview
          id={exercise.id}
          position={position}
          statement={exercise.statement}
        ></CQPreview>
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
