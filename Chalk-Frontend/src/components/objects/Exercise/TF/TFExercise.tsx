import {
  CreateEditProps,
  ExerciseComponentProps,
  ExerciseContext,
  PreviewProps,
  SolveProps,
  TFExercise,
} from "../Exercise";
import { TFEdit } from "./TFEdit";
import { TFPreview } from "./TFPreview";
import { TFSolve } from "./TFSolve";

export function TFExerciseComp({
  exercise,
  position,
  context,
}: ExerciseComponentProps) {
  let exerciseDisplay = <></>;

  switch (context.context) {
    case ExerciseContext.SOLVE:
      exerciseDisplay = (
        <TFSolve
          context={context as SolveProps}
          exercise={exercise as TFExercise}
          position={position}
        />
      );
      break;

    case ExerciseContext.PREVIEW:
      exerciseDisplay = (
        <TFPreview
          context={context as PreviewProps}
          exercise={exercise as TFExercise}
          position={position}
        />
      );
      break;

    case ExerciseContext.EDIT:
      exerciseDisplay = (
        <TFEdit
          context={context as CreateEditProps}
          exercise={exercise as TFExercise}
        />
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
      {context.context !== ExerciseContext.EDIT ? (
        <div className="m-5 text-title-2 ">
          {position + ") " + exercise.base.title}
        </div>
      ) : null}
      <div className="m-5 text-lg ">{exerciseDisplay}</div>
    </>
  );
}
