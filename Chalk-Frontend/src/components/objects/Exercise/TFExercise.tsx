import { useReducer, createContext, useContext } from "react";

type TFExerciseProps = {
  enunciado: any;
  problema?: any;
  contexto: string;
  cotacao?: number;
};

export function TFExercise(props: TFExerciseProps) {
  let exerciseDisplay = <></>;

  switch (props.contexto) {
    case "solve":
      exerciseDisplay = (
        <TFSolve problem={props.problema} enunciado={props.enunciado}></TFSolve>
      );
      break;

    case "edit":
      exerciseDisplay = (
        <TFEdit problem={props.problema} enunciado={props.enunciado}></TFEdit>
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
        <p className="text-4xl strong mb-8">Verdadeiro ou Falso</p>
        {exerciseDisplay}
      </div>
    </>
  );
}

enum TFSolveActionKind {
  CHOOSE = "CHOOSE",
  JUSTIFY = "JUSTIFY",
}

type TFSolveAction = {
  type: TFSolveActionKind;
  index: number;
  value: string;
};

type TFSolve = {
  phrase: string;
  tfvalue: string;
  justification: string;
};

type SolveState = TFSolve[];

function SolveReducer(state: SolveState, action: TFSolveAction): SolveState {
  switch (action.type) {
    case TFSolveActionKind.CHOOSE:
      let chooseState = [...state];
      chooseState[action.index].tfvalue = action.value;
      return chooseState;

    case TFSolveActionKind.JUSTIFY:
      let justifyState = [...state];
      justifyState[action.index].justification = action.value;
      return justifyState;
    default:
      throw new Error();
  }
}

const StateContext = createContext<{ state: SolveState; dispatch: any }>({
  state: [],
  dispatch: undefined,
});

function TFSolve(props: any) {
  let initState: SolveState = [];
  props.problem.statements.map((text: any) =>
    initState.push({ phrase: text, tfvalue: "", justification: "" })
  );

  const [state, dispatch] = useReducer(SolveReducer, initState);

  return (
    <>
      <p>{props.enunciado.text}</p>
      <table className="table-auto mt-4">
        <thead>
          <tr>
            <th className="p-3">V</th>
            <th className="p-3">F</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <StateContext.Provider value={{ state, dispatch }}>
            {state.map((_solve: TFSolve, index: number) => {
              return <TFStatement key={index} id={index} justify={true} />;
            })}
          </StateContext.Provider>
        </tbody>
      </table>
    </>
  );
}

function TFStatement(props: any) {
  let name = "radio-button-" + props.id;
  const { state, dispatch } = useContext(StateContext);
  return (
    <tr>
      <td className="p-3">
        <input
          className="radio-green"
          type="radio"
          name={name}
          onChange={() =>
            dispatch({
              type: TFSolveActionKind.CHOOSE,
              index: props.id,
              value: "true",
            })
          }
        ></input>
      </td>
      <td className="p-3">
        <input
          className="radio-red"
          type="radio"
          name={name}
          onChange={() =>
            dispatch({
              type: TFSolveActionKind.CHOOSE,
              index: props.id,
              value: "false",
            })
          }
        ></input>
      </td>
      <td>
        {props.justify ? (
          <TFJustify key={props.id} id={props.id}></TFJustify>
        ) : (
          <p>{state[props.id].phrase}</p>
        )}
      </td>
    </tr>
  );
}

function TFJustify(props: any) {
  let hidden = "hidden";
  let fontsize = "";
  const { state, dispatch } = useContext(StateContext);
  if (state[props.id].tfvalue === "false") {
    hidden = "";
    fontsize = "text-xs";
  }
  return (
    <div>
      <p className={fontsize}>{state[props.id].phrase}</p>
      <input
        className={hidden + " basic-input-text"}
        type="text"
        name={"justification" + props.id}
        placeholder="Justifique a sua resposta"
        value={state[props.id].justification}
        onChange={(e) =>
          dispatch({
            type: TFSolveActionKind.JUSTIFY,
            index: props.id,
            value: e.target.value,
          })
        }
      ></input>
    </div>
  );
}

enum TFEditActionKind {
  JUSTIFYFALSE = "JUSTIFYFALSE",
  ADDSTATEMENT = "ADDSTATEMENT",
  CORRECTTF = "CORRECTTF",
  CHANGESTATEMENT = "CHANGESTATEMENT",
  CHANGEHEADER = "CHANGEHEADER",
  REMOVESTATE = "REMOVESTATE",
}

type TFEditState = {
  justify: boolean;
  header: string;
  statements: { phrase: string; tfvalue: string }[];
};

type TFEditAction = {
  type: TFEditActionKind;
  payload: { id?: number; flag?: boolean; value?: string };
};

const TFEditStateContext = createContext<{ state: TFEditState; dispatch: any }>(
  {
    state: { justify: false, header: "", statements: [] },
    dispatch: undefined,
  }
);

function TFEditReducer(state: TFEditState, action: TFEditAction) {
  switch (action.type) {
    case TFEditActionKind.JUSTIFYFALSE:
      let justifyState = { ...state };
      justifyState.justify = action.payload.flag ?? false;
      return justifyState;

    case TFEditActionKind.ADDSTATEMENT:
      let temp1 = [...state.statements];
      temp1.push({ phrase: "", tfvalue: "" });
      let addState = { ...state, statements: temp1 };
      return addState;

    case TFEditActionKind.CORRECTTF:
      let temp2 = [...state.statements];
      temp2[action.payload.id!].tfvalue = action.payload.value ?? "";
      let correctstate = { ...state, statments: temp2 };

      return correctstate;

    case TFEditActionKind.CHANGEHEADER:
      let headerState = { ...state };
      headerState.header = action.payload.value ?? "";
      return headerState;

    case TFEditActionKind.CHANGESTATEMENT:
      let temp3 = [...state.statements];
      temp3[action.payload.id!].phrase = action.payload.value ?? "";
      let statementState = { ...state, statements: temp3 };
      return statementState;

    case TFEditActionKind.REMOVESTATE:
      let temp4 = [...state.statements];
      temp4.splice(action.payload.id!, 1);
      let removeState = { ...state, statements: temp4 };
      return removeState;

    default:
      throw new Error();
  }
}

function TFEdit(props: any) {
  let initState: TFEditState = { justify: false, header: "", statements: [] };
  initState.header = props.enunciado.text;
  props.problem.statements.map((text: any) =>
    initState.statements.push({ phrase: text, tfvalue: "" })
  );
  const [state, dispatch] = useReducer(TFEditReducer, initState);

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
                type: TFEditActionKind.CHANGEHEADER,
                payload: { value: e.target.value },
              })
            }
          ></textarea>
        </div>

        <p className="block mb-2 text-sm text-gray-900 dark:text-white">
          Adicione as afirmações e indique se são verdadeiras ou falsas
        </p>
        <table className="table-auto mt-4">
          <thead>
            <tr>
              <th className="p-3">V</th>
              <th className="p-3">F</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            <TFEditStateContext.Provider value={{ state, dispatch }}>
              {state.statements.map((_item, counter) => {
                return (
                  <TFStatementEdit id={counter} key={counter}></TFStatementEdit>
                );
              })}
            </TFEditStateContext.Provider>
          </tbody>
        </table>
        <input
          type="button"
          className="edit-btn"
          value="Add"
          onClick={() => {
            dispatch({ type: TFEditActionKind.ADDSTATEMENT, payload: {} });
          }}
        ></input>
        <div className="mt-5 flex items-center pl-4 border border-gray-200 rounded dark:border-gray-700">
          <input
            id="bordered-checkbox"
            type="checkbox"
            checked={state.justify}
            onChange={(e) => {
              dispatch({
                type: TFEditActionKind.JUSTIFYFALSE,
                payload: { flag: e.target.checked },
              });
            }}
            name="bordered-checkbox"
            className="basic-checkbox"
          />
          <label
            htmlFor="bordered-checkbox"
            className="w-full py-4 ml-2 text-sm font-medium text-gray-900 dark:text-gray-300"
          >
            Pedir a justificação de afirmações falsas?
          </label>
        </div>
      </form>
    </>
  );
}

