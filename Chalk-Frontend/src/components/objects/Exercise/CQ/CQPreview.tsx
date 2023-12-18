import { ExerciseHeader } from "../Header/ExHeader";

export function CQPreview({ id, position, statement, additionalProps }: any) {
  return (
    <>
      <ExerciseHeader header={statement} />
    </>
  );
}
