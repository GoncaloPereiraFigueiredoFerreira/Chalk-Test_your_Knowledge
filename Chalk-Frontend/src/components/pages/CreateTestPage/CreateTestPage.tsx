import { EditTestDragDrop } from "../../objects/Test/EditTestDragDrop";
import { ExerciseBankDragDrop } from "../../objects/Test/ExerciseBankDragDrop";
import { EditExercise } from "../../objects/EditExercise/EditExercise";
import { Searchbar } from "../../objects/Searchbar/Searchbar";
import { useReducer, useState } from "react";
import {
  EditTestActionKind,
  EditTestContext,
  EditTestState,
  EditTestStateReducer,
} from "../../objects/Test/EditTestContext";
import { InitTest, Test } from "../../objects/Test/Test";
import { EditGroup } from "../../objects/Test/EditGroup";
import { EditTestInfo } from "../../objects/Test/EditTestInfo";
import {
  DndContext,
  DragEndEvent,
  DragOverEvent,
  DragOverlay,
  DragStartEvent,
  KeyboardSensor,
  PointerSensor,
  closestCenter,
  useSensor,
  useSensors,
} from "@dnd-kit/core";
import { sortableKeyboardCoordinates } from "@dnd-kit/sortable";
import { ShowExerciseDragDrop } from "../../objects/Test/ShowExerciseDragDrop";
import { Exercise } from "../../objects/Exercise/Exercise";
import { GroupDragDrop } from "../../objects/Test/GroupDragDrop";

type EventInfo =
  | {
      type: "group";
      groupPosition: number;
    }
  | {
      type: "add" | "exercise";
      exercise: Exercise;
      exercisePosition: number;
      groupPosition: number;
    };

interface CreateTestProps {
  test?: Test;
}

