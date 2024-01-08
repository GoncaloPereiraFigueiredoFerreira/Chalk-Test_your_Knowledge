import { useState } from "react";
import { EditTestActionKind, useEditTestContext } from "./EditTestContext";
import { GroupDragDrop } from "./GroupDragDrop";
import { CreateNewExercisePopUp } from "../ListExercises/CreateNewExercisePopUp";
import { ExerciseType } from "../Exercise/Exercise";
import { RiAddFill } from "react-icons/ri";
import { FaPencil } from "react-icons/fa6";
import "./EditTestDragDrop.css";
import { textToHTML } from "../../interactiveElements/TextareaBlock";
import { SortableContext } from "@dnd-kit/sortable";

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

  return (
    <div className="flex flex-col w-full bg-2-1 min-h-max">
      <div className="flex w-full items-center justify-between mt-8 px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
        <div className="text-title-1">
          {testState.test.title
            ? testState.test.title
            : "Novo Teste - " + draggingExercises}
        </div>
        {/* <div className="flex gap-4"></div> */}
      </div>
      <div className="flex flex-col pb-4 mb-8 gap-4 border-b-2 border-gray-2-2">
        <div className="mx-4 mt-4">
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
          <div className="gridTestInfo text-md m-4 gap-4">
            <strong>Autor: </strong>
            <p>{testState.test.author}</p>
            <strong>Cotação máxima do teste: </strong>
            <p>{testState.test.globalCotation} pts</p>
            <strong>Instruções do Teste: </strong>
            <p>{textToHTML(testState.test.globalInstructions)}</p>
          </div>
        </div>
      </div>
      <div className="flex flex-col px-4 gap-4">
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
          className="flex w-full p-3 gap-2 justify-center items-center rounded-lg bg-btn-4-1 transition-all group"
          onClick={() => {
            if (selectedMenu !== "edit-group")
              dispatch({ type: EditTestActionKind.ADD_GROUP });
          }}
        >
          <RiAddFill className="group-gray-icon size-8" />
          <label className="group-gray-icon font-medium text-lg">
            Novo Grupo
          </label>
        </div>
      </div>
      <div className="flex flex-col pt-4 mt-8 gap-4 border-t-2 border-gray-2-2">
        <div className="mx-4 mt-4">
          <div className="flex items-center justify-between">
            <strong className="text-md">Conclusão</strong>
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
          <p className="text-md m-4">{textToHTML(testState.test.conclusion)}</p>
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
          });
          setNewExercisePopUp(-1);
          setSelectedMenu("create-exercise");
        }}
      />
    </div>
  );
}
