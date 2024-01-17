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
  justification?: string;
  value?: boolean;
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
  tags: string[];
}

export interface ExerciseIdentity {
  id: string;
  points?: number;
  specialistId: string;
  visibility: string;
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
  resolution?: ResolutionData;
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
  const base = {
    title: "Novo Exercício",
    statement: {
      text: "",
    },
    tags: [],
  };

  const identity: ExerciseIdentity = {
    id: "",
    specialistId: "",
    visibility: "public",
  };

  switch (type) {
    case ExerciseType.TRUE_OR_FALSE:
      const TFExercise: TFExercise = {
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
      const MCExercise: MCExercise = {
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
      const OAExercise: OAExercise = {
        base: base,
        type: ExerciseType.OPEN_ANSWER,
        props: {},
        identity: identity,
      };
      newExercise = OAExercise;
      break;

    case ExerciseType.CHAT:
      const CQExercise: CQExercise = {
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
      const CQRes: CQResolutionData = {
        type: ExerciseType.CHAT,
        msgs: [exercise.base.statement.text],
      };
      newRes = CQRes;
      break;
    case ExerciseType.MULTIPLE_CHOICE:
      const MCRes: MCResolutionData = {
        type: ExerciseType.MULTIPLE_CHOICE,
        items: { ...exercise.props.items },
        justifyType: exercise.props.justifyType,
      };
      newRes = MCRes;
      break;
    case ExerciseType.TRUE_OR_FALSE:
      const TFRes: TFResolutionData = {
        type: ExerciseType.TRUE_OR_FALSE,
        items: { ...exercise.props.items },
        justifyType: exercise.props.justifyType,
      };
      newRes = TFRes;
      break;

    case ExerciseType.OPEN_ANSWER:
      const OARes: OAResolutionData = {
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
      const CQRes: CQResolutionData = {
        type: ExerciseType.CHAT,
        msgs: [],
      };
      newRes = CQRes;
      break;
    case ExerciseType.MULTIPLE_CHOICE:
      const MCRes: MCResolutionData = {
        type: ExerciseType.MULTIPLE_CHOICE,
        justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      };
      newRes = MCRes;
      break;
    case ExerciseType.TRUE_OR_FALSE:
      const TFRes: TFResolutionData = {
        type: ExerciseType.TRUE_OR_FALSE,
        justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
        items: {},
      };
      newRes = TFRes;
      break;

    case ExerciseType.OPEN_ANSWER:
      const OARes: OAResolutionData = {
        type: ExerciseType.OPEN_ANSWER,
        text: "",
      };
      newRes = OARes;
      break;
  }
  return newRes;
}

//----------------------------------------//
//                                        //
//      Translate exercise functions      //
//                                        //
//----------------------------------------//

export function TranslateExerciseOUT(exercise: Exercise): {
  exerciseTR: any;
  solutionTR: any;
  //faltam as tags
} {
  let exerciseTR = {};

  const exerciseBASE = {
    title: exercise.base.title,
    visibility: exercise.identity.visibility,
    statement: exercise.base.statement,
    specialistId: exercise.identity.specialistId,
  };

  let solutionTR: any = {};

  switch (exercise.type) {
    case ExerciseType.OPEN_ANSWER:
      exerciseTR = {
        ...exerciseBASE,
        type: "OA",
      };
      solutionTR["type"] = "OA";
      solutionTR["text"] = "Something ....";
      break;

    case ExerciseType.MULTIPLE_CHOICE:
    case ExerciseType.TRUE_OR_FALSE:
      let mcType = exercise.type === ExerciseType.MULTIPLE_CHOICE ? "1" : "2";
      switch (exercise.props.justifyType) {
        case ExerciseJustificationKind.NO_JUSTIFICATION:
          mcType += "0";
          break;
        case ExerciseJustificationKind.JUSTIFY_ALL:
          mcType += "1";
          break;
        case ExerciseJustificationKind.JUSTIFY_UNMARKED:
        case ExerciseJustificationKind.JUSTIFY_FALSE:
          mcType += "2";
          break;
        case ExerciseJustificationKind.JUSTIFY_MARKED:
        case ExerciseJustificationKind.JUSTIFY_TRUE:
          mcType += "3";
          break;
      }

      const newExItems: { [id: string]: any } = {};
      const newSolItems: { [id: string]: any } = {};

      Object.keys(exercise.props.items).map((key) => {
        newExItems[key] = {
          type: "string",
          text: exercise.props.items[key].text,
        };
        newSolItems[key] = {
          value: exercise.props.items[key].value,
        };
      });

      exerciseTR = {
        ...exerciseBASE,
        type: "MC",
        mctype: Number.parseInt(mcType),
        items: newExItems,
      };
      solutionTR = {
        type: "MC",
        items: newSolItems,
      };
      break;

    case ExerciseType.CHAT:
      exerciseTR = {
        ...exerciseBASE,
        type: "CE",
        topics: exercise.props.topics,
        maxAnswers: exercise.props.maxAnswers,
      };
      solutionTR["type"] = "CE";
      break;
  }

  return { exerciseTR: exerciseTR, solutionTR: solutionTR };
}

export function TranslateExerciseIN(exercise: any): Exercise {
  const tags: string[] = [];
  exercise.tags.map((tag: any) => {
    tags.push(tag.name);
  });

  const exerciseBase = {
    base: {
      title: exercise.title,
      statement: exercise.statement,
      tags: tags,
    },
    identity: {
      id: exercise.id,
      specialistId: exercise.specialistId,
      visibility: exercise.visibility,
    },
  };
  let type: ExerciseType;
  let exerciseTR: Exercise;

  switch (exercise.type) {
    case "MC":
      let justification: ExerciseJustificationKind;
      type =
        (exercise.mctype as number).toString().charAt(0) === "1"
          ? ExerciseType.MULTIPLE_CHOICE
          : ExerciseType.TRUE_OR_FALSE;

      switch ((exercise.mctype as number).toString().charAt(1)) {
        case "0":
          justification = ExerciseJustificationKind.NO_JUSTIFICATION;
          break;
        case "1":
          justification = ExerciseJustificationKind.JUSTIFY_ALL;
          break;
        case "2":
          justification =
            type === ExerciseType.MULTIPLE_CHOICE
              ? ExerciseJustificationKind.JUSTIFY_UNMARKED
              : ExerciseJustificationKind.JUSTIFY_FALSE;

          break;
        case "3":
          justification =
            type === ExerciseType.MULTIPLE_CHOICE
              ? ExerciseJustificationKind.JUSTIFY_MARKED
              : ExerciseJustificationKind.JUSTIFY_TRUE;
          break;
        default:
          justification = ExerciseJustificationKind.NO_JUSTIFICATION;
      }
      const items: any = {};

      Object.keys(exercise.items).map((key: string) => {
        items[key] = { text: exercise.items[key].text };
      });

      const propsMC: MCProps = {
        items: items,
        justifyType: justification,
      };

      exerciseTR = { ...exerciseBase, type: type, props: propsMC };

      break;

    case "CE":
      const propsCQ: CQProps = {
        maxAnswers: exercise.maxAnswers,
        topics: exercise.topics,
      };
      type = ExerciseType.CHAT;
      exerciseTR = { ...exerciseBase, type: type, props: propsCQ };
      break;
    case "OA":
      type = ExerciseType.OPEN_ANSWER;
      exerciseTR = { ...exerciseBase, type: type, props: {} };
      break;
    default:
      exerciseTR = {
        ...exerciseBase,
        type: ExerciseType.OPEN_ANSWER,
        props: {},
      };
  }

  return exerciseTR;
}

export function TranslateTestExerciseIN(exercise: any): Exercise {
  const newEx = TranslateExerciseIN(exercise.exercise);
  newEx.identity.points = exercise.points;
  return newEx;
}

export function TranslateTestExerciseOut(exercise: Exercise) {
  return {
    type: "reference",
    points: exercise.identity.points,
    id: exercise.identity.id,
  };
}

export function TranslateResolutionIN(
  solution: any,
  exercise: Exercise
): ResolutionData {
  let newRes: ResolutionData;
  switch (exercise.type) {
    case ExerciseType.CHAT:
      const CQRes: CQResolutionData = {
        type: ExerciseType.CHAT,
        msgs: [...solution.chat], // CHECK WITH BRONZE
      };
      newRes = CQRes;
      break;

    case ExerciseType.MULTIPLE_CHOICE:
      const MCRes: MCResolutionData = {
        type: ExerciseType.MULTIPLE_CHOICE,
        items: { ...exercise.props.items },
        justifyType: exercise.props.justifyType,
      };
      Object.keys(solution.items).map((key) => {
        MCRes.items[key].value = solution.items[key].value;
        MCRes.items[key].justification = solution.items[key].justification;
      });

      newRes = MCRes;
      break;

    case ExerciseType.TRUE_OR_FALSE:
      const TFRes: TFResolutionData = {
        type: ExerciseType.TRUE_OR_FALSE,
        items: { ...exercise.props.items },
        justifyType: exercise.props.justifyType,
      };
      Object.keys(solution.items).map((key) => {
        TFRes.items[key].value = solution.items[key].value;
        TFRes.items[key].justification = solution.items[key].justification;
      });
      newRes = TFRes;
      break;

    case ExerciseType.OPEN_ANSWER:
      const OARes: OAResolutionData = {
        type: ExerciseType.OPEN_ANSWER,
        text: solution.text,
      };
      newRes = OARes;
      break;
  }
  return newRes;
}

export function TranslateResolutionOUT(resolution: ResolutionData) {
  switch (resolution.type) {
    case ExerciseType.CHAT:
      return { type: "CE", chat: [...resolution.msgs] };

    case ExerciseType.OPEN_ANSWER:
      return { type: "OA", text: resolution.text };

    case ExerciseType.MULTIPLE_CHOICE:
    case ExerciseType.TRUE_OR_FALSE:
      return { type: "MC", items: { ...resolution.items } };
  }
}
export function TranslateTestResolutionIN(resolution: any): ResolutionData {
  let newRes: ResolutionData;
  switch (resolution.type) {
    case "CE":
      const CQRes: CQResolutionData = {
        type: ExerciseType.CHAT,
        msgs: [...resolution.chat],
      };
      newRes = CQRes;
      break;

    case "MC":
      // Got to fix this
      const MCRes: TFResolutionData = {
        type: ExerciseType.TRUE_OR_FALSE,
        items: resolution.items,
        justifyType: ExerciseJustificationKind.JUSTIFY_ALL,
      };
      newRes = MCRes;
      break;

    case "OA":
      const OARes: OAResolutionData = {
        type: ExerciseType.OPEN_ANSWER,
        text: resolution.text,
      };
      newRes = OARes;
      break;

    default:
      newRes = InitResolutionDataType(ExerciseType.MULTIPLE_CHOICE);
  }
  return newRes;
}
