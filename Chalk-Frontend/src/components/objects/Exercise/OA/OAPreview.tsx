import { TextareaBlock } from "../../../interactiveElements/TextareaBlock";
import { OAExercise, OAResolutionData, PreviewProps } from "../Exercise";
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

      <form className="w-full mb-4 ">
        <div className="w-full mb-4">
          <div className="py-2 rounded-b-lg">
            <TextareaBlock
              toolbar
              rows={6}
              className="w-full focus:ring-0  pb-2"
              disabled={true}
              value={
                props.context.resolution
                  ? (props.context.resolution as OAResolutionData).text
                  : ""
              }
            ></TextareaBlock>
          </div>
        </div>
      </form>
    </>
  );
}
