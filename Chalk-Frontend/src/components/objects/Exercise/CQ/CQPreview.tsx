import { CQExercise, PreviewProps } from "../Exercise";
import { ExerciseHeaderComp } from "../Header/ExHeader";

export interface CQPreviewProps {
  exercise: CQExercise;
  position: string;
  context: PreviewProps;
}

export function CQPreview({ exercise, position, context }: CQPreviewProps) {
  return (
    <>
      <ExerciseHeaderComp header={exercise.base.statement} />
    </>
  );
}
