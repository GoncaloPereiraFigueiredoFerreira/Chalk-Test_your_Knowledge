import { EditTestDragDrop } from "../../objects/Test/EditTestDragDrop";
import { ExerciseBankDragDrop } from "../../objects/Test/ExerciseBankDragDrop";
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
import { EditGroup } from "../../objects/Test/EditGroup";

interface CreateTestProps {
  test?: Test;
}

export function CreateTest({ test }: CreateTestProps) {
  const [selectedMenu, setSelectedMenu] = useState("");
  const [exerciseID, setExerciseID] = useState({
    // exercicio selecionado atualmente
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
      <div
        className={`${
          selectedMenu === "" ? "" : "divide-x-2"
        } flex flex-row border-gray-2-2`}
      >
        <div
          className={`${
            selectedMenu === "dd-list-exercises" ? "w-full" : "w-0"
          } flex flex-col transition-[width] h-screen overflow-auto bg-2-1`}
        >
          {selectedMenu === "dd-list-exercises" ? (
            <>
              <Searchbar></Searchbar>
              <ExerciseBankDragDrop
                exerciseID={exerciseID}
                setExerciseID={(value) => setExerciseID(value)}
                setSelectedMenu={(value) => setSelectedMenu(value)}
              ></ExerciseBankDragDrop>
            </>
          ) : null}
        </div>
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1">
          <EditTestDragDrop
            exerciseID={exerciseID}
            setExerciseID={(value) => setExerciseID(value)}
            selectedMenu={selectedMenu}
            setSelectedMenu={(value) => setSelectedMenu(value)}
          ></EditTestDragDrop>
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
              position={(testState.exercisePosition + 1).toString()}
              exercise={
                testState.test.groups[testState.groupPosition].exercises[
                  testState.exercisePosition
                ]
              }
              saveEdit={(state) => {
                if (selectedMenu === "create-exercise") {
                  // <<< ALTERAR ESTE IF >>>
                  // SOLUCAO TEMPORARIa ENQUANTO NAO EXISTE LIGAÇÂO AO BACKEND
                  // PARA SE SABER O ID DO NOVO EXERCICIO
                  dispatch({
                    type: CreateTestActionKind.EDIT_EXERCISE,
                    exercise: {
                      groupPosition: testState.groupPosition,
                      exercisePosition: testState.exercisePosition,
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
                      groupPosition: testState.groupPosition,
                      exercisePosition: testState.exercisePosition,
                      exercise: state.exercise,
                    },
                  });
                  // <<< MANTER (final)>>>
                }
                setSelectedMenu("");
              }}
              cancelEdit={() => {
                if (selectedMenu === "create-exercise")
                  dispatch({
                    type: CreateTestActionKind.REMOVE_EXERCISE,
                    exercise: {
                      groupPosition: testState.groupPosition,
                      exercisePosition: testState.exercisePosition,
                    },
                  });
                setSelectedMenu("");
              }}
            ></EditExercise>
          ) : null}
          {selectedMenu === "edit-group" ? (
            <EditGroup
              exerciseInstructions={
                testState.test.groups[testState.groupPosition].groupInstructions
              }
              saveEdit={(state) => {
                dispatch({
                  type: CreateTestActionKind.EDIT_GROUP,
                  group: {
                    groupPosition: testState.groupPosition,
                    groupInstructions: state,
                  },
                });
                setSelectedMenu("");
              }}
              cancelEdit={() => {
                setSelectedMenu("");
              }}
            ></EditGroup>
          ) : null}
        </div>
      </div>
    </CreateTestContext.Provider>
  );
}
