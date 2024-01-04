import { useContext, createContext } from "react";
import { ExerciseGroup, Test } from "./Test";
import { Exercise, ExerciseType, InitExercise } from "../Exercise/Exercise";

//------------------------------------//
//                                    //
//      CreateTestStateReducer        //
//                                    //
//------------------------------------//

export enum CreateTestActionKind {
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
}

export interface CreateTestAction {
  type: CreateTestActionKind;
  testInfo?: {
    type: string;
    conclusion: string;
    title: string;
    creationDate: string;
    globalInstructions: string;
  };
  group?: {
    groupPosition: number;
    groupInstructions?: string;
    exerciseType?: ExerciseType;
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

export interface CreateTestState {
  test: Test;
  // posição do exercicio/grupo selecionado para edição
  exercisePosition: number;
  groupPosition: number;
}

export function CreateTestStateReducer(
  state: CreateTestState,
  action: CreateTestAction
) {
  switch (action.type) {
    case CreateTestActionKind.EDIT_TEST_INFO:
      if (action.testInfo)
        return {
          ...state,
          test: { ...state.test, ...action.testInfo },
        };
      throw new Error("Invalid Action");

    case CreateTestActionKind.CREATE_NEW_EXERCISE:
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
        } as CreateTestState;
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.ADD_EXERCISE:
      if (action.exercise && action.exercise.exercise) {
        let newGroups = [...state.test.groups];
        let newExerciseGroup = {
          ...newGroups[action.exercise.groupPosition],
          groupCotation:
            newGroups[action.exercise.groupPosition].groupCotation +
            (action.exercise.exercise.identity.cotation ?? 0),
          exercises: [
            ...newGroups[action.exercise.groupPosition].exercises,
            action.exercise.exercise,
          ],
        } as ExerciseGroup;
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
        } as CreateTestState;
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.REMOVE_EXERCISE:
      if (action.exercise) {
        let newGroups = [...state.test.groups];
        if (
          newGroups[action.exercise.groupPosition] &&
          newGroups[action.exercise.groupPosition].exercises[
            action.exercise.exercisePosition
          ]
        ) {
          const before = newGroups[
            action.exercise.groupPosition
          ].exercises.slice(0, action.exercise.exercisePosition);
          const after = newGroups[
            action.exercise.groupPosition
          ].exercises.slice(action.exercise.exercisePosition + 1);

          let cleanGroup = {
            ...newGroups[action.exercise.groupPosition],
            groupCotation:
              newGroups[action.exercise.groupPosition].groupCotation -
              (newGroups[action.exercise.groupPosition].exercises[
                action.exercise.exercisePosition
              ].identity.cotation ?? 0),
            exercises: [...before, ...after],
          };
          newGroups[action.exercise.groupPosition] = cleanGroup;
          return {
            ...state,
            test: {
              ...state.test,
              globalCotation:
                state.test.globalCotation -
                (state.test.groups[action.exercise.groupPosition].exercises[
                  action.exercise.exercisePosition
                ].identity.cotation ?? 0),
              groups: newGroups,
            },
          } as CreateTestState;
        }
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.EDIT_EXERCISE:
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
        } as CreateTestState;
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.ADD_GROUP:
      return {
        ...state,
        groupPosition: state.test.groups.length,
        test: {
          ...state.test,
          groups: [
            ...state.test.groups,
            {
              groupInstructions: "",
              exercises: [],
              groupCotation: 0,
            },
          ],
        },
      } as CreateTestState;

    case CreateTestActionKind.REMOVE_GROUP:
      if (action.group) {
        let newGroups = [...state.test.groups];
        const before = newGroups.slice(0, action.group.groupPosition);
        const after = newGroups.slice(action.group.groupPosition + 1);
        return {
          ...state,
          test: {
            ...state.test,
            globalCotation:
              state.test.globalCotation -
              state.test.groups[action.group.groupPosition].groupCotation,
            groups: [...before, ...after],
          },
        } as CreateTestState;
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.EDIT_GROUP:
      if (action.group && action.group.groupInstructions) {
        let newGroups = [...state.test.groups];
        newGroups[action.group.groupPosition].groupInstructions =
          action.group.groupInstructions;
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          },
        } as CreateTestState;
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.CHANGE_EXERCISE_COTATION:
      if (action.exercise && action.exercise.newCotation !== undefined) {
        if (action.exercise.newCotation >= 0) {
          let newGroups = [...state.test.groups];
          newGroups[action.exercise.groupPosition].groupCotation +=
            action.exercise.newCotation -
            (newGroups[action.exercise.groupPosition].exercises[
              action.exercise.exercisePosition
            ].identity.cotation ?? 0);
          newGroups[action.exercise.groupPosition].exercises[
            action.exercise.exercisePosition
          ].identity.cotation = action.exercise.newCotation;
          var globalCotation = 0;
          newGroups.forEach((element) => {
            globalCotation += element.groupCotation;
          });
          return {
            ...state,
            test: {
              ...state.test,
              globalCotation: globalCotation,
              groups: newGroups,
            },
          } as CreateTestState;
        } else {
          console.warn("Cotation cannot be negative");
          return state;
        }
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.SELECT_EXERCISE_POSITION:
      if (action.exercise) {
        return {
          ...state,
          exercisePosition: action.exercise.exercisePosition,
          groupPosition: action.exercise.groupPosition,
        } as CreateTestState;
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.MOVE_EXERCISE:
      if (action.exercise && action.exercise.newPosition) {
        if (
          action.exercise.groupPosition !==
          action.exercise.newPosition.groupPosition
        ) {
          // Move to another group
          const newGroups = [...state.test.groups];
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

          const cleanOriginGroup = {
            ...origGroup,
            groupCotation:
              origGroup.groupCotation -
              (origGroup.exercises[action.exercise.exercisePosition].identity
                .cotation ?? 0),
            exercises: [...before, ...after],
          };
          before = destGroup.exercises.slice(
            0,
            action.exercise.exercisePosition
          );
          after = destGroup.exercises.slice(action.exercise.exercisePosition);

          const cleanDestGroup = {
            ...destGroup,
            groupCotation:
              destGroup.groupCotation +
              (origGroup.exercises[action.exercise.exercisePosition].identity
                .cotation ?? 0),
            exercises: [
              ...before,
              origGroup.exercises[action.exercise.exercisePosition],
              ...after,
            ],
          };
          newGroups[action.exercise.groupPosition] = cleanOriginGroup;
          newGroups[action.exercise.newPosition.groupPosition] = cleanDestGroup;
          return {
            ...state,
            test: {
              ...state.test,
              groups: newGroups,
            },
          } as CreateTestState;
        } else {
          // Move within the same group
          const newGroups = [...state.test.groups];
          const origGroup = state.test.groups[action.exercise.groupPosition];
          const exercise =
            origGroup.exercises[action.exercise.exercisePosition];

          const before = origGroup.exercises.slice(
            0,
            action.exercise.exercisePosition
          );
          const after = origGroup.exercises.slice(
            action.exercise.exercisePosition + 1
          );
          var list;

          if (
            action.exercise.exercisePosition >
            action.exercise.newPosition.exercisePosition
          ) {
            const beforeDest = before.slice(
              0,
              action.exercise.newPosition.exercisePosition
            );
            const afterDest = before.slice(
              action.exercise.newPosition.exercisePosition + 1
            );
            list = [...beforeDest, exercise, ...afterDest, ...after];
          } else {
            const beforeDest = after.slice(
              0,
              action.exercise.newPosition.exercisePosition
            );
            const afterDest = after.slice(
              action.exercise.newPosition.exercisePosition + 1
            );
            list = [...before, ...beforeDest, exercise, ...afterDest];
          }
          newGroups[action.exercise.groupPosition].exercises = list;
          return {
            ...state,
            test: {
              ...state.test,
              groups: newGroups,
            },
          } as CreateTestState;
        }
      }
      throw new Error("Invalid Action");

    default:
      alert("Unknown action");
      return state;
  }
}

//------------------------------------//
//                                    //
//        CreateTestProvider          //
//                                    //
//------------------------------------//

export const CreateTestContext = createContext<
  | { testState: CreateTestState; dispatch: React.Dispatch<CreateTestAction> }
  | undefined
>(undefined);

//------------------------------------//
//                                    //
//       useCreateTestContext         //
//                                    //
//------------------------------------//

// Exports function useCreateTestContext that allows you to access the contents of a CreateTestContext if the context has already been defined
export function useCreateTestContext() {
  const createTest = useContext(CreateTestContext);
  if (createTest === undefined) {
    throw new Error(
      "useCreateTestContext must be used with a CreateTestContext.Provider"
    );
  }
  return createTest;
}
