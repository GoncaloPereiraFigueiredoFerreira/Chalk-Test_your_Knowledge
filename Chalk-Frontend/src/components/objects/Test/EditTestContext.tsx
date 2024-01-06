import { useContext, createContext } from "react";
import { ExerciseGroup, Test } from "./Test";
import { Exercise, ExerciseType, InitExercise } from "../Exercise/Exercise";
import { arrayMove } from "@dnd-kit/sortable";

//------------------------------------//
//                                    //
//      EditTestStateReducer        //
//                                    //
//------------------------------------//

export enum EditTestActionKind {
  EDIT_TEST_INFO = "EDIT_TEST_INFO",
  CREATE_NEW_EXERCISE = "CREATE_NEW_EXERCISE", // Add a new exercise to a given group
  ADD_EXERCISE = "ADD_EXERCISE", // Add an exercise from User Exercise List
  REMOVE_EXERCISE = "REMOVE_EXERCISE",
  EDIT_EXERCISE = "EDIT_EXERCISE", // Edit an exercise
  ADD_GROUP = "ADD_GROUP",
  REMOVE_GROUP = "REMOVE_GROUP",
  EDIT_GROUP = "EDIT_GROUP",
  CHANGE_EXERCISE_COTATION = "CHANGE_EXERCISE_COTATION",
  SELECT_EXERCISE_POSITION = "SELECT_EXERCISE_POSITION",
  MOVE_EXERCISE = "MOVE_EXERCISE",
  MOVE_GROUP = "MOVE_GROUP",
}

export interface EditTestAction {
  type: EditTestActionKind;
  testInfo?: {
    type: string;
    conclusion: string;
    title: string;
    globalInstructions: string;
  };
  group?: {
    groupPosition: number;
    groupInstructions?: string;
    exerciseType?: ExerciseType;
    newPosition?: number;
  };
  exercise?: {
    exercisePosition: number;
    groupPosition: number;
    exercise?: Exercise;
    newPosition?: {
      exercisePosition: number;
      groupPosition: number;
    };
    newCotation?: number;
  };
}

export interface EditTestState {
  test: Test;
  // posição do exercicio/grupo selecionado para edição
  exercisePosition: number;
  groupPosition: number;
}

