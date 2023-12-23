import { EditTestView } from "../../objects/Test/EditTestView";
import { DragDropListExercises } from "../../objects/Test/DragDropListExercises";
import { EditExercise } from "../../objects/EditExercise/EditExercise";
import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { useReducer, useState } from "react";
import {
  CreateTestActionKind,
  CreateTestContext,
  CreateTestState,
  CreateTestStateReducer,
} from "../../objects/Test/CreateTestContext";
import { InitTest, Test } from "../../objects/Test/Test";

interface CreateTestProps {
  test?: Test;
}

export function CreateTest({ test }: CreateTestProps) {
  const [selectedMenu, setSelectedMenu] = useState("");
  const [exerciseID, setExerciseID] = useState({
    groupPosition: 0,
    exercisePosition: 0,
  });

  const inicialState: CreateTestState = {
    test: test !== undefined ? test : InitTest(),
    exercisePosition: 0,
    groupPosition: 0,
  };
  const [testState, dispatch] = useReducer(
    CreateTestStateReducer,
    inicialState
  );

  return (
    <CreateTestContext.Provider value={{ testState, dispatch }}>
      <div className="flex flex-row divide-x-2 border-gray-2-2">
        <div
          className={`${
            selectedMenu === "dd-list-exercises" ? "w-full" : "w-0"
          } flex flex-col transition-[width] h-screen overflow-auto bg-2-1`}
        >
          {selectedMenu === "dd-list-exercises" ? (
            <>
              <Searchbar></Searchbar>
              <DragDropListExercises
                setSelectedMenu={(value) => setSelectedMenu(value)}
              ></DragDropListExercises>
            </>
          ) : null}
        </div>
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
          <EditTestView
            setExerciseID={(value) => setExerciseID(value)}
            selectedMenu={selectedMenu}
            setSelectedMenu={(value) => setSelectedMenu(value)}
          ></EditTestView>
        </div>
        <div
          className={`${
            selectedMenu === "edit-exercise" ||
            selectedMenu === "create-exercise"
              ? "w-full"
              : "w-0"
          } flex flex-col h-screen overflow-auto bg-2-1 transition-[width]`}
        >
          {selectedMenu === "edit-exercise" ||
          selectedMenu === "create-exercise" ? (
            <EditExercise
              exercise={
                testState.test.groups[exerciseID.groupPosition].exercises[
                  exerciseID.exercisePosition
                ]
              }
              saveExercise={(state) => {
                if (selectedMenu === "create-exercise") {
                  // <<< ALTERAR ESTE IF >>>
                  // SOLUCAO TEMPORARIa ENQUANTO NAO EXISTE LIGAÇÂO AO BACKEND
                  // PARA SE SABER O ID DO NOVO EXERCICIO
                  dispatch({
                    type: CreateTestActionKind.EDIT_EXERCISE,
                    exercise: {
                      groupPosition: exerciseID.groupPosition,
                      exercisePosition: exerciseID.exercisePosition,
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
                    type: CreateTestActionKind.EDIT_EXERCISE,
                    exercise: {
                      groupPosition: exerciseID.groupPosition,
                      exercisePosition: exerciseID.exercisePosition,
                      exercise: state.exercise,
                    },
                  });
                  // <<< MANTER (final)>>>
                }
                setSelectedMenu("");
              }}
              cancelEditExercise={(state) => {
                if (selectedMenu === "create-exercise")
                  dispatch({
                    type: CreateTestActionKind.REMOVE_EXERCISE,
                    exercise: {
                      groupPosition: exerciseID.groupPosition,
                      exercisePosition: exerciseID.exercisePosition,
                      exerciseID: state.exercise.identity.id,
                    },
                  });
                setExerciseID({
                  groupPosition: 0,
                  exercisePosition: 0,
                });
                setSelectedMenu("");
              }}
            ></EditExercise>
          ) : null}
        </div>
      </div>
    </CreateTestContext.Provider>
  );
}
