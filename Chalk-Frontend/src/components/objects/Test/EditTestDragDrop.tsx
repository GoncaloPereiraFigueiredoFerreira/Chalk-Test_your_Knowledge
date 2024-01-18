import { useContext, useState } from "react";
import { EditTestActionKind, useEditTestContext } from "./EditTestContext";
import { GroupDragDrop } from "./GroupDragDrop";
import { CreateNewExercisePopUp } from "../ListExercises/CreateNewExercisePopUp";
import { ExerciseType } from "../Exercise/Exercise";
import { RiAddFill } from "react-icons/ri";
import { FaPencil } from "react-icons/fa6";
import "./EditTestDragDrop.css";
import { textToHTML } from "../../interactiveElements/TextareaBlock";
import { SortableContext } from "@dnd-kit/sortable";
import { UserContext } from "../../../UserContext";
import { translateVisibilityToString } from "./EditTestInfo";

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
  draggingExercises: boolean;
}

export function EditTestDragDrop({
  exerciseID,
  setExerciseID,
  selectedMenu,
  setSelectedMenu,
  draggingExercises,
}: EditTestProps) {
  const [draggingGroups, setDraggingGroups] = useState(false);
  const [newExercisePopUp, setNewExercisePopUp] = useState(-1);
  const { testState, dispatch } = useEditTestContext();
  const { user } = useContext(UserContext);

  return (
    /* <div className="flex flex-col w-full bg-2-1 min-h-max"> */
    <div className="flex flex-col w-full h-screen overflow-auto bg-2-1 min-h-max px-16 pb-8 dark:text-white">
      <div className="flex w-full items-center justify-between mt-8 px-4 pb-6 mb-3 border-b-2 border-gray-2-2 dark:border-[#dddddd]">
        <div className="text-title-1">
          {testState.test.title
            ? testState.test.title
            : "Novo Teste - " + draggingExercises}
        </div>
      </div>
      <div className="flex flex-col px-4 pt-4 gap-4">
        <div className="flex items-center justify-between">
          <strong className="text-xl">Informações Gerais do Teste:</strong>
          <button
            className="flex p-2 gap-2 rounded-md bg-btn-4-1 group"
            onClick={() => {
              setSelectedMenu("edit-test-info");
            }}
          >
            <FaPencil className="size-5" />
            Editar
          </button>
        </div>
        <div className="gridTestInfo text-md pl-4 gap-x-5 gap-y-3">
          <strong>Autor: </strong>
          <p>{testState.test.author}</p>
          <strong>Visibilidade do Teste: </strong>
          <p>{translateVisibilityToString(testState.test.visibility)}</p>
          <strong>Cotação máxima do teste: </strong>
          <p>{testState.test.globalPoints} pts</p>
        </div>
        <div className="flex flex-col py-4 gap-4">
          <strong className="text-xl">Instruções do Teste</strong>
          <p className="text-md mx-4">
            {textToHTML(testState.test.globalInstructions)}
          </p>
        </div>
      </div>
      <div className="flex flex-col px-4 py-8 gap-4 border-t-2 border-gray-2-2">
        <SortableContext items={testState.test.groups.map((group) => group.id)}>
          {testState.test.groups.map((group, index) => (
            <GroupDragDrop
              key={index}
              exerciseGroupPosition={index}
              exerciseGroupID={group.id}
              exerciseID={exerciseID}
              setExerciseID={setExerciseID}
              selectedMenu={selectedMenu}
              setSelectedMenu={setSelectedMenu}
              setNewExercisePopUp={(value: number) =>
                setNewExercisePopUp(value)
              }
              draggingGroups={draggingGroups}
              setDraggingGroups={(value) => setDraggingGroups(value)}
              draggingExercises={draggingExercises}
            ></GroupDragDrop>
          ))}
        </SortableContext>
        <div
          className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group cursor-pointer"
          onClick={() => {
            if (selectedMenu !== "edit-group") {
              dispatch({ type: EditTestActionKind.ADD_GROUP });
            }
          }}
        >
          <RiAddFill className=" size-8" />
          <label className=" font-medium text-lg">Novo Grupo</label>
        </div>
      </div>
      <div className="flex flex-col pt-4 gap-4 border-t-2 border-gray-2-2">
        <div className="mx-4 mt-4">
          <div className="flex items-center justify-between">
            <strong className="text-xl">Conclusão</strong>
            <button
              className="flex p-2 gap-2 rounded-md bg-btn-4-1 group"
              onClick={() => {
                setSelectedMenu("edit-test-info");
              }}
            >
              <FaPencil className="size-5" />
              Editar
            </button>
          </div>
          <p className="text-md mx-4">
            {textToHTML(testState.test.conclusion)}
          </p>
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
            type: EditTestActionKind.CREATE_NEW_EXERCISE,
            group: {
              groupPosition: newExercisePopUp,
              exerciseType: newExerciseType,
            },
            exercise: {
              specialist: user.user?.id,
              exercisePosition: 0,
              groupPosition: 0,
            },
          });
          setNewExercisePopUp(-1);
          setSelectedMenu("create-exercise");
        }}
      />
    </div>
  );
}
