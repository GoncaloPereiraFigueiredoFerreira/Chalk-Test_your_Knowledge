import { MCExercise, MCResolutionData, PreviewProps } from "../Exercise";
import { ExerciseHeaderComp } from "../Header/ExHeader";

export interface MCPreviewProps {
  exercise: MCExercise;
  position: string;
  context: PreviewProps;
}

export function MCPreview({ exercise, position, context }: MCPreviewProps) {
  return (
    <>
      <ExerciseHeaderComp header={exercise.base.statement}></ExerciseHeaderComp>
      <div className="text-base">Selecione a opções correta:</div>
      <div className="flex flex-col pl-4 pr-4 py-4 text-black dark:text-white">
        {Object.entries(exercise.props.items).map(([index, value]) => (
          <label
            key={index}
            htmlFor={"mc" + exercise.identity?.id + index + position}
            className="flex px-4 py-2 gap-2 items-center"
          >
            <input
              id={"mc" + exercise.identity?.id + index + position}
              name={"mc" + exercise.identity?.id + position}
              type="radio"
              className="radio-blue mr-3"
              checked={
                context.resolution
                  ? (context.resolution as MCResolutionData).items[index].value
                  : false
              }
              disabled
            ></input>
            <div className="text-base">{value.text}</div>
          </label>
        ))}
      </div>
      <p className="pb-4 text-sm text-black dark:text-white">
        <strong>Tipo de Justificação:</strong> {exercise.props.justifyType}
      </p>
    </>
  );
}
