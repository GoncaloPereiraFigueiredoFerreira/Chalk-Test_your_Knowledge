import { useEffect, useState } from "react";
import {
  Exercise,
  ExerciseComponent,
  ExerciseComponentProps,
  ExerciseContext,
  ExerciseType,
} from "../Exercise/Exercise";
import { FaArrowRightToBracket } from "react-icons/fa6";
import { FaPencilAlt } from "react-icons/fa";
import { HiOutlineTrash } from "react-icons/hi";
import { PiChatsBold } from "react-icons/pi";
import {
  CreateTestActionKind,
  useCreateTestContext,
} from "./CreateTestContext";
import {
  CheckboxIcon,
  CheckedListIcon,
  GraduateIcon,
  LinkIcon,
  LockIcon,
  SchoolIcon,
  TextIcon,
  WorldSearchIcon,
} from "../SVGImages/SVGImages";

interface ShowExerciseDragDropProps {
  listExerciseButtons: boolean;
  groupPosition: number;
  exercisePosition: number;
  exercise: Exercise;
  setExerciseID: (value: {
    groupPosition: number;
    exercisePosition: number;
  }) => void;
  selectedExercise: boolean;
  setSelectedExercise: (value: string) => void;
  selectedMenu: string;
  setSelectedMenu: (value: string) => void;
}

