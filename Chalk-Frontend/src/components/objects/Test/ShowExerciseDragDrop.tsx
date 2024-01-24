import { useContext, useEffect, useState } from "react";
import { useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { EditTestActionKind, useEditTestContext } from "./EditTestContext";
import {
  Exercise,
  ExerciseComponent,
  ExerciseComponentProps,
  ExerciseContext,
  ExerciseType,
} from "../Exercise/Exercise";
import { FaArrowRightToBracket } from "react-icons/fa6";
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
import { APIContext } from "../../../APIContext";

const classname = " bg-[#bdcee6] dark:bg-slate-700";

interface ShowExerciseDragDropProps {
  groupPosition: number;
  exercisePosition: number;
  exercise: Exercise;
  selectedMenu: string;
  setSelectedMenu: (value: string) => void;
  listExerciseButtons: boolean;
  // este exercicio está selecionado
  exerciseIsSelected: boolean;
  // definir exercicio aberto
  setSelectedExercise: (value: string) => void;
  // definir exercicio selecionado no GroupDragDrop
  setExerciseID: (value: {
    groupPosition: number;
    exercisePosition: number;
  }) => void;
  draggingExercises: boolean;
}

export function ShowExerciseDragDrop({
  groupPosition,
  exercisePosition,
  exercise,
  selectedMenu,
  setSelectedMenu,
  listExerciseButtons,
  exerciseIsSelected,
  setSelectedExercise,
  setExerciseID,
  draggingExercises,
}: ShowExerciseDragDropProps) {
  const exerciseComponent: ExerciseComponentProps = {
    exercise: exercise,
    position: (exercisePosition + 1).toString(),
    context: { context: ExerciseContext.PREVIEW },
  };
  const [typeLabel, setTypeLabel] = useState(<></>);
  const [visibility, setVisibility] = useState(<></>);
  const [preview, setPreview] = useState(<></>);
  const { testState, dispatch } = useEditTestContext();
  const { contactBACK } = useContext(APIContext);
  const [value, setValue] = useState(
    (exercise.identity.points ?? 1).toString()
  );
  const [changeCotationIsActive, setChangeCotationIsActive] = useState(false);

  const {
    attributes,
    setNodeRef,
    listeners,
    transform,
    transition,
    isDragging,
  } = useSortable({
    id: exercise.identity.id,
    data: {
      type: listExerciseButtons ? "add" : "exercise", // exercise from ExerciseBankDragDrop/EditTestDragDrop
      exercise: exercise,
      groupPosition: listExerciseButtons ? -1 : groupPosition, // exercise on ExerciseBankDragDrop / exercise group position
      exercisePosition: exercisePosition, // exercise position on listExercise / selected exercise position
    },
  });

  useEffect(() => {
    setValue((exercise.identity.points ?? 0).toString());
  }, [exercise]);

  function handleChange(value: string) {
    const result = value.match(/^(0*)(\d*[\.,]?\d{0,2})$/);
    if (result) {
      const resultStr = result[2].toString().replace(",", ".");
      if (resultStr !== "") {
        let newCotation = parseFloat(resultStr);
        if (newCotation >= 0) setValue(resultStr);
        else setValue("1");
      } else setValue("0");
    }
  }

  function handleBlur() {
    setChangeCotationIsActive(false);
    dispatch({
      type: EditTestActionKind.CHANGE_EXERCISE_COTATION,
      exercise: {
        groupPosition: groupPosition,
        exercisePosition: exercisePosition,
        newCotation: parseFloat(value) ? parseFloat(value) : 1,
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
            <CiCircleList className="size-5 stroke-1" />
            Escolha múltipla
          </label>
        );

        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case ExerciseType.OPEN_ANSWER:
        setTypeLabel(
          <label className="caracteristics-exercise ex-icon">
            <PiTextTBold className="size-5" />
            Resposta aberta
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;
      case ExerciseType.TRUE_OR_FALSE:
        setTypeLabel(
          <label className="caracteristics-exercise ex-icon">
            <TbCheckbox className="size-5 stroke-[2.3]" />
            Verdadeiro ou falso
          </label>
        );
        setPreview(<ExerciseComponent {...exerciseComponent} />);
        break;

      case ExerciseType.CHAT:
        setTypeLabel(
          <label className="caracteristics-exercise ex-icon">
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
          <label className="caracteristics-exercise ex-icon">
            <BiSolidLock className="size-5" />
            Privado
          </label>
        );
        break;
      case "not-listed":
        setVisibility(
          <label className="caracteristics-exercise ex-icon">
            <TbLink className="size-5" />
            Não listado
          </label>
        );
        break;
      case "course":
        setVisibility(
          <label className="caracteristics-exercise ex-icon">
            <FaUserGraduate className="size-5" />
            Curso
          </label>
        );
        break;
      case "institutional":
        setVisibility(
          <label className="caracteristics-exercise ex-icon">
            <LuSchool className="size-5" />
            Institucional
          </label>
        );
        break;
      case "public":
        setVisibility(
          <label className="caracteristics-exercise ex-icon">
            <MdPublic className="size-5" />
            Público
          </label>
        );
        break;
      default:
        break;
    }
  }, [exercise]);

  if (isDragging)
    return (
      <div
        {...attributes}
        ref={setNodeRef}
        style={
          listExerciseButtons
            ? {}
            : {
                transition,
                transform: CSS.Translate.toString(transform),
              }
        }
        {...listeners}
        className={
          "flex h-[78px] border-2 rounded-lg opacity-50 border-blue-500" +
          classname
        }
      />
    );

  if (draggingExercises)
    return (
      <div
        {...attributes}
        ref={setNodeRef}
        style={
          listExerciseButtons
            ? {}
            : {
                transition,
                transform: CSS.Translate.toString(transform),
              }
        }
        className={
          "h-[78px] overflow-hidden cursor-default rounded-lg group" + classname
        }
        {...listeners}
      >
        <div className="flex flex-col h-full px-5 py-2.5 text-black dark:text-white">
          <div className="flex items-center text-sm font-normal transition-all mb-4 group">
            <button className="flex flex-col gap-1.5 h-14 justify-center">
              <label className="flex min-w-max font-medium text-xl">
                {exercise.base.title}
              </label>
            </button>
            <button
              className={
                ` ${
                  listExerciseButtons
                    ? "group-hover:mr-[75px]"
                    : "group-hover:mr-[118px]"
                } group-hover:pr-4 group-hover:border-r-2 pl-4 w-full py-1 justify-end z-10 border-slate-500 dark:border-slate-600` +
                classname
              }
            >
              <div className="flex flex-col justify-around items-end">
                {visibility}
                {typeLabel}
              </div>
            </button>
            <div className="flex flex-row-reverse w-0 items-center gap-4 z-0">
              {listExerciseButtons ? (
                <button className="btn-options-exercise ex-icon">
                  <FaArrowRightToBracket />
                  Adicionar
                </button>
              ) : (
                <>
                  <button className="btn-options-exercise ex-icon">
                    <HiOutlineTrash className="size-5" />
                    Eliminar
                  </button>
                  <button className="btn-options-exercise ex-icon">
                    <FaPencil className="size-5" />
                    Editar
                  </button>
                </>
              )}
            </div>

            {!listExerciseButtons && (
              <div className="flex ml-4 min-w-fit rounded-lg appearance-none cursor-pointer bg-slate-200 dark:bg-slate-600">
                <p className="flex justify-center text-base min-w-[56px] px-2 py-1 dark:text-white">
                  {value} pts
                </p>
              </div>
            )}
          </div>
        </div>
      </div>
    );

  return (
    <div
      {...attributes}
      ref={setNodeRef}
      className={
        `${
          exerciseIsSelected ? "max-h-full" : "max-h-[78px]"
        } transition-[max-height] overflow-hidden duration-200 cursor-default rounded-lg group` +
        classname
      }
    >
      <div className="flex flex-col h-full px-5 py-2.5 text-black dark:text-white">
        <div
          className="flex items-center text-sm font-normal transition-all mb-4 group"
          {...listeners}
        >
          <button
            className="flex flex-col gap-1.5 h-14 justify-center"
            onClick={() =>
              exerciseIsSelected
                ? setSelectedExercise("")
                : setSelectedExercise(exercise.identity.id)
            }
          >
            <label className="flex min-w-max font-medium text-xl">
              {exercise.base.title}
            </label>
          </button>
          <button
            className={
              ` ${
                listExerciseButtons
                  ? exerciseIsSelected
                    ? "mr-[75px] pr-4 border-r-2"
                    : "group-hover:mr-[75px] group-hover:pr-4 group-hover:border-r-2"
                  : exerciseIsSelected
                  ? "mr-[118px] pr-4 border-r-2"
                  : "group-hover:mr-[118px] group-hover:pr-4 group-hover:border-r-2"
              } pl-4 w-full py-1 justify-end z-10 duration-100 transition-[margin] cursor-default border-slate-500 dark:border-slate-600` +
              classname
            }
            onClick={() =>
              exerciseIsSelected
                ? setSelectedExercise("")
                : setSelectedExercise(exercise.identity.id)
            }
          >
            <div className="flex flex-col justify-around items-end">
              {visibility}
              {typeLabel}
            </div>
          </button>
          <div className="flex flex-row-reverse w-0 items-center gap-4 z-0">
            {listExerciseButtons ? (
              <button
                className="btn-options-exercise ex-icon"
                onClick={() => {
                  contactBACK(
                    "tests/" + testState.test.id + "/createExercise",
                    "PUT",
                    undefined,
                    {
                      exercise: {
                        points: 1,
                        id: exercise.identity.id,
                        type: "reference",
                      },
                      groupIndex: groupPosition,
                      exeIndex:
                        testState.test.groups[groupPosition].exercises.length,
                    },
                    "string"
                  ).then((id) => {
                    setExerciseID({
                      groupPosition: groupPosition,
                      exercisePosition:
                        testState.test.groups[groupPosition].exercises.length,
                    });
                    dispatch({
                      type: EditTestActionKind.ADD_NEW_EXERCISE,
                      exercise: {
                        groupPosition: groupPosition,
                        newID: id,
                        exercisePosition:
                          testState.test.groups[groupPosition].exercises.length,
                        exercise: exercise,
                      },
                    });
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
                    if (selectedMenu !== "edit-exercise") {
                      setExerciseID({
                        groupPosition: groupPosition,
                        exercisePosition: -1,
                      });
                      dispatch({
                        type: EditTestActionKind.REMOVE_EXERCISE,
                        exercise: {
                          groupPosition: groupPosition,
                          exercisePosition: exercisePosition,
                        },
                      });
                    }
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
                        type: EditTestActionKind.SELECT_EXERCISE_POSITION,
                        exercise: {
                          groupPosition: groupPosition,
                          exercisePosition: exercisePosition,
                        },
                      });
                    }
                  }}
                >
                  <FaPencil className="size-5" />
                  Editar
                </button>
              </>
            )}
          </div>

          {!listExerciseButtons &&
            (changeCotationIsActive ? (
              <input
                id="cotation"
                className="flex ml-4 w-14 px-3 py-1 rounded-lg border-0 focus:border-0 focus:ring-0 bg-slate-200 dark:bg-slate-600"
                value={value}
                onChange={(e) => handleChange(e.target.value)}
                onKeyDown={(e) => {
                  if (e.key !== "Enter") return;
                  handleBlur();
                }}
                onBlur={() => handleBlur()}
                autoFocus
              />
            ) : (
              <div
                className="flex ml-4 min-w-fit rounded-lg appearance-none cursor-pointer bg-slate-200 dark:bg-slate-600"
                onClick={() => handleClick()}
              >
                <p className="flex justify-center text-base min-w-[56px] px-2 py-1 dark:text-white">
                  {value} pts
                </p>
              </div>
            ))}
        </div>
        <div className="flex flex-wrap w-full text-sm font-normal gap-2 mx-1 mb-4 pb-4 border-b-2  border-slate-500 dark:border-slate-600">
          {exercise.base.tags.map((tag, key) => (
            <div key={key} className="bg-yellow-600 tag-exercise">
              {tag.name}
            </div>
          ))}
        </div>
        <div
          className={`${
            !exerciseIsSelected ? "scale-y-0" : ""
          } mx-4 mb-4 border rounded-lg text-black dark:text-white bg-slate-200 dark:bg-slate-800 border-slate-500`}
        >
          {preview}
        </div>
      </div>
    </div>
  );
}
