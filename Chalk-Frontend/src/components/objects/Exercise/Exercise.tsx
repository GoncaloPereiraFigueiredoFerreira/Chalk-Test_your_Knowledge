//------------------------------------//
//                                    //
//         createNewExercise          //
//                                    //
//------------------------------------//

import { ImgPos } from "./Header/ExHeader";

export function createNewExercise(newExercisetype: ExerciseType) {
  // colocar aqui as chamadas para criação dos exercicios
  let newExercise: Exercise = {
    id: "-1",
    title: "",
    cotation: 0,
    visibility: "private",
    specialistId: "This User",
    type: newExercisetype,
    statement: {
      text: "",
    },
  };

  switch (newExercisetype) {
    case ExerciseType.MULTIPLE_CHOICE:
      return {
        ...newExercise,
        justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      };
    case ExerciseType.OPEN_ANSWER:
      return {
        ...newExercise,
      };
    case ExerciseType.TRUE_OR_FALSE:
      return {
        ...newExercise,
        justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      };
    case ExerciseType.FILL_IN_THE_BLANK:
      return {
        ...newExercise,
        justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      };
    case ExerciseType.CODE:
      return {
        ...newExercise,
        justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      };
  }
}

//------------------------------------//
//                                    //
//            Resolution              //
//                                    //
//------------------------------------//

enum ResolutionStatus {
  PENDING = "pending",
}

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

//------------------------------------//
//                                    //
//             Exercise               //
//                                    //
//------------------------------------//

export enum ExerciseType {
  MULTIPLE_CHOICE = "multiple-choice",
  OPEN_ANSWER = "open-answer",
  TRUE_OR_FALSE = "true-or-false",
  FILL_IN_THE_BLANK = "fill-in-the-blank",
  CODE = "code",
}

export enum ExerciseJustificationKind {
  JUSTIFY_ALL = "X1",
  JUSTIFY_FALSE = "X2",
  JUSTIFY_UNMARKED = "X2",
  JUSTIFY_TRUE = "X3",
  JUSTIFY_MARKED = "X3",
  NO_JUSTIFICATION = "X0",
}

export interface Exercise {
  id: string;
  title: string;
  cotation?: number;
  specialistId: string;
  visibility: string;
  type: ExerciseType;
  statement: {
    imagePath?: string;
    imagePosition?: ImgPos;
    text: string;
  };
  justifyKind?: ExerciseJustificationKind;
  items?: { [id: string]: { text: string; type: string } };

  solution?: Resolution;
  resolution?: Resolution;
}

//------------------------------------//
//                                    //
//          ExerciseGroup             //
//                                    //
//------------------------------------//

export interface ExerciseGroup {
  exercises: Exercise[];
  groupInstructions: string;
  groupCotations: number;
}
