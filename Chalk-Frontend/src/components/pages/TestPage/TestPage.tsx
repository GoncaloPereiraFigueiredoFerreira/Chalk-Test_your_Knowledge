import { Searchbar } from "../../objects/Searchbar/Searchbar.tsx";
import { useState } from "react";

export function TestPage() {
  const [selectedExercise, setSelectedExercise] = useState(-1);
  return (
    <div className="flex flex-row divide-x-2 border-gray-2-2">
      <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
        <Searchbar></Searchbar>
        <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8">
          <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
            <label className="flex text-title-1">Testes</label>
          </div>
        </div>
      </div>
    </div>
  );
}
