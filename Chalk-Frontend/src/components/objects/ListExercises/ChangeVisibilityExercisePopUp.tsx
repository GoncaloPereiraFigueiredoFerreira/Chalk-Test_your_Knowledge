import "../../interactiveElements/PopUpListExercise.css";
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
        <label className="flex w-full justify-between mb-4 px-4 pb-2.5 text-4xl text-slate-600 dark:text-white border-b-2 border-slate-400">
          Criar novo exercício
        </label>
        <div className="grid grid-cols-2 gap-4 py-4 px-4">
          <button
            onClick={() => setSelectedVisibility("private")}
            className={`${
              "private" != selectedVisibility
                ? "btn-base-color"
                : "bg-[#5555ce] dark:bg-[#ffd025] text-white dark:text-black"
            } rounded-xl btn-PopUp group`}
          >
            <BiSolidLock className="size-8 " />
            Privado
          </button>
          <button
            onClick={() => setSelectedVisibility("not-listed")}
            className={`${
              "not-listed" != selectedVisibility
                ? "btn-base-color"
                : "bg-[#5555ce] dark:bg-[#ffd025] text-white dark:text-black"
            } rounded-xl btn-PopUp group`}
          >
            <TbLink className="size-8" />
            Não listado
          </button>
          <button
            onClick={() => setSelectedVisibility("course")}
            className={`${
              "course" != selectedVisibility
                ? "btn-base-color"
                : "bg-[#5555ce] dark:bg-[#ffd025] text-white dark:text-black"
            } rounded-xl btn-PopUp group`}
          >
            <FaUserGraduate className="size-8" />
            Curso
          </button>
          <button
            onClick={() => setSelectedVisibility("institutional")}
            className={`${
              "institutional" != selectedVisibility
                ? "btn-base-color"
                : "bg-[#5555ce] dark:bg-[#ffd025] text-white dark:text-black"
            } rounded-xl btn-PopUp group`}
          >
            <LuSchool className="size-8" />
            Institucional
          </button>
          <button
            onClick={() => setSelectedVisibility("public")}
            className={`${
              "public" != selectedVisibility
                ? "btn-base-color"
                : "bg-[#5555ce] dark:bg-[#ffd025] text-white dark:text-black"
            } rounded-xl btn-PopUp group`}
          >
            <MdPublic className="size-8" />
            Público
          </button>
        </div>
        <div className="flex justify-end mt-4">
          <button
            onClick={() => changeVisibility(selectedVisibility)}
            className="py-4 px-8 text-base rounded-lg font-medium btn-base-color"
          >
            Guardar
          </button>
        </div>
      </>
    </PopUp>
  );
}
