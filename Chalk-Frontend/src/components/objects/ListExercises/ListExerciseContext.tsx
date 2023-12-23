import { useContext, createContext } from "react";
import { Exercise, ExerciseType, InitExercise } from "../Exercise/Exercise";

//------------------------------------//
//                                    //
//      ListExerciseStateReducer      //
//                                    //
//------------------------------------//

// Type of actions allowed on the state
export enum ListExerciseActionKind {
  ADD_EXERCISES = "ADD_EXERCISES",
  ADD_NEW_EXERCISE = "ADD_NEW_EXERCISE",
  EDIT_EXERCISE = "EDIT_EXERCISE",
  CREATE_NEW_EXERCISE = "CREATE_NEW_EXERCISE",
  REMOVE_EXERCISE = "REMOVE_EXERCISE",
  SET_SELECTED_EXERCISE = "SET_SELECTED_EXERCISE",
}

// ListExerciseAction Definition
export interface ListExerciseAction {
  type: ListExerciseActionKind;
  payload?: {
    exercises?: Exercise[];
    exercise?: Exercise;
    selectedExercise?: string;
    newExerciseType?: ExerciseType;
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
    case ListExerciseActionKind.ADD_EXERCISES:
      if (action.payload)
        if (action.payload.exercises) {
          let newListExercises = { ...listExerciseState.listExercises };
          action.payload.exercises.forEach((element) => {
            newListExercises[element.identity!.id] = element;
          });
          return { ...listExerciseState, listExercises: newListExercises };
        } else throw new Error("No data provided in action.payload.exercises");
      else throw new Error("No data provided in action.payload");

    case ListExerciseActionKind.ADD_NEW_EXERCISE:
      if (action.payload)
        if (action.payload.newExerciseType) {
          let newExercise: Exercise = InitExercise(
            action.payload.newExerciseType
          );
          let newListExercises = {
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

    case ListExerciseActionKind.CREATE_NEW_EXERCISE:
      if (action.payload)
        if (action.payload.exercise) {
          let { ["-1"]: removedItem, ...cleanListExercises } =
            listExerciseState.listExercises;
          let newListExercises = {
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
          let newListExercises = {
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
          let exerciseID = action.payload.selectedExercise;
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
          let exerciseID = action.payload.selectedExercise;
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
