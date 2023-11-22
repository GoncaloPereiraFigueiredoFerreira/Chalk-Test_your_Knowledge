import { useState } from "react";
import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { Searchbar } from "../../objects/Searchbar/Searchbar";

import {
  DndContext,
  closestCenter,
  KeyboardSensor,
  PointerSensor,
  useSensor,
  useSensors,
} from "@dnd-kit/core";
import {
  arrayMove,
  SortableContext,
  sortableKeyboardCoordinates,
  verticalListSortingStrategy,
} from "@dnd-kit/sortable";

import { Draggable } from "./Draggable";
import { Droppable } from "./Droppable";
import { SortableItem } from "./SortableItem";

interface ExerciseItem {
  id: number;
  exercise: string;
}

export function FrontPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(true);
  const [ExerciseItem, setExerciseItem] = useState<ExerciseItem[]>([]);

  const sensors = useSensors(
    useSensor(PointerSensor),
    useSensor(KeyboardSensor, {
      coordinateGetter: sortableKeyboardCoordinates,
    })
  );

  //const draggable = <Draggable id="draggable">Go ahead, drag me.</Draggable>;

  //function draggable(ide: any) {
  //  const coisa = <Draggable id={ide}>Go ahead, drag me {ide}.</Draggable>;
  //  return coisa;
  //}

  return (
    <DndContext onDragEnd={handleDragEnd}>
      <div className="flex flex-row">
        <div className="flex flex-col w-full h-screen overflow-x-hidden overflow-y-auto bg-2-1">
          <Searchbar></Searchbar>
          <ListExercises
            setEditMenuIsOpen={(value) => setEditMenuIsOpen(value)}
          ></ListExercises>
        </div>
        <div className="flex flex-col w-full h-screen overflow-auto bg-amber-600">
          <div className="p-20 m-20 h-full bg-green-700">
            <DndContext
              sensors={sensors}
              collisionDetection={closestCenter}
              onDragEnd={handleDragEnd2}
            >
              <SortableContext
                items={ExerciseItem}
                strategy={verticalListSortingStrategy}
              >
                {ExerciseItem.map((exercise) => (
                  <SortableItem id={exercise.id}>
                    <div className="dropped-widget" key={exercise.id}>
                      {`Exercício ${exercise.exercise} com ID ${exercise.id}`}
                    </div>
                  </SortableItem>
                ))}
              </SortableContext>
            </DndContext>

            <Droppable id="droppable">Drop here</Droppable>
          </div>
        </div>
      </div>
    </DndContext>
  );
  function handleDragEnd(evento: any) {
    const { over, active } = evento;
    //setParent(active ? active.id : null);
    if (over) {
      const newExercise: ExerciseItem = {
        id: ExerciseItem.length + 1,
        exercise: active.id,
      };
      setExerciseItem([...ExerciseItem, newExercise]);
    }
  }
  function handleDragEnd2(evento: any) {
    const { active, over } = evento;
    //setParent(active ? active.id : null);
    if (active.id !== over.id) {
      setExerciseItem((items) => {
        const oldIndex = items.findIndex(
          (exercise) => exercise.id === active.id
        );
        const newIndex = items.findIndex((exercise) => exercise.id === over.id);

        return arrayMove(items, oldIndex, newIndex);
      });
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
