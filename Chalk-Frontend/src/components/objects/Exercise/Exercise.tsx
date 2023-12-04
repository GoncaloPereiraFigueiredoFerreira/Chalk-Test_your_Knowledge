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
        author: "utilizador atual", //userState.username,
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
        author: "utilizador atual", //userState.username,
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
        author: "utilizador atual", //userState.username,
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
        author: "utilizador atual", //userState.username,
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
        author: "utilizador atual", //userState.username,
        statement: {
          text: "",
        },
        problem: {
          statements: [""],
        },
      };
  }
}

export interface TFStatement {
  phrase: string;
  tfvalue: string;
  justification: string;
}

export interface Exercise {
  id: string;
  name: string;
  visibility: string;
  type: ExerciseType;
  author: string;
  statement: {
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
  solution?: {
    multiple_choice?: string;
    open_answer?: string;
    true_or_false?: TFStatement[];
  };
  resolution?: {
    multiple_choice?: string;
    open_answer?: string;
    true_or_false?: TFStatement[];
  };
}
