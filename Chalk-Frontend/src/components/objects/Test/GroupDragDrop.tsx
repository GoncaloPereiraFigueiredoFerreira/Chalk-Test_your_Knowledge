import { RiAddFill } from "react-icons/ri";
import { FaPencil } from "react-icons/fa6";
import { ShowExerciseDragDrop } from "./ShowExerciseDragDrop";
import {
  CreateTestActionKind,
  useCreateTestContext,
} from "./CreateTestContext";
import { GarbageIcon } from "../SVGImages/SVGImages";

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
  const { testState, dispatch } = useCreateTestContext();

  return (
    <>
      <div className="flex flex-col rounded-lg px-7 py-5 bg-3-2">
        <div
          className="flex w-full justify-between pb-4 px-4 border-b border-gray-2-2 dark:border-[#dddddd] dark:text-[#dddddd] mb-4"
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
            <div className=" flex justify-center min-w-fit w-10 rounded-lg px-3 py-1 bg-3-2 dark:text-[#dddddd]">
              {testState.test.groups[exerciseGroupID].groupCotation}
            </div>
          </div>
        </div>
        {testState.test.groups[exerciseGroupID].groupInstructions}
        <div className="flex flex-col gap-4">
          {testState.test.groups[exerciseGroupID].exercises.map(
            (value, index) => (
              <ShowExerciseDragDrop
                key={index}
                listExerciseButtons={false}
                setExerciseID={setExerciseID}
                exercisePosition={index}
                groupPosition={exerciseID.groupPosition}
                exercise={value}
                selectedMenu={selectedMenu}
                setSelectedMenu={setSelectedMenu}
                selectedExercise={
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
            <div
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group cursor-pointer"
              onClick={() => {
                setExerciseID({
                  groupPosition: exerciseGroupID,
                  exercisePosition: -1,
                });
                setSelectedMenu("dd-list-exercises");
              }}
            >
              <FaPencil className=" size-6" />
              <label className=" font-medium text-lg">
                Lista de Exercicios
              </label>
            </div>
            <div
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group cursor-pointer"
              onClick={() => {
                setSelectedMenu("");
                setNewExercisePopUp(exerciseGroupID);
              }}
            >
              <RiAddFill className=" size-8" />
              <label className=" font-medium text-lg">Criar Novo</label>
            </div>
            <div
              className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group cursor-pointer"
              onClick={() => {
                setExerciseID({
                  groupPosition: -1,
                  exercisePosition: -1,
                });
                dispatch({
                  type: CreateTestActionKind.REMOVE_GROUP,
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
