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
  closestCorners,
  useSensor,
  useSensors,
} from "@dnd-kit/core";
import { sortableKeyboardCoordinates } from "@dnd-kit/sortable";
import { ShowExerciseDragDrop } from "../../objects/Test/ShowExerciseDragDrop";
import { Exercise } from "../../objects/Exercise/Exercise";
import { GroupDragDrop } from "../../objects/Test/GroupDragDrop";
import { HiOutlineTrash } from "react-icons/hi";

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

interface ActiveAdd {
  type: "add"; // exercise from ExerciseBankDragDrop
  exercise: Exercise;
  exercisePosition: number;
  added:
    | false
    | {
        exercisePosition: number;
        groupPosition: number;
      };
}
interface ActiveExercise {
  type: "exercise"; // exercise from EditTestDragDrop
  exercise: Exercise;
  exercisePosition: number;
  onGroup: {
    exercisePosition: number;
    groupPosition: number;
  };
}
interface ActiveGroup {
  type: "group"; // group from EditTestDragDrop
  groupPosition: number;
  exerciseGroupID: string;
}

interface CreateTestProps {
  test?: Test;
}

export function CreateTest({ test }: CreateTestProps) {
  const [draggingExercises, setDraggingExercises] = useState(false);
  const inicialState: EditTestState = {
    test: test !== undefined ? test : InitTest(),
    exercisePosition: 0,
    groupPosition: 0,
  };
  const [listExercises, setListExercises] = useState<Exercise[]>([]);
  const [testState, dispatch] = useReducer(EditTestStateReducer, inicialState);
  const [selectedMenu, setSelectedMenu] = useState("");
  const [selectedExercise, setSelectedExercise] = useState(-1);
  const [exerciseID, setExerciseID] = useState({
    // exercicio selecionado atualmente
    groupPosition: 0,
    exercisePosition: 0,
  });
  const [activeDnD, setActiveDnD] = useState<
    ActiveAdd | ActiveExercise | ActiveGroup | null
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
      setDraggingExercises(true);
      if (info.type === "add") {
        setSelectedExercise(-1);
        setActiveDnD({
          type: "add",
          exercise: info.exercise,
          exercisePosition: info.exercisePosition,
          added: false,
        });
      } else if (info && info.type === "exercise")
        setActiveDnD({
          type: "exercise",
          exercise: info.exercise,
          exercisePosition: info.exercisePosition,
          onGroup: {
            exercisePosition: info.exercisePosition,
            groupPosition: info.groupPosition,
          },
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

    // Moving a Group
    if (
      activeInfo.type === "group" ||
      activeDnD === null ||
      (activeDnD && activeDnD.type === "group")
    )
      return;

    if (activeID !== overID) {
      // Add an Exercise to the Test
      if (activeDnD.type === "add") {
        // Add the new Exercise to the Test => over is an Group
        if (overInfo.type === "group") {
          if (activeDnD.added) {
            // Move the new Exercise to another Group
            let insertPosition = 0;
            if (activeDnD.added.groupPosition > overInfo.groupPosition)
              insertPosition =
                testState.test.groups[overInfo.groupPosition].exercises.length;
            dispatch({
              type: EditTestActionKind.MOVE_EXERCISE,
              exercise: {
                exercisePosition: activeDnD.added.exercisePosition,
                groupPosition: activeDnD.added.groupPosition,
                newPosition: {
                  exercisePosition: insertPosition,
                  groupPosition: overInfo.groupPosition,
                },
              },
            });
            setActiveDnD({
              ...activeDnD,
              added: {
                exercisePosition: insertPosition,
                groupPosition: overInfo.groupPosition,
              },
            });
          } else {
            // Add the new Exercise to the Test => over is an Group
            let newListExercises: Exercise[] = JSON.parse(
              JSON.stringify(listExercises)
            );
            newListExercises.splice(activeDnD.exercisePosition, 1);
            setListExercises(newListExercises);
            dispatch({
              type: EditTestActionKind.ADD_NEW_EXERCISE,
              exercise: {
                groupPosition: overInfo.groupPosition,
                exercisePosition: 0,
                exercise: activeInfo.exercise,
                tmp: true,
              },
            });
            setActiveDnD({
              ...activeDnD,
              added: {
                exercisePosition: 0,
                groupPosition: overInfo.groupPosition,
              },
            });
          }
        }
        // Add the new Exercise to the Test => over is an Exercise
        else if (overInfo.type === "exercise") {
          if (activeDnD.added) {
            // Move the new Exercise to another Group
            let insertPosition = 0;
            if (activeDnD.added.groupPosition > overInfo.groupPosition)
              insertPosition =
                testState.test.groups[overInfo.groupPosition].exercises.length;
            dispatch({
              type: EditTestActionKind.MOVE_EXERCISE,
              exercise: {
                exercisePosition: activeDnD.added.exercisePosition,
                groupPosition: activeDnD.added.groupPosition,
                newPosition: {
                  exercisePosition: insertPosition,
                  groupPosition: overInfo.groupPosition,
                },
              },
            });
            setActiveDnD({
              ...activeDnD,
              added: {
                exercisePosition: insertPosition,
                groupPosition: overInfo.groupPosition,
              },
            });
          } else {
            // Add the new Exercise to the Test => over is an Exercise
            let newListExercises: Exercise[] = JSON.parse(
              JSON.stringify(listExercises)
            );
            newListExercises.splice(activeDnD.exercisePosition, 1);
            setListExercises(newListExercises);
            dispatch({
              type: EditTestActionKind.ADD_NEW_EXERCISE,
              exercise: {
                groupPosition: overInfo.groupPosition,
                exercisePosition: overInfo.exercisePosition,
                exercise: activeInfo.exercise,
                tmp: true,
              },
            });
            setActiveDnD({
              ...activeDnD,
              added: {
                exercisePosition: overInfo.exercisePosition,
                groupPosition: overInfo.groupPosition,
              },
            });
          }
        }
        // Add the new Exercise to the Test => over is an Exercise
        else if (overInfo.type === "add" && activeDnD.added) {
          // Add the new Exercise to the Test => over is an Exercise
          let newListExercises: Exercise[] = JSON.parse(
            JSON.stringify(listExercises)
          );
          newListExercises.splice(
            activeDnD.exercisePosition,
            0,
            activeDnD.exercise
          );
          setListExercises(newListExercises);
          dispatch({
            type: EditTestActionKind.REMOVE_EXERCISE,
            exercise: {
              groupPosition: activeDnD.added.groupPosition,
              exercisePosition: activeDnD.added.exercisePosition,
            },
          });
          setActiveDnD({
            ...activeDnD,
            added: false,
          });
        }
      } else {
        // Moving an Exercise to another Group => over is an Exercise
        if (
          activeInfo.type === "exercise" &&
          overInfo.type === "exercise" &&
          activeInfo.groupPosition !== overInfo.groupPosition &&
          activeDnD
        ) {
          let insertPosition = 0;
          if (activeDnD.onGroup.groupPosition > overInfo.groupPosition)
            insertPosition =
              testState.test.groups[overInfo.groupPosition].exercises.length;
          setActiveDnD({
            ...(activeDnD as ActiveExercise),
            onGroup: {
              exercisePosition: insertPosition,
              groupPosition: overInfo.groupPosition,
            },
          });
          dispatch({
            type: EditTestActionKind.MOVE_EXERCISE,
            exercise: {
              exercisePosition: activeInfo.exercisePosition,
              groupPosition: activeInfo.groupPosition,
              newPosition: {
                exercisePosition: insertPosition,
                groupPosition: overInfo.groupPosition,
              },
            },
          });
        }

        // Moving an Exercise to another Group => over is a Group
        if (
          activeInfo.type === "exercise" &&
          overInfo.type === "group" &&
          activeInfo.groupPosition !== overInfo.groupPosition &&
          activeDnD
        ) {
          let insertPosition = 0;
          if (activeDnD.onGroup.groupPosition > overInfo.groupPosition)
            insertPosition =
              testState.test.groups[overInfo.groupPosition].exercises.length;
          setActiveDnD({
            ...(activeDnD as ActiveExercise),
            onGroup: {
              exercisePosition: insertPosition,
              groupPosition: overInfo.groupPosition,
            },
          });
          dispatch({
            type: EditTestActionKind.MOVE_EXERCISE,
            exercise: {
              exercisePosition: activeInfo.exercisePosition,
              groupPosition: activeInfo.groupPosition,
              newPosition: {
                exercisePosition: insertPosition,
                groupPosition: overInfo.groupPosition,
              },
            },
          });
        }
      }
    }
  }

  function handleOnDragEnd(event: DragEndEvent) {
    const { active, over } = event;

    if (
      selectedMenu === "edit-exercise" ||
      selectedMenu === "create-exercise" ||
      selectedMenu === "edit-group" ||
      over === null ||
      over.data.current === undefined ||
      active.data.current === undefined
    ) {
      setDraggingExercises(false);
      setActiveDnD(null);
      return;
    }

    const activeID = active.id;
    let activeInfo = active.data.current as EventInfo;

    const overID = over.id;
    let overInfo = over.data.current as EventInfo;

    // save exercise
    if (activeDnD && activeDnD.type === "add" && activeDnD.added) {
      if (overInfo.type !== "add") {
        dispatch({
          type: EditTestActionKind.SAVE_DD_NEW_EXERCISE,
          exercise: {
            exercisePosition: activeDnD.added.exercisePosition,
            groupPosition: activeDnD.added.groupPosition,
            newPosition: {
              exercisePosition:
                overInfo.type === "exercise" ? overInfo.exercisePosition : 0,
              groupPosition: activeDnD.added.groupPosition,
            },
          },
        });
        let newListExercises: Exercise[] = JSON.parse(
          JSON.stringify(listExercises)
        );
        let replaceExercise: Exercise = JSON.parse(
          JSON.stringify(activeDnD.exercise)
        );
        replaceExercise.identity.id = "new-exercise-".concat(
          Math.random().toString()
        );
        newListExercises.splice(activeDnD.exercisePosition, 0, replaceExercise);
        setListExercises(newListExercises);
        setActiveDnD(null);
        setDraggingExercises(false);
        return;
      }
    }
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
      }

      if (
        activeInfo.type === "exercise" &&
        overInfo.type !== "add" &&
        activeDnD &&
        activeDnD.type === "exercise"
      ) {
        dispatch({
          type: EditTestActionKind.MOVE_EXERCISE,
          exercise: {
            exercisePosition: activeInfo.exercisePosition,
            groupPosition: activeInfo.groupPosition,
            newPosition: {
              exercisePosition:
                overInfo.type === "exercise" ? overInfo.exercisePosition : 0,
              groupPosition: overInfo.groupPosition,
            },
          },
        });
      }

      // Moving an Exercise to the ExerciseBank => Delete Exercise
      if (
        activeInfo.type === "exercise" &&
        overInfo.type === "add" &&
        activeDnD &&
        activeDnD.type === "exercise"
      ) {
        dispatch({
          type: EditTestActionKind.REMOVE_EXERCISE,
          exercise: {
            exercisePosition: activeDnD.onGroup.exercisePosition,
            groupPosition: activeDnD.onGroup.groupPosition,
          },
        });
      }
    }

    setDraggingExercises(false);
    setActiveDnD(null);
  }

  return (
    <EditTestContext.Provider value={{ testState, dispatch }}>
      <DndContext
        sensors={sensors}
        collisionDetection={closestCorners}
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
              selectedMenu === "dd-list-exercises" ? "w-[60%] px-8 pb-8" : "w-0"
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
                  draggingExercises={draggingExercises}
                  listExercises={listExercises}
                  setListExercises={(value) => setListExercises(value)}
                ></ExerciseBankDragDrop>
              </>
            )}
          </div>
          <div className="flex flex-col w-full h-screen px-8 pb-8 overflow-y-auto bg-2-1">
            <EditTestDragDrop
              exerciseID={exerciseID}
              setExerciseID={(value) => setExerciseID(value)}
              selectedMenu={selectedMenu}
              setSelectedMenu={(value) => setSelectedMenu(value)}
              draggingExercises={draggingExercises}
            ></EditTestDragDrop>
          </div>
          <div
            className={`${
              selectedMenu === "edit-exercise" ||
              selectedMenu === "create-exercise" ||
              selectedMenu === "edit-group" ||
              selectedMenu === "edit-test-info"
                ? "w-full px-16 pb-8"
                : "w-0"
            } flex flex-col h-screen overflow-auto bg-2-1 transition-[width]`}
          >
            {(selectedMenu === "edit-exercise" ||
              selectedMenu === "create-exercise") && (
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
            )}
            {selectedMenu === "edit-group" && (
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
            )}
            {selectedMenu === "edit-test-info" && (
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
            )}
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
                setSelectedMenu={() => {}}
                setSelectedExercise={() => {}}
                exercisePosition={activeDnD.exercisePosition}
                setExerciseID={setExerciseID}
                draggingExercises={true}
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
                setSelectedMenu={() => {}}
                setSelectedExercise={() => {}}
                exercisePosition={activeDnD.exercisePosition}
                setExerciseID={setExerciseID}
                draggingExercises={true}
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
                draggingExercises={true}
              ></GroupDragDrop>
            </div>
          )}
        </DragOverlay>
      </DndContext>
    </EditTestContext.Provider>
  );
}
