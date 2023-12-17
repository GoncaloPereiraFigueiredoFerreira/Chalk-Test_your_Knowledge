import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
  Resolution,
} from "../Exercise/Exercise";
import { useState, useEffect } from "react";
import { TFAnswer } from "./TFAnswer";
import { OAAnswer } from "./OAAnswer";
import { MCAnswer } from "./MCAnswer";
import { students } from "../../pages/Tests/Correction/example";

export function Answer(
  solution: Resolution,
  cotation: number,
  justifyKind: ExerciseJustificationKind,
  resolution: Resolution
) {
  const [preview, setPreview] = useState(<></>);

  useEffect(() => {
    switch (resolution.data.type) {
      case ExerciseType.MULTIPLE_CHOICE:
        setPreview(
          <MCAnswer
            solution={solution}
            cotation={cotation}
            justifyKind={justifyKind}
            resolution={resolution}
          ></MCAnswer>
        );
        break;
      case ExerciseType.OPEN_ANSWER:
        setPreview(
          <OAAnswer
            solution={solution}
            cotation={cotation}
            justifyKind={justifyKind}
            resolution={resolution}
          ></OAAnswer>
        );
        break;
      case ExerciseType.TRUE_OR_FALSE:
        setPreview(
          <TFAnswer
            solution={solution}
            cotation={cotation}
            justifyKind={justifyKind}
            resolution={resolution}
          ></TFAnswer>
        );
        break;
      case ExerciseType.FILL_IN_THE_BLANK:
        setPreview(
          <></>
          // <FillBlankExercise
          //   statement={exercise.statement}
          //   problem={exercise.problem}
          //   contexto="solve"
          //   name={name}
          // ></FillBlankExercise>
        );
        break;
      case ExerciseType.CODE:
        setPreview(
          <></>
          // <CodeExercise
          //   statement={statement}
          //   problem={problem}
          //   contexto="solve"
          //   name={name}
          // ></CodeExercise>
        );
        break;
    }
  }, []);

  return <div>{preview}</div>;
}
