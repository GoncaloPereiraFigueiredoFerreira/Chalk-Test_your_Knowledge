import { useReducer } from "react";
import { ImgPos } from "../Exercise/Header/ExHeader";
import "./EditExercise.css";
import { EditHeader } from "../Exercise/Header/EditHeader";
import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
  ExerciseComponent,
  ExerciseContext,
  ExerciseHeader,
  ResolutionData,
  TFResolutionData,
  MCResolutionData,
  OAResolutionData,
  InitResolutionDataEx,
} from "../Exercise/Exercise";

//------------------------------------//
//                                    //
//         ExerciseReducer            //
//                                    //
//------------------------------------//

export enum EditActionKind {
  CHANGE_STATEMENT = "CHANGE_STATEMENT",
  ADD_IMG = "ADD_IMG",
  REMOVE_IMG = "REMOVE_IMG",
  CHANGE_IMG_URL = "CHANGE_IMG_URL",
  CHANGE_IMG_POS = "CHANGE_IMG_POS",
  CHANGE_SOLUTION_TEXT = "CHANGE_SOLUTION_TEXT",
  ADD_ITEM = "ADD_ITEM",
  REMOVE_ITEM = "REMOVE_ITEM",
  CHANGE_ITEM_TF = "CHANGE_ITEM_TF",
  CHANGE_ITEM_MC = "CHANGE_ITEM_MC",
  CHANGE_ITEM_TEXT = "CHANGE_ITEM_TEXT",
  CHANGE_JUSTIFY_KIND = "CHANGE_JUSTIFY_KIND",
  CHANGE_MAX_ANSWERS = "CHANGE_MAX_ANSWERS",
  ADD_TOPIC = "ADD_TOPIC",
}

export interface EditAction {
  type: EditActionKind;
  dataString?: string;
  dataImgPos?: ImgPos;
  dataItem?: {
    text?: string;
    justification?: string;
    value?: boolean;
  };
  dataJK?: ExerciseJustificationKind;
}

