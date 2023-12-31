import { RiAddFill } from "react-icons/ri";
import { FaPencil } from "react-icons/fa6";
import { DragDropShowExercise } from "./DragDropShowExercise";
import { useCreateTestContext } from "./CreateTestContext";

interface DragDropGroupPreviewProps {
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

export function DragDropGroupPreview({
  exerciseGroupID,
  exerciseID,
  setExerciseID,
  selectedMenu,
  setSelectedMenu,
  setNewExercisePopUp,
}: DragDropGroupPreviewProps) {
  const { testState, dispatch } = useCreateTestContext();
  const groupIsSelected = exerciseGroupID === exerciseID.groupPosition;

  return (
    <>
      <div className="flex flex-col rounded-lg px-7 py-5 bg-3-1">
        <div className="flex w-full justify-between pb-4 px-4 border-b border-gray-2-2 mb-4">
          <label className="w-full text-xl font-medium">
            Grupo {exerciseGroupID + 1}
          </label>
          <div className="flex w-full justify-end items-center gap-3">
            Cotação do Grupo:
            <div className=" flex justify-center min-w-fit w-10 rounded-lg px-3 py-1 bg-3-2">
              {testState.test.groups[exerciseGroupID].groupCotation}
            </div>
          </div>
        </div>
        {testState.test.groups[exerciseGroupID].groupInstructions}
        <div className="flex flex-col gap-4">
          {testState.test.groups[exerciseGroupID].exercises.map(
            (value, index) => (
              <DragDropShowExercise
                key={index}
                position={(index + 1).toString()}
                exercise={value}
                selectedExercise={
                  index === exerciseID.exercisePosition && groupIsSelected
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
              ></DragDropShowExercise>
            )
          )}
          <div className="flex gap-7 w-full">
            <div
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group"
              onClick={() => setSelectedMenu("dd-list-exercises")}
            >
              <FaPencil className="group-gray-icon size-6" />
              <label className="group-gray-icon font-medium text-lg">
                Lista de Exercicios
              </label>
            </div>
            <div
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
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
