import { createContext, useContext, useReducer, useState } from "react";
import { ExerciseHeader } from "../ExHeader";
import {
  DownloadIcon,
  FileUploadIcon,
  ListIcon,
} from "../../SVGImages/SVGImages";

export function FBExercise(props: any) {
  let exerciseDisplay = <></>;

  switch (props.contexto) {
    case "solve":
      exerciseDisplay = (
        <FBSolve problem={props.problema} enunciado={props.enunciado}></FBSolve>
      );
      break;

    case "edit":
      exerciseDisplay = (
        <FBEdit problem={props.problema} enunciado={props.enunciado}></FBEdit>
      );
      break;

    case "preview":
      exerciseDisplay = <></>;
      break;

    case "correct":
      exerciseDisplay = <></>;
      break;

    case "psolution":
      exerciseDisplay = <></>;
      break;
  }
  return (
    <>
      <div className="m-5 text-xl">
        <p className="text-4xl strong mb-8">Preenchimento de Espaços</p>
        {exerciseDisplay}
      </div>
    </>
  );
}

type FBStatement = {
  phrase: string;
  options: string[];
};

type FBSolveState = {
  phrase: string;
  solution: string;
}[];

type FBSolvePayload = {
  id: number;
  value: string;
};

function FBSolveReducer(
  state: FBSolveState,
  Payload: FBSolvePayload
): FBSolveState {
  let transState = [...state];
  transState[Payload.id].solution = Payload.value;
  return transState;
}

function FBSolve(props: any) {
  let initState: FBSolveState = [];
  props.problem.statements.map((statement: FBStatement, index: number) => {
    initState.push({ phrase: statement.phrase, solution: "" });
  });

  const [state, setState] = useReducer(FBSolveReducer, initState);

  return (
    <>
      <ExerciseHeader header={props.enunciado}></ExerciseHeader>
      <span>
        {props.problem.statements.map(
          (statement: FBStatement, index: number) => {
            return (
              <FBSolveStatement
                key={index}
                statement={statement}
                id={index}
                setState={setState}
              ></FBSolveStatement>
            );
          }
        )}
      </span>
    </>
  );
}

function FBSolveStatement(props: {
  statement: FBStatement;
  id: number;
  setState: Function;
}) {
  let option = <></>;
  if (props.statement.options.length == 0) {
    option = (
      <input
        type="text"
        id={"fb" + props.id}
        className="basic-input-text"
        onChange={(e) => {
          props.setState({ id: props.id, value: e.target.value });
        }}
      ></input>
    );
  } else {
    option = (
      <select
        id={"fb" + props.id}
        className="basic-input-text"
        onChange={(e) => {
          props.setState({ id: props.id, value: e.target.value });
        }}
      >
        <option value="" disabled selected>
          Seleciona a opção
        </option>
        {props.statement.options.map((opt: string, id: number) => {
          return <option value={opt}>{opt}</option>;
        })}
      </select>
    );
  }

  return (
    <>
      {props.statement.phrase}
      {option}
    </>
  );
}

enum FBEditActionKind {
  ADDSEGMENT = "ADDSEGMENT",
  ADDOPTION = "ADDOPTION",
  REMOVESEGMENT = "REMOVESEGMENT",
  REMOVEOPTION = "REMOVEOPTION",
  CHANGESEGMENT = "CHANGESTATEMENT",
  CHOOSEOPT = "CHOOSEOPT",
  CHANGEHEADER = "CHANGEHEADER",
  CHANGEFINAL = "CHANGEFINAL",
}

type FBEditAction = {
  type: FBEditActionKind;
  payload: { id?: number; value?: string; optID?: number };
};

type FBEditState = {
  header: ExerciseHeader;
  segments: { phrase: string; options: string[]; correct: string }[];
  ending: string;
};

