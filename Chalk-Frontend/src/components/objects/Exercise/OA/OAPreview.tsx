import { TextareaBlock } from "../../../interactiveElements/TextareaBlock";
import { OAExercise, PreviewProps } from "../Exercise";
import { ExerciseHeaderComp } from "../Header/ExHeader";

export interface OAPreviewProps {
  exercise: OAExercise;
  position: string;
  context: PreviewProps;
}

export function OAPreview(props: OAPreviewProps) {
  return (
    <>
      <ExerciseHeaderComp
        header={props.exercise.base.statement}
      ></ExerciseHeaderComp>

      <form>
        <div className="w-full mb-4">
          <div className="px-4 py-2 rounded-b-lg">
            <TextareaBlock
              toolbar
              className="w-full focus:ring-0 bg-white pb-2"
              disabled={true}
            ></TextareaBlock>
          </div>
        </div>
      </form>
    </>
  );
}
