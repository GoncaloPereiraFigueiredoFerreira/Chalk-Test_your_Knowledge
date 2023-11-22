import { useReducer, useState } from "react";
import { Outlet } from "react-router-dom";
import { Sidebar } from "./components/objects/Sidebar/Sidebar";
import { UserContext } from "./context";

export enum ExerciseType {
  MULTIPLE_CHOICE = "multiple-choice",
  OPEN_ANSWER = "open-answer",
  TRUE_OR_FALSE = "true-or-false",
  FILL_IN_THE_BLANK = "fill-in-the-blank",
  CODE = "code",
}

export enum ExerciseJustificationKind {
  JUSTIFY_ALL = "JUSTIFY_ALL",
  JUSTIFY_FALSE = "JUSTIFY_FALSE",
  JUSTIFY_TRUE = "JUSTIFY_TRUE",
  NO_JUSTIFICATION = "NO_JUSTIFICATION",
}

// Exercise definition
export interface Exercise {
  id: string;
  name: string;
  visibility: string;
  type: ExerciseType;
  author: string;
  enunciado: {
    text: string;
    img?: {
      url: string;
      pos: string;
    };
  };
  problem?: {
    justify?: ExerciseJustificationKind;
    statements: string[];
  };
}

function createNewExercise(newExercisetype: ExerciseType) {
  // colocar aqui as chamadas para criação dos exercicios
  switch (newExercisetype) {
    case ExerciseType.MULTIPLE_CHOICE:
      return {
        id: "-1",
        name: "",
        visibility: "public",
        type: ExerciseType.MULTIPLE_CHOICE,
        author: "utilizador atual", //userState.username,
        enunciado: {
          text: "",
        },
        problem: {
          justify: ExerciseJustificationKind.NO_JUSTIFICATION,
          statements: [""],
        },
      };
    case ExerciseType.OPEN_ANSWER:
      return {
        id: "-1",
        name: "",
        visibility: "public",
        type: ExerciseType.MULTIPLE_CHOICE,
        author: "utilizador atual", //userState.username,
        enunciado: {
          text: "",
        },
        problem: {
          justify: ExerciseJustificationKind.NO_JUSTIFICATION,
          statements: [""],
        },
      };
    case ExerciseType.TRUE_OR_FALSE:
      return {
        id: "-1",
        name: "",
        visibility: "public",
        type: ExerciseType.MULTIPLE_CHOICE,
        author: "utilizador atual", //userState.username,
        enunciado: {
          text: "",
        },
        problem: {
          justify: ExerciseJustificationKind.NO_JUSTIFICATION,
          statements: [""],
        },
      };
    case ExerciseType.FILL_IN_THE_BLANK:
      return {
        id: "-1",
        name: "",
        visibility: "public",
        type: ExerciseType.MULTIPLE_CHOICE,
        author: "utilizador atual", //userState.username,
        enunciado: {
          text: "",
        },
        problem: {
          justify: ExerciseJustificationKind.NO_JUSTIFICATION,
          statements: [""],
        },
      };
    case ExerciseType.CODE:
      return {
        id: "-1",
        name: "",
        visibility: "public",
        type: ExerciseType.MULTIPLE_CHOICE,
        author: "utilizador atual", //userState.username,
        enunciado: {
          text: "",
        },
        problem: {
          justify: ExerciseJustificationKind.NO_JUSTIFICATION,
          statements: [""],
        },
      };
  }
}

// UserState definition
export interface UserState {
  id: number;
  listExercises: { [key: string]: Exercise };
  selectedExercise: string;
  selectedGroup: string;
}

// Type of actions allowed on the state
export enum UserActionKind {
  ADD_EXERCISES = "ADD_EXERCISES",
  CREATE_NEW_EXERCISE = "CREATE_NEW_EXERCISE",
  REMOVE_EXERCISE = "REMOVE_EXERCISE",
  SET_SELECTED_EXERCISE = "SET_SELECTED_EXERCISE",
  SET_SELECTED_GROUP = "SET_SELECTED_GROUP",
}

// UserAction Definition
export interface UserAction {
  type: UserActionKind;
  payload?: {
    exercises?: Exercise[];
    type?: ExerciseType;
    selectedExercise?: string;
    selectedGroup?: string;
  };
}

// Takes the current UserState and an action to update the UserState
function UserStateReducer(userState: UserState, userAction: UserAction) {
  switch (userAction.type) {
    case UserActionKind.ADD_EXERCISES:
      // console.log(UserActionKind.ADD_EXERCISES);

      if (userAction.payload)
        if (userAction.payload.exercises) {
          let newListExercises = { ...userState.listExercises };
          userAction.payload.exercises.forEach((element) => {
            // if (newListExercises[element.id])
            //   console.log("already present " + element.id);
            // else
            newListExercises[element.id] = element;
          });
          return { ...userState, listExercises: newListExercises };
        } else
          throw new Error("No data provided in userAction.payload.exercises");
      else throw new Error("No data provided in userAction.payload");
    case UserActionKind.CREATE_NEW_EXERCISE:
      if (userAction.payload)
        if (userAction.payload.type) {
          let newListExercises = { ...userState.listExercises };
          newListExercises["-1"] = createNewExercise(userAction.payload.type);
          return { ...userState, listExercises: newListExercises };
        } else throw new Error("No data provided in userAction.payload.type");
      else throw new Error("No data provided in userAction.payload");
    case UserActionKind.REMOVE_EXERCISE:
      // console.log(UserActionKind.REMOVE_EXERCISE);

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
    case UserActionKind.SET_SELECTED_EXERCISE:
      // console.log(UserActionKind.SET_SELECTED_EXERCISE);

      if (userAction.payload)
        if (userAction.payload.selectedExercise) {
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
    case UserActionKind.SET_SELECTED_GROUP:
      // console.log(UserActionKind.SET_SELECTED_GROUP);

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

interface UserInterfaceProps {
  userData: UserState;
}
export function UserInterface({ userData }: UserInterfaceProps) {
  const [isOpen, toggle] = useState(true);

  const [userState, dispatch] = useReducer(UserStateReducer, userData);

  return (
    <div className="flex flex-row h-full">
      <UserContext.Provider value={{ userState, dispatch }}>
        <Sidebar isOpen={isOpen} toggle={toggle}></Sidebar>
        <div
          className={`w-full z-10 h-full transition-all ${
            isOpen ? "ml-64" : "ml-16"
          }`}
        >
          <Outlet />
        </div>
      </UserContext.Provider>
      <div className="fixed top-0 left-0 h-screen w-screen bg-1-1 -z-10"></div>
    </div>
  );
}