export function EditTestStateReducer(
  state: EditTestState,
  action: EditTestAction
) {
  switch (action.type) {
    case EditTestActionKind.EDIT_TEST_INFO:
      if (action.testInfo)
        return {
          ...state,
          test: { ...state.test, ...action.testInfo },
        };
      throw new Error("Invalid Action");

    case EditTestActionKind.CREATE_NEW_EXERCISE:
      if (action.group && action.group.exerciseType) {
        let newGroups = [...state.test.groups];
        let newExerciseGroup = {
          ...newGroups[action.group.groupPosition],
          exercises: [
            ...newGroups[action.group.groupPosition].exercises,
            InitExercise(action.group.exerciseType),
          ],
        } as ExerciseGroup;
        newGroups[action.group.groupPosition] = newExerciseGroup;
        return {
          exercisePosition: newExerciseGroup.exercises.length - 1,
          groupPosition: action.group.groupPosition,
          test: {
            ...state.test,
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.ADD_EXERCISE:
      if (action.exercise && action.exercise.exercise) {
        let newGroups = [...state.test.groups];
        const before = newGroups[action.exercise.groupPosition].exercises.slice(
          0,
          action.exercise.exercisePosition
        );
        const after = newGroups[action.exercise.groupPosition].exercises.slice(
          action.exercise.exercisePosition
        );
        const exercise = {
          ...action.exercise.exercise,
          identity: {
            ...action.exercise.exercise.identity,
            // <<<<<<<<<  ALTERAR  >>>>>>>>>
            // Exercicio tem de ser sempre duplicado
            // Novo id resultante de duplicar este exercicio no backend
            id: "test-exercise-".concat(Math.random().toString()),
            // <<<<<<<<<  ALTERAR (fim)  >>>>>>>>>
          },
        } as Exercise;
        let newExerciseGroup = {
          ...newGroups[action.exercise.groupPosition],
          groupCotation:
            newGroups[action.exercise.groupPosition].groupCotation +
            (action.exercise.exercise.identity.cotation ?? 0),
          exercises: [...before, exercise, ...after],
        };
        newGroups[action.exercise.groupPosition] = newExerciseGroup;
        return {
          ...state,
          test: {
            ...state.test,
            globalCotation:
              state.test.globalCotation +
              (action.exercise.exercise.identity.cotation ?? 0),
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.REMOVE_EXERCISE:
      if (action.exercise) {
        let newGroups = [...state.test.groups];
        if (
          newGroups[action.exercise.groupPosition] &&
          newGroups[action.exercise.groupPosition].exercises[
            action.exercise.exercisePosition
          ]
        ) {
          // remove exercise
          const before = newGroups[
            action.exercise.groupPosition
          ].exercises.slice(0, action.exercise.exercisePosition);
          const after = newGroups[
            action.exercise.groupPosition
          ].exercises.slice(action.exercise.exercisePosition + 1);
          // update newGroups
          let cleanGroup = {
            ...newGroups[action.exercise.groupPosition],
            exercises: [...before, ...after],
          };
          newGroups[action.exercise.groupPosition] = cleanGroup;
          // update groupCotation
          let auxCotation = 0;
          newGroups[action.exercise.groupPosition].exercises.forEach(
            (element) => {
              auxCotation += element.identity.cotation ?? 0;
            }
          );
          newGroups[action.exercise.groupPosition].groupCotation = auxCotation;
          // update globalCotation
          auxCotation = 0;
          newGroups.forEach((element) => {
            auxCotation += element.groupCotation;
          });
          return {
            ...state,
            test: {
              ...state.test,
              globalCotation: auxCotation,
              groups: newGroups,
            },
          } as EditTestState;
        }
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.EDIT_EXERCISE:
      if (action.exercise && action.exercise.exercise) {
        let newGroups = [...state.test.groups];
        newGroups[action.exercise.groupPosition].exercises[
          action.exercise.exercisePosition
        ] = action.exercise.exercise;
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.ADD_GROUP:
      return {
        ...state,
        groupPosition: state.test.groups.length,
        test: {
          ...state.test,
          groups: [
            ...state.test.groups,
            {
              id: "test-group-" + state.test.groups.length,
              groupInstructions: "",
              exercises: [],
              groupCotation: 0,
            },
          ],
        },
      } as EditTestState;

    case EditTestActionKind.REMOVE_GROUP:
      if (action.group) {
        let newGroups = [...state.test.groups];
        // remove group
        const before = newGroups.slice(0, action.group.groupPosition);
        const after = newGroups.slice(action.group.groupPosition + 1);
        // update groups
        let cleanGroups = [...before, ...after];
        // update globalCotation
        let auxCotation = 0;
        newGroups.forEach((element) => {
          auxCotation += element.groupCotation;
        });
        return {
          ...state,
          test: {
            ...state.test,
            globalCotation: auxCotation,
            groups: cleanGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.EDIT_GROUP:
      console.log(action);

      if (action.group && action.group.groupInstructions !== undefined) {
        let newGroups = [...state.test.groups];
        newGroups[action.group.groupPosition].groupInstructions =
          action.group.groupInstructions;
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.CHANGE_EXERCISE_COTATION:
      if (action.exercise && action.exercise.newCotation !== undefined) {
        if (action.exercise.newCotation >= 0) {
          let newGroups = [...state.test.groups];
          newGroups[action.exercise.groupPosition].exercises[
            action.exercise.exercisePosition
          ].identity.cotation = action.exercise.newCotation;
          let auxCotation = 0;
          newGroups[action.exercise.groupPosition].exercises.forEach(
            (element) => {
              auxCotation += element.identity.cotation ?? 0;
            }
          );
          newGroups[action.exercise.groupPosition].groupCotation = auxCotation;
          auxCotation = 0;
          newGroups.forEach((element) => {
            auxCotation += element.groupCotation;
          });
          return {
            ...state,
            test: {
              ...state.test,
              globalCotation: auxCotation,
              groups: newGroups,
            },
          } as EditTestState;
        } else {
          console.warn("Cotation cannot be negative");
          return state;
        }
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.SELECT_EXERCISE_POSITION:
      if (action.exercise) {
        return {
          ...state,
          exercisePosition: action.exercise.exercisePosition,
          groupPosition: action.exercise.groupPosition,
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.MOVE_EXERCISE:
      if (action.exercise && action.exercise.newPosition) {
        if (
          action.exercise.groupPosition !==
          action.exercise.newPosition.groupPosition
        ) {
          // Move to another group
          let newGroups = [...state.test.groups];
          const origGroup = state.test.groups[action.exercise.groupPosition];
          const destGroup =
            state.test.groups[action.exercise.newPosition.groupPosition];
          let before = origGroup.exercises.slice(
            0,
            action.exercise.exercisePosition
          );
          let after = origGroup.exercises.slice(
            action.exercise.exercisePosition + 1
          );

          let cleanOriginGroup = {
            ...origGroup,
            exercises: [...before, ...after],
          };
          let auxCotation = 0;
          cleanOriginGroup.exercises.forEach((element) => {
            auxCotation += element.identity.cotation ?? 0;
          });
          cleanOriginGroup.groupCotation = auxCotation;

          before = destGroup.exercises.slice(
            0,
            action.exercise.exercisePosition
          );
          after = destGroup.exercises.slice(action.exercise.exercisePosition);

          let cleanDestGroup = {
            ...destGroup,
            exercises: [
              ...before,
              origGroup.exercises[action.exercise.exercisePosition],
              ...after,
            ],
          };
          auxCotation = 0;
          cleanDestGroup.exercises.forEach((element) => {
            auxCotation += element.identity.cotation ?? 0;
          });
          cleanDestGroup.groupCotation = auxCotation;

          newGroups[action.exercise.groupPosition] = cleanOriginGroup;
          newGroups[action.exercise.newPosition.groupPosition] = cleanDestGroup;
          return {
            ...state,
            test: {
              ...state.test,
              groups: newGroups,
            },
          } as EditTestState;
        } else {
          // Move within the same group
          let newGroups = [...state.test.groups];
          let origGroup = newGroups[action.exercise.groupPosition];
          origGroup.exercises = arrayMove(
            origGroup.exercises,
            action.exercise.exercisePosition,
            action.exercise.newPosition.exercisePosition
          );
          newGroups[action.exercise.groupPosition] = origGroup;
          return {
            ...state,
            test: {
              ...state.test,
              groups: newGroups,
            },
          } as EditTestState;
        }
      }
      throw new Error("Invalid Action");

    case EditTestActionKind.MOVE_GROUP:
      if (action.group && action.group.newPosition !== undefined) {
        let newGroups = [...state.test.groups];
        newGroups = arrayMove(
          newGroups,
          action.group.groupPosition,
          action.group.newPosition
        );
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          },
        } as EditTestState;
      }
      throw new Error("Invalid Action");

    default:
      alert("Unknown action");
      return state;
  }
}

//------------------------------------//
//                                    //
//          EditTestProvider          //
//                                    //
//------------------------------------//

export const EditTestContext = createContext<
  | { testState: EditTestState; dispatch: React.Dispatch<EditTestAction> }
  | undefined
>(undefined);

//------------------------------------//
//                                    //
//        useEditTestContext          //
//                                    //
//------------------------------------//

// Exports function useEditTestContext that allows you to access the contents of a EditTestContext if the context has already been defined
export function useEditTestContext() {
  const createTest = useContext(EditTestContext);
  if (createTest === undefined) {
    throw new Error(
      "useEditTestContext must be used with a EditTestContext.Provider"
    );
  }
  return createTest;
}

//------------------------------------//
//                                    //
//         EditTest Handlers          //
//                                    //
//------------------------------------//

export function findGroupByID(id: string) {
  const { testState } = useEditTestContext();
  testState.test.groups.forEach((group, index) => {
    if (group.id === id) return index;
  });
  return -1;
}

export function findExerciseByID(id: string) {
  const { testState } = useEditTestContext();
  testState.test.groups.forEach((group, groupPosition) => {
    group.exercises.forEach((exercise, exercisePosition) => {
      if (exercise.identity.id === id)
        return {
          groupPosition: groupPosition,
          exercisePosition: exercisePosition,
        };
    });
  });
  return -1;
}
