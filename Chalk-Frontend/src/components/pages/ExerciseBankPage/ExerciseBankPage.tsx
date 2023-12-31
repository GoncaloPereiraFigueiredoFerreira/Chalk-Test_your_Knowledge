import { ListExercises } from "../../objects/ListExercises/ListExercises";
import { EditExercise } from "../../objects/EditExercise/EditExercise";
import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { useReducer, useState } from "react";
import {
  ListExerciseActionKind,
  ListExerciseContext,
  ListExerciseStateReducer,
} from "../../objects/ListExercises/ListExerciseContext";

export function ExerciseBankPage() {
  const [editMenuIsOpen, setEditMenuIsOpen] = useState(false);
  const [exerciseID, setExerciseID] = useState("");

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
              saveExercise={(state) => {
                if (exerciseID === "-1") {
                  // <<< ALTERAR ESTE IF >>>
                  // SOLUCAO TEMPORARIa ENQUANTO NAO EXISTE LIGAÇÂO AO BACKEND
                  // PARA SE SABER O ID DO NOVO EXERCICIO
                  dispatch({
                    type: ListExerciseActionKind.ADD_EXERCISE,
                    payload: {
                      exercise: {
                        ...state.exercise,
                        identity: {
                          ...state.exercise.identity,
                          id: "novo id 1000",
                          visibility: state.exercise.identity?.visibility ?? "",
                          specialistId:
                            state.exercise.identity?.specialistId ?? "",
                        },
                      },
                    },
                  });
                  // <<< ALTERAR ESTE IF (final)>>>
                } else {
                  // <<< MANTER >>>
                  dispatch({
                    type: ListExerciseActionKind.EDIT_EXERCISE,
                    payload: { exercise: state.exercise },
                  });
                  // <<< MANTER (final)>>>
                }
                setExerciseID("");
                setEditMenuIsOpen(false);
              }}
              cancelEditExercise={() => {
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
