import "./ListExercises.css";
import { useState } from "react";
import { PopUp } from "../../interactiveElements/PopUp";
import { BiSolidLock } from "react-icons/bi";
import { TbLink } from "react-icons/tb";
import { FaUserGraduate } from "react-icons/fa";
import { LuSchool } from "react-icons/lu";
import { MdPublic } from "react-icons/md";

interface ChangeVisibilityProps {
  show: boolean;
  closePopUp: () => void;
  changeVisibility: (value: string) => void;
}

export function ChangeVisibility({
  show,
  closePopUp,
  changeVisibility,
}: ChangeVisibilityProps) {
  const [selectedVisibility, setSelectedVisibility] = useState("public");
  return (
    <PopUp show={show} closePopUp={closePopUp}>
      <>
        <label className="flex w-full justify-between mb-4 px-4 pb-2.5 text-title-1 border-b-2 border-gray-1">
          Criar novo exercício
        </label>
        <div className="grid grid-cols-2">
          <button
            onClick={() => setSelectedVisibility("private")}
            className={`${
              "private" != selectedVisibility ? "bg-btn-4-1" : "btn-selected"
            } btn-ListExercises group`}
          >
            <BiSolidLock className="size-10" />
            Privado
          </button>
          <button
            onClick={() => setSelectedVisibility("not-listed")}
            className={`${
              "not-listed" != selectedVisibility ? "bg-btn-4-1" : "btn-selected"
            } btn-ListExercises group`}
          >
            <TbLink className="size-10" />
            Não listado
          </button>
          <button
            onClick={() => setSelectedVisibility("course")}
            className={`${
              "course" != selectedVisibility ? "bg-btn-4-1" : "btn-selected"
            } btn-ListExercises group`}
          >
            <FaUserGraduate className="size-10" />
            Curso
          </button>
          <button
            onClick={() => setSelectedVisibility("institutional")}
            className={`${
              "institutional" != selectedVisibility
                ? "bg-btn-4-1"
                : "btn-selected"
            } btn-ListExercises group`}
          >
            <LuSchool className="size-10" />
            Institucional
          </button>
          <button
            onClick={() => setSelectedVisibility("public")}
            className={`${
              "public" != selectedVisibility ? "bg-btn-4-1" : "btn-selected"
            } btn-ListExercises group`}
          >
            <MdPublic className="size-10" />
            Público
          </button>
        </div>
        <div className="flex justify-end">
          <button
            onClick={() => changeVisibility(selectedVisibility)}
            className="btn-selected btn-ListExercises group"
          >
            Seguinte
          </button>
        </div>
      </>
    </PopUp>
  );
}
