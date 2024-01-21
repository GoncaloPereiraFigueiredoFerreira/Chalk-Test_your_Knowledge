import { PreviewProps, TFExercise, TFResolutionData } from "../Exercise";
import { ExerciseHeaderComp } from "../Header/ExHeader";

export interface TFPreviewProps {
  exercise: TFExercise;
  position: string;
  context: PreviewProps;
}

export function TFPreview({ exercise, position, context }: TFPreviewProps) {
  return (
    <>
      <ExerciseHeaderComp header={exercise.base.statement} />
      <div>Indique se as seguintes opções são Verdadeiras ou Falsas:</div>
      <div className="flex flex-col pl-4 pr-4 py-4 text-black dark:text-white">
        {Object.entries(exercise.props.items).map(([index, value]) => (
          <TFShowStatement
            key={index}
            id={index}
            text={value.text}
            name={`radio-button-${index}-${exercise.identity?.id}-${position}`}
            justifyKind={exercise.props.justifyType}
            context={context}
          />
        ))}
      </div>
    </>
  );
}

function TFShowStatement(props: any) {
  return (
    <>
      <div className="flex flex-row px-4 py-2 gap-4 items-center">
        <input
          className="radio-green"
          type="radio"
          name={props.name}
          checked={
            props.context.resolution
              ? (props.context.resolution as TFResolutionData).items[props.id]
                  .value
              : false
          }
          disabled
        ></input>
        <input
          className="radio-red"
          type="radio"
          name={props.name}
          checked={
            props.context.resolution
              ? !(props.context.resolution as TFResolutionData).items[props.id]
                  .value
              : false
          }
          disabled
        ></input>
        <div className="text-base">{props.text}</div>
      </div>
    </>
  );
}