function TFStatementEdit(props: any) {
  let name = "radio-button-" + props.id;
  const { state, dispatch } = useContext(TFEditStateContext);
  return (
    <>
      <tr>
        <td className="p-3">
          <input
            className="radio-green"
            type="radio"
            name={name}
            onChange={() =>
              dispatch({
                type: TFEditActionKind.CORRECTTF,
                payload: { id: props.id, value: "true" },
              })
            }
          ></input>
        </td>
        <td className="p-3">
          <input
            className="radio-red"
            type="radio"
            name={name}
            onChange={() =>
              dispatch({
                type: TFEditActionKind.CORRECTTF,
                payload: { id: props.id, value: "false" },
              })
            }
          ></input>
        </td>
        <td>
          <input
            type="text"
            className="basic-input-text"
            onChange={(e) =>
              dispatch({
                type: TFEditActionKind.CHANGESTATEMENT,
                payload: { id: props.id, value: e.target.value },
              })
            }
            value={state.statements[props.id].phrase}
          ></input>
        </td>
        <td>
          <input
            className="edit-btn"
            type="button"
            onClick={() =>
              dispatch({
                type: TFEditActionKind.REMOVESTATE,
                payload: { id: props.id },
              })
            }
            value="Remove"
          ></input>
        </td>
      </tr>
    </>
  );
}
