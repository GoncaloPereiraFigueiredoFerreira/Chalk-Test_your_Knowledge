// import { FillBlankExercise } from "./FillBlank/FillBlankExercise";
// import { CodeExercise } from "./Code/CodeExercise";
import { MCExercise } from "./MC/MCExercise";
import { OAExercise } from "./OA/OAExercise";
import { TFExercise } from "./TF/TFExercise";
import { useEffect, useState } from "react";
import "./ShowExercise.css";
import { Exercise } from "./Exercise";

interface ExerciseProps {
  position: string;
  exercise: Exercise;
}

export function PreviewExercise({ position, exercise }: ExerciseProps) {
  const [preview, setPreview] = useState(<></>);

  useEffect(() => {
    switch (exercise.type) {
      case "multiple-choice":
        setPreview(
          <MCExercise
            exercise={exercise}
            contexto="preview"
            position={position}
          ></MCExercise>
        );
        break;
      case "open-answer":
        setPreview(
          <OAExercise
            exercise={exercise}
            contexto="preview"
            position={position}
          ></OAExercise>
        );
        break;
      case "true-or-false":
        setPreview(
          <TFExercise
            exercise={exercise}
            contexto="preview"
            position={position}
          ></TFExercise>
        );
        break;
      case "fill-in-the-blank":
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
      case "code":
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
  }, [exercise]);

  return (
    <div className="max-h-56 border-gray-500 rounded-lg overflow-auto">
      <div className="">{preview}</div>;
    </div>
  );
}
