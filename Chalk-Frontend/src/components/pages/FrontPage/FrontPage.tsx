import { useState } from "react";
import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { Searchbar } from "../../objects/Searchbar/Searchbar";

import { DndContext } from "@dnd-kit/core";
import { Draggable } from "./Draggable";
import { Droppable } from "./Droppable";

export function FrontPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(true);
  const [parent, setParent] = useState(null);
  const [widgets, setWidgets] = useState<string[]>([]);

  //const draggable = <Draggable id="draggable">Go ahead, drag me.</Draggable>;

  function draggable(ide: any) {
    const coisa = <Draggable id={ide}>Go ahead, drag me {ide}.</Draggable>;
    return coisa;
  }

  function handleOnDrop(e: React.DragEvent) {
    const widgetType = e.dataTransfer.getData("widgetType") as string;
    setWidgets([...widgets, widgetType]);
  }

  function handleDragoOver(e: React.DragEvent) {
    e.preventDefault();
  }

  return (
    <DndContext onDragEnd={handleDragEnd}>
      <div className="flex flex-row">
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
          {draggable("ola")}
          {draggable("ola3")}
          {draggable("ola4")}
          <Searchbar></Searchbar>
          <ListExercises
            setEditMenuIsOpen={(value) => setEditMenuIsOpen(value)}
          ></ListExercises>
        </div>
        <div className="flex flex-col w-full h-screen overflow-auto bg-amber-600">
          <div
            className="p-20 m-20 h-full bg-green-700"
            onDrop={handleOnDrop}
            onDragOver={handleDragoOver}
          >
            {widgets.map((widget, index) => (
              <div className="dropped-widget" key={index}>
                {widget}
              </div>
            ))}{" "}
            asda s adasd asd sad assd
            <Droppable id="droppable">{"Drop here"}</Droppable>
          </div>
        </div>
      </div>
    </DndContext>
  );
  function handleDragEnd({ over, active }) {
    //setParent(active ? active.id : null);
    if (over) {
      setWidgets([...widgets, active.id]);
    }
  }
}

//export function FrontPage() {
//  const [editMenuIsOpen, setEditMenuIsOpen] = useState(true);
//  const [widgets, setWidgets] = useState<string[]>([]);
//
//  function handleOnDrop(e: React.DragEvent) {
//    const widgetType = e.dataTransfer.getData("widgetType") as string;
//    setWidgets([...widgets, widgetType]);
//  }
//
//  function handleDragoOver(e: React.DragEvent) {
//    e.preventDefault();
//  }
//
//  return (
//    <div className="flex flex-row">
//      <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
//        <Searchbar></Searchbar>
//        <ListExercises
//          setEditMenuIsOpen={(value) => setEditMenuIsOpen(value)}
//        ></ListExercises>
//      </div>
//      <div className="flex flex-col w-full h-screen overflow-auto bg-amber-600">
//        <div
//          className="p-20 m-20 h-full bg-green-700"
//          onDrop={handleOnDrop}
//          onDragOver={handleDragoOver}
//        >
//          {widgets.map((widget, index) => (
//            <div className="dropped-widget" key={index}>
//              {widget}
//            </div>
//          ))}{" "}
//          asda s adasd asd sad assd
//        </div>
//      </div>
//    </div>
//  );
//}
