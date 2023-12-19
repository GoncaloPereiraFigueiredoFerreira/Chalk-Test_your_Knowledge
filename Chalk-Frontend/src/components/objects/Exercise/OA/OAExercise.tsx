import {
  ExerciseComponentProps,
  ExerciseContext,
  OAExercise,
  PreviewProps,
  SolveProps,
} from "../Exercise";
import { OASolve } from "./OASolve";
import { OAPreview } from "./OAPreview";

export function OAExerciseComp({
  position,
  context,
  exercise,
}: ExerciseComponentProps) {
  let exerciseDisplay = <></>;
  switch (context.context) {
    case ExerciseContext.SOLVE:
      exerciseDisplay = (
        <OASolve
          position={position}
          context={context as SolveProps}
          exercise={exercise as OAExercise}
        ></OASolve>
      );
      break;

    case ExerciseContext.PREVIEW:
      exerciseDisplay = (
        <OAPreview
          position={position}
          context={context as PreviewProps}
          exercise={exercise as OAExercise}
        ></OAPreview>
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
    <div className="">
      <div className="m-5 text-title-2">
        {position + ") " + exercise.base.title}
      </div>
      <div className="m-5 text-lg">{exerciseDisplay}</div>
    </div>
  );
}
