import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { EditExercise } from "../../objects/EditExercise/EditExercise";
import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { useState } from "react";
import { ListExerciseProvider } from "../../objects/ListExercises/ListExerciseContext";

export function ExerciseBankPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(false);
  const [exerciseID, setExerciseID] = useState("");

  return (
    <ListExerciseProvider>
      <div className="flex flex-row divide-x-2 border-gray-2-2">
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
          <Searchbar></Searchbar>
          <ListExercises
            setExerciseID={(value) => setExerciseID(value)}
            editMenuIsOpen={editMenuIsOpen}
            setEditMenuIsOpen={(value) => setEditMenuIsOpen(value)}
          ></ListExercises>
        </div>
        <div
          className={`${
            editMenuIsOpen ? "w-full" : "w-0"
          } flex flex-col h-screen overflow-auto bg-2-1 transition-[width]`}
        >
          {editMenuIsOpen ? (
            <EditExercise
              exerciseID={exerciseID}
              setExerciseID={(value) => setExerciseID(value)}
              setEditMenuIsOpen={(value) => setEditMenuIsOpen(value)}
            ></EditExercise>
          ) : null}
        </div>
      </div>
    </ListExerciseProvider>
  );
}
