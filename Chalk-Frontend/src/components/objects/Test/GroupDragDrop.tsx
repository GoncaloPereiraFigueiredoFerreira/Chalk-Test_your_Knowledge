import { ShowExerciseDragDrop } from "./ShowExerciseDragDrop";
import { EditTestActionKind, useEditTestContext } from "./EditTestContext";
import { SortableContext, useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { useEffect, useRef } from "react";
import { RiAddFill } from "react-icons/ri";
import { FaPencil } from "react-icons/fa6";
import { HiOutlineTrash } from "react-icons/hi";

const classname = " bg-[#d8e3f1]";

interface GroupDragDropProps {
  exerciseGroupPosition: number;
  exerciseGroupID: string;
  exerciseID: {
    groupPosition: number;
    exercisePosition: number;
  };
  setExerciseID: (value: {
    groupPosition: number;
    exercisePosition: number;
  }) => void;
  selectedMenu: string;
  setSelectedMenu: (value: string) => void;
  setNewExercisePopUp: (value: number) => void;
  draggingGroups: boolean;
  setDraggingGroups: (value: boolean) => void;
  draggingExercises: boolean;
}

export function GroupDragDrop({
  exerciseGroupPosition,
  exerciseGroupID,
  exerciseID,
  setExerciseID,
  selectedMenu,
  setSelectedMenu,
  setNewExercisePopUp,
  draggingGroups,
  setDraggingGroups,
  draggingExercises,
}: GroupDragDropProps) {
  const { testState, dispatch } = useEditTestContext();
  const divRef = useRef<HTMLDivElement>(null);
  const divRefOnDrag = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (divRefOnDrag.current)
      divRefOnDrag.current.innerHTML =
        testState.test.groups[exerciseGroupPosition].groupInstructions ?? "";
  }, [
    divRefOnDrag,
    testState.test.groups[exerciseGroupPosition].groupInstructions,
  ]);

  useEffect(() => {
    if (divRef.current)
      divRef.current.innerHTML =
        testState.test.groups[exerciseGroupPosition].groupInstructions ?? "";
  }, [divRef, testState.test.groups[exerciseGroupPosition].groupInstructions]);

  const {
    attributes,
    setNodeRef,
    listeners,
    transform,
    transition,
    isDragging,
  } = useSortable({
    id: exerciseGroupID,
    data: {
      type: "group",
      groupPosition: exerciseGroupPosition,
    },
  });

  useEffect(() => {
    setDraggingGroups(isDragging);
  }, [isDragging]);

  if (draggingGroups) {
    return (
      <>
        <div
          {...attributes}
          ref={setNodeRef}
          style={{
            transition,
            transform: CSS.Translate.toString(transform),
          }}
          className={
            `${
              isDragging && "opacity-40"
            } flex flex-col gap-4 rounded-lg px-7 py-5 cursor-default dark:bg-[#1e2a3f] text-black dark:text-white h-56 overflow-hidden` +
            classname
          }
        >
          <div className="flex w-full justify-between pb-4 px-4 border-b-2 border-slate-400 dark:border-slate-600">
            <label className="flex w-full items-center text-xl font-medium">
              Grupo {exerciseGroupPosition + 1}
            </label>
            <div className="flex w-full justify-end items-center gap-4">
              Cotação do Grupo:
              <div className="flex justify-center min-w-fit w-10 rounded-md px-3 py-1 bg-white dark:bg-slate-600">
                {testState.test.groups[exerciseGroupPosition].groupPoints} pts
              </div>
              <div className="flex border-l-2 pl-4 border-slate-400 dark:border-slate-600">
                <button className="btn-options-exercise ex-icon">
                  <HiOutlineTrash className="size-5" />
                  Eliminar
                </button>
              </div>
            </div>
          </div>
          <div className="px-4">
            <div className="flex items-center justify-between">
              <strong>Instruções do grupo:</strong>
              <button className="flex gap-2 py-2 px-3 text-base rounded-lg btn-base-color group">
                <FaPencil className="size-5" />
                Editar
              </button>
            </div>
            <div className={"px-4"}>
              <div className="block" ref={divRefOnDrag}></div>
            </div>
          </div>
          <div className="flex gap-7 w-full">
            <button className="flex w-full p-3 gap-2 justify-center items-center cursor-pointer rounded-lg btn-base-color group">
              <FaPencil className="group-gray-icon size-6" />
              <label className="group-gray-icon font-medium text-lg">
                Lista de Exercicios
              </label>
            </button>
            <button className="flex w-full p-3 gap-2 justify-center items-center cursor-pointer rounded-lg btn-base-color group">
              <RiAddFill className="group-gray-icon size-8" />
              <label className="group-gray-icon font-medium text-lg">
                Criar Novo
              </label>
            </button>
          </div>
        </div>
      </>
    );
  }

  return (
    <>
      <div
        {...attributes}
        ref={setNodeRef}
        className={
          `${
            isDragging && "opacity-50"
          } flex flex-col gap-4 rounded-lg px-7 py-5 cursor-default dark:bg-[#1e2a3f] text-black dark:text-white` +
          classname
        }
      >
        <div
          className="flex w-full justify-between pb-4 px-4 border-b-2 border-slate-400 dark:border-slate-600"
          {...listeners}
          onClick={() =>
            setExerciseID({
              groupPosition: exerciseGroupPosition,
              exercisePosition: -1,
            })
          }
        >
          <label className="flex w-full items-center text-xl font-medium">
            Grupo {exerciseGroupPosition + 1}
          </label>
          <div className="flex w-full justify-end items-center gap-4">
            Cotação do Grupo:
            <div className="flex justify-center min-w-fit w-10 rounded-md px-3 py-1 bg-white dark:bg-slate-600">
              {testState.test.groups[exerciseGroupPosition].groupPoints} pts
            </div>
            <div className="flex border-l-2 pl-4 border-slate-400 dark:border-slate-600">
              <button
                className="btn-options-exercise ex-icon"
                onClick={() => {
                  if (
                    selectedMenu === "" ||
                    selectedMenu === "dd-list-exercises" ||
                    selectedMenu === "edit-test-info"
                  ) {
                    setSelectedMenu("");
                    dispatch({
                      type: EditTestActionKind.REMOVE_GROUP,
                      group: {
                        groupPosition: testState.groupPosition,
                      },
                    });
                  }
                }}
              >
                <HiOutlineTrash className="size-5" />
                Eliminar
              </button>
            </div>
          </div>
        </div>
        <div className="px-4">
          <div className="flex items-center justify-between">
            <strong>Instruções do grupo:</strong>
            <button
              className="flex gap-2 py-2 px-3 text-base rounded-lg btn-base-color group"
              onClick={() => {
                if (selectedMenu !== "edit-group") {
                  setExerciseID({
                    groupPosition: exerciseGroupPosition,
                    exercisePosition: -1,
                  });
                  setSelectedMenu("edit-group");
                  dispatch({
                    type: EditTestActionKind.SELECT_EXERCISE_POSITION,
                    exercise: {
                      groupPosition: exerciseGroupPosition,
                      exercisePosition: -1,
                    },
                  });
                }
              }}
            >
              <FaPencil className="size-5" />
              Editar
            </button>
          </div>
          <div className={"px-4"}>
            <div className="block" ref={divRef}></div>
          </div>
        </div>
        <div className={"flex flex-col min-h-max gap-4"}>
          <SortableContext
            items={testState.test.groups[exerciseGroupPosition].exercises.map(
              (exercise) => exercise.identity.id
            )}
          >
            {testState.test.groups[exerciseGroupPosition].exercises.map(
              (exercise, index) => (
                <ShowExerciseDragDrop
                  key={index}
                  listExerciseButtons={false}
                  exercisePosition={index}
                  groupPosition={exerciseGroupPosition}
                  exercise={exercise}
                  selectedMenu={selectedMenu}
                  setExerciseID={setExerciseID}
                  setSelectedMenu={setSelectedMenu}
                  exerciseIsSelected={
                    index === exerciseID.exercisePosition &&
                    exerciseGroupPosition === exerciseID.groupPosition &&
                    !isDragging
                  }
                  setSelectedExercise={() => {
                    if (
                      exerciseGroupPosition != exerciseID.groupPosition ||
                      index != exerciseID.exercisePosition
                    )
                      setExerciseID({
                        groupPosition: exerciseGroupPosition,
                        exercisePosition: index,
                      });
                    else
                      setExerciseID({
                        groupPosition: exerciseGroupPosition,
                        exercisePosition: -1,
                      });
                  }}
                  draggingExercises={draggingExercises}
                ></ShowExerciseDragDrop>
              )
            )}
          </SortableContext>
          <div className="flex gap-7 w-full">
            <button
              className="flex w-full p-3 gap-2 justify-center items-center cursor-pointer rounded-lg btn-base-color group"
              onClick={() => {
                setExerciseID({
                  groupPosition: exerciseGroupPosition,
                  exercisePosition: -1,
                });
                setSelectedMenu("dd-list-exercises");
              }}
            >
              <FaPencil className=" size-6" />
              <label className=" font-medium text-lg">
                Lista de Exercicios
              </label>
            </button>
            <button
              className="flex w-full p-3 gap-2 justify-center items-center cursor-pointer rounded-lg btn-base-color group"
              onClick={() => {
                setSelectedMenu("");
                setNewExercisePopUp(exerciseGroupPosition);
              }}
            >
              <RiAddFill className=" size-8" />
              <label className=" font-medium text-lg">Criar Novo</label>
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
