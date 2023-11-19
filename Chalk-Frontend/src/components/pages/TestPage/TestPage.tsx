import { Searchbar } from "../../objects/Searchbar/Searchbar.tsx";
import { Exercise } from "../../objects/Exercise/ShowExercise.tsx";
import { useState } from "react";

export function TestPage() {
  const [selectedExercise, setSelectedExercise] = useState(-1);
  return (
    <div className="flex flex-col w-full dark:bg-gray-700 bg-white py-8 gap-8">
      <Searchbar></Searchbar>
      <div className="flex-col flex w-full h-screen dark:bg-gray-700 px-16"></div>
    </div>
  );
}
