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
      <p>
        <strong>Tipo de Justificação:</strong> {exercise.props.justifyType}
      </p>
      <div className="grid-layout-exercise mt-4 gap-2 min-h-max items-center">
        <div className="flex text-xl font-bold px-4">V</div>
        <div className="flex text-xl font-bold px-4">F</div>
        <div></div>
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
      <div className="flex items-start justify-center">
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
      </div>
      <div className="flex items-start justify-center">
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
      </div>
      <div className="">
        <p>{props.text}</p>
      </div>
    </>
  );
}
