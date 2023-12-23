import { useEffect, useState } from "react";
import { useCreateTestContext } from "./CreateTestContext";

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
    <div className="flex flex-col items-center justify-center h-screen m-28 bg-amber-800">
      <div className="flex flex-row">
        <button
          className="p-3 m-6 border rounded-md"
          onClick={() => setSelectedMenu("dd-list-exercises")}
        >
          dd-list-exercises
        </button>
        <button
          className="p-3 m-6 border rounded-md"
          onClick={() => setSelectedMenu("edit-exercise")}
        >
          edit-exercise
        </button>
        <button
          className="p-3 m-6 border rounded-md"
          onClick={() => setSelectedMenu("create-exercise")}
        >
          create-exercise
        </button>
      </div>
      <div className="flex flex-col">
        {testState.test.groups.map((value, index) => (
          <div key={index}>
            {value.exercises.map((value, index) => (
              <div className="flex flex-row gap-3" key={index}>
                <div>{value.identity.id}</div>
                <div>{value.base.title}</div>
                <div>{value.type}</div>
                <div>{value.identity.cotation}</div>
              </div>
            ))}
          </div>
        ))}
      </div>
    </div>
  );
}
