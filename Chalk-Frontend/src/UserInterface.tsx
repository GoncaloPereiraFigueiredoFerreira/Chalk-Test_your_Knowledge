import { useReducer, useState } from "react";
import { Outlet } from "react-router-dom";
import { Sidebar } from "./components/objects/Sidebar/Sidebar";
import { UserContext } from "./context";

// Exercise definition
export interface Exercise {
  id: string;
  name: string;
  visibility: string;
  type: string;
  author: string;
  enunciado: any;
  problema?: any;
}

// UserState definition
export interface UserState {
  id: number;
  listExercises: { [key: string]: Exercise };
  selectedExercise: string;
}

// Type of actions allowed on the state
export enum UserActionKind {
  ADD_EXERCISES = "ADD_EXERCISES",
  REMOVE_EXERCISE = "REMOVE_EXERCISE",
  SET_SELECTED_EXERCISE = "SELECTED_EXERCISE",
}

// UserAction Definition
export interface UserAction {
  type: UserActionKind;
  payload?: {
    exercises?: Exercise[];
    selectedExercise?: string;
  };
}

// Takes the current UserState and an action to update the UserState
function UserStateReducer(userState: UserState, userAction: UserAction) {
  switch (userAction.type) {
    case UserActionKind.ADD_EXERCISES:
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
    case UserActionKind.REMOVE_EXERCISE:
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
          throw new Error("No data provided in userAction.payload.exercises");
      else throw new Error("No data provided in userAction.payload");
    case UserActionKind.SET_SELECTED_EXERCISE:
      if (userAction.payload)
        if (userAction.payload.selectedExercise) {
          let exerciseID = userAction.payload.selectedExercise;
          if (!Object.keys(userState.listExercises).includes(exerciseID))
            return { ...userState, selectedExercise: exerciseID };
          else throw new Error("Exercise does not exist");
        } else
          throw new Error("No data provided in userAction.payload.exercises");
      else throw new Error("No data provided in userAction.payload");
    default:
      throw new Error("Unknown action");
  }
}

interface UserInterfaceProps {
  userData: UserState;
}
export function UserInterface({ userData }: UserInterfaceProps) {
  const [selected, setSelected] = useState(0);
  const [isOpen, toggle] = useState(true);

  const [userState, dispatch] = useReducer(UserStateReducer, userData);

  return (
    <div className="flex flex-row h-full">
      <Sidebar
        isOpen={isOpen}
        toggle={toggle}
        selected={selected}
        setSelected={setSelected}
      ></Sidebar>
      <div
        className={`w-full z-10 h-full transition-all ${
          isOpen ? "ml-64" : "ml-16"
        }`}
      >
        <UserContext.Provider value={{ userState, dispatch }}>
          <Outlet />
        </UserContext.Provider>
      </div>
      <div className="fixed top-0 left-0 h-screen w-screen bg-1-1 -z-10"></div>
    </div>
  );
}
