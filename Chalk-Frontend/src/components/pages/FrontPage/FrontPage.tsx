import { useState } from "react";
import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { Searchbar } from "../../objects/Searchbar/Searchbar";

export function FrontPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(true);
  return (
    <div className="flex flex-row">
      <div className="flex flex-col w-full min-h-screen bg-2-1 overflow-auto">
        <Searchbar></Searchbar>
        <ListExercises
          setEditMenuIsOpen={(value) => setEditMenuIsOpen(value)}
        ></ListExercises>
      </div>
      {/* <div className="w-full bg-amber-600">c xc </div> */}
    </div>
  );
}