function EditReducer(state: EditState, action: EditAction) {
  let exercise = state.exercise;
  let solution = state.solution;

  const changeStatement = (statement: ExerciseHeader): EditState => {
    return {
      ...state,
      exercise: {
        ...exercise,
        base: { ...exercise.base, statement: statement },
      },
    };
  };

  switch (action.type) {
    case EditActionKind.CHANGE_STATEMENT:
      let newStatement1: ExerciseHeader = {
        ...exercise.base.statement,
        text: action.dataString ?? "",
      };
      return changeStatement(newStatement1);

    case EditActionKind.ADD_IMG:
      let newStatement2: ExerciseHeader = {
        ...exercise.base.statement,
        imagePath: "",
        imagePosition: ImgPos.BOT,
      };
      return changeStatement(newStatement2);

    case EditActionKind.REMOVE_IMG:
      let newStatement3: ExerciseHeader = {
        text: exercise.base.statement.text,
      };
      return changeStatement(newStatement3);

    case EditActionKind.CHANGE_IMG_URL:
      let newStatement4: ExerciseHeader = {
        ...exercise.base.statement,
        imagePath: action.dataString ?? "",
      };
      return changeStatement(newStatement4);

    case EditActionKind.CHANGE_IMG_POS:
      let newStatement5: ExerciseHeader = {
        ...exercise.base.statement,
        imagePosition: action.dataImgPos ?? ImgPos.BOT,
      };
      return changeStatement(newStatement5);

    case EditActionKind.ADD_ITEM:
      if (
        exercise.type === ExerciseType.MULTIPLE_CHOICE ||
        exercise.type === ExerciseType.TRUE_OR_FALSE
      ) {
        let newSolution: TFResolutionData | MCResolutionData = {
          ...solution,
        } as MCResolutionData;
        if (exercise.props.items && newSolution.items) {
          let newSolItems = { ...newSolution.items };
          let newItems = { ...exercise.props.items };
          newItems[action.dataString!] = {
            text: "",
            justification: "",
            value: false,
          };
          newSolItems[action.dataString!] = {
            text: "",
            justification: "",
            value: false,
          };
          return {
            solution: { ...solution, items: newSolItems } as ResolutionData,
            exercise: {
              ...exercise,
              props: { ...exercise.props, items: newItems },
            },
          };
        }
      }
      throw new Error("Invalid action");

    case EditActionKind.CHANGE_ITEM_TEXT:
      if (
        exercise.type === ExerciseType.MULTIPLE_CHOICE ||
        exercise.type === ExerciseType.TRUE_OR_FALSE
      ) {
        let newSolution: TFResolutionData | MCResolutionData = {
          ...solution,
        } as MCResolutionData;
        let newItems = { ...exercise.props.items };
        let newSolutionItems = { ...newSolution.items };

        newItems[action.dataString!].text = action.dataItem!.text!;
        newSolutionItems[action.dataString!].text = action.dataItem!.text!;
        return {
          exercise: {
            ...exercise,
            props: { ...exercise.props, items: newItems },
          } as Exercise,
          solution: { ...solution, items: newItems } as ResolutionData,
        };
      } else throw new Error("Invalid action");

    case EditActionKind.CHANGE_ITEM_TF:
      if (exercise.type === ExerciseType.TRUE_OR_FALSE) {
        let newSolution: TFResolutionData | MCResolutionData = {
          ...solution,
        } as MCResolutionData;
        let newItems = { ...newSolution.items };
        newItems[action.dataString!].value = action.dataItem!.value!;
        return {
          ...state,
          solution: {
            ...state.solution,
            items: newItems,
          } as ResolutionData,
        };
      } else throw new Error("Invalid action");

    case EditActionKind.CHANGE_ITEM_MC:
      if (exercise.type === ExerciseType.MULTIPLE_CHOICE) {
        let newSolution: TFResolutionData | MCResolutionData = {
          ...solution,
        } as MCResolutionData;
        let newItems = { ...newSolution.items };
        let selectedID = action.dataString!;
        Object.keys(newItems).map((key) => {
          newItems[key].value = key === selectedID;
        });
        return {
          ...state,
          solution: {
            ...state.solution,
            items: newItems,
          } as ResolutionData,
        };
      } else throw new Error("Invalid action");

    case EditActionKind.REMOVE_ITEM:
      if (
        exercise.type === ExerciseType.MULTIPLE_CHOICE ||
        exercise.type === ExerciseType.TRUE_OR_FALSE
      ) {
        let newSolution: TFResolutionData | MCResolutionData = {
          ...solution,
        } as MCResolutionData;
        let newItems = { ...exercise.props.items };
        delete newItems[action.dataString!];

        let newSolutionItems = { ...newSolution.items };
        delete newSolutionItems[action.dataString!];
        return {
          exercise: {
            ...exercise,
            props: { ...exercise.props, items: newItems },
          },

          solution: {
            ...solution,
            items: newSolutionItems,
          } as ResolutionData,
        };
      } else throw new Error("Invalid action");

    case EditActionKind.CHANGE_SOLUTION_TEXT:
      if (exercise.type === ExerciseType.OPEN_ANSWER) {
        let newSolution: OAResolutionData = { ...solution } as OAResolutionData;

        newSolution.text = action.dataString!;
        return { ...state, solution: newSolution };
      }

      throw new Error("Invalid action");

    case EditActionKind.CHANGE_JUSTIFY_KIND:
      if (
        exercise.type === ExerciseType.MULTIPLE_CHOICE ||
        exercise.type === ExerciseType.TRUE_OR_FALSE
      )
        if (exercise.props.justifyType != action.dataJK!) {
          return {
            ...state,
            exercise: {
              ...exercise,
              props: {
                ...exercise.props,
                justifyType: action.dataJK!,
              },
            },
          };
        }
      throw new Error("Invalid action");
    default:
      return state;
  }
}

interface EditState {
  exercise: Exercise;
  solution: ResolutionData;
}

//------------------------------------//
//                                    //
//           EditExercise             //
//                                    //
//------------------------------------//

interface EditExerciseProps {
  exercise: Exercise;
  saveExercise: (state: EditState) => void;
  cancelEditExercise: (state: EditState) => void;
}

export function EditExercise({
  exercise,
  saveExercise,
  cancelEditExercise,
}: EditExerciseProps) {
  let solution = InitResolutionDataEx(exercise);

  let initState: EditState = { exercise: exercise, solution: solution };
  const [state, editDispatch] = useReducer(EditReducer, initState);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max mt-8 px-16 pb-8 bg-2-1">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Editar</label>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => saveExercise(state)}
          >
            Guardar e fechar
          </button>
        </div>
        <ExerciseComponent
          position="1"
          exercise={state.exercise}
          context={{
            context: ExerciseContext.PREVIEW,
          }}
        ></ExerciseComponent>
        <EditHeader dispatch={editDispatch} state={state.exercise} />
        <ExerciseComponent
          position="1"
          exercise={state.exercise}
          context={{
            context: ExerciseContext.EDIT,
            dispatch: editDispatch,
            solutionData: state.solution,
          }}
        ></ExerciseComponent>
        <div className="flex gap-2">
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => saveExercise(state)}
          >
            Guardar e fechar
          </button>
          <button
            className="transition-all duration-100 py-2 px-4 rounded-lg bg-btn-4-2"
            onClick={() => cancelEditExercise(state)}
          >
            Cancelar
          </button>
        </div>
      </div>
    </>
  );
}
