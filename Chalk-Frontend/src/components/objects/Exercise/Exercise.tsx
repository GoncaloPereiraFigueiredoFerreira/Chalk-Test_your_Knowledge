import { EditAction } from "../EditExercise/EditExercise";
import { CQExerciseComp } from "./CQ/CQExercise";
import { ImgPos } from "./Header/ExHeader";
import { MCExerciseComp } from "./MC/MCExercise";
import { OAExerciseComp } from "./OA/OAExercise";
import { TFExerciseComp } from "./TF/TFExercise";

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
  id: string;
  exerciseID: string;
  cotation: number;
  comment?: string;
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
  justifyType: ExerciseJustificationKind;
  items: ResolutionItems;
}

export interface MCResolutionData {
  type: ExerciseType.MULTIPLE_CHOICE;
  justifyType: ExerciseJustificationKind;
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
  [id: string]: ResolutionItem;
}

export interface ResolutionItem {
  text: string;
  justification: string;
  value: boolean;
}

//------------------------------------//
//                                    //
//            Resolutions             //
//                                    //
//------------------------------------//

export interface Resolutions {
  [exerciseID: string]: {
    solution: Resolution;
    cotation: number;
    studentRes: { [resolutionID: string]: Resolution };
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

export function ExerciseTypeToString(type: ExerciseType) {
  switch (type) {
    case ExerciseType.MULTIPLE_CHOICE:
      return "Multiple Choice Exercise";
    case ExerciseType.OPEN_ANSWER:
      return "Open Answer Exercise";
    case ExerciseType.TRUE_OR_FALSE:
      return "True-False Exercise";
    case ExerciseType.CHAT:
      return "Chat-Based Exercise";
  }
}

export enum ExerciseJustificationKind {
  JUSTIFY_ALL = "Todas",
  JUSTIFY_FALSE = "Apenas Falsas",
  JUSTIFY_UNMARKED = "Apenas Não Selecionadas",
  JUSTIFY_TRUE = "Apenas Verdadeiras",
  JUSTIFY_MARKED = "Apenas Selecionadas",
  NO_JUSTIFICATION = "Nenhuma",
}
export type Exercise = OAExercise | TFExercise | MCExercise | CQExercise;

export interface OAProps {}

export interface TFProps {
  items: ResolutionItems;
  justifyType: ExerciseJustificationKind;
}
export type MCProps = TFProps;

export interface CQProps {
  maxAnswers: number;
  topics: string[];
}

export interface OAExercise {
  identity: ExerciseIdentity;
  base: ExerciseBase;
  type: ExerciseType.OPEN_ANSWER;
  props: OAProps;
}

export interface TFExercise {
  identity: ExerciseIdentity;
  base: ExerciseBase;
  type: ExerciseType.TRUE_OR_FALSE;
  props: TFProps;
}

export interface MCExercise {
  identity: ExerciseIdentity;
  base: ExerciseBase;
  type: ExerciseType.MULTIPLE_CHOICE;
  props: MCProps;
}

export interface CQExercise {
  identity: ExerciseIdentity;
  base: ExerciseBase;
  type: ExerciseType.CHAT;
  props: CQProps;
}

export interface ExerciseHeader {
  text: string;
  imagePath?: string;
  imagePosition?: ImgPos;
}

export interface ExerciseBase {
  title: string;
  statement: ExerciseHeader;
}

export interface ExerciseIdentity {
  id: string;
  cotation?: number;
  specialistId: string;
  visibility: string;
}

//------------------------------------//
//                                    //
//          ExerciseGroup             //
//                                    //
//------------------------------------//

// REDUNDANTE -> importar de Test
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

export type ContextBasedProps =
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
  dispatch: React.Dispatch<EditAction>;
  solutionData: ResolutionData;
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
        <MCExerciseComp
          context={context}
          exercise={exercise}
          position={position}
        ></MCExerciseComp>
      );
    }
    case ExerciseType.TRUE_OR_FALSE: {
      return (
        <TFExerciseComp
          context={context}
          exercise={exercise}
          position={position}
        ></TFExerciseComp>
      );
    }
    case ExerciseType.OPEN_ANSWER: {
      return (
        <OAExerciseComp
          context={context}
          exercise={exercise}
          position={position}
        ></OAExerciseComp>
      );
    }
    case ExerciseType.CHAT: {
      return (
        <CQExerciseComp
          context={context}
          exercise={exercise}
          position={position}
        ></CQExerciseComp>
      );
    }
  }
}

