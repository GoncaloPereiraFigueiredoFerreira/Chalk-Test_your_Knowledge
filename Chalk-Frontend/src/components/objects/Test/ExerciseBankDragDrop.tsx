import { useContext, useEffect, useState } from "react";
import { Exercise, TranslateExerciseIN } from "../Exercise/Exercise";
import { ShowExerciseDragDrop } from "./ShowExerciseDragDrop";
import { SortableContext } from "@dnd-kit/sortable";
import { APIContext } from "../../../APIContext";
import { UserContext } from "../../../UserContext";
import { Pagination } from "flowbite-react";
import { IoClose } from "react-icons/io5";

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
  const [totalPages, setTotalPages] = useState(1);
  const [currentPage, setCurrentPage] = useState(1);
  const onPageChange = (page: number) => setCurrentPage(page);
  const { user } = useContext(UserContext);

  useEffect(() => {
    contactBACK("exercises", "GET", {
      page: (currentPage - 1).toString(),
      itemsPerPage: "10",
      visibility: "public",
      specialistId: user.user?.id!,
    }).then((page) => {
      setTotalPages(page.totalPages);
      const exercises = page.items;
      const exerciseL: Exercise[] = [];
      exercises.map((ex: any) => {
        let exercise: Exercise = TranslateExerciseIN(ex);
        console.log(ex);
        console.log(exercise);
        exercise.identity.points = 1;
        exerciseL.push(exercise);
      });
      setListExercises(exerciseL);
    });
  }, [currentPage]);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max ">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-[#bbbbbb] dark:border-slate-600">
          <label className="flex text-4xl text-gray-600 dark:text-white">
            Exercícios
          </label>
          <button
            className="py-2 px-3 rounded-lg btn-base-color group"
            onClick={() => setSelectedMenu("")}
          >
            <IoClose className="size-5" />
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
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={onPageChange}
          showIcons
        />
      </div>
    </>
  );
}
