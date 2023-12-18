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
// import { FillBlankExercise } from "./FillBlank/FillBlankExercise";
// import { CodeExercise } from "./Code/CodeExercise";
import { useEffect, useState } from "react";
import "./ShowExercise.css";
import {
  Exercise,
  ExerciseComponent,
  ExerciseComponentProps,
  ExerciseContext,
  ExerciseType,
  createNewResolution,
} from "../Exercise/Exercise";
import {
  ListExerciseActionKind,
  useListExerciseContext,
} from "./ListExerciseContext";

interface ExerciseProps {
  position: string;
  exercise: Exercise;
  setEditMenuIsOpen: (value: string) => void;
  editMenuIsOpen: string;
  selectedExercise: string;
  setSelectedExercise: (value: string) => void;
}

export function ShowExercise({
  position,
  exercise,
  setEditMenuIsOpen,
  editMenuIsOpen,
  selectedExercise,
  setSelectedExercise,
}: ExerciseProps) {
  const [typeLabel, setTypeLabel] = useState(<></>);
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
            <CheckedListIcon size="size-4" />
            Escolha múltipla
          </label>
        );

        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case ExerciseType.OPEN_ANSWER:
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <TextIcon size="size-4" />
            Resposta aberta
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case ExerciseType.TRUE_OR_FALSE:
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <CheckboxIcon size="size-4" />
            Verdadeiro ou falso
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case ExerciseType.FILL_IN_THE_BLANK:
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
      case ExerciseType.CODE:
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
      case ExerciseType.CHAT:
        setTypeLabel(
          <label className="caracteristics-exercise gray-icon">
            <TextIcon size="size-4" />
            Chat Question
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
    }
  }, [exercise]);

  function getVisibility() {
    switch (exercise.visibility) {
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
        exercise.id === selectedExercise ? "max-h-full" : "max-h-[78px]"
      } transition-[max-height] overflow-hidden duration-300 rounded-lg bg-3-2`}
    >
      <div className="flex flex-col h-full px-5 py-2.5">
        <div className="flex items-center text-sm font-normal transition-all mb-4 group">
          <button
            className="flex flex-col gap-1.5 h-14 justify-center cursor-default"
            onClick={() =>
              exercise.id === selectedExercise
                ? setSelectedExercise("")
                : setSelectedExercise(exercise.id)
            }
          >
            <label className="flex min-w-max font-medium text-xl">
              {exercise.title}
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
          >
            <div className="flex flex-col justify-center">
              {getVisibility()}
              {typeLabel}
            </div>
          </button>
          <div className="flex flex-row-reverse w-0 items-center gap-4 z-0">
            <button
              className="btn-options-exercise gray-icon"
              onClick={() => {
                if (editMenuIsOpen === "") {
                  if (exercise.solution === undefined) {
                    dispatch({
                      type: ListExerciseActionKind.EDIT_EXERCISE,
                      payload: {
                        exercise: {
                          ...exercise,
                          solution: createNewResolution(exercise),
                        },
                      },
                    });
                  }
                  setEditMenuIsOpen(exercise.id);
                }
                setSelectedExercise(exercise.id);
              }}
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
          } flex flex-col mx-4 mb-4 border rounded-lg ex-1 border-gray-1`}
        >
          {preview}
        </div>
      </div>
    </div>
  );
}
