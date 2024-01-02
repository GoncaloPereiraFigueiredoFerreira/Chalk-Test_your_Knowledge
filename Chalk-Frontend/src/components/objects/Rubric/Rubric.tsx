import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
  Resolution,
} from "../Exercise/Exercise";
import { useState, useEffect } from "react";
import "./FBRubric";
import "./OARubric";
import "./MCRubric";

export interface RubricProps {
  type: ExerciseType;
}

export function Rubric({ type }: RubricProps) {
  const [preview, setPreview] = useState(<></>);

  useEffect(() => {
    switch (type) {
      case ExerciseType.MULTIPLE_CHOICE:
        setPreview(<MCRubric></MCRubric>);
        break;
      case ExerciseType.OPEN_ANSWER:
        setPreview(<OARubric></OARubric>);
        break;
      case ExerciseType.TRUE_OR_FALSE:
        setPreview(<MCRubric></MCRubric>);
        break;
    }
  }, [solution]);

  return <div>{preview}</div>;
}
