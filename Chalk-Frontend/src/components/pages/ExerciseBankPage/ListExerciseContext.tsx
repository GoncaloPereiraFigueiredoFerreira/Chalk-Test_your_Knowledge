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
  userState: ListExerciseState,
  userAction: ListExerciseAction
) {
  switch (userAction.type) {
    case ListExerciseActionKind.ADD_EXERCISES:
      if (userAction.payload)
        if (userAction.payload.exercises) {
          let newListExercises = { ...userState.listExercises };
          userAction.payload.exercises.forEach((element) => {
            newListExercises[element.id] = element;
          });
          return { ...userState, listExercises: newListExercises };
        } else
          throw new Error("No data provided in userAction.payload.exercises");
      else throw new Error("No data provided in userAction.payload");

    case ListExerciseActionKind.CREATE_NEW_EXERCISE:
      if (userAction.payload)
        if (userAction.payload.exercise) {
          let newListExercises = {
            [userAction.payload.exercise.id]: userAction.payload.exercise,
            ...userState.listExercises,
          };
          return {
            ...userState,
            listExercises: newListExercises,
            selectedExercise: "",
          };
        } else
          throw new Error("No data provided in userAction.payload.exercise");
      else throw new Error("No data provided in userAction.payload");

    case ListExerciseActionKind.REMOVE_EXERCISE:
      if (userAction.payload)
        if (userAction.payload.selectedExercise) {
          let exerciseID = userAction.payload.selectedExercise;
          if (!Object.keys(userState.listExercises).includes(exerciseID)) {
            let { exerciseID, ...newListExercises } = userState.listExercises;
            return {
              ...userState,
              listExercises: newListExercises,
              selectedExercise: "",
            };
          } else throw new Error("Exercise does not exist");
        } else
          throw new Error(
            "No data provided in userAction.payload.selectedExercise"
          );
      else throw new Error("No data provided in userAction.payload");

    case ListExerciseActionKind.SET_SELECTED_EXERCISE:
      if (userAction.payload)
        if (userAction.payload.newExercise) {
          let newExercise: Exercise = {
            id: "-1",
            name: "Novo exercício",
            visibility: "public",
            type: userAction.payload.newExercise,
            author: "This user",
            statement: {
              text: "Escreva aqui o enunciado...",
            },
            problem: {
              justify: ExerciseJustificationKind.NO_JUSTIFICATION,
              statements: [],
            },
          };
          let newListExercises = {
            ...userState.listExercises,
            "-1": newExercise,
          };
          return {
            ...userState,
            listExercises: newListExercises,
            selectedExercise: "-1",
          };
        } else if (userAction.payload.selectedExercise) {
          let exerciseID = userAction.payload.selectedExercise;
          if (userState.listExercises[exerciseID])
            return { ...userState, selectedExercise: exerciseID };
          else throw new Error("Exercise does not exist");
        } else if (userAction.payload.selectedExercise === "") {
          return { ...userState, selectedExercise: "" };
        } else
          throw new Error(
            "No data provided in userAction.payload.selectedExercise"
          );
      else throw new Error("No data provided in userAction.payload");

    case ListExerciseActionKind.SET_SELECTED_GROUP:
      if (userAction.payload)
        if (userAction.payload.selectedGroup) {
          return {
            ...userState,
            selectedGroup: userAction.payload.selectedGroup,
          };
        } else
          throw new Error(
            "No data provided in userAction.payload.selectedGroup"
          );
      else throw new Error("No data provided in userAction.payload");
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
  const user = useContext(ListExerciseContext);
  if (user === undefined) {
    throw new Error(
      "useListExerciseContext must be used with a ListExerciseContext.Provider"
    );
  }
  return user;
}
