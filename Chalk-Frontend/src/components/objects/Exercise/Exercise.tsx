import {
  CheckboxIcon,
  CheckedListIcon,
  CodeIcon,
  EyeSlashIcon,
  GarbageIcon,
  GraduateIcon,
  InputIcon,
  LinkIcon,
  LockIcon,
  PenIcon,
  SchoolIcon,
  TextIcon,
  WorldSearchIcon,
} from "../SVGImages/SVGImages";
import "./Exercise.css";
import { FillBlankExercise } from "./FillBlankExercise";
import { MCExercise } from "./MCExercise";
import { OAExercise } from "./OAExercise";
import { TFExercise } from "./TFExercise";
import { useEffect, useState } from "react";

type ExerciseProps = {
  name: string;
  visibility: string;
  type: string;
  author: string;
  enunciado: any;
  problema: any;
  exerciseKey: string;
  selectedExercise: string;
  setSelectedExercise: (value: string) => void;
};

export function Exercise({
  name,
  visibility,
  type,
  enunciado,
  problema,
  exerciseKey,
  selectedExercise,
  setSelectedExercise,
}: ExerciseProps) {
  const [typeLabel, setTypeLabel] = useState(<></>);
  const [preview, setPreview] = useState(<></>);

  useEffect(() => {
    switch (type) {
      case "multiple-choice":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CheckedListIcon size="size-4" />
            Escolha múltipla
          </label>
        );
        setPreview(
          <MCExercise
            enunciado={enunciado}
            problema={problema}
            contexto="solve"
          ></MCExercise>
        );
        break;
      case "open-answer":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <TextIcon size="size-4" />
            Resposta aberta
          </label>
        );
        setPreview(
          <OAExercise
            enunciado={enunciado}
            problema={problema}
            contexto="solve"
          ></OAExercise>
        );
        break;
      case "true-or-false":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CheckboxIcon size="size-4" />
            Verdadeiro ou falso
          </label>
        );
        setPreview(
          <TFExercise
            enunciado={enunciado}
            problema={problema}
            contexto="solve"
          ></TFExercise>
        );
        break;
      case "fill-in-the-blank":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <InputIcon size="size-4" />
            Preenchimento de espaços
          </label>
        );
        setPreview(
          <FillBlankExercise
            enunciado={enunciado}
            problema={problema}
            contexto="solve"
          ></FillBlankExercise>
        );
        break;
      case "code":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CodeIcon size="size-4" />
            Código
          </label>
        );
        setPreview(
          <TFExercise
            enunciado={enunciado}
            problema={problema}
            contexto="solve"
          ></TFExercise>
        );
        break;
    }
  }, [type]);

  function getVisibility() {
    switch (visibility) {
      case "private":
        return (
          <label className="caracteristics-exercise gray-icon">
            <LockIcon size="size-4" />
            Privado
          </label>
        );
      case "not-listed":
        return (
          <label className="caracteristics-exercise gray-icon">
            <LinkIcon size="size-4" />
            Não listado
          </label>
        );
      case "course":
        return (
          <label className="caracteristics-exercise gray-icon">
            <GraduateIcon size="size-4" />
            Curso
          </label>
        );
      case "institutional":
        return (
          <label className="caracteristics-exercise gray-icon">
            <SchoolIcon size="size-4" />
            Institucional
          </label>
        );
      case "public":
        return (
          <label className="caracteristics-exercise gray-icon">
            <WorldSearchIcon size="size-4" />
            Público
          </label>
        );
      default:
        break;
    }
  }

  return (
    <div
      className={`${
        exerciseKey === selectedExercise ? "max-h-full" : "max-h-[78px]"
      } transition-[max-height] overflow-hidden duration-300 rounded-lg bg-3-2`}
    >
      <div className="flex flex-col w-full h-full px-5 py-2.5">
        <div className="flex w-full items-center text-sm font-normal transition-all mb-4 group">
          <button
            className="flex flex-col gap-1.5 h-14 justify-center cursor-default"
            onClick={() =>
              exerciseKey === selectedExercise
                ? setSelectedExercise("")
                : setSelectedExercise(exerciseKey)
            }
          >
            <label className="flex min-w-max font-medium text-xl">{name}</label>
            <div
              className={`${
                exerciseKey === selectedExercise ? "hidden" : "flex"
              } ml-1 gap-2`}
            >
              <div className="bg-yellow-600 tag-exercise">Matemática</div>
              <div className="bg-blue-600 tag-exercise">4º ano</div>
              <div className="bg-green-600 tag-exercise">escolinha</div>
              <div className="bg-gray-500 tag-exercise">+8</div>
            </div>
          </button>
          <button
            className={`${
              exerciseKey === selectedExercise
                ? "mr-[204px] pr-4 border-r-2"
                : "group-hover:mr-[204px] group-hover:pr-4 group-hover:border-r-2"
            } pl-4 w-full h-full flex justify-end items-center gap-4 z-10 duration-100 transition-[margin] cursor-default bg-3-2 border-gray-1`}
            onClick={() =>
              exerciseKey === selectedExercise
                ? setSelectedExercise("")
                : setSelectedExercise(exerciseKey)
            }
          >
            <div className="flex flex-col justify-center">
              {getVisibility()}
              {typeLabel}
            </div>
          </button>
          <div className="flex absolute right-[86px] items-center gap-4 z-0">
            <button
              className="btn-options-exercise gray-icon"
              onClick={() => setSelectedExercise(exerciseKey)}
            >
              <PenIcon size="size-5" />
              Editar
            </button>
            <button className="btn-options-exercise gray-icon">
              <EyeSlashIcon size="size-5" />
              Visibilidade
            </button>
            <button className="btn-options-exercise gray-icon">
              <GarbageIcon size="size-5" />
              Eliminar
            </button>
          </div>
        </div>
        <div
          className={`${
            exerciseKey != selectedExercise ? "hidden" : "flex"
          } flex-wrap w-full text-sm font-normal gap-2 mx-1 mb-4 pb-4 border-b-2 border-gray-1`}
        >
          <div className="bg-yellow-600 tag-exercise">Matemática</div>
          <div className="bg-blue-600 tag-exercise">4º ano</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-blue-600 tag-exercise">4º ano</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-blue-600 tag-exercise">4º ano</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-green-600 tag-exercise">escolinha</div>
          <div className="bg-blue-600 tag-exercise">4º ano</div>
        </div>
        <div
          className={`${
            exerciseKey != selectedExercise ? "scale-y-0" : ""
          } flex flex-col mx-4 mb-4 min-h-[24rem] border rounded-lg border-gray-1 bg-white text-black`}
        >
          {preview}
        </div>
      </div>
    </div>
  );
}
