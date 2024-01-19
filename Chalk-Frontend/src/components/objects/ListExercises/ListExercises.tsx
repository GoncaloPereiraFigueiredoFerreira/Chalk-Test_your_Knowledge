import { ShowExercise } from "./ShowExercise";
import "./ListExercises.css";
import { useContext, useEffect, useState } from "react";
import { CreateNewExercisePopUp } from "./CreateNewExercisePopUp";
import {
  Exercise,
  ExerciseType,
  TranslateExerciseIN,
} from "../Exercise/Exercise";
import {
  ListExerciseActionKind,
  useListExerciseContext,
} from "./ListExerciseContext";
import { APIContext } from "../../../APIContext";
import { ChangeVisibility } from "./ChangeVisibilityExercisePopUp";
import { Pagination } from "flowbite-react";

interface ListExercisesProps {
  setExerciseID: (value: string) => void;
  editMenuIsOpen: boolean;
  setEditMenuIsOpen: (value: boolean) => void;
}

export function ListExercises({
  setExerciseID,
  editMenuIsOpen,
  setEditMenuIsOpen,
}: ListExercisesProps) {
  const [currentPage, setCurrentPage] = useState(1);
  const onPageChange = (page: number) => setCurrentPage(page);
  const [newExercisePopUp, setNewExercisePopUp] = useState(false);
  const [changeVisibilityPopUp, setChangeVisibilityPopUp] = useState("");
  const { listExerciseState, dispatch } = useListExerciseContext();
  const { contactBACK } = useContext(APIContext);

  useEffect(() => {
    contactBACK("exercises", "GET", {
      page: (currentPage - 1).toString(),
      itemsPerPage: "10",
      visibility: "public",
    }).then((response) => {
      response.json().then((exercises) => {
        const exerciseL: Exercise[] = [];
        exercises.map((ex: any) => {
          exerciseL.push(TranslateExerciseIN(ex));
        });
        dispatch({
          type: ListExerciseActionKind.SET_LIST_EXERCISES,
          payload: {
            exercises: exerciseL,
          },
        });
      });
    });
  }, [currentPage]);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max bg-white dark:bg-black ">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
          <label className="flex text-4xl text-gray-600 dark:text-white">
            Exercícios
          </label>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black"
            onClick={() => {
              setNewExercisePopUp(true);
              setEditMenuIsOpen(false);
            }}
          >
            Criar exercício
          </button>
        </div>
        {listExerciseState.listExercises.hasOwnProperty("-1") ? (
          <ShowExercise
            position={"1"}
            exercise={listExerciseState.listExercises["-1"]}
            setExerciseID={setExerciseID}
            editMenuIsOpen={editMenuIsOpen}
            setEditMenuIsOpen={setEditMenuIsOpen}
            selectedExercise={"-1" === listExerciseState.selectedExercise}
            setSelectedExercise={(value) =>
              dispatch({
                type: ListExerciseActionKind.SET_SELECTED_EXERCISE,
                payload: {
                  selectedExercise: value,
                },
              })
            }
            changeVisibilityPopUp=""
            setChangeVisibilityPopUp={(value) =>
              setChangeVisibilityPopUp(value)
            }
          ></ShowExercise>
        ) : null}
        {Object.keys(listExerciseState.listExercises).map((key, index) =>
          key === "-1" ? null : (
            <ShowExercise
              key={index}
              position={(index + 1).toString()}
              exercise={listExerciseState.listExercises[key]}
              setExerciseID={setExerciseID}
              editMenuIsOpen={editMenuIsOpen}
              setEditMenuIsOpen={setEditMenuIsOpen}
              selectedExercise={key === listExerciseState.selectedExercise}
              setSelectedExercise={(value) =>
                dispatch({
                  type: ListExerciseActionKind.SET_SELECTED_EXERCISE,
                  payload: {
                    selectedExercise: value,
                  },
                })
              }
              changeVisibilityPopUp=""
              setChangeVisibilityPopUp={(value) =>
                setChangeVisibilityPopUp(value)
              }
            ></ShowExercise>
          )
        )}
        <div className="w-full flex justify-center">
          <Pagination
            currentPage={currentPage}
            totalPages={30}
            onPageChange={onPageChange}
            showIcons
          />
        </div>
      </div>
      <ChangeVisibility
        show={changeVisibilityPopUp !== ""}
        closePopUp={() => setChangeVisibilityPopUp("")}
        changeVisibility={(newVisibility: string) => {
          console.log(changeVisibilityPopUp);
          dispatch({
            type: ListExerciseActionKind.CHANGE_VISIBILITY_EXERCISE,
            payload: {
              selectedExercise: changeVisibilityPopUp,
              visibility: newVisibility,
            },
          });
          setChangeVisibilityPopUp("");
        }}
      />
      <CreateNewExercisePopUp
        show={newExercisePopUp}
        closePopUp={() => setNewExercisePopUp(false)}
        createNewExercise={(newExerciseType: ExerciseType) => {
          if (!editMenuIsOpen) {
            dispatch({
              type: ListExerciseActionKind.CREATE_NEW_EXERCISE,
              payload: {
                newExerciseType: newExerciseType,
              },
            });
            setEditMenuIsOpen(true);
            setExerciseID("-1");
          }
          setNewExercisePopUp(false);
        }}
      />
    </>
  );
}
