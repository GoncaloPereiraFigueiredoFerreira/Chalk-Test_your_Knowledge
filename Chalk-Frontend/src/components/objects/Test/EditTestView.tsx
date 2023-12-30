import { useState } from "react";
import { useCreateTestContext } from "./CreateTestContext";
import type { DragDropGroupPreview } from "./DragDropGroupPreview";

interface EditTestViewProps {
  setExerciseID: (value: {
    groupPosition: number;
    exercisePosition: number;
  }) => void;
  selectedMenu: string;
  setSelectedMenu: (value: string) => void;
}

export function EditTestView({
  setExerciseID,
  selectedMenu,
  setSelectedMenu,
}: EditTestViewProps) {
  const [newExercisePopUp, setNewExercisePopUp] = useState(false);
  const { testState, dispatch } = useCreateTestContext();

  return (
    <div className="flex flex-col w-full h-screen overflow-auto bg-2-1 min-h-max px-16 pb-8">
      <div className="flex w-full items-center justify-between mt-8 px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
        <div className="text-title-1">
          {testState.test.title ? testState.test.title : "Novo Teste"}
        </div>
        <div className="flex space-x-4">
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setSelectedMenu("dd-list-exercises")}
          >
            dd-list-exercises
          </button>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setSelectedMenu("edit-exercise")}
          >
            edit-exercise
          </button>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setSelectedMenu("create-exercise")}
          >
            create-exercise
          </button>
        </div>
      </div>
      <div className="flex flex-col">
        <div className="ml-4 mt-4">
          <h2 className="text-xl">Informações Gerais do Teste:</h2>
          <table className="text-md ml-4">
            <h3>
              <strong>Autor: </strong>
              {testState.test.author}
            </h3>
            <h3>
              <strong>Cotação máxima do teste: </strong>
              {testState.test.globalCotation}
            </h3>
            <h3>
              <strong>Instruções do Teste: </strong>
              {testState.test.globalInstructions}
            </h3>
          </table>
        </div>
      </div>
      <DragDropGroupPreview
        test={test}
        setShowExID={() => {}}
        showExId={""}
      ></DragDropGroupPreview>
    </div>
  );
}
