import "./ListExercises.css";
import {
  CheckboxIcon,
  CheckedListIcon,
  CodeIcon,
  InputIcon,
  TextIcon,
} from "../SVGImages/SVGImages";
import { useState } from "react";
import { ExerciseType } from "../Exercise/Exercise";

interface CreateNewExercisePopUpProps {
  createNewExercise: (value: ExerciseType) => void;
}

export function CreateNewExercisePopUp({
  createNewExercise,
}: CreateNewExercisePopUpProps) {
  const [newExercisetype, setNewExercisetype] = useState(
    ExerciseType.MULTIPLE_CHOICE
  );
  return (
    <>
      <label className="flex w-full justify-between mb-4 px-4 pb-2.5 text-title-1 border-b-2 border-gray-1">
        Criar novo exercício
      </label>
      <div className="grid grid-cols-2">
        <button
          onClick={() => setNewExercisetype(ExerciseType.MULTIPLE_CHOICE)}
          className={`${
            ExerciseType.MULTIPLE_CHOICE != newExercisetype
              ? "bg-btn-4-1"
              : "btn-selected"
          } btn-ListExercises text-lg group`}
        >
          <CheckedListIcon style="inherit-icon" size="size-12" />
          Escolha múltipla
        </button>
        <button
          onClick={() => setNewExercisetype(ExerciseType.OPEN_ANSWER)}
          className={`${
            ExerciseType.OPEN_ANSWER != newExercisetype
              ? "bg-btn-4-1"
              : "btn-selected"
          } btn-ListExercises text-lg group`}
        >
          <TextIcon style="inherit-icon" size="size-12" />
          Resposta aberta
        </button>
        <button
          onClick={() => setNewExercisetype(ExerciseType.TRUE_OR_FALSE)}
          className={`${
            ExerciseType.TRUE_OR_FALSE != newExercisetype
              ? "bg-btn-4-1"
              : "btn-selected"
          } btn-ListExercises text-lg group`}
        >
          <CheckboxIcon style="inherit-icon" size="size-12" />
          Verdadeiro ou falso
        </button>
      </div>
      <div className="flex justify-end">
        <button
          onClick={() => createNewExercise(newExercisetype)}
          className="btn-selected btn-ListExercises group"
        >
          <label className="text-lg">Seguinte</label>
        </button>
      </div>
    </>
  );
}
