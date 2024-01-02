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
import { CQAnswer } from "./CQAnswer";

export interface AnswerProps {
  solution: Resolution;
}

export function Answer({ solution }: AnswerProps) {
  const [preview, setPreview] = useState(<></>);

  useEffect(() => {
    switch (solution.data.type) {
      case ExerciseType.MULTIPLE_CHOICE:
        setPreview(<MCAnswer solution={solution}></MCAnswer>);
        break;
      case ExerciseType.OPEN_ANSWER:
        setPreview(<OAAnswer solution={solution}></OAAnswer>);
        break;
      case ExerciseType.TRUE_OR_FALSE:
        setPreview(<TFAnswer solution={solution}></TFAnswer>);
        break;
      case ExerciseType.CHAT:
        setPreview(<CQAnswer solution={solution}></CQAnswer>);
        break;
    }
  }, [solution]);

  return <>{preview}</>;
}
