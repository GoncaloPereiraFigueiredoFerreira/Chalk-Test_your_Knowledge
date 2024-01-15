import "./ListExercises.css";
import {
  CheckboxIcon,
  CheckedListIcon,
  TextIcon,
} from "../SVGImages/SVGImages";
import { PiChatsBold } from "react-icons/pi";
import { useState } from "react";
import { ExerciseType } from "../Exercise/Exercise";
import { PopUp } from "../../interactiveElements/PopUp";

interface CreateNewExercisePopUpProps {
  show: boolean;
  closePopUp: () => void;
  createNewExercise: (value: ExerciseType) => void;
}

export function CreateNewExercisePopUp({
  show,
  closePopUp,
  createNewExercise,
}: CreateNewExercisePopUpProps) {
  const [newExercisetype, setNewExercisetype] = useState(
    ExerciseType.MULTIPLE_CHOICE
  );
  return (
    <PopUp show={show} closePopUp={closePopUp}>
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
            } btn-ListExercises group`}
          >
            <CheckedListIcon size="size-12" />
            Escolha múltipla
          </button>
          <button
            onClick={() => setNewExercisetype(ExerciseType.OPEN_ANSWER)}
            className={`${
              ExerciseType.OPEN_ANSWER != newExercisetype
                ? "bg-btn-4-1"
                : "btn-selected"
            } btn-ListExercises group`}
          >
            <TextIcon size="size-12" />
            Resposta aberta
          </button>
          <button
            onClick={() => setNewExercisetype(ExerciseType.TRUE_OR_FALSE)}
            className={`${
              ExerciseType.TRUE_OR_FALSE != newExercisetype
                ? "bg-btn-4-1"
                : "btn-selected"
            } btn-ListExercises group`}
          >
            <CheckboxIcon size="size-12" />
            Verdadeiro ou falso
          </button>
          <button
            onClick={() => setNewExercisetype(ExerciseType.CHAT)}
            className={`${
              ExerciseType.CHAT != newExercisetype
                ? "bg-btn-4-1"
                : "btn-selected"
            } btn-ListExercises group`}
          >
            <PiChatsBold className="size-12" />
            Chat
          </button>
        </div>
        <div className="flex justify-end">
          <button
            onClick={() => createNewExercise(newExercisetype)}
            className="bg-btn-4-1 btn-ListExercises group"
          >
            Seguinte
          </button>
        </div>
      </>
    </PopUp>
  );
}
