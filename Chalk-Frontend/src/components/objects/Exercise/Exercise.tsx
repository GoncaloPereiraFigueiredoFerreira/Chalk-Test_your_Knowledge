//------------------------------------//
//                                    //
//         createNewExercise          //
//                                    //
//------------------------------------//

import { ImgPos } from "./Header/ExHeader";
import { MCExercise } from "./MC/MCExercise";
import { OAExercise } from "./OA/OAExercise";
import { TFExercise } from "./TF/TFExercise";

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
    case ExerciseType.TRUE_OR_FALSE:
    case ExerciseType.MULTIPLE_CHOICE:
      return {
        ...newExercise,
        justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {
          "0": {
            text: "",
          },
        },
        solution: {
          data: {
            type: newExercisetype,
            items: {
              "0": {
                text: "",
                value: false,
                justification: "",
              },
            },
          },
        },
      } as Exercise;
    case ExerciseType.OPEN_ANSWER:
      return {
        ...newExercise,
        solution: { data: { type: newExercisetype, text: "" } },
      } as Exercise;
    case ExerciseType.FILL_IN_THE_BLANK:
      return {
        ...newExercise,
        justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      } as Exercise;
    case ExerciseType.CODE:
      return {
        ...newExercise,
        justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      } as Exercise;
  }
}

export function createNewResolution(exercise: Exercise) {
  // colocar aqui as chamadas para criação dos exercicios
  switch (exercise.type) {
    case ExerciseType.OPEN_ANSWER:
      return { text: "" };

    case ExerciseType.FILL_IN_THE_BLANK:
    case ExerciseType.CODE:
      return {};

    case ExerciseType.MULTIPLE_CHOICE:
    case ExerciseType.TRUE_OR_FALSE:
      let newResolutionData: TFResolutionData | MCResolutionData = {
        type: exercise.type,
        items: {},
      };

      if (newResolutionData.items != undefined && exercise.items != undefined)
        for (let key in exercise.items) {
          newResolutionData.items[key] = {
            value: false,
            text: "",
            justification: "",
          };
        }
      else throw new Error("Invalid State");

      let newResolution: Resolution = {
        data: newResolutionData,
      };

      return newResolution;
  }
}

//------------------------------------//
//                                    //
//            Resolution              //
//                                    //
//------------------------------------//

export enum ResolutionStatus {
  PENDING = "pending",
}

export type ResolutionData =
  | TFResolutionData
  | MCResolutionData
  | OAResolutionData;

export interface ResolutionItems {
  [id: string]: {
    text: string;
    justification: string;
    value?: boolean;
  };
}

export interface TFResolutionData {
  type: ExerciseType.TRUE_OR_FALSE;
  items: ResolutionItems;
}

export interface MCResolutionData {
  type: ExerciseType.MULTIPLE_CHOICE;
  items: ResolutionItems;
}

export interface OAResolutionData {
  type: ExerciseType.OPEN_ANSWER;
  text: string;
}

export interface Resolution {
  id?: string;
  cotation?: number;
  studentID?: string;
  status?: ResolutionStatus;
  data: ResolutionData;
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
  JUSTIFY_ALL = "Todas",
  JUSTIFY_FALSE = "Apenas Falsas",
  JUSTIFY_UNMARKED = "Apenas Não Selecionadas",
  JUSTIFY_TRUE = "Apenas Verdadeiras",
  JUSTIFY_MARKED = "Apenas Selecionadas",
  NO_JUSTIFICATION = "Nenhuma",
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
  items?: { [id: string]: { text: string } };

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
  groupCotation: number;
}

//------------------------------------//
//                                    //
//         ExerciseComponent          //
//                                    //
//------------------------------------//

export enum ExerciseContext {
  PREVIEW = "PREVIEW",
  SOLVE = "SOLVE",
  EDIT = "EDIT",
  GRADING = "GRADING",
  REVIEW = "REVIEW",
}

export interface ExerciseComponentProps {
  exercise: Exercise;
  position: string;
  context: ContextBasedProps;
}

type ContextBasedProps =
  | PreviewProps
  | SolveProps
  | CreateEditProps
  | GradingProps
  | ReviewProps;

export interface PreviewProps {
  context: ExerciseContext.PREVIEW;
}

export interface SolveProps {
  context: ExerciseContext.SOLVE;
  resolutionData: ResolutionData;
  setExerciseSolution: Function;
}

export interface CreateEditProps {
  context: ExerciseContext.EDIT;
  setExercise: Function;
}

export interface GradingProps {
  context: ExerciseContext.GRADING;
  setExerciseGrade: Function;
}

export interface ReviewProps {
  context: ExerciseContext.REVIEW;
}

export function ExerciseComponent({
  exercise,
  context,
  position,
}: ExerciseComponentProps) {
  switch (exercise.type) {
    case ExerciseType.MULTIPLE_CHOICE: {
      return (
        <MCExercise
          context={context}
          exercise={exercise}
          position={position}
        ></MCExercise>
      );
    }
    case ExerciseType.TRUE_OR_FALSE: {
      return (
        <TFExercise
          context={context}
          exercise={exercise}
          position={position}
        ></TFExercise>
      );
    }
    case ExerciseType.OPEN_ANSWER: {
      return (
        <OAExercise
          context={context}
          exercise={exercise}
          position={position}
        ></OAExercise>
      );
    }
  }
}
