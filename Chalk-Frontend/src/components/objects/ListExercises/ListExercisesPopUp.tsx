import "./ListExercises.css";
import {
  CheckboxIcon,
  CheckedListIcon,
  CodeIcon,
  InputIcon,
  TextIcon,
} from "../SVGImages/SVGImages";
import { useState } from "react";

interface ListExercisesPopUpProps {
  createNewExercise: (value: string) => void;
}

export function ListExercisesPopUp({
  createNewExercise,
}: ListExercisesPopUpProps) {
  const [newExercisetype, setNewExercisetype] = useState("multiple-choice");
  return (
    <>
      <label className="flex w-full justify-between mb-4 px-4 pb-2.5 text-title-1 border-b-2 border-gray-1">
        Criar novo exercício
      </label>
      <div className="grid grid-cols-2">
        <button
          onClick={() => setNewExercisetype("multiple-choice")}
          className={`${
            "multiple-choice" != newExercisetype ? "bg-btn-4-1" : "btn-selected"
          } btn-ListExercises text-lg group`}
        >
          <CheckedListIcon style="inherit-icon" size="size-12" />
          Escolha múltipla
        </button>
        <button
          onClick={() => setNewExercisetype("open-answer")}
          className={`${
            "open-answer" != newExercisetype ? "bg-btn-4-1" : "btn-selected"
          } btn-ListExercises text-lg group`}
        >
          <TextIcon style="inherit-icon" size="size-12" />
          Resposta aberta
        </button>
        <button
          onClick={() => setNewExercisetype("true-or-false")}
          className={`${
            "true-or-false" != newExercisetype ? "bg-btn-4-1" : "btn-selected"
          } btn-ListExercises text-lg group`}
        >
          <CheckboxIcon style="inherit-icon" size="size-12" />
          Verdadeiro ou falso
        </button>
        <button
          onClick={() => setNewExercisetype("fill-in-the-blank")}
          className={`${
            "fill-in-the-blank" != newExercisetype
              ? "bg-btn-4-1"
              : "btn-selected"
          } btn-ListExercises text-lg group`}
        >
          <InputIcon style="inherit-icon" size="size-12" />
          Preenchimento de espaços
        </button>
        <button
          onClick={() => setNewExercisetype("code")}
          className={`${
            "code" != newExercisetype ? "bg-btn-4-1" : "btn-selected"
          } btn-ListExercises text-lg group`}
        >
          <CodeIcon style="inherit-icon" size="size-12" />
          Código
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