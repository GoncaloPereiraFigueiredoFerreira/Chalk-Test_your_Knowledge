import { RiAddFill } from "react-icons/ri";
import { FaPencil } from "react-icons/fa6";
import { ShowExerciseDragDrop } from "./ShowExerciseDragDrop";
import { useEditTestContext } from "./EditTestContext";
import { textToHTML } from "../../interactiveElements/TextareaBlock";
import { SortableContext, useSortable } from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { useEffect } from "react";

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
  const { testState } = useEditTestContext();

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
          } flex flex-col gap-4 rounded-lg px-7 py-5 bg-3-1 h-56 overflow-hidden`}
        >
          <div className="flex w-full justify-between pb-4 px-4 border-b border-gray-2-2">
            <label className="w-full text-xl font-medium">
              Grupo {exerciseGroupPosition + 1}
            </label>
            <div className="flex w-full justify-end items-center gap-3">
              Cotação do Grupo:
              <div className="flex justify-center min-w-fit w-10 rounded-md px-3 py-1 bg-3-2">
                {testState.test.groups[exerciseGroupPosition].groupCotation} pts
              </div>
            </div>
          </div>
          <div className="px-4">
            <div className="flex items-center justify-between">
              <strong>Instruções do grupo:</strong>
              <button
                className="flex p-2 gap-2 rounded-md bg-btn-4-1 group"
                onClick={() => {
                  setExerciseID({
                    groupPosition: exerciseGroupPosition,
                    exercisePosition: -1,
                  });
                  setSelectedMenu("edit-group");
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
          <div className="flex gap-7 w-full">
            <button
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group"
              onClick={() => {
                setExerciseID({
                  groupPosition: exerciseGroupPosition,
                  exercisePosition: -1,
                });
                setSelectedMenu("dd-list-exercises");
              }}
            >
              <FaPencil className="group-gray-icon size-6" />
              <label className="group-gray-icon font-medium text-lg">
                Lista de Exercicios
              </label>
            </button>
            <button
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group"
              onClick={() => {
                setSelectedMenu("");
                setNewExercisePopUp(exerciseGroupPosition);
              }}
            >
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
        style={{
          transition,
          transform: CSS.Translate.toString(transform),
        }}
        className={`${
          isDragging && "opacity-50"
        } flex flex-col gap-4 rounded-lg px-7 py-5 bg-3-1`}
      >
        <div
          className="flex w-full justify-between pb-4 px-4 border-b border-gray-2-2"
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
          <div className="flex w-full justify-end items-center gap-3">
            Cotação do Grupo:
            <div className="flex justify-center min-w-fit w-10 rounded-md px-3 py-1 bg-3-2">
              {testState.test.groups[exerciseGroupPosition].groupCotation} pts
            </div>
          </div>
        </div>
        <div className="px-4">
          <div className="flex items-center justify-between">
            <strong>Instruções do grupo:</strong>
            <button
              className="flex p-2 gap-2 rounded-md bg-btn-4-1 group"
              onClick={() => {
                setExerciseID({
                  groupPosition: exerciseGroupPosition,
                  exercisePosition: -1,
                });
                setSelectedMenu("edit-group");
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
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group"
              onClick={() => {
                setExerciseID({
                  groupPosition: exerciseGroupPosition,
                  exercisePosition: -1,
                });
                setSelectedMenu("dd-list-exercises");
              }}
            >
              <FaPencil className="group-gray-icon size-6" />
              <label className="group-gray-icon font-medium text-lg">
                Lista de Exercicios
              </label>
            </button>
            <button
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group"
              onClick={() => {
                setSelectedMenu("");
                setNewExercisePopUp(exerciseGroupPosition);
              }}
            >
              <RiAddFill className="group-gray-icon size-8" />
              <label className="group-gray-icon font-medium text-lg">
                Criar Novo
              </label>
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
