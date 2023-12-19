import {
  CQExercise,
  ExerciseComponentProps,
  ExerciseContext,
  PreviewProps,
  SolveProps,
} from "../Exercise";
import { CQEdit } from "./CQEdit";
import { CQPreview } from "./CQPreview";
import { CQSolve } from "./CQSolve";

export function CQExerciseComp({
  exercise,
  position,
  context,
}: ExerciseComponentProps) {
  let exerciseDisplay = <></>;
  switch (context.context) {
    case ExerciseContext.SOLVE:
      exerciseDisplay = (
        <CQSolve
          exercise={exercise as CQExercise}
          position={position}
          context={context as SolveProps}
        ></CQSolve>
      );
      break;

    case ExerciseContext.PREVIEW:
      exerciseDisplay = (
        <CQPreview
          exercise={exercise as CQExercise}
          position={position}
          context={context as PreviewProps}
        ></CQPreview>
      );
      break;

    case ExerciseContext.EDIT:
      exerciseDisplay = (
        <CQEdit exercise={exercise as CQExercise} context={context}></CQEdit>
      );
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
      <div className="m-5 text-title-2">
        {position + ") " + exercise.base.title}
      </div>
      <div className="m-5 text-lg">{exerciseDisplay}</div>
    </>
  );
}