export function CreateTest({ test }: CreateTestProps) {
  const inicialState: EditTestState = {
    test: test !== undefined ? test : InitTest(),
    exercisePosition: 0,
    groupPosition: 0,
  };
  const [testState, dispatch] = useReducer(EditTestStateReducer, inicialState);
  const [selectedMenu, setSelectedMenu] = useState("");
  const [selectedExercise, setSelectedExercise] = useState("");
  const [exerciseID, setExerciseID] = useState({
    // exercicio selecionado atualmente
    groupPosition: 0,
    exercisePosition: 0,
  });
  const [activeDnD, setActiveDnD] = useState<
    | {
        type: "add" | "exercise"; // exercise from ExerciseBankDragDrop
        exercise: Exercise;
        exercisePosition: number;
        added?: {
          exercisePosition: number;
          groupPosition: number;
        };
      }
    | {
        type: "group"; // exercise/group from EditTestDragDrop
        groupPosition: number;
        exerciseGroupID: string;
      }
    | null
  >(null);

  const sensors = useSensors(
    useSensor(PointerSensor, {
      activationConstraint: {
        distance: 10,
      },
    }),
    useSensor(KeyboardSensor, {
      coordinateGetter: sortableKeyboardCoordinates,
    })
  );

  function handleOnDragStart(event: DragStartEvent) {
    const info = event.active.data.current;
    if (info) {
      setExerciseID({
        groupPosition: exerciseID.groupPosition,
        exercisePosition: -1,
      });
      if (info.type === "add") {
        setSelectedExercise("");
        setActiveDnD({
          type: "add",
          exercise: info.exercise,
          exercisePosition: info.exercisePosition,
        });
      } else if (info && info.type === "exercise")
        setActiveDnD({
          type: "exercise",
          exercise: info.exercise,
          exercisePosition: info.exercisePosition,
        });
      else if (info && info.type === "group")
        setActiveDnD({
          type: "group",
          groupPosition: info.groupPosition,
          exerciseGroupID:
            typeof event.active.id === "string"
              ? event.active.id
              : event.active.id.toString(),
        });
    }
  }

  function handleOnDragOver(event: DragOverEvent) {
    const { active, over } = event;
    if (
      selectedMenu === "edit-exercise" ||
      selectedMenu === "create-exercise" ||
      selectedMenu === "edit-group" ||
      over === null ||
      over.data.current === undefined ||
      active.data.current === undefined
    )
      return;
    const activeID = active.id;
    let activeInfo = active.data.current as EventInfo;
    const overID = over.id;
    let overInfo = over.data.current as EventInfo;

    if (
      activeID !== overID &&
      // Moving a Group
      activeInfo.type !== "group"
    ) {
      console.log("activeInfo");
      console.log(activeInfo);
      console.log("overInfo");
      console.log(overInfo);

      // Cange Exercise order on ExerciseBank
      if (activeInfo.type === "add" && overInfo.type === "add") return;

      // Add an Exercise to the Test
      // Add the new Exercise to the Test => over is an Group
      // if (activeInfo.type === "add" && overInfo.type === "group") {
      //   if (
      //     activeDnD &&
      //     activeDnD.type === "add" &&
      //     activeDnD.added === undefined
      //   ) {
      //     // Add the new Exercise to the Test => over is an Group
      //     dispatch({
      //       type: EditTestActionKind.ADD_EXERCISE,
      //       exercise: {
      //         groupPosition: overInfo.groupPosition,
      //         exercisePosition: 0,
      //         exercise: activeInfo.exercise,
      //       },
      //     });
      //     setActiveDnD({
      //       ...activeDnD,
      //       added: {
      //         exercisePosition: 0,
      //         groupPosition: overInfo.groupPosition,
      //       },
      //     });
      //   } else if (activeDnD && activeDnD.type === "add" && activeDnD.added) {
      //     // Move the new Exercise to another Group
      //     dispatch({
      //       type: EditTestActionKind.MOVE_EXERCISE,
      //       exercise: {
      //         exercisePosition: activeDnD.added.exercisePosition,
      //         groupPosition: activeDnD.added.groupPosition,
      //         newPosition: {
      //           exercisePosition: 0,
      //           groupPosition: overInfo.groupPosition,
      //         },
      //       },
      //     });
      //   }
      // }
      // // Add the new Exercise to the Test => over is an Exercise
      // else if (activeInfo.type === "add" && overInfo.type === "exercise") {
      //   if (
      //     activeDnD &&
      //     activeDnD.type === "add" &&
      //     activeDnD.added === undefined
      //   ) {
      //     // Add the new Exercise to the Test => over is an Exercise
      //     dispatch({
      //       type: EditTestActionKind.ADD_EXERCISE,
      //       exercise: {
      //         groupPosition: overInfo.groupPosition,
      //         exercisePosition: overInfo.exercisePosition,
      //         exercise: activeInfo.exercise,
      //       },
      //     });
      //     setActiveDnD({
      //       ...activeDnD,
      //       added: {
      //         exercisePosition: overInfo.exercisePosition,
      //         groupPosition: overInfo.groupPosition,
      //       },
      //     });
      //   } else if (activeDnD && activeDnD.type === "add" && activeDnD.added) {
      //     // Move the new Exercise to another Group
      //     dispatch({
      //       type: EditTestActionKind.MOVE_EXERCISE,
      //       exercise: {
      //         exercisePosition: activeDnD.added.exercisePosition,
      //         groupPosition: activeDnD.added.groupPosition,
      //         newPosition: {
      //           exercisePosition: overInfo.exercisePosition,
      //           groupPosition: overInfo.groupPosition,
      //         },
      //       },
      //     });
      //   }
      // }

      // Moving an Exercise to another Group => over is an Exercise
      if (
        activeInfo.type === "exercise" &&
        overInfo.type === "exercise" &&
        activeInfo.groupPosition !== overInfo.groupPosition
      ) {
        console.log({
          type: EditTestActionKind.MOVE_EXERCISE,
          exercise: {
            exercisePosition: activeInfo.exercisePosition,
            groupPosition: activeInfo.groupPosition,
            newPosition: {
              exercisePosition: overInfo.exercisePosition,
              groupPosition: overInfo.groupPosition,
            },
          },
        });

        dispatch({
          type: EditTestActionKind.MOVE_EXERCISE,
          exercise: {
            exercisePosition: activeInfo.exercisePosition,
            groupPosition: activeInfo.groupPosition,
            newPosition: {
              exercisePosition: overInfo.exercisePosition,
              groupPosition: overInfo.groupPosition,
            },
          },
        });
      }

      // Moving an Exercise to another Group => over is a Group
      if (
        activeInfo.type === "exercise" &&
        overInfo.type === "group" &&
        activeInfo.groupPosition !== overInfo.groupPosition
      ) {
        dispatch({
          type: EditTestActionKind.MOVE_EXERCISE,
          exercise: {
            exercisePosition: activeInfo.exercisePosition,
            groupPosition: activeInfo.groupPosition,
            newPosition: {
              exercisePosition: 0,
              groupPosition: overInfo.groupPosition,
            },
          },
        });
      }

      // Moving an Exercise to the ExerciseBank => Delete Exercise
      if (
        activeInfo.type === "exercise" &&
        overInfo.type === "add" &&
        activeInfo.groupPosition !== overInfo.groupPosition
      ) {
        dispatch({
          type: EditTestActionKind.REMOVE_EXERCISE,
          exercise: {
            exercisePosition: activeInfo.exercisePosition,
            groupPosition: activeInfo.groupPosition,
          },
        });
      }
    }
  }

  function handleOnDragEnd(event: DragEndEvent) {
    setActiveDnD(null);
    const { active, over } = event;
    if (
      selectedMenu === "edit-exercise" ||
      selectedMenu === "create-exercise" ||
      selectedMenu === "edit-group" ||
      over === null ||
      over.data.current === undefined ||
      active.data.current === undefined
    )
      return;

    const activeID = active.id;
    let activeInfo = active.data.current as EventInfo;

    const overID = over.id;
    let overInfo = over.data.current as EventInfo;

    if (activeID !== overID) {
      if (activeInfo.type === "group" && overInfo.type === "group") {
        // Moving a Group to another Position
        dispatch({
          type: EditTestActionKind.MOVE_GROUP,
          group: {
            groupPosition: activeInfo.groupPosition,
            newPosition: overInfo.groupPosition,
          },
        });
      } else if (activeInfo.type === "exercise") {
      }
    }
  }

  return (
    <EditTestContext.Provider value={{ testState, dispatch }}>
      <DndContext
        sensors={sensors}
        collisionDetection={closestCenter}
        onDragStart={handleOnDragStart}
        onDragOver={handleOnDragOver}
        onDragEnd={handleOnDragEnd}
      >
        <div
          className={`${
            selectedMenu === "" ? "" : "divide-x-2"
          } flex flex-row border-gray-2-2`}
        >
          <div
            className={`${
              selectedMenu === "dd-list-exercises" ? "w-full" : "w-0"
            } flex flex-col transition-[width] h-screen overflow-y-auto bg-2-1`}
          >
            {selectedMenu === "dd-list-exercises" && (
              <>
                <Searchbar />
                <ExerciseBankDragDrop
                  exerciseID={exerciseID}
                  setExerciseID={(value) => setExerciseID(value)}
                  setSelectedMenu={(value) => setSelectedMenu(value)}
                  selectedExercise={selectedExercise}
                  setSelectedExercise={(value) => setSelectedExercise(value)}
                ></ExerciseBankDragDrop>
              </>
            )}
          </div>
          <div className="flex flex-col w-full h-screen overflow-y-auto bg-2-1">
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
              selectedMenu === "create-exercise" ||
              selectedMenu === "edit-group" ||
              selectedMenu === "edit-test-info"
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
                      type: EditTestActionKind.EDIT_EXERCISE,
                      exercise: {
                        groupPosition: testState.groupPosition,
                        exercisePosition: testState.exercisePosition,
                        exercise: {
                          ...state.exercise,
                          identity: {
                            ...state.exercise.identity,
                            id: "novo id 1000",
                            visibility:
                              state.exercise.identity?.visibility ?? "",
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
                      type: EditTestActionKind.EDIT_EXERCISE,
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
                      type: EditTestActionKind.REMOVE_EXERCISE,
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
                  testState.test.groups[testState.groupPosition]
                    .groupInstructions
                }
                saveEdit={(state) => {
                  dispatch({
                    type: EditTestActionKind.EDIT_GROUP,
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
            {selectedMenu === "edit-test-info" ? (
              <EditTestInfo
                testInfo={{
                  type: testState.test.type,
                  conclusion: testState.test.conclusion,
                  title: testState.test.title,
                  globalInstructions: testState.test.globalInstructions,
                }}
                saveEdit={(state) => {
                  dispatch({
                    type: EditTestActionKind.EDIT_TEST_INFO,
                    testInfo: state,
                  });
                  setSelectedMenu("");
                }}
                cancelEdit={() => {
                  setSelectedMenu("");
                }}
              ></EditTestInfo>
            ) : null}
          </div>
        </div>
        <DragOverlay>
          {activeDnD && activeDnD.type === "add" && (
            <div className="shadow-2xl border-2 border-gray-300 rounded-lg">
              <ShowExerciseDragDrop
                listExerciseButtons={true}
                exercise={activeDnD.exercise}
                exerciseIsSelected={false}
                groupPosition={exerciseID.groupPosition}
                selectedMenu={selectedMenu}
                setSelectedMenu={(value) => setSelectedMenu(value)}
                setSelectedExercise={(value) => setSelectedExercise(value)}
                exercisePosition={activeDnD.exercisePosition}
                setExerciseID={setExerciseID}
                isBeeingDragged
              ></ShowExerciseDragDrop>
            </div>
          )}
          {activeDnD && activeDnD.type === "exercise" && (
            <div className="shadow-2xl border-2 border-gray-300 rounded-lg">
              <ShowExerciseDragDrop
                listExerciseButtons={false}
                exercise={activeDnD.exercise}
                exerciseIsSelected={false}
                groupPosition={exerciseID.groupPosition}
                selectedMenu={selectedMenu}
                setSelectedMenu={(value) => setSelectedMenu(value)}
                setSelectedExercise={(value) => setSelectedExercise(value)}
                exercisePosition={activeDnD.exercisePosition}
                setExerciseID={setExerciseID}
                isBeeingDragged
              ></ShowExerciseDragDrop>
            </div>
          )}
          {activeDnD && activeDnD.type === "group" && (
            <div className="shadow-2xl border-2 border-gray-300 rounded-lg">
              <GroupDragDrop
                exerciseGroupPosition={activeDnD.groupPosition}
                exerciseGroupID={activeDnD.exerciseGroupID}
                exerciseID={exerciseID}
                setExerciseID={setExerciseID}
                selectedMenu={selectedMenu}
                setSelectedMenu={setSelectedMenu}
                setNewExercisePopUp={() => {}}
                draggingGroups={true}
                setDraggingGroups={() => {}}
              ></GroupDragDrop>
            </div>
          )}
        </DragOverlay>
      </DndContext>
    </EditTestContext.Provider>
  );
}
