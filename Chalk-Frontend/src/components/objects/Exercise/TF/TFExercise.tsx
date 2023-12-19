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

export function TFExerciseComp(props: ExerciseComponentProps) {
  let exerciseDisplay = <></>;

  switch (props.context.context) {
    case ExerciseContext.SOLVE:
      exerciseDisplay = (
        <TFSolve
          context={props.context as SolveProps}
          exercise={props.exercise as TFExercise}
          position={props.position}
        />
      );
      break;

    case ExerciseContext.PREVIEW:
      exerciseDisplay = (
        <TFPreview
          context={props.context as PreviewProps}
          exercise={props.exercise as TFExercise}
          position={props.position}
        />
      );
      break;

    case ExerciseContext.EDIT:
      exerciseDisplay = (
        <TFEdit
          context={props.context as CreateEditProps}
          exercise={props.exercise as TFExercise}
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
      <div className="m-5 text-title-2">
        {props.position + ") " + props.exercise.base.title}
      </div>
      <div className="m-5 text-lg">{exerciseDisplay}</div>
    </>
  );
}
