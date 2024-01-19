import { ShowExerciseDragDrop } from "./ShowExerciseDragDrop";
import { EditTestActionKind, useEditTestContext } from "./EditTestContext";
import { textToHTML } from "../../interactiveElements/TextareaBlock";
import { SortableContext, useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { useEffect } from "react";
import { RiAddFill } from "react-icons/ri";
import { FaPencil } from "react-icons/fa6";
import { HiOutlineTrash } from "react-icons/hi";
import { GarbageIcon } from "../SVGImages/SVGImages";

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
          className={`${
            isDragging && "opacity-40"
          } flex flex-col gap-4 rounded-lg px-7 py-5 bg-[#dddddd] dark:bg-gray-600 h-56 overflow-hidden`}
        >
          <div className="flex w-full justify-between pb-4 px-4 border-b border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd] dark:border-[#dddddd] dark:text-[#dddddd] ">
            <label className="w-full text-xl font-medium">
              Grupo {exerciseGroupPosition + 1}
            </label>
            <div className="flex w-full justify-end items-center gap-3">
              Cotação do Grupo:
              <div className="flex justify-center min-w-fit w-10 rounded-md px-3 py-1 bg-white dark:bg-black">
                {testState.test.groups[exerciseGroupPosition].groupPoints} pts
              </div>
            </div>
          </div>
          <div className="px-4">
            <div className="flex items-center justify-between">
              <strong>Instruções do grupo:</strong>
              <button className="flex p-2 gap-2 rounded-md bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] group">
                <FaPencil className="size-5" />
                Editar
              </button>
            </div>
            <div className={"px-4"}>
              {textToHTML(
                testState.test.groups[exerciseGroupPosition].groupInstructions
              )}
            </div>
          </div>
          <div className="flex gap-7 w-full">
            <button className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] transition-all group">
              <FaPencil className="group-gray-icon size-6" />
              <label className="group-gray-icon font-medium text-lg">
                Lista de Exercicios
              </label>
            </button>
            <button className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] transition-all group">
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
        className={`${
          isDragging && "opacity-50"
        } flex flex-col gap-4 rounded-lg px-7 py-5 cursor-default bg-[#dddddd] dark:bg-gray-600`}
      >
        <div
          className="flex w-full justify-between pb-4 px-4 border-b border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]"
          {...listeners}
          onClick={() =>
            setExerciseID({
              groupPosition: exerciseGroupPosition,
              exercisePosition: -1,
            })
          }
        >
          <label className="w-full text-xl font-medium">
            Grupo {exerciseGroupPosition + 1}
          </label>
          <div className="flex w-full justify-end items-center gap-4">
            Cotação do Grupo:
            <div className="flex justify-center min-w-fit w-10 rounded-md px-3 py-1 bg-white dark:bg-black dark:text-[#dddddd]">
              {testState.test.groups[exerciseGroupPosition].groupPoints} pts
            </div>
            <div className="flex border-l-2 pl-4 border-[#dddddd]">
              <button
                className="btn-options-exercise gray-icon"
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
              className="flex p-2 gap-2 rounded-md bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] group"
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
            {textToHTML(
              testState.test.groups[exerciseGroupPosition].groupInstructions
            )}
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
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] transition-all group cursor-pointer"
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
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] transition-all group cursor-pointer"
              onClick={() => {
                setSelectedMenu("");
                setNewExercisePopUp(exerciseGroupPosition);
              }}
            >
              <RiAddFill className=" size-8" />
              <label className=" font-medium text-lg">Criar Novo</label>
            </button>
            <div
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-[#acacff] hover:bg-[#5555ce] text-black hover:text-white dark:bg-[#dddddd] hover:dark:text-black dark:hover:bg-[#ffd025] transition-all group cursor-pointer"
              onClick={() => {
                setExerciseID({
                  groupPosition: -1,
                  exercisePosition: -1,
                });
                dispatch({
                  type: EditTestActionKind.REMOVE_GROUP,
                  exercise: {
                    groupPosition: exerciseID.groupPosition,
                    exercisePosition: exerciseID.exercisePosition,
                  },
                });
              }}
            >
              <GarbageIcon style=" size-8" />
              <label className=" font-medium text-lg">Remove</label>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
