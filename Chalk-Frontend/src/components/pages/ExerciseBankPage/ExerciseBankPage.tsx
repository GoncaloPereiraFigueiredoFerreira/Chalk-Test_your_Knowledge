import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { EditExercise } from "../../objects/EditExercise/EditExercise";
import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { useContext, useReducer, useState } from "react";
import {
  ListExerciseActionKind,
  ListExerciseContext,
  ListExerciseStateReducer,
} from "../../objects/ListExercises/ListExerciseContext";
import { APIContext } from "../../../APIContext";
import {
  TranslateExerciseIN,
  TranslateExerciseOUT,
} from "../../objects/Exercise/Exercise";

export function ExerciseBankPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(false);
  const [exerciseID, setExerciseID] = useState("");
  const { contactBACK } = useContext(APIContext);

  const inicialState = {
    listExercises: {},
    selectedExercise: "",
  };

  const [listExerciseState, dispatch] = useReducer(
    ListExerciseStateReducer,
    inicialState
  );

  return (
    <ListExerciseContext.Provider value={{ listExerciseState, dispatch }}>
      <div className="flex flex-row divide-x-2 border-gray-2-2">
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
          <Searchbar></Searchbar>
          <ListExercises
            setExerciseID={(value) => setExerciseID(value)}
            editMenuIsOpen={editMenuIsOpen}
            setEditMenuIsOpen={(value) => setEditMenuIsOpen(value)}
          ></ListExercises>
        </div>
        <div
          className={`${
            editMenuIsOpen ? "w-full" : "w-0"
          } flex flex-col h-screen overflow-auto bg-2-1 transition-[width]`}
        >
          {editMenuIsOpen ? (
            <EditExercise
              exercise={listExerciseState.listExercises[exerciseID]}
              saveEdit={(state) => {
                const { exerciseTR, solutionTR } = TranslateExerciseOUT(
                  state.exercise
                );
                if (exerciseID === "-1") {
                  contactBACK("exercises", "POST", undefined, {
                    exercise: exerciseTR,
                    solution: solutionTR,
                    //falta a rubrica
                    tags: ["Português"],
                  }).then((response) => {
                    response.text().then((jsonRes) => {
                      dispatch({
                        type: ListExerciseActionKind.ADD_EXERCISE,
                        payload: {
                          exercise: {
                            ...state.exercise,
                            identity: {
                              ...state.exercise.identity,
                              id: jsonRes,
                              visibility:
                                state.exercise.identity?.visibility ?? "",
                              specialistId:
                                state.exercise.identity?.specialistId ?? "",
                            },
                          },
                        },
                      });
                    });
                  });
                } else {
                  contactBACK("exercises/" + exerciseID, "PUT", undefined, {
                    exercise: exerciseTR,
                    solution: solutionTR,
                    //falta a rubrica
                  }).then((response) => {
                    dispatch({
                      type: ListExerciseActionKind.EDIT_EXERCISE,
                      payload: { exercise: state.exercise },
                    });
                  });
                }
                setExerciseID("");
                setEditMenuIsOpen(false);
              }}
              cancelEdit={() => {
                if (exerciseID === "-1")
                  dispatch({
                    type: ListExerciseActionKind.REMOVE_EXERCISE,
                    payload: { selectedExercise: exerciseID },
                  });
                setExerciseID("");
                setEditMenuIsOpen(false);
              }}
            ></EditExercise>
          ) : null}
        </div>
      </div>
    </ListExerciseContext.Provider>
  );
}
