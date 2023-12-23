import { useContext, createContext } from "react";
import { ExerciseGroup, Test } from "./Test";
import { Exercise } from "../Exercise/Exercise";

//------------------------------------//
//                                    //
//      CreateTestStateReducer        //
//                                    //
//------------------------------------//

export enum CreateTestActionKind {
  EDIT_TEST_INFO = "EDIT_TEST_INFO",
  ADD_EXERCISE = "ADD_EXERCISE",
  REMOVE_EXERCISE = "REMOVE_EXERCISE",
  EDIT_EXERCISE = "EDIT_EXERCISE",
  ADD_GROUP = "ADD_GROUP",
  REMOVE_GROUP = "REMOVE_GROUP",
  EDIT_GROUP = "EDIT_GROUP",
  CHANGE_EXERCISE_COTATION = "CHANGE_EXERCISE_COTATION",
  CHANGE_EXERCISE_POSITION = "CHANGE_EXERCISE_POSITION",
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
  };
  exercise?: {
    exercisePosition: number;
    groupPosition: number;
    exerciseID?: string;
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

    case CreateTestActionKind.ADD_EXERCISE:
      if (action.exercise && action.exercise.exercise) {
        let newGroups = [...state.test.groups];
        let newExerciseGroup = {
          ...newGroups[action.exercise.groupPosition],
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
            groups: newGroups,
          } as Test,
        };
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.REMOVE_EXERCISE:
      if (action.exercise && action.exercise.exerciseID) {
        let newGroups = [...state.test.groups];
        if (
          newGroups[action.exercise.groupPosition].exercises[
            action.exercise.exercisePosition
          ] &&
          newGroups[action.exercise.groupPosition].exercises[
            action.exercise.exercisePosition
          ].identity.id === action.exercise.exerciseID
        ) {
          newGroups[action.exercise.groupPosition].exercises.splice(
            action.exercise.exercisePosition,
            1
          );
          return {
            ...state,
            test: {
              ...state.test,
              groups: newGroups,
            } as Test,
          };
        } else return state;
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
          } as Test,
        };
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.ADD_GROUP:
      return {
        ...state,
        test: {
          ...state.test,
          groups: [
            ...state.test.groups,
            {
              groupInstructions: "",
              exercises: [],
            },
          ],
        } as Test,
      };

    case CreateTestActionKind.REMOVE_GROUP:
      if (action.group) {
        let newGroups = [...state.test.groups];
        newGroups.splice(action.group.groupPosition, 1);
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          } as Test,
        };
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
          } as Test,
        };
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.CHANGE_EXERCISE_COTATION:
      if (action.exercise && action.exercise.newCotation) {
        let newGroups = [...state.test.groups];
        newGroups[action.exercise.groupPosition].exercises[
          action.exercise.exercisePosition
        ].identity.cotation = action.exercise.newCotation;
        return {
          ...state,
          test: {
            ...state.test,
            groups: newGroups,
          } as Test,
        };
      }
      throw new Error("Invalid Action");

    case CreateTestActionKind.CHANGE_EXERCISE_POSITION:
      return state;

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
