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

export function TFExercise(props: TFExerciseProps) {
  let exerciseDisplay = <></>;

  switch (props.contexto) {
    case "solve":
      exerciseDisplay = (
        <TFSolve problem={props.problema} enunciado={props.enunciado}></TFSolve>
      );
      break;

    case "edit":
      exerciseDisplay = <></>;
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
