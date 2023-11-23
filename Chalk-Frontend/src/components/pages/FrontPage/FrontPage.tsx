import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { EditExercise } from "../../objects/EditExercise/EditExercise";
import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { useState } from "react";

export function FrontPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(false);
  return (
    <div className="flex flex-row divide-x-2 border-gray-2-2">
      <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
        <Searchbar></Searchbar>
        <ListExercises></ListExercises>
      </div>
    </div>
  );
}
