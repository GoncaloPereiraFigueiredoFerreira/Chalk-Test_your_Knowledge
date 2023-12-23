import { useEffect, useState } from "react";
import { getListExercises } from "../ListExercises/ListExercises";
import { Exercise } from "../Exercise/Exercise";
import { DragDropShowExercise } from "./DragDropShowExercise";

function convertListExercises() {
  let listExercises = {} as { [key: string]: Exercise };
  getListExercises().forEach((value) => {
    listExercises[value.identity.id] = value;
  });
  return listExercises;
}

interface DragDropListExercisesProps {
  setSelectedMenu: (value: string) => void;
}

export function DragDropListExercises({
  setSelectedMenu,
}: DragDropListExercisesProps) {
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
          <DragDropShowExercise
            key={index}
            position={(index + 1).toString()}
            exercise={listExercises[key]}
            selectedExercise={key === selectedExercise}
            setSelectedExercise={(value) => setSelectedExercise(value)}
          ></DragDropShowExercise>
        ))}
      </div>
    </>
  );
}
