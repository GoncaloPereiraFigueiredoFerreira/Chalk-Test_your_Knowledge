import { useContext, createContext, useReducer } from "react";
import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
} from "../../objects/Exercise/Exercise";

// Type of actions allowed on the state
export enum ListExerciseActionKind {
  ADD_EXERCISES = "ADD_EXERCISES",
  ADD_NEW_EXERCISE = "ADD_NEW_EXERCISE",
  CREATE_NEW_EXERCISE = "CREATE_NEW_EXERCISE",
  REMOVE_EXERCISE = "REMOVE_EXERCISE",
  SET_SELECTED_EXERCISE = "SET_SELECTED_EXERCISE",
  SET_SELECTED_GROUP = "SET_SELECTED_GROUP",
}

// Takes the current ListExerciseState and an action to update the ListExerciseState
function ListExerciseStateReducer(
  listExerciseState: ListExerciseState,
  listExerciseAction: ListExerciseAction
) {
  switch (listExerciseAction.type) {
    case ListExerciseActionKind.ADD_EXERCISES:
      if (listExerciseAction.payload)
        if (listExerciseAction.payload.exercises) {
          let newListExercises = { ...listExerciseState.listExercises };
          listExerciseAction.payload.exercises.forEach((element) => {
            newListExercises[element.id] = element;
          });
          return { ...listExerciseState, listExercises: newListExercises };
        } else
          throw new Error(
            "No data provided in listExerciseAction.payload.exercises"
          );
      else throw new Error("No data provided in listExerciseAction.payload");

    case ListExerciseActionKind.CREATE_NEW_EXERCISE:
      if (listExerciseAction.payload)
        if (listExerciseAction.payload.exercise) {
          let newListExercises = {
            [listExerciseAction.payload.exercise.id]:
              listExerciseAction.payload.exercise,
            ...listExerciseState.listExercises,
          };
          return {
            ...listExerciseState,
            listExercises: newListExercises,
            selectedExercise: "",
          };
        } else
          throw new Error(
            "No data provided in listExerciseAction.payload.exercise"
          );
      else throw new Error("No data provided in listExerciseAction.payload");

    case ListExerciseActionKind.REMOVE_EXERCISE:
      if (listExerciseAction.payload)
        if (listExerciseAction.payload.selectedExercise) {
          let exerciseID = listExerciseAction.payload.selectedExercise;
          if (
            !Object.keys(listExerciseState.listExercises).includes(exerciseID)
          ) {
            let { exerciseID, ...newListExercises } =
              listExerciseState.listExercises;
            return {
              ...listExerciseState,
              listExercises: newListExercises,
              selectedExercise: "",
            };
          } else throw new Error("Exercise does not exist");
        } else
          throw new Error(
            "No data provided in listExerciseAction.payload.selectedExercise"
          );
      else throw new Error("No data provided in listExerciseAction.payload");

    case ListExerciseActionKind.SET_SELECTED_EXERCISE:
      if (listExerciseAction.payload)
        if (listExerciseAction.payload.newExercise) {
          let newExercise: Exercise = {
            id: "-1",
            name: "Novo exercício",
            visibility: "public",
            type: listExerciseAction.payload.newExercise,
            author: "This listExercise",
            statement: {
              text: "Escreva aqui o enunciado...",
            },
            problem: {
              justify: ExerciseJustificationKind.NO_JUSTIFICATION,
              statements: [],
            },
          };
          let newListExercises = {
            ...listExerciseState.listExercises,
            "-1": newExercise,
          };
          return {
            ...listExerciseState,
            listExercises: newListExercises,
            selectedExercise: "-1",
          };
        } else if (listExerciseAction.payload.selectedExercise) {
          let exerciseID = listExerciseAction.payload.selectedExercise;
          if (listExerciseState.listExercises[exerciseID])
            return { ...listExerciseState, selectedExercise: exerciseID };
          else throw new Error("Exercise does not exist");
        } else if (listExerciseAction.payload.selectedExercise === "") {
          return { ...listExerciseState, selectedExercise: "" };
        } else
          throw new Error(
            "No data provided in listExerciseAction.payload.selectedExercise"
          );
      else throw new Error("No data provided in listExerciseAction.payload");

    case ListExerciseActionKind.SET_SELECTED_GROUP:
      if (listExerciseAction.payload)
        if (listExerciseAction.payload.selectedGroup) {
          return {
            ...listExerciseState,
            selectedGroup: listExerciseAction.payload.selectedGroup,
          };
        } else
          throw new Error(
            "No data provided in listExerciseAction.payload.selectedGroup"
          );
      else throw new Error("No data provided in listExerciseAction.payload");
    default:
      throw new Error("Unknown action");
  }
}

// ListExerciseAction Definition
export interface ListExerciseAction {
  type: ListExerciseActionKind;
  payload?: {
    exercises?: Exercise[];
    exercise?: Exercise;
    selectedExercise?: string;
    newExercise?: ExerciseType;
    selectedGroup?: string;
  };
}

// ListExerciseState definition
export interface ListExerciseState {
  listExercises: { [key: string]: Exercise };
  selectedExercise: string;
  selectedGroup: string;
}

// ListExerciseContext definition
export const ListExerciseContext = createContext<
  | {
      listExerciseState: ListExerciseState;
      dispatch: React.Dispatch<ListExerciseAction>;
    }
  | undefined
>(undefined);

interface ListExerciseProviderProps {
  children: any;
  listExercises?: { [key: string]: Exercise };
}

// Exports the ListExerciseProvider component that creates a context of type ListExerciseContext
export function ListExerciseProvider({
  children,
  listExercises,
}: ListExerciseProviderProps) {
  const inicialState = {
    listExercises: listExercises ? listExercises : {},
    selectedExercise: "",
    selectedGroup: "",
  };

  const [listExerciseState, dispatch] = useReducer(
    ListExerciseStateReducer,
    inicialState
  );

  return (
    <ListExerciseContext.Provider value={{ listExerciseState, dispatch }}>
      {children}
    </ListExerciseContext.Provider>
  );
}

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
