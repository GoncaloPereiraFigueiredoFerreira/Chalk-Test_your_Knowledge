import "../../interactiveElements/PopUpListExercise.css";
import { useState } from "react";
import { ExerciseType } from "../Exercise/Exercise";
import { PopUp } from "../../interactiveElements/PopUp";
import { CiCircleList } from "react-icons/ci";
import { PiTextTBold } from "react-icons/pi";
import { TbCheckbox } from "react-icons/tb";
import { PiChatsBold } from "react-icons/pi";

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
        <label className="flex w-full justify-between mb-4 px-4 pb-2.5 text-4xl text-slate-600 dark:text-white border-b-2 border-[#dddddd]">
          Criar novo exercício
        </label>
        <div className="grid grid-cols-2 gap-4 py-4 px-4">
          <button
            onClick={() => setNewExercisetype(ExerciseType.MULTIPLE_CHOICE)}
            className={`${
              ExerciseType.MULTIPLE_CHOICE != newExercisetype
                ? "btn-base-color"
                : "bg-[#5555ce] dark:bg-[#ffd025] text-white dark:text-black"
            } rounded-xl btn-PopUp group`}
          >
            <CiCircleList className="size-10 stroke-1" />
            Escolha múltipla
          </button>
          <button
            onClick={() => setNewExercisetype(ExerciseType.OPEN_ANSWER)}
            className={`${
              ExerciseType.OPEN_ANSWER != newExercisetype
                ? "btn-base-color"
                : "bg-[#5555ce] dark:bg-[#ffd025] text-white dark:text-black"
            } rounded-xl btn-PopUp group`}
          >
            <PiTextTBold className="size-10" />
            Resposta aberta
          </button>
          <button
            onClick={() => setNewExercisetype(ExerciseType.TRUE_OR_FALSE)}
            className={`${
              ExerciseType.TRUE_OR_FALSE != newExercisetype
                ? "btn-base-color"
                : "bg-[#5555ce] dark:bg-[#ffd025] text-white dark:text-black"
            } rounded-xl btn-PopUp group`}
          >
            <TbCheckbox className="size-10 stroke-[2.3]" />
            Verdadeiro ou falso
          </button>
          <button
            onClick={() => setNewExercisetype(ExerciseType.CHAT)}
            className={`${
              ExerciseType.CHAT != newExercisetype
                ? "btn-base-color"
                : "bg-[#5555ce] dark:bg-[#ffd025] text-white dark:text-black"
            } rounded-xl btn-PopUp group`}
          >
            <PiChatsBold className="size-10" />
            Chat
          </button>
        </div>
        <div className="flex justify-end mt-4">
          <button
            onClick={() => createNewExercise(newExercisetype)}
            className="py-4 px-8 text-base rounded-lg font-medium btn-base-color"
          >
            Criar Exercício
          </button>
        </div>
      </>
    </PopUp>
  );
}
