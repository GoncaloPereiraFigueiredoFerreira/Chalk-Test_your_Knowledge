//------------------------------------//
//                                    //
//         createNewExercise          //
//                                    //
//------------------------------------//

import { CQExercise } from "./CQ/CQExercise";
import { ImgPos } from "./Header/ExHeader";
import { MCExercise } from "./MC/MCExercise";
import { OAExercise } from "./OA/OAExercise";
import { TFExercise } from "./TF/TFExercise";

//------------------------------------//
//                                    //
//            Resolution              //
//                                    //
//------------------------------------//

export enum ResolutionType {
  PENDING = "PENDING",
  GRADED = "GRADED",
  SOLUTION = "SOLUTION",
}

export interface Resolution {
  id?: string;
  cotation?: number;
  student?: {
    id: string;
    name: string;
    email: string;
  };
  type: ResolutionType;
  data: ResolutionData;
}

export type ResolutionData =
  | TFResolutionData
  | MCResolutionData
  | CQResolutionData
  | OAResolutionData;

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

export interface CQResolutionData {
  type: ExerciseType.CHAT;
  msgs: string[];
}

export interface ResolutionItems {
  [id: string]: {
    text: string;
    justification: string;
    value?: boolean;
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
  CHAT = "chat",
}
export enum ExerciseJustificationKind {
  JUSTIFY_ALL = "Todas",
  JUSTIFY_FALSE = "Apenas Falsas",
  JUSTIFY_UNMARKED = "Apenas Não Selecionadas",
  JUSTIFY_TRUE = "Apenas Verdadeiras",
  JUSTIFY_MARKED = "Apenas Selecionadas",
  NO_JUSTIFICATION = "Nenhuma",
}
export type Exercise2 = OAExercise | TFExercise | MCExercise | CQExercise;

export interface OAProps {}

export interface TFProps {
  items: ResolutionItems;
  justifyType: ExerciseJustificationKind;
}
export type MCProps = TFProps;

export interface CQProps {
  msgs: string[];
  maxAnswers: number;
  topics: string[];
}

export interface OAExercise {
  base: ExerciseBase;
  type: ExerciseType.OPEN_ANSWER;
  props: OAProps;
}

export interface TFExercise {
  base: ExerciseBase;
  type: ExerciseType.TRUE_OR_FALSE;
  props: TFProps;
}

export interface MCExercise {
  base: ExerciseBase;
  type: ExerciseType.MULTIPLE_CHOICE;
  props: MCProps;
}

export interface CQExercise {
  base: ExerciseBase;
  type: ExerciseType.CHAT;
  props: CQProps;
}

export interface ExerciseBase {
  title: string;
  statement: {
    imagePath?: string;
    imagePosition?: ImgPos;
    text: string;
  };
}

export interface ExerciseIdentity {
  id: string;
  cotation?: number;
  visibility: string;
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
  additionalProps?: ChatBasedProps;
}

export interface ChatBasedProps {
  topics: string[];
  maxMsgs: number;
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
    case ExerciseType.CHAT: {
      return (
        <CQExercise
          context={context}
          exercise={exercise}
          position={position}
        ></CQExercise>
      );
    }
  }
}
