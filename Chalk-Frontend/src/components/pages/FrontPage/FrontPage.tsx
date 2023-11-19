import { useState } from "react";
import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { Searchbar } from "../../objects/Searchbar/Searchbar";

export function FrontPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(true);
  return (
    <div className="flex flex-row">
      <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
        <Searchbar></Searchbar>
        <ListExercises
          setEditMenuIsOpen={(value) => setEditMenuIsOpen(value)}
        ></ListExercises>
      </div>
      <div className="flex flex-col w-full h-screen overflow-auto bg-amber-600">
        <div className="p-20 m-20 h-full bg-green-700">
          {" "}
          asda s adasd asd sad assd
        </div>
      </div>
    </div>
  );
}
