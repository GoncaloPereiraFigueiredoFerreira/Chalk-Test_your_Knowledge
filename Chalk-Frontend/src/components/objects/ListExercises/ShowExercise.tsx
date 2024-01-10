import "./ShowExercise.css";
import { useEffect, useState } from "react";
import {
  Exercise,
  ExerciseComponent,
  ExerciseComponentProps,
  ExerciseContext,
  ExerciseType,
} from "../Exercise/Exercise";
import {
  ListExerciseActionKind,
  useListExerciseContext,
} from "./ListExerciseContext";
import { FaPencil } from "react-icons/fa6";
import { HiOutlineTrash } from "react-icons/hi";
import { CiCircleList } from "react-icons/ci";
import { PiTextTBold } from "react-icons/pi";
import { TbCheckbox } from "react-icons/tb";
import { PiChatsBold } from "react-icons/pi";
import { BiSolidLock } from "react-icons/bi";
import { TbLink } from "react-icons/tb";
import { FaUserGraduate } from "react-icons/fa";
import { LuSchool } from "react-icons/lu";
import { MdPublic } from "react-icons/md";
import { HiOutlineEyeOff } from "react-icons/hi";

interface ExerciseProps {
  position: string;
  exercise: Exercise;
  setExerciseID: (value: string) => void;
  editMenuIsOpen: boolean;
  setEditMenuIsOpen: (value: boolean) => void;
  selectedExercise: boolean;
  setSelectedExercise: (value: string) => void;
  changeVisibilityPopUp: string;
  setChangeVisibilityPopUp: (value: string) => void;
}

export function ShowExercise({
  position,
  exercise,
  setExerciseID,
  editMenuIsOpen,
  setEditMenuIsOpen,
  selectedExercise,
  setSelectedExercise,
  changeVisibilityPopUp,
  setChangeVisibilityPopUp,
}: ExerciseProps) {
  const [typeLabel, setTypeLabel] = useState(<></>);
  const [visibility, setVisibility] = useState(<></>);
  const [preview, setPreview] = useState(<></>);
  const { dispatch } = useListExerciseContext();
  const exerciseComponent: ExerciseComponentProps = {
    exercise: exercise,
    position: position,
    context: { context: ExerciseContext.PREVIEW },
  };

  useEffect(() => {
    switch (exercise.type) {
      case ExerciseType.MULTIPLE_CHOICE:
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CiCircleList className="size-5 stroke-1" />
            Escolha múltipla
          </label>
        );

        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case ExerciseType.OPEN_ANSWER:
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <PiTextTBold className="size-5" />
            Resposta aberta
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case ExerciseType.TRUE_OR_FALSE:
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <TbCheckbox className="size-5 stroke-[2.3]" />
            Verdadeiro ou falso
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;

      case ExerciseType.CHAT:
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <PiChatsBold className="size-5" />
            Chat Question
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
    }
  }, [exercise]);

  useEffect(() => {
    switch (exercise.identity.visibility) {
      case "private":
        setVisibility(
          <label className="caracteristics-exercise gray-icon">
            <BiSolidLock className="size-5" />
            Privado
          </label>
        );
        break;
      case "not-listed":
        setVisibility(
          <label className="caracteristics-exercise gray-icon">
            <TbLink className="size-5" />
            Não listado
          </label>
        );
        break;
      case "course":
        setVisibility(
          <label className="caracteristics-exercise gray-icon">
            <FaUserGraduate className="size-5" />
            Curso
          </label>
        );
        break;
      case "institutional":
        setVisibility(
          <label className="caracteristics-exercise gray-icon">
            <LuSchool className="size-5" />
            Institucional
          </label>
        );
        break;
      case "public":
        setVisibility(
          <label className="caracteristics-exercise gray-icon">
            <MdPublic className="size-5" />
            Público
          </label>
        );
        break;
      default:
        break;
    }
  }, [exercise]);

  return (
    <div
      className={`${
        selectedExercise ? "max-h-full" : "max-h-[78px]"
      } mx-4 transition-[max-height] overflow-hidden duration-300 rounded-lg bg-3-2`}
    >
      <div className="flex flex-col h-full px-5 py-2.5">
        <div className="flex items-center text-sm font-normal transition-all mb-4 group">
          <button
            className="flex flex-col gap-1.5 h-14 justify-center cursor-default"
            onClick={() =>
              selectedExercise
                ? setSelectedExercise("")
                : setSelectedExercise(exercise.identity.id)
            }
          >
            <label className="flex min-w-max font-medium text-xl">
              {exercise.base.title}
            </label>
            <div
              className={`${selectedExercise ? "hidden" : "flex"} ml-1 gap-2`}
            >
              {exercise.base.tags.map((tag) => {
                return <div className="bg-yellow-600 tag-exercise"> {tag}</div>;
              })}
            </div>
          </button>
          <button
            className={`${
              selectedExercise
                ? "mr-[204px] pr-4 border-r-2"
                : "group-hover:mr-[204px] group-hover:pr-4 group-hover:border-r-2"
            } pl-4 w-full h-full flex relative justify-end items-center gap-4 z-10 duration-100 transition-[margin] cursor-default bg-3-2 border-gray-1`}
            onClick={() =>
              selectedExercise
                ? setSelectedExercise("")
                : setSelectedExercise(exercise.identity.id)
            }
          >
            <div className="flex flex-col justify-center">
              {visibility}
              {typeLabel}
            </div>
          </button>
          <div className="flex flex-row-reverse w-0 items-center gap-4 z-0">
            <button
              className="btn-options-exercise gray-icon"
              onClick={() => {
                if (!editMenuIsOpen) {
                  dispatch({
                    type: ListExerciseActionKind.EDIT_EXERCISE,
                    payload: {
                      exercise: exercise,
                    },
                  });

                  setEditMenuIsOpen(true);
                  setExerciseID(exercise.identity.id);
                }
                setSelectedExercise(exercise.identity.id);
              }}
            >
              <FaPencil className="size-5" />
              Editar
            </button>
            <button
              className="btn-options-exercise gray-icon"
              onClick={() => {
                if (changeVisibilityPopUp === "")
                  setChangeVisibilityPopUp(exercise.identity.id);
              }}
            >
              <HiOutlineEyeOff className="size-6" />
              Visibilidade
            </button>
            <button className="btn-options-exercise gray-icon">
              <HiOutlineTrash className="size-6" />
              Eliminar
            </button>
          </div>
        </div>
        <div
          className={`${
            !selectedExercise ? "hidden" : "flex"
          } flex-wrap w-full text-sm font-normal gap-2 mx-1 mb-4 pb-4 border-b-2 border-gray-1`}
        >
          {exercise.base.tags.map((tag) => {
            return <div className="bg-yellow-600 tag-exercise"> {tag}</div>;
          })}
        </div>
        <div
          className={`${
            !selectedExercise ? "scale-y-0" : ""
          } flex flex-col mx-4 mb-4 border rounded-lg ex-1 border-gray-1`}
        >
          {preview}
        </div>
      </div>
    </div>
  );
}
