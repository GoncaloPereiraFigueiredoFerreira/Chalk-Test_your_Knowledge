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
      <p>
        <strong>Tipo de Justificação:</strong> {exercise.props.justifyType}
      </p>
      <ul>
        {Object.entries(exercise.props.items).map(([index, value]) => (
          <div key={index}>
            <label
              htmlFor={"mc" + exercise.identity?.id + index + position}
              className="flex px-4 py-2 gap-2 items-center hover:bg-gray-300"
            >
              <input
                id={"mc" + exercise.identity?.id + index + position}
                name={"mc" + exercise.identity?.id + position}
                type="radio"
                className="radio-blue mr-3"
                checked={
                  context.resolution
                    ? (context.resolution as MCResolutionData).items[index]
                        .value
                    : false
                }
                disabled
              ></input>
              {value.text}
            </label>
            {/*
            <MCJustify
              index={index}
              chosenOption={chosenOption}
              justifyKind={justifyKind}
            ></MCJustify>*/}
          </div>
        ))}
      </ul>
    </>
  );
}
