import { useContext, useEffect } from "react";
import { Exercise, TranslateExerciseIN } from "../Exercise/Exercise";
import { ShowExerciseDragDrop } from "./ShowExerciseDragDrop";
import { SortableContext } from "@dnd-kit/sortable";
import { APIContext } from "../../../APIContext";

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
  selectedExercise: number;
  setSelectedExercise: (value: number) => void;
  listExercises: Exercise[];
  setListExercises: (value: Exercise[]) => void;
  draggingExercises: boolean;
}

export function ExerciseBankDragDrop({
  exerciseID,
  setExerciseID,
  setSelectedMenu,
  selectedExercise,
  setSelectedExercise,
  draggingExercises,
  listExercises,
  setListExercises,
}: ExerciseBankDragDropProps) {
  const { contactBACK } = useContext(APIContext);

  useEffect(() => {
    contactBACK("exercises", "GET", {
      page: "0",
      itemsPerPage: "10",
      visibility: "public",
    }).then((response) => {
      response.json().then((exercises) => {
        console.log(exercises);
        let exerciseL: Exercise[] = [];
        exercises.map((ex: any) => {
          exerciseL.push(TranslateExerciseIN(ex));
        });
        setListExercises(exerciseL);
      });
    });
  }, []);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max bg-2-1">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Exercícios</label>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => setSelectedMenu("")}
          >
            X
          </button>
        </div>
        <SortableContext
          items={listExercises.map((exercise) => exercise.identity.id)}
        >
          {listExercises.map((exercise, index) => (
            <ShowExerciseDragDrop
              key={index}
              listExerciseButtons={true}
              groupPosition={exerciseID.groupPosition}
              exercise={exercise}
              selectedMenu={"dd-list-exercises"}
              setSelectedMenu={setSelectedMenu}
              exerciseIsSelected={index === selectedExercise}
              setSelectedExercise={(value) => {
                if (value === "") setSelectedExercise(-1);
                else setSelectedExercise(index);
              }}
              exercisePosition={index}
              setExerciseID={setExerciseID}
              draggingExercises={draggingExercises}
            ></ShowExerciseDragDrop>
          ))}
        </SortableContext>
      </div>
    </>
  );
}
