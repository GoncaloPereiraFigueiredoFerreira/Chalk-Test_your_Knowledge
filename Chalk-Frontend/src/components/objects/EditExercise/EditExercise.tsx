import { useEffect, useReducer } from "react";
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
import { Rubric, RubricContext } from "../Rubric/Rubric";
import { FiSave } from "react-icons/fi";
import { IoClose } from "react-icons/io5";

//------------------------------------//
//                                    //
//         ExerciseReducer            //
//                                    //
//------------------------------------//

export enum EditActionKind {
  CHANGE_TITLE = "CHANGE_TITLE",
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
  SET_TOPIC = "SET_TOPIC",
  SET_RUBRIC = "SET_RUBRIC",
  SET_SOLUTION = "SET_SOLUTION",
}

export interface EditAction {
  type: EditActionKind;
  dataString?: string;
  dataImgPos?: ImgPos;
  dataLString?: string[];
  dataItem?: {
    text?: string;
    justification?: string;
    value?: boolean;
  };
  dataSolution?: ResolutionData;
  dataRubric?: Rubric;
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
    case EditActionKind.CHANGE_TITLE:
      return {
        ...state,
        exercise: {
          ...exercise,
          base: {
            ...exercise.base,
            title: action.dataString,
          },
        },
      } as EditState;

      throw new Error("Invalid action");

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

    case EditActionKind.CHANGE_MAX_ANSWERS:
      if (exercise.type === ExerciseType.CHAT) {
        return {
          ...state,
          exercise: {
            ...exercise,
            props: {
              ...exercise.props,
              maxAnswers: Number.parseInt(action.dataString!),
            },
          },
        };
      }
      throw new Error("Invalid action");

    case EditActionKind.SET_TOPIC:
      if (exercise.type === ExerciseType.CHAT) {
        let newTopics = [...action.dataLString!];
        return {
          ...state,
          exercise: {
            ...exercise,
            props: {
              ...exercise.props,
              topics: newTopics,
            },
          },
        };
      }
      throw new Error("Invalid action");

    case EditActionKind.SET_RUBRIC:
      if (
        exercise.type === ExerciseType.OPEN_ANSWER ||
        exercise.type === ExerciseType.CHAT
      ) {
        return { ...state, rubric: action.dataRubric };
      }
      throw new Error("Invalid action");

    case EditActionKind.SET_SOLUTION:
      if (
        exercise.type === ExerciseType.MULTIPLE_CHOICE ||
        exercise.type === ExerciseType.TRUE_OR_FALSE
      ) {
        return {
          ...state,
          solution: action.dataSolution ?? InitResolutionDataEx(exercise),
        };
      }
      throw new Error("Invalid action");

    default:
      return state;
  }
}

interface EditState {
  exercise: Exercise;
  rubric?: Rubric;
  solution: ResolutionData;
}

//------------------------------------//
//                                    //
//           EditExercise             //
//                                    //
//------------------------------------//

interface EditExerciseProps {
  position?: string;
  exercise: Exercise;
  solution: ResolutionData;
  rubric?: Rubric;
  saveEdit: (state: EditState) => void;
  cancelEdit: (state: EditState) => void;
}

export function EditExercise({
  position,
  exercise,
  solution,
  rubric,
  saveEdit,
  cancelEdit,
}: EditExerciseProps) {
  const initState: EditState = {
    exercise: exercise,
    solution: solution ?? InitResolutionDataEx(exercise),
    rubric: rubric,
  };
  const [state, editDispatch] = useReducer(EditReducer, initState);

  useEffect(() => {
    if (
      exercise.type == ExerciseType.CHAT ||
      exercise.type == ExerciseType.OPEN_ANSWER
    )
      editDispatch({
        type: EditActionKind.SET_RUBRIC,
        dataRubric: rubric,
      });
  }, [rubric]);

  useEffect(() => {
    if (
      exercise.type == ExerciseType.MULTIPLE_CHOICE ||
      exercise.type == ExerciseType.TRUE_OR_FALSE
    )
      editDispatch({
        type: EditActionKind.SET_SOLUTION,
        dataSolution: solution,
      });
  }, [solution]);

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max mt-8 bg-2-1">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
          <label className="flex text-title-1">Editar</label>
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-btn-4-1 group"
            onClick={() => cancelEdit(state)}
          >
            <IoClose className="size-5" />
            Cancelar
          </button>
        </div>
        <div className="rounded-lg bg-3-2">
          <ExerciseComponent
            position={position ? position : "1"}
            exercise={state.exercise}
            context={{
              context: ExerciseContext.PREVIEW,
            }}
          ></ExerciseComponent>
        </div>
        <div className="flex gap-4 px-4 rounded-lg bg-3-2">
          <strong>Título:</strong>
          <div className="px-4">
            <input
              className="rounded-lg bg-input-2"
              placeholder="Novo Teste"
              value={state.exercise.base.title}
              onChange={(e) =>
                editDispatch({
                  type: EditActionKind.CHANGE_TITLE,
                  dataString: e.target.value,
                } as EditAction)
              }
            />
          </div>
        </div>
        <div className="px-5 py-2 rounded-lg bg-3-2">
          <EditHeader dispatch={editDispatch} state={state.exercise} />
        </div>

        <div className="px-5 py-2 rounded-lg bg-3-2">
          <h3 className="font-medium text-xl">Detalhes do Exercício:</h3>
          <ExerciseComponent
            position={position ?? "1"}
            exercise={state.exercise}
            context={{
              context: ExerciseContext.EDIT,
              dispatch: editDispatch,
              solutionData: state.solution,
            }}
          ></ExerciseComponent>
        </div>
        {state.exercise.type === ExerciseType.CHAT ||
        state.exercise.type === ExerciseType.OPEN_ANSWER ? (
          <>
            <h3 className="font-medium text-xl">Rúbrica:</h3>
            <Rubric
              context={{
                context: RubricContext.EDIT,
                setRubric: (rubric: Rubric) => {
                  editDispatch({
                    type: EditActionKind.SET_RUBRIC,
                    dataRubric: rubric,
                  });
                },
              }}
              rubric={state.rubric ?? { criteria: [] }}
            ></Rubric>
          </>
        ) : (
          <></>
        )}
        <div className="flex gap-2">
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-btn-4-1 group"
            onClick={() => saveEdit(state)}
          >
            <FiSave className="size-5" />
            Guardar e fechar
          </button>
          <button
            className="flex p-3 items-center gap-2 rounded-md bg-btn-4-1 group"
            onClick={() => cancelEdit(state)}
          >
            <IoClose className="size-5" />
            Cancelar
          </button>
        </div>
      </div>
    </>
  );
}
