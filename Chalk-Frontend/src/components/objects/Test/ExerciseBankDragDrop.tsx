import { useEffect, useState } from "react";
import { getListExercises } from "../ListExercises/ListExercises";
import { Exercise } from "../Exercise/Exercise";
import { ShowExerciseDragDrop } from "./ShowExerciseDragDrop";

function convertListExercises() {
  let listExercises = {} as { [key: string]: Exercise };
  getListExercises().forEach((value) => {
    listExercises[value.identity.id] = value;
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
}

export function ExerciseBankDragDrop({
  exerciseID,
  setExerciseID,
  setSelectedMenu,
}: ExerciseBankDragDropProps) {
  const [listExercises, setListExercises] = useState<{
    [key: string]: Exercise;
  }>({});
  const [selectedExercise, setSelectedExercise] = useState("");

  useEffect(() => {
    setListExercises(convertListExercises());
  }, []);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8 bg-2-1 ">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Exercícios</label>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setSelectedMenu("")}
          >
            X
          </button>
        </div>
        {Object.keys(listExercises).map((key, index) => (
          <ShowExerciseDragDrop
            key={index}
            listExerciseButtons={true}
            groupPosition={exerciseID.groupPosition}
            exercise={listExercises[key]}
            selectedMenu={"dd-list-exercises"}
            setSelectedMenu={setSelectedMenu}
            exerciseIsSelected={key === selectedExercise}
            setSelectedExercise={(value) => setSelectedExercise(value)}
            exercisePosition={index}
            setExerciseID={setExerciseID}
          ></ShowExerciseDragDrop>
        ))}
      </div>
    </>
  );
}
