import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { EditExercise } from "../../objects/EditExercise/EditExercise";
import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { useState } from "react";
import { ListExerciseProvider } from "../../objects/ListExercises/ListExerciseContext";

export function CreateTest() {
  const [selectedMenu, setSelectedMenu] = useState<"edit" | "dragDrop" | "">(
    ""
  );

  return (
    <></>
    // <ListExerciseProvider>
    //   <div className="flex flex-row divide-x-2 border-gray-2-2">
    //     <div
    //       className={`${
    //         selectedMenu === "dragDrop" ? "w-full" : "w-0"
    //       } flex flex-col w-full h-screen overflow-auto bg-2-1`}
    //     >
    //       <Searchbar></Searchbar>
    //       <DragDropListExercises
    //         editMenuIsOpen={selectedMenu}
    //         setEditMenuIsOpen={(value) => setSelectedMenu(value)}
    //       ></DragDropListExercises>
    //     </div>
    //     <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
    //       <ListExercises
    //         editMenuIsOpen={selectedMenu}
    //         setEditMenuIsOpen={(value) => setSelectedMenu(value)}
    //       ></ListExercises>
    //     </div>
    //     <div
    //       className={`${
    //         selectedMenu === "edit" ? "w-full" : "w-0"
    //       } flex flex-col h-screen overflow-auto bg-2-1 transition-[width]`}
    //     >
    //       {selectedMenu != "" ? (
    //         <EditExercise
    //           editMenuIsOpen={selectedMenu}
    //           setEditMenuIsOpen={(value) => setSelectedMenu(value)}
    //         ></EditExercise>
    //       ) : null}
    //     </div>
    //   </div>
    // </ListExerciseProvider>
  );
}
