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

export function createNewExercise(newExercisetype: ExerciseType) {
  // colocar aqui as chamadas para criação dos exercicios
  switch (newExercisetype) {
    case ExerciseType.MULTIPLE_CHOICE:
      return {
        id: "-1",
        name: "",
        visibility: "public",
        type: ExerciseType.MULTIPLE_CHOICE,
        author: "utilizador atual", //exerciseState.exercisename,
        statement: {
          text: "",
        },
        problem: {
          statements: [""],
        },
      };
    case ExerciseType.OPEN_ANSWER:
      return {
        id: "-1",
        name: "",
        visibility: "public",
        type: ExerciseType.OPEN_ANSWER,
        author: "utilizador atual", //exerciseState.exercisename,
        statement: {
          text: "",
        },
      };
    case ExerciseType.TRUE_OR_FALSE:
      return {
        id: "-1",
        name: "",
        visibility: "public",
        type: ExerciseType.TRUE_OR_FALSE,
        author: "utilizador atual", //exerciseState.exercisename,
        statement: {
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
        author: "utilizador atual", //exerciseState.exercisename,
        statement: {
          text: "",
        },
        problem: {
          statements: [""],
        },
      };
    case ExerciseType.CODE:
      return {
        id: "-1",
        name: "",
        visibility: "public",
        type: ExerciseType.MULTIPLE_CHOICE,
        author: "utilizador atual", //exerciseState.exercisename,
        statement: {
          text: "",
        },
        problem: {
          statements: [""],
        },
      };
  }
}

enum ResolutionStatus {}

export interface Resolution {
  id: string;
  cotation: number;
  studentID: string;
  status: ResolutionStatus;
  data:
    | string
    | {
        [id: string]: {
          text: string;
          justification: string;
          type: string;
          value: boolean;
        };
      };
}

interface exercise {
  asd: string;
}

export interface Exercise {
  id: string;
  title: string;
  cotation?: number;
  specialistId: string;
  type: ExerciseType;
  statement: {
    imagePath: string;
    imagePosition: string;
    text: string;
  };
  justifyKind?: ExerciseJustificationKind;
  items?: { [id: string]: { text: string; type: string } };

  solution?: Resolution;
  resolution?: Resolution;
}

export interface ExerciseGroup {
  exercises: Exercise[];
  groupInstructions: string;
  groupCotations: number;
}

// Type of actions allowed on the state
export enum ExerciseActionKind {
  JUSTIFY = "JUSTIFY",
  SELECT_OPTION = "SELECT_OPTION",
  SET_TF = "SET_TF",
}

// ExerciseAction Definition
export interface ExerciseAction {
  type: ExerciseActionKind;
  payload: {
    optionID?: string;
    justify?: string;
    tfValue?: boolean;
  };
}

// Takes the current ExerciseState and an action to update the ExerciseState
function ExerciseSolveReducer(
  exerciseState: Exercise,
  exerciseAction: ExerciseAction
) {
  switch (exerciseAction.type) {
    case ExerciseActionKind.JUSTIFY:
      if (exerciseAction.payload.optionID && exerciseAction.payload.justify) {
        let newExercise = { ...exerciseState };
        exerciseAction.payload.exercises.forEach((element) => {
          newExercises[element.id] = element;
        });
        return { ...exerciseState, listExercises: newExercises };
      } else throw new Error("Invalid data provided in exerciseAction.payload");
    default:
      throw new Error("Unknown action");
  }
}
