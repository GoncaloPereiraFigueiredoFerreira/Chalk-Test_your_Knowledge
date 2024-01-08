import { useEffect } from "react";
import { getListExercises } from "../ListExercises/ListExercises";
import { Exercise } from "../Exercise/Exercise";
import { ShowExerciseDragDrop } from "./ShowExerciseDragDrop";
import { SortableContext } from "@dnd-kit/sortable";

function convertListExercises() {
  let listExercises: Exercise[] = [];
  getListExercises().forEach((value) => {
    let exercise: Exercise = JSON.parse(JSON.stringify(value));
    exercise.identity.id = "new-exercise-".concat(Math.random().toString());
    listExercises.push(exercise);
  });
  return listExercises;
}

interface ExerciseBankDragDropProps {
  exerciseID: {
    groupPosition: number;
    exercisePosition: number;
  };
  setExerciseID: (value: {
    groupPosition: number;
    exercisePosition: number;
  }) => void;
  setSelectedMenu: (value: string) => void;
  selectedExercise: number;
  setSelectedExercise: (value: number) => void;
  listExercises: Exercise[];
  setListExercises: (value: Exercise[]) => void;
  draggingExercises: boolean;
}

export function ExerciseBankDragDrop({
  exerciseID,
  setExerciseID,
  setSelectedMenu,
  selectedExercise,
  setSelectedExercise,
  draggingExercises,
  listExercises,
  setListExercises,
}: ExerciseBankDragDropProps) {
  useEffect(() => {
    setListExercises(convertListExercises());
  }, []);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max bg-2-1">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Exercícios</label>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setSelectedMenu("")}
          >
            X
          </button>
        </div>
        <SortableContext
          items={listExercises.map((exercise) => exercise.identity.id)}
        >
          {listExercises.map((exercise, index) => (
            <ShowExerciseDragDrop
              key={index}
              listExerciseButtons={true}
              groupPosition={exerciseID.groupPosition}
              exercise={exercise}
              selectedMenu={"dd-list-exercises"}
              setSelectedMenu={setSelectedMenu}
              exerciseIsSelected={index === selectedExercise}
              setSelectedExercise={(value) => {
                if (value === "") setSelectedExercise(-1);
                else setSelectedExercise(index);
              }}
              exercisePosition={index}
              setExerciseID={setExerciseID}
              draggingExercises={draggingExercises}
            ></ShowExerciseDragDrop>
          ))}
        </SortableContext>
      </div>
    </>
  );
}
