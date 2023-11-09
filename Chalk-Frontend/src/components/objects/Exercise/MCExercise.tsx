import { useReducer, createContext, useContext, useState } from "react";

export function MCExercise(props: any) {
  let exerciseDisplay = <></>;
  switch (props.contexto) {
    case "solve":
      exerciseDisplay = (
        <MCSolve problem={props.problema} enunciado={props.enunciado}></MCSolve>
      );
      break;

    case "edit":
      exerciseDisplay = (
        <MCEdit problem={props.problema} enunciado={props.enunciado}></MCEdit>
      );
      break;

    case "previz":
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
        <p className="text-4xl strong mb-8">Escolha Múltipla</p>
        {exerciseDisplay}
      </div>
    </>
  );
}

function MCSolve(props: any) {
  const [state, setState] = useState("");

  return (
    <>
      <p className="mb-4">{props.enunciado.text}</p>
      <ul>
        {props.problem.statements.map((text: string, id: number) => {
          return (
            <MCStatement
              key={id}
              text={text}
              id={id}
              choose={setState}
            ></MCStatement>
          );
        })}
      </ul>
    </>
  );
}

function MCStatement(props: any) {
  return (
    <>
      <li className="flex mb-4">
        <input
          id={"mc" + props.id}
          name="mc"
          type="radio"
          className="radio-blue mr-3"
          onClick={() => props.choose(props.text)}
        ></input>
        <label htmlFor={"mc" + props.id}>{props.text}</label>
      </li>
    </>
  );
}

enum MCEditActionKind {
  ADDSTATEMENT = "ADDSTATEMENT",
  CHANGESTATEMENT = "CHANGESTATEMENT",
  CHANGEHEADER = "CHANGEHEADER",
  REMOVESTATE = "REMOVESTATE",
  RIGHTSTATEMENT = "RIGHTSTATEMENT",
}

type MCEditState = {
  header: string;
  statements: string[];
  correct: string;
};

type MCEditAction = {
  type: MCEditActionKind;
  payload: { id?: number; value?: string };
};

const MCEditStateContext = createContext<{ state: MCEditState; dispatch: any }>(
  {
    state: { header: "", statements: [], correct: "" },
    dispatch: undefined,
  }
);

function EditReducer(state: MCEditState, action: MCEditAction): MCEditState {
  switch (action.type) {
    case MCEditActionKind.ADDSTATEMENT:
      let temp1 = [...state.statements];
      temp1.push("");
      let addState = { ...state, statements: temp1 };
      return addState;

    case MCEditActionKind.RIGHTSTATEMENT:
      let correctstate = { ...state, correct: action.payload.value ?? "" };
      return correctstate;

    case MCEditActionKind.CHANGEHEADER:
      let headerState = { ...state };
      headerState.header = action.payload.value ?? "";
      return headerState;

    case MCEditActionKind.CHANGESTATEMENT:
      let changed = [...state.statements];
      changed[action.payload.id!] = action.payload.value ?? "";
      let statementState = { ...state, statements: changed };
      return statementState;

    case MCEditActionKind.REMOVESTATE:
      let removed = [...state.statements];
      let correct = state.correct;
      if (state.correct === state.statements[action.payload.id!]) {
        correct = "";
      }
      removed.splice(action.payload.id!, 1);
      let removeState = { ...state, statements: removed, correct: correct };
      return removeState;

    default:
      throw new Error();
  }
}

function MCEdit(props: any) {
  let initState: MCEditState = { header: "", statements: [], correct: "" };
  initState.header = props.enunciado.text;
  props.problem.statements.map((text: any) => initState.statements.push(text));
  const [state, dispatch] = useReducer(EditReducer, initState);

  return (
    <>
      <form>
        <div className="mb-9">
          <p className="block mb-2 ml-1 text-sm text-gray-900 dark:text-white">
            Enunciado:
          </p>
          <textarea
            id="message"
            className="header-textarea"
            placeholder=""
            value={state.header}
            onChange={(e) =>
              dispatch({
                type: MCEditActionKind.CHANGEHEADER,
                payload: { value: e.target.value },
              })
            }
          ></textarea>
        </div>

        <p className="block mb-2 text-sm text-gray-900 dark:text-white">
          Adicione as afirmações e escolha a opção correta.
        </p>
        <ul>
          <MCEditStateContext.Provider value={{ state, dispatch }}>
            {state.statements.map((_item, counter) => {
              return (
                <MCStatementEdit id={counter} key={counter}></MCStatementEdit>
              );
            })}
          </MCEditStateContext.Provider>
        </ul>
        <input
          type="button"
          className="edit-btn mt-4"
          value="Add"
          onClick={() => {
            dispatch({ type: MCEditActionKind.ADDSTATEMENT, payload: {} });
          }}
        ></input>
      </form>
    </>
  );
}

function MCStatementEdit(props: any) {
  let name = "mc";
  const { state, dispatch } = useContext(MCEditStateContext);
  return (
    <>
      <li className="flex items-center">
        <input
          className="radio-blue mr-3"
          type="radio"
          name={name}
          checked={state.correct === state.statements[props.id]}
          onChange={() =>
            dispatch({
              type: MCEditActionKind.RIGHTSTATEMENT,
              payload: { id: props.id, value: state.statements[props.id] },
            })
          }
        ></input>
        <input
          type="text"
          className="basic-input-text mr-3"
          onChange={(e) =>
            dispatch({
              type: MCEditActionKind.CHANGESTATEMENT,
              payload: { id: props.id, value: e.target.value },
            })
          }
          value={state.statements[props.id]}
        ></input>

        <input
          className="edit-btn"
          type="button"
          onClick={() =>
            dispatch({
              type: MCEditActionKind.REMOVESTATE,
              payload: { id: props.id },
            })
          }
          value="Remove"
        ></input>
      </li>
    </>
  );
}
