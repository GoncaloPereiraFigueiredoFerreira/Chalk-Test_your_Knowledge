import { useReducer, createContext, useContext } from "react";

type TFExerciseProps = {
  enunciado: any;
  problema?: any;
  contexto: string;
  cotacao?: number;
};

enum SolveActionKind {
  CHOOSE = "CHOOSE",
  JUSTIFY = "JUSTIFY",
}

type SolveAction = {
  type: SolveActionKind;
  index: number;
  value: string;
};

type Solve = {
  phrase: string;
  tfvalue: string;
  justification: string;
};

type SolveState = Solve[];

export function FillBlankExercise(props: TFExerciseProps) {
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

function SolveReducer(state: SolveState, action: SolveAction): SolveState {
  switch (action.type) {
    case SolveActionKind.CHOOSE:
      let chooseState = [...state];
      chooseState[action.index].tfvalue = action.value;
      return chooseState;

    case SolveActionKind.JUSTIFY:
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
            {state.map((_solve: Solve, index: number) => {
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
          className="relative h-5 w-5 cursor-pointer appearance-none rounded-full focus:ring-0 border text-green-500 transition-all before:absolute before:top-2/4 before:left-2/4 before:block before:h-12 before:w-12 before:-translate-y-2/4 before:-translate-x-2/4 before:rounded-full before:bg-blue-gray-500 before:opacity-0 before:transition-opacity checked:border-green-500 checked:before:bg-green-500 hover:before:opacity-10"
          type="radio"
          name={name}
          onChange={() =>
            dispatch({
              type: SolveActionKind.CHOOSE,
              index: props.id,
              value: "true",
            })
          }
        ></input>
      </td>
      <td className="p-3">
        <input
          className="relative h-5 w-5 cursor-pointer appearance-none focus:ring-0 rounded-full border  text-red-500 transition-all before:absolute before:top-2/4 before:left-2/4 before:block before:h-12 before:w-12 before:-translate-y-2/4 before:-translate-x-2/4 before:rounded-full before:bg-blue-gray-500 before:opacity-0 before:transition-opacity checked:border-red-700 checked:before:bg-red-500 hover:before:opacity-10"
          type="radio"
          name={name}
          onChange={() =>
            dispatch({
              type: SolveActionKind.CHOOSE,
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
        className={hidden + " text-black min-w-[40rem] max-h-8"}
        type="text"
        name={"justification" + props.id}
        placeholder="Justifique a sua resposta"
        value={state[props.id].justification}
        onChange={(e) =>
          dispatch({
            type: SolveActionKind.JUSTIFY,
            index: props.id,
            value: e.target.value,
          })
        }
      ></input>
    </div>
  );
}

enum EditActionKind {
  JUSTIFYFALSE = "JUSTIFYFALSE",
  ADDSTATEMENT = "ADDSTATEMENT",
  CORRECTTF = "CORRECTTF",
  CHANGESTATEMENT = "CHANGESTATEMENT",
  CHANGEHEADER = "CHANGEHEADER",
  REMOVESTATE = "REMOVESTATE",
}

type EditState = {
  justify: boolean;
  header: string;
  statements: { phrase: string; tfvalue: string }[];
};

type EditAction = {
  type: EditActionKind;
  payload: { id?: number; flag?: boolean; value?: string };
};

const EditStateContext = createContext<{ state: EditState; dispatch: any }>({
  state: { justify: false, header: "", statements: [] },
  dispatch: undefined,
});

function EditReducer(state: EditState, action: EditAction) {
  switch (action.type) {
    case EditActionKind.JUSTIFYFALSE:
      let justifyState = { ...state };
      justifyState.justify = action.payload.flag ?? false;
      return justifyState;

    case EditActionKind.ADDSTATEMENT:
      let temp1 = [...state.statements];
      temp1.push({ phrase: "", tfvalue: "" });
      let addState = { ...state, statements: temp1 };
      return addState;

    case EditActionKind.CORRECTTF:
      let temp2 = [...state.statements];
      temp2[action.payload.id!].tfvalue = action.payload.value ?? "";
      let correctstate = { ...state, statments: temp2 };

      return correctstate;

    case EditActionKind.CHANGEHEADER:
      let headerState = { ...state };
      headerState.header = action.payload.value ?? "";
      return headerState;

    case EditActionKind.CHANGESTATEMENT:
      let temp3 = [...state.statements];
      temp3[action.payload.id!].phrase = action.payload.value ?? "";
      let statementState = { ...state, statement: temp3 };
      return statementState;

    case EditActionKind.REMOVESTATE:
      let temp4 = [...state.statements];
      temp4.splice(action.payload.id!, 1);
      let removeState = { ...state, statements: temp4 };
      return removeState;

    default:
      throw new Error();
  }
}

function TFEdit(props: any) {
  let initState: EditState = { justify: false, header: "", statements: [] };
  initState.header = props.enunciado.text;
  props.problem.statements.map((text: any) =>
    initState.statements.push({ phrase: text, tfvalue: "" })
  );
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
            className="block p-2.5 w-3/4  text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            placeholder=""
            value={state.header}
            onChange={(e) =>
              dispatch({
                type: EditActionKind.CHANGEHEADER,
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
            <EditStateContext.Provider value={{ state, dispatch }}>
              {state.statements.map((_item, counter) => {
                return (
                  <TFStatementEdit id={counter} key={counter}></TFStatementEdit>
                );
              })}
            </EditStateContext.Provider>
          </tbody>
        </table>
        <input
          type="button"
          className=""
          value="Add"
          onClick={() => {
            dispatch({ type: EditActionKind.ADDSTATEMENT, payload: {} });
          }}
        ></input>
      </form>
    </>
  );
}

function TFStatementEdit(props: any) {
  let name = "radio-button-" + props.id;
  const { state, dispatch } = useContext(EditStateContext);
  return (
    <>
      <tr>
        <td className="p-3">
          <input
            className="relative h-5 w-5 cursor-pointer appearance-none rounded-full border text-green-500 transition-all before:absolute before:top-2/4 before:left-2/4 before:block before:h-12 before:w-12 before:-translate-y-2/4 before:-translate-x-2/4 before:rounded-full before:bg-blue-gray-500 before:opacity-0 before:transition-opacity checked:border-green-500 checked:before:bg-green-500 hover:before:opacity-10"
            type="radio"
            name={name}
            onChange={() =>
              dispatch({
                type: EditActionKind.CORRECTTF,
                payload: { id: props.id, value: "true" },
              })
            }
          ></input>
        </td>
        <td className="p-3">
          <input
            className="relative h-5 w-5 cursor-pointer appearance-none rounded-full border  text-red-500 transition-all before:absolute before:top-2/4 before:left-2/4 before:block before:h-12 before:w-12 before:-translate-y-2/4 before:-translate-x-2/4 before:rounded-full before:bg-blue-gray-500 before:opacity-0 before:transition-opacity checked:border-red-700 checked:before:bg-red-500 hover:before:opacity-10"
            type="radio"
            name={name}
            onChange={() =>
              dispatch({
                type: EditActionKind.CORRECTTF,
                payload: { id: props.id, value: "false" },
              })
            }
          ></input>
        </td>
        <td>
          <input
            type="text"
            className="block p-2.5  text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
            onChange={(e) =>
              dispatch({
                type: EditActionKind.CHANGESTATEMENT,
                payload: { id: props.id, value: e.target.value },
              })
            }
            value={state.statements[props.id].phrase}
          ></input>
        </td>
        <td>
          <input
            type="button"
            onClick={() =>
              dispatch({
                type: EditActionKind.REMOVESTATE,
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