function FBEditReducer(state: FBEditState, action: FBEditAction) {
  let copyState = { ...state };
  switch (action.type) {
    case FBEditActionKind.ADDSEGMENT: {
      let copySegment = [...copyState.segments];
      copySegment.push({ phrase: "", options: [], correct: "" });
      copyState.segments = copySegment;
      return copyState;
    }
    case FBEditActionKind.CHANGEFINAL: {
      copyState.ending = action.payload.value ?? "";
      return copyState;
    }
    case FBEditActionKind.ADDOPTION: {
      let copySegments = [...copyState.segments];
      let copySegment = { ...copySegments[action.payload.id!] };
      let copyOptions = [...copySegment.options];
      copyOptions.push(action.payload.value ?? "");
      copySegment.options = copyOptions;
      copySegments[action.payload.id!] = copySegment;
      copyState.segments = copySegments;
      return copyState;
    }
    case FBEditActionKind.CHANGEHEADER: {
      copyState.header.text = action.payload.value ?? "";
      return copyState;
    }
    case FBEditActionKind.CHANGESEGMENT: {
      let copySegment = [...copyState.segments];
      let segment = { ...copySegment[action.payload.id!] };
      segment.phrase = action.payload.value ?? "";
      copySegment[action.payload.id!] = segment;
      copyState.segments = copySegment;
      return copyState;
    }
    case FBEditActionKind.CHOOSEOPT: {
      let copySegment = [...copyState.segments];
      copySegment[action.payload.id!].correct = action.payload.value!;
      copyState.segments = copySegment;
      return copyState;
    }
    case FBEditActionKind.REMOVEOPTION: {
      let copySegment = [...copyState.segments];
      let copyOptions = [...copySegment[action.payload.id!].options];
      if (
        copySegment[action.payload.id!].correct ===
        copySegment[action.payload.id!].options[action.payload.optID!]
      )
        copySegment[action.payload.id!].correct = "";
      copyOptions.splice(action.payload.optID!, 1);
      copySegment[action.payload.id!].options = copyOptions;
      copyState.segments = copySegment;
      return copyState;
    }
    case FBEditActionKind.REMOVESEGMENT: {
      copyState.segments.splice(action.payload.id!);
      return copyState;
    }
  }
}

const FBEditStateContext = createContext<{ state: FBEditState; dispatch: any }>(
  {
    state: { header: { text: "" }, segments: [], ending: "" },
    dispatch: undefined,
  }
);

