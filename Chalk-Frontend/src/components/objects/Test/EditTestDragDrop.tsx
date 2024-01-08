import { useState } from "react";
import {
  CreateTestActionKind,
  useCreateTestContext,
} from "./CreateTestContext";
import { GroupDragDrop } from "./GroupDragDrop";
import { CreateNewExercisePopUp } from "../ListExercises/CreateNewExercisePopUp";
import { ExerciseType } from "../Exercise/Exercise";
import { RiAddFill } from "react-icons/ri";

interface EditTestProps {
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
}

export function EditTestDragDrop({
  exerciseID,
  setExerciseID,
  selectedMenu,
  setSelectedMenu,
}: EditTestProps) {
  const [newExercisePopUp, setNewExercisePopUp] = useState(-1);
  const { testState, dispatch } = useCreateTestContext();

  return (
    <div className="flex flex-col w-full h-screen overflow-auto bg-2-1 min-h-max px-16 pb-8 dark:text-white">
      <div className="flex w-full items-center justify-between mt-8 px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
        <div className="text-title-1">
          {testState.test.title ? testState.test.title : "Novo Teste"}
        </div>
        <div className="flex space-x-4">
          <div className="flex flex-col">
            <label>group:{exerciseID.groupPosition}</label>
            <label>exercise:{exerciseID.exercisePosition}</label>
          </div>
        </div>
      </div>
      <div className="flex flex-col pb-4 mb-4 gap-4 border-b-2 border-gray-2-2">
        <div className="ml-4 mt-4">
          <h2 className="text-xl">Informações Gerais do Teste:</h2>
          <div className="grid grid-cols-2 w-fit text-md m-4 gap-4">
            <strong>Autor: </strong>
            <p>{testState.test.author}</p>
            <strong>Cotação máxima do teste: </strong>
            <p>{testState.test.globalCotation}</p>
            <strong>Instruções do Teste: </strong>
            <p>{testState.test.globalInstructions}</p>
          </div>
        </div>
      </div>
      <div className="flex flex-col p-2 gap-4">
        {testState.test.groups.map((_, index) => (
          <GroupDragDrop
            key={index}
            exerciseGroupID={index}
            exerciseID={exerciseID}
            setExerciseID={setExerciseID}
            selectedMenu={selectedMenu}
            setSelectedMenu={setSelectedMenu}
            setNewExercisePopUp={(value: number) => setNewExercisePopUp(value)}
          ></GroupDragDrop>
        ))}
        <div
          className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group cursor-pointer"
          onClick={() => {
            setSelectedMenu("edit-group");
            dispatch({ type: CreateTestActionKind.ADD_GROUP });
          }}
        >
          <RiAddFill className="group-gray-icon size-8" />
          <label className="group-gray-icon font-medium text-lg">
            Novo Grupo
          </label>
        </div>
      </div>
      <CreateNewExercisePopUp
        show={newExercisePopUp != -1}
        closePopUp={() => setNewExercisePopUp(-1)}
        createNewExercise={(newExerciseType: ExerciseType) => {
          setExerciseID({
            groupPosition: newExercisePopUp,
            exercisePosition:
              testState.test.groups[newExercisePopUp].exercises.length,
          });
          dispatch({
            type: CreateTestActionKind.CREATE_NEW_EXERCISE,
            group: {
              groupPosition: newExercisePopUp,
              exerciseType: newExerciseType,
            },
          });
          setNewExercisePopUp(-1);
          setSelectedMenu("create-exercise");
        }}
      />
    </div>
  );
}
