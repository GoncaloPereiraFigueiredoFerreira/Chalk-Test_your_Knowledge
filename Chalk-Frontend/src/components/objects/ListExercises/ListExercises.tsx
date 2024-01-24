import { ShowExercise } from "./ShowExercise";
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
  tagsList: any;
}

export function ListExercises({
  setExerciseID,
  editMenuIsOpen,
  setEditMenuIsOpen,
  tagsList,
}: ListExercisesProps) {
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const onPageChange = (page: number) => setCurrentPage(page);
  const [newExercisePopUp, setNewExercisePopUp] = useState(false);
  const [changeVisibilityPopUp, setChangeVisibilityPopUp] = useState("");
  const { listExerciseState, dispatch } = useListExerciseContext();
  const { contactBACK } = useContext(APIContext);

  useEffect(() => {
    let requestTags: any = tagsList.map((tag: any) => tag.id);
    if (requestTags.length == 0) requestTags = "";

    contactBACK("exercises", "GET", {
      page: (currentPage - 1).toString(),
      itemsPerPage: "10",
      visibility: "public",
      tags: requestTags,
    }).then((page) => {
      const exercises = page.items;
      setTotalPages(page.totalPages==0?1:page.totalPages);
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
  }, [currentPage, tagsList]);

  const deleteEx = (id: string) => {
    contactBACK("exercises/" + id, "DELETE", undefined, undefined, "none").then(
      () => {
        dispatch({
          type: ListExerciseActionKind.REMOVE_EXERCISE,
          payload: {
            selectedExercise: id,
          },
        });
      }
    );
  };

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-slate-400 dark:border-slate-600">
          <label className="flex text-4xl text-slate-600 dark:text-white">
            Exercícios
          </label>
          <button
            className="py-2 px-4 text-base rounded-lg font-medium btn-base-color active:scale-90"
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
            deleteEx={() => {}}
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
              deleteEx={() => deleteEx(key)}
            ></ShowExercise>
          )
        )}
        <div className="w-full flex justify-center">
          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={onPageChange}
            showIcons
          />
        </div>
      </div>
      <ChangeVisibility
        show={changeVisibilityPopUp !== ""}
        closePopUp={() => setChangeVisibilityPopUp("")}
        changeVisibility={(newVisibility: string) => {
          contactBACK(
            "exercises/" + changeVisibilityPopUp + "/visibility",
            "PUT",
            undefined,
            newVisibility,
            "none"
          ).then(() => {
            dispatch({
              type: ListExerciseActionKind.CHANGE_VISIBILITY_EXERCISE,
              payload: {
                selectedExercise: changeVisibilityPopUp,
                visibility: newVisibility,
              },
            });
            setChangeVisibilityPopUp("");
          });
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