export function ShowExerciseDragDrop({
  listExerciseButtons,
  groupPosition,
  exercisePosition,
  exercise,
  setExerciseID,
  selectedExercise,
  setSelectedExercise,
  selectedMenu,
  setSelectedMenu,
}: ShowExerciseDragDropProps) {
  const [typeLabel, setTypeLabel] = useState(<></>);
  const [visibility, setVisibility] = useState(<></>);
  const [preview, setPreview] = useState(<></>);
  const { testState, dispatch } = useCreateTestContext();

  const exerciseComponent: ExerciseComponentProps = {
    exercise: exercise,
    position: (exercisePosition + 1).toString(),
    context: { context: ExerciseContext.PREVIEW },
  };

  const [value, setValue] = useState(
    (exercise.identity.cotation ?? 0).toString()
  );
  const [changeCotationIsActive, setChangeCotationIsActive] = useState(false);

  function handleChange(value: string) {
    const result = value.match(/^(0*)(\d*[\.,]?\d{0,2})$/);
    if (result) {
      let resultStr = result[2].toString();
      if (resultStr !== "") setValue(resultStr);
      else setValue("0");
    }
  }

  function handleBlur() {
    setChangeCotationIsActive(false);
    dispatch({
      type: CreateTestActionKind.CHANGE_EXERCISE_COTATION,
      exercise: {
        groupPosition: groupPosition,
        exercisePosition: exercisePosition,
        newCotation: parseFloat(value),
      },
    });
  }

  function handleClick() {
    setChangeCotationIsActive(true);
    const inputElement = document.getElementById("cotation");
    if (inputElement) inputElement.focus();
  }

  useEffect(() => {
    switch (exercise.type) {
      case ExerciseType.MULTIPLE_CHOICE:
        setTypeLabel(
          <label className="caracteristics-exercise ex-icon">
            <CheckedListIcon size="size-4" />
            Escolha múltipla
          </label>
        );

        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case ExerciseType.OPEN_ANSWER:
        setTypeLabel(
          <label className="caracteristics-exercise ex-icon">
            <TextIcon size="size-4" />
            Resposta aberta
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case ExerciseType.TRUE_OR_FALSE:
        setTypeLabel(
          <label className="caracteristics-exercise ex-icon">
            <CheckboxIcon size="size-4" />
            Verdadeiro ou falso
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;

      case ExerciseType.CHAT:
        setTypeLabel(
          <label className="caracteristics-exercise ex-icon">
            <div className="h-full scale-125">
              <PiChatsBold />
            </div>
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
          <label className="caracteristics-exercise ex-icon">
            <LockIcon size="size-4" />
            Privado
          </label>
        );
        break;
      case "not-listed":
        setVisibility(
          <label className="caracteristics-exercise ex-icon">
            <LinkIcon size="size-4" />
            Não listado
          </label>
        );
        break;
      case "course":
        setVisibility(
          <label className="caracteristics-exercise ex-icon">
            <GraduateIcon size="size-4" />
            Curso
          </label>
        );
        break;
      case "institutional":
        setVisibility(
          <label className="caracteristics-exercise ex-icon">
            <SchoolIcon size="size-4" />
            Institucional
          </label>
        );
        break;
      case "public":
        setVisibility(
          <label className="caracteristics-exercise ex-icon">
            <WorldSearchIcon size="size-4" />
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
      } transition-[max-height] overflow-hidden duration-300 rounded-lg bg-3-2 group-hover`}
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
            <label className="flex min-w-max font-medium text-xl dark:text-[#dddddd]">
              {exercise.base.title}
            </label>
          </button>
          <button
            className={`${
              listExerciseButtons
                ? selectedExercise
                  ? "mr-[75px] pr-4 border-r-2"
                  : "group-hover:mr-[75px] group-hover:pr-4 group-hover:border-r-2"
                : selectedExercise
                ? "mr-[118px] pr-4 border-r-2"
                : "group-hover:mr-[118px] group-hover:pr-4 group-hover:border-r-2"
            } pl-4 w-full h-full flex relative justify-end items-center gap-4 z-10 duration-100 transition-[margin] cursor-default bg-3-2 border-gray-600 dark:border-[#dddddd]`}
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
            {listExerciseButtons ? (
              <button
                className="btn-options-exercise ex-icon"
                onClick={() => {
                  setExerciseID({
                    groupPosition: groupPosition,
                    exercisePosition:
                      testState.test.groups[groupPosition].exercises.length,
                  });
                  setSelectedExercise(exercise.identity.id);
                  dispatch({
                    type: CreateTestActionKind.ADD_EXERCISE,
                    exercise: {
                      groupPosition: groupPosition,
                      exercisePosition:
                        testState.test.groups[groupPosition].exercises.length,
                      exercise: exercise,
                    },
                  });
                }}
              >
                <FaArrowRightToBracket />
                Adicionar
              </button>
            ) : (
              <>
                <button
                  className="btn-options-exercise ex-icon"
                  onClick={() => {
                    setExerciseID({
                      groupPosition: groupPosition,
                      exercisePosition: -1,
                    });
                    dispatch({
                      type: CreateTestActionKind.REMOVE_EXERCISE,
                      exercise: {
                        groupPosition: groupPosition,
                        exercisePosition: exercisePosition,
                      },
                    });
                  }}
                >
                  <HiOutlineTrash className="size-5" />
                  Eliminar
                </button>
                <button
                  className="btn-options-exercise ex-icon"
                  onClick={() => {
                    if (selectedMenu !== "edit-exercise") {
                      setSelectedMenu("edit-exercise");
                      setExerciseID({
                        groupPosition: groupPosition,
                        exercisePosition: exercisePosition,
                      });
                      dispatch({
                        type: CreateTestActionKind.SELECT_EXERCISE_POSITION,
                        exercise: {
                          groupPosition: groupPosition,
                          exercisePosition: exercisePosition,
                        },
                      });
                    }
                  }}
                >
                  <FaPencilAlt className="size-5" />
                  Editar
                </button>
              </>
            )}
          </div>

          {!listExerciseButtons ? (
            changeCotationIsActive ? (
              <input
                id="cotation"
                className="flex ml-4 w-14 px-2 py-1 rounded-lg bg-input-2 dark:text-[#dddddd]"
                value={value}
                onChange={(e) => handleChange(e.target.value)}
                onBlur={() => handleBlur()}
                autoFocus
              />
            ) : (
              <div
                className="flex ml-4 min-w-fit rounded-lg appearance-none dark:text-[#dddddd] bg-3-1 bg-input-1 "
                onClick={() => handleClick()}
              >
                <p className="flex justify-center text-base min-w-[56px] px-2 py-1 dark:text-[#dddddd] ">
                  {value} pts
                </p>
              </div>
            )
          ) : null}
        </div>
        <div
          className={`${
            !selectedExercise ? "hidden" : "flex"
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
            !selectedExercise ? "scale-y-0" : ""
          } flex flex-col mx-4 mb-4 border rounded-lg ex-1 border-gray-1 bg-in-ex`}
        >
          {preview}
        </div>
      </div>
    </div>
  );
}
