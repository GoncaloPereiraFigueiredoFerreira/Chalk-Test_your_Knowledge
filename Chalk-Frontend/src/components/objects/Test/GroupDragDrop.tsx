import { RiAddFill } from "react-icons/ri";
import { FaPencil } from "react-icons/fa6";
import { ShowExerciseDragDrop } from "./ShowExerciseDragDrop";
import { useEditTestContext } from "./EditTestContext";
import { textToHTML } from "../../interactiveElements/TextareaBlock";

interface GroupDragDropProps {
  exerciseGroupID: number;
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
}

export function GroupDragDrop({
  exerciseGroupID,
  exerciseID,
  setExerciseID,
  selectedMenu,
  setSelectedMenu,
  setNewExercisePopUp,
}: GroupDragDropProps) {
  const { testState } = useEditTestContext();

  return (
    <>
      <div className="flex flex-col gap-4 rounded-lg px-7 py-5 bg-3-1">
        <div
          className="flex w-full justify-between pb-4 px-4 border-b border-gray-2-2"
          onClick={() =>
            setExerciseID({
              groupPosition: exerciseGroupID,
              exercisePosition: -1,
            })
          }
        >
          <label className="w-full text-xl font-medium">
            Grupo {exerciseGroupID + 1}
          </label>
          <div className="flex w-full justify-end items-center gap-3">
            Cotação do Grupo:
            <div className="flex justify-center min-w-fit w-10 rounded-md px-3 py-1 bg-3-2">
              {testState.test.groups[exerciseGroupID].groupCotation}
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
                  groupPosition: exerciseGroupID,
                  exercisePosition: -1,
                });
                setSelectedMenu("edit-group");
              }}
            >
              <FaPencil className="size-5" />
              Editar
            </button>
          </div>
          <div className="px-4">
            {textToHTML(
              testState.test.groups[exerciseGroupID].groupInstructions
            )}
          </div>
        </div>
        <div className="flex flex-col gap-4">
          {testState.test.groups[exerciseGroupID].exercises.map(
            (value, index) => (
              <ShowExerciseDragDrop
                key={index}
                listExerciseButtons={false}
                exercisePosition={index}
                groupPosition={exerciseGroupID}
                exercise={value}
                selectedMenu={selectedMenu}
                setExerciseID={setExerciseID}
                setSelectedMenu={setSelectedMenu}
                exerciseIsSelected={
                  index === exerciseID.exercisePosition &&
                  exerciseGroupID === exerciseID.groupPosition
                }
                setSelectedExercise={() => {
                  if (
                    exerciseGroupID != exerciseID.groupPosition ||
                    index != exerciseID.exercisePosition
                  )
                    setExerciseID({
                      groupPosition: exerciseGroupID,
                      exercisePosition: index,
                    });
                  else
                    setExerciseID({
                      groupPosition: exerciseGroupID,
                      exercisePosition: -1,
                    });
                }}
              ></ShowExerciseDragDrop>
            )
          )}
          <div className="flex gap-7 w-full">
            <button
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group"
              onClick={() => {
                setExerciseID({
                  groupPosition: exerciseGroupID,
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
                setNewExercisePopUp(exerciseGroupID);
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
