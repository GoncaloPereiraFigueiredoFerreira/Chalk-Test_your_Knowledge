import { ExerciseComponentProps, ExerciseContext } from "../Exercise";
import { MCPreview } from "./MCPreview";
import { MCSolve } from "./MCSolve";

export function MCExercise({
  exercise,
  position,
  context,
}: ExerciseComponentProps) {
  let exerciseDisplay = <></>;
  if (exercise.items && exercise.justifyKind)
    switch (context.context) {
      case ExerciseContext.SOLVE:
        exerciseDisplay = (
          <MCSolve
            id={exercise.id}
            position={position}
            items={exercise.items}
            statement={exercise.statement}
            justifyKind={exercise.justifyKind}
          ></MCSolve>
        );
        break;

      case ExerciseContext.PREVIEW:
        exerciseDisplay = (
          <MCPreview
            id={exercise.id}
            position={position}
            items={exercise.items}
            statement={exercise.statement}
            justifyKind={exercise.justifyKind}
          ></MCPreview>
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
