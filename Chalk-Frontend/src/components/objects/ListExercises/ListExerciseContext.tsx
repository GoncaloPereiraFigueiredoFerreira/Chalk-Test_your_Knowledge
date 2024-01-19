import { useContext, createContext } from "react";
import { Exercise, ExerciseType, InitExercise } from "../Exercise/Exercise";

//------------------------------------//
//                                    //
//      ListExerciseStateReducer      //
//                                    //
//------------------------------------//

// Type of actions allowed on the state
export enum ListExerciseActionKind {
  SET_LIST_EXERCISES = "ADD_LIST_EXERCISES",
  CREATE_NEW_EXERCISE = "CREATE_NEW_EXERCISE",
  EDIT_EXERCISE = "EDIT_EXERCISE",
  ADD_EXERCISE = "ADD_EXERCISE",
  REMOVE_EXERCISE = "REMOVE_EXERCISE",
  SET_SELECTED_EXERCISE = "SET_SELECTED_EXERCISE",
  CHANGE_VISIBILITY_EXERCISE = "CHANGE_VISIBILITY_EXERCISE",
}

// ListExerciseAction Definition
export interface ListExerciseAction {
  type: ListExerciseActionKind;
  payload?: {
    exercises?: Exercise[];
    exercise?: Exercise;
    selectedExercise?: string;
    newExerciseType?: ExerciseType;
    visibility?: string;
  };
}

// ListExerciseState definition
export interface ListExerciseState {
  listExercises: { [key: string]: Exercise };
  selectedExercise: string;
}

// Takes the current ListExerciseState and an action to update the ListExerciseState
export function ListExerciseStateReducer(
  listExerciseState: ListExerciseState,
  action: ListExerciseAction
) {
  switch (action.type) {
    case ListExerciseActionKind.SET_LIST_EXERCISES:
      if (action.payload)
        if (action.payload.exercises) {
          const newListExercises: { [id: string]: Exercise } = {};
          action.payload.exercises.forEach((element) => {
            newListExercises[element.identity!.id] = element;
          });
          return { ...listExerciseState, listExercises: newListExercises };
        } else throw new Error("No data provided in action.payload.exercises");
      else throw new Error("No data provided in action.payload");

    case ListExerciseActionKind.CREATE_NEW_EXERCISE:
      if (action.payload)
        if (action.payload.newExerciseType) {
          const newExercise: Exercise = InitExercise(
            action.payload.newExerciseType
          );
          const newListExercises = {
            ...listExerciseState.listExercises,
            ["-1" as string]: newExercise,
          };
          return {
            ...listExerciseState,
            listExercises: newListExercises,
            selectedExercise: "-1",
          };
        } else
          throw new Error(
            "No data provided in action.payload.selectedExercise"
          );
      else throw new Error("No data provided in action.payload");

    case ListExerciseActionKind.ADD_EXERCISE:
      if (action.payload)
        if (action.payload.exercise) {
          const { ["-1"]: removedItem, ...cleanListExercises } =
            listExerciseState.listExercises;
          const newListExercises = {
            ...cleanListExercises,
            [action.payload.exercise.identity!.id]: action.payload.exercise,
          };
          return {
            ...listExerciseState,
            listExercises: newListExercises,
            selectedExercise: action.payload.exercise.identity!.id,
          };
        } else throw new Error("No data provided in action.payload.exercise");
      else throw new Error("No data provided in action.payload");

    case ListExerciseActionKind.EDIT_EXERCISE:
      if (action.payload)
        if (action.payload.exercise) {
          const newListExercises = {
            ...listExerciseState.listExercises,
            [action.payload.exercise.identity!.id]: action.payload.exercise,
          };
          return {
            ...listExerciseState,
            listExercises: newListExercises,
            selectedExercise: action.payload.exercise.identity!.id,
          };
        } else throw new Error("No data provided in action.payload.exercise");
      else throw new Error("No data provided in action.payload");

    case ListExerciseActionKind.REMOVE_EXERCISE:
      if (action.payload)
        if (action.payload.selectedExercise) {
          const exerciseID = action.payload.selectedExercise;
          if (exerciseID in listExerciseState.listExercises) {
            const { [exerciseID]: removedItem, ...newListExercises } =
              listExerciseState.listExercises;
            return {
              ...listExerciseState,
              listExercises: newListExercises,
            };
          } else throw new Error("Exercise does not exist");
        } else
          throw new Error(
            "No data provided in action.payload.selectedExercise"
          );
      else throw new Error("No data provided in action.payload");

    case ListExerciseActionKind.SET_SELECTED_EXERCISE:
      if (action.payload)
        if (action.payload.selectedExercise) {
          const exerciseID = action.payload.selectedExercise;
          if (listExerciseState.listExercises[exerciseID])
            return { ...listExerciseState, selectedExercise: exerciseID };
          else throw new Error("Exercise does not exist");
        } else if (action.payload.selectedExercise === "") {
          return { ...listExerciseState, selectedExercise: "" };
        } else
          throw new Error(
            "No data provided in action.payload.selectedExercise"
          );
      else throw new Error("No data provided in action.payload");

    case ListExerciseActionKind.CHANGE_VISIBILITY_EXERCISE:
      if (
        action.payload &&
        action.payload.visibility &&
        action.payload.selectedExercise
      ) {
        const exercise =
          listExerciseState.listExercises[action.payload.selectedExercise];
        return {
          ...listExerciseState,
          listExercises: {
            ...listExerciseState.listExercises,
            [action.payload.selectedExercise]: {
              ...exercise,
              identity: {
                ...exercise.identity,
                visibility: action.payload.visibility,
              },
            } as Exercise,
          },
        } as ListExerciseState;
      } else throw new Error("No data provided in action.payload");

    default:
      alert("Unknown action");
      return listExerciseState;
  }
}

//------------------------------------//
//                                    //
//        ListExerciseContext         //
//                                    //
//------------------------------------//

// ListExerciseContext definition
export const ListExerciseContext = createContext<
  | {
      listExerciseState: ListExerciseState;
      dispatch: React.Dispatch<ListExerciseAction>;
    }
  | undefined
>(undefined);

//------------------------------------//
//                                    //
//      useListExerciseContext        //
//                                    //
//------------------------------------//

// Exports function useListExerciseContext that allows you to access the contents of a ListExerciseContext if the context has already been defined
export function useListExerciseContext() {
  const listExercise = useContext(ListExerciseContext);
  if (listExercise === undefined) {
    throw new Error(
      "useListExerciseContext must be used with a ListExerciseContext.Provider"
    );
  }
  return listExercise;
}
