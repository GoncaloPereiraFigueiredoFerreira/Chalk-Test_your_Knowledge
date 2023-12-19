import {
  CreateEditProps,
  ExerciseComponentProps,
  ExerciseContext,
  MCExercise,
  PreviewProps,
  SolveProps,
} from "../Exercise";
import { MCEdit } from "./MCEdit";
import { MCPreview } from "./MCPreview";
import { MCSolve } from "./MCSolve";

export function MCExerciseComp({
  exercise,
  position,
  context,
}: ExerciseComponentProps) {
  let exerciseDisplay = <></>;

  switch (context.context) {
    case ExerciseContext.SOLVE:
      exerciseDisplay = (
        <MCSolve
          exercise={exercise as MCExercise}
          position={position}
          context={context as SolveProps}
        ></MCSolve>
      );
      break;

    case ExerciseContext.PREVIEW:
      exerciseDisplay = (
        <MCPreview
          exercise={exercise as MCExercise}
          position={position}
          context={context as PreviewProps}
        ></MCPreview>
      );
      break;

    case ExerciseContext.EDIT:
      exerciseDisplay = (
        <MCEdit
          context={context as CreateEditProps}
          exercise={exercise as MCExercise}
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
        {position + ") " + exercise.base.title}
      </div>
      <div className="m-5 text-lg">{exerciseDisplay}</div>
    </>
  );
}