function FBEdit(props: any) {
  let initState: FBEditState = {
    header: props.enunciado,
    segments: [],
    ending: "",
  };

  props.problem.statements.map((statement: FBStatement) => {
    initState.segments.push({
      phrase: statement.phrase,
      options: [...statement.options],
      correct: "",
    });
  });

  const [state, dispatch] = useReducer(FBEditReducer, initState);
  return (
    <>
      {/* <ExerciseHeaderEdit
        header={{
          text: "",
        }}
        editFunc={(t: string) => {
          dispatch({
            type: FBEditActionKind.CHANGEHEADER,
            payload: { value: t },
          });
        }}
      ></ExerciseHeaderEdit> */}
      <FBEditStateContext.Provider value={{ state, dispatch }}>
        <p className="block mb-2 text-sm text-gray-900 dark:text-white">
          Escreva o texto a ser substituído
        </p>
        <div className="w-full mb-4 border border-gray-200 rounded-lg bg-gray-50 dark:bg-gray-700 dark:border-gray-600">
          <div className="flex items-center justify-between px-3 py-2 border-b dark:border-gray-600">
            <div className="flex flex-wrap items-center divide-gray-200 sm:divide-x dark:divide-gray-600">
              <div className="flex items-center space-x-1 sm:pr-4">
                <button
                  type="button"
                  className="p-2 text-gray-500 rounded cursor-pointer hover:text-gray-900 hover:bg-gray-100 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600"
                >
                  <FileUploadIcon></FileUploadIcon>
                  <span className="sr-only">Attach file</span>
                </button>
              </div>
              <div className="flex flex-wrap items-center space-x-1 sm:pl-4">
                <button
                  type="button"
                  className="p-2 text-gray-500 rounded cursor-pointer hover:text-gray-900 hover:bg-gray-100 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600"
                >
                  <ListIcon></ListIcon>
                  <span className="sr-only">Add list</span>
                </button>

                <button
                  type="button"
                  className="p-2 text-gray-500 rounded cursor-pointer hover:text-gray-900 hover:bg-gray-100 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600"
                >
                  <DownloadIcon></DownloadIcon>
                  <span className="sr-only">Download</span>
                </button>
              </div>
            </div>
          </div>
          <div className="px-4 py-2 bg-white rounded-b-lg dark:bg-gray-800">
            <textarea
              id="editor"
              rows={3}
              className="block w-full px-0 text-gray-800 bg-white border-0 dark:bg-gray-800 focus:ring-0 dark:text-white dark:placeholder-gray-400"
              placeholder="Write your answer..."
              onChange={(e) => {}}
              required
            ></textarea>
          </div>
        </div>
        <p className="block mb-2 text-sm text-gray-900 dark:text-white">
          Assinale as palavras a preencher
        </p>

        {state.segments.map((_segment: any, id: number) => {
          return (
            <div className="flex-col">
              <FBEditStatement key={id} id={id}></FBEditStatement>
            </div>
          );
        })}
        <div className="mt-4">
          <input
            type="button"
            className="edit-btn"
            onClick={() => {
              dispatch({
                type: FBEditActionKind.ADDSEGMENT,
                payload: {},
              });
            }}
            value="+"
          ></input>
          <input
            type="text"
            className="basic-input-text"
            value={state.ending}
            placeholder="Segmento Final"
            onChange={(e) => {
              dispatch({
                type: FBEditActionKind.CHANGEFINAL,
                payload: { value: e.target.value },
              });
            }}
          ></input>
        </div>
        <div className="mt-5 p-3 border  border-gray-200 rounded dark:border-gray-700">
          <p className="font-medium">Preview:</p>
          <span>
            {state.segments.map((statement: FBStatement, index: number) => {
              return (
                <FBSolveStatement
                  key={index}
                  statement={statement}
                  id={index}
                  setState={() => {}}
                ></FBSolveStatement>
              );
            })}
          </span>
        </div>
      </FBEditStateContext.Provider>
    </>
  );
}

function FBEditStatement(props: any) {
  const { state, dispatch } = useContext(FBEditStateContext);
  let inputTxt = "";
  return (
    <span>
      <input
        type="button"
        className="edit-btn"
        value="X"
        onClick={(e) => {
          dispatch({
            type: FBEditActionKind.REMOVESEGMENT,
            payload: { id: props.id },
          });
        }}
      ></input>
      <input
        type="text"
        className="basic-input-text"
        placeholder={"Segmento " + props.id}
        value={state.segments[props.id].phrase}
        onChange={(e) => {
          dispatch({
            type: FBEditActionKind.CHANGESEGMENT,
            payload: { id: props.id, value: e.target.value },
          });
        }}
      ></input>
      <input
        type="text"
        className="basic-input-text"
        placeholder={"Opções"}
        onChange={(e) => (inputTxt = e.target.value)}
        onKeyDown={(e) => {
          if (e.key === "Enter")
            dispatch({
              type: FBEditActionKind.ADDOPTION,
              payload: { id: props.id, value: inputTxt },
            });
        }}
      ></input>

      {state.segments[props.id].options.map((opt: string, id: number) => {
        return (
          <input
            id={"opt-" + props.id + "-" + id}
            type="button"
            className="edit-btn"
            value={opt}
            onClick={() =>
              dispatch({
                type: FBEditActionKind.CHOOSEOPT,
                payload: { id: props.id, optID: id },
              })
            }
          ></input>
        );
      })}
    </span>
  );
}
