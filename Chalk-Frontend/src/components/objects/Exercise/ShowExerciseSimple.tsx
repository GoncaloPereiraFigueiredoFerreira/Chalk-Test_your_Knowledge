import {
  CheckboxIcon,
  CheckedListIcon,
  CodeIcon,
  InputIcon,
  TextIcon,
} from "../SVGImages/SVGImages";
// import { FillBlankExercise } from "./FillBlank/FillBlankExercise";
// import { CodeExercise } from "./Code/CodeExercise";
import { MCExercise } from "./MC/MCExercise";
import { OAExercise } from "./OA/OAExercise";
import { TFExercise } from "./TF/TFExercise";
import { useEffect, useState } from "react";
import { Exercise } from "./Exercise";
import "./ShowExercise.css";

interface ExerciseSimpleProps {
  position: string;
  exercise: Exercise;
  selectedExercise: string;
  setSelectedExercise: (value: string) => void;
}

export function ShowExerciseSimple({
  position,
  exercise,
  selectedExercise,
  setSelectedExercise,
}: ExerciseSimpleProps) {
  const [typeLabel, setTypeLabel] = useState(<></>);
  const [preview, setPreview] = useState(<></>);

  useEffect(() => {
    switch (exercise.type) {
      case "multiple-choice":
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CheckedListIcon size="size-4" />
            Escolha múltipla
          </label>
        );

        setPreview(
          <MCExercise
            statement={exercise.statement}
            problem={exercise.problem}
            contexto="preview"
            name={exercise.name}
            position={position}
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
            statement={exercise.statement}
            contexto="preview"
            name={exercise.name}
            position={position}
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
            id={exercise.id}
            statement={exercise.statement}
            problem={exercise.problem}
            contexto="solve"
            name={exercise.name}
            position={position}
            justify={exercise.problem!.justify!} // none, false-only or all
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
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CodeIcon size="size-4" />
            Código
          </label>
        );
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
    <div
      className={`${
        exercise.id === selectedExercise ? "max-h-full" : "max-h-[78px]"
      } transition-[max-height] overflow-hidden duration-300 rounded-lg bg-3-2 mb-2`}
    >
      <div
        className={`${
          exercise.id === selectedExercise ? "bg-white" : " "
        } flex flex-col h-full px-5 py-2.5`}
      >
        <div
          className="flex items-center text-sm font-normal transition-all mb-4 group"
          onClick={() =>
            exercise.id === selectedExercise
              ? setSelectedExercise("")
              : setSelectedExercise(exercise.id)
          }
        >
          <button className="flex flex-col gap-1.5 h-14 justify-center cursor-default">
            <label
              className={`${
                exercise.id === selectedExercise ? " text-black" : " "
              } flex min-w-max font-medium text-xl`}
            >
              {exercise.name}
            </label>
            <div
              className={`${
                exercise.id === selectedExercise ? "hidden" : "flex"
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
              exercise.id === selectedExercise
                ? "mr-[204px] pr-4 border-r-2"
                : "group-hover:mr-[204px] group-hover:pr-4 group-hover:border-r-2"
            } pl-4 w-full h-full flex relative justify-end items-center gap-4 z-10 duration-100 transition-[margin] cursor-default bg-3-2 border-gray-1`}
            onClick={() =>
              exercise.id === selectedExercise
                ? setSelectedExercise("")
                : setSelectedExercise(exercise.id)
            }
          ></button>
        </div>
        <div
          className={`${
            exercise.id != selectedExercise ? "hidden" : "flex"
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
            exercise.id != selectedExercise ? "scale-y-0" : ""
          } flex flex-col mx-4 mb-4 border rounded-lg ex-1 border-gray-1 cursor-pointer`}
        >
          {preview}
        </div>
      </div>
    </div>
  );
}