//------------------------------------//
//                                    //
//      Init exercise functions       //
//                                    //
//------------------------------------//

export function InitExercise(type: ExerciseType): Exercise {
  let newExercise: Exercise;
  let base = {
    title: "",
    statement: {
      text: "",
    },
  };

  let identity: ExerciseIdentity = {
    id: "",
    specialistId: "",
    visibility: "public",
  };

  switch (type) {
    case ExerciseType.TRUE_OR_FALSE:
      let TFExercise: TFExercise = {
        base: base,
        type: ExerciseType.TRUE_OR_FALSE,
        props: {
          items: {},
          justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
        identity: identity,
      };
      newExercise = TFExercise;
      break;
    case ExerciseType.MULTIPLE_CHOICE:
      let MCExercise: MCExercise = {
        base: base,
        type: ExerciseType.MULTIPLE_CHOICE,
        props: {
          items: {},
          justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
        identity: identity,
      };
      newExercise = MCExercise;
      break;
    case ExerciseType.OPEN_ANSWER:
      let OAExercise: OAExercise = {
        base: base,
        type: ExerciseType.OPEN_ANSWER,
        props: {},
        identity: identity,
      };
      newExercise = OAExercise;
      break;

    case ExerciseType.CHAT:
      let CQExercise: CQExercise = {
        base: base,
        type: ExerciseType.CHAT,
        props: {
          maxAnswers: 0,
          topics: [],
        },
        identity: identity,
      };
      newExercise = CQExercise;
  }
  return newExercise;
}

export function InitResolutionDataEx(exercise: Exercise): ResolutionData {
  let newRes: ResolutionData;
  switch (exercise.type) {
    case ExerciseType.CHAT:
      let CQRes: CQResolutionData = {
        type: ExerciseType.CHAT,
        msgs: [exercise.base.statement.text],
      };
      newRes = CQRes;
      break;
    case ExerciseType.MULTIPLE_CHOICE:
      let MCRes: MCResolutionData = {
        type: ExerciseType.MULTIPLE_CHOICE,
        items: { ...exercise.props.items },
        justifyType: exercise.props.justifyType,
      };
      newRes = MCRes;
      break;
    case ExerciseType.TRUE_OR_FALSE:
      let TFRes: TFResolutionData = {
        type: ExerciseType.TRUE_OR_FALSE,
        items: { ...exercise.props.items },
        justifyType: exercise.props.justifyType,
      };
      newRes = TFRes;
      break;

    case ExerciseType.OPEN_ANSWER:
      let OARes: OAResolutionData = {
        type: ExerciseType.OPEN_ANSWER,
        text: "",
      };
      newRes = OARes;
      break;
  }
  return newRes;
}

export function InitResolutionDataType(type: ExerciseType): ResolutionData {
  let newRes: ResolutionData;
  switch (type) {
    case ExerciseType.CHAT:
      let CQRes: CQResolutionData = {
        type: ExerciseType.CHAT,
        msgs: [],
      };
      newRes = CQRes;
      break;
    case ExerciseType.MULTIPLE_CHOICE:
      let MCRes: MCResolutionData = {
        type: ExerciseType.MULTIPLE_CHOICE,
        justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      };
      newRes = MCRes;
      break;
    case ExerciseType.TRUE_OR_FALSE:
      let TFRes: TFResolutionData = {
        type: ExerciseType.TRUE_OR_FALSE,
        justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      };
      newRes = TFRes;
      break;

    case ExerciseType.OPEN_ANSWER:
      let OARes: OAResolutionData = {
        type: ExerciseType.OPEN_ANSWER,
        text: "",
      };
      newRes = OARes;
      break;
  }
  return newRes;
}
