import { useReducer } from "react";
import { ExerciseHeader, ExerciseHeaderEdit } from "./ExHeader";

interface TFStatement {
  phrase: string;
  tfvalue: string;
  justification: string;
}

type TFState = TFStatement[];

// Type of actions allowed on the state
enum TFActionKind {
  CHOOSE = "CHOOSE",
  JUSTIFY = "JUSTIFY",
}

// TFAction Definition
interface TFAction {
  type: TFActionKind;
  index: number;
  value: string;
}

interface ExerciseProps {
  enunciado: any;
  problem?: any;
  name: string;
  position: string;
  contexto: string;
  justify?: string;
}

export function TFExercise({
  enunciado,
  problem,
  name,
  position,
  contexto,
  justify,
}: ExerciseProps) {
  let exerciseDisplay = <></>;

  switch (contexto) {
    case "solve":
      exerciseDisplay = justify ? (
        <TFSolve
          problem={problem}
          enunciado={enunciado}
          justify={justify}
        ></TFSolve>
      ) : (
        <></>
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
      <div className="m-5 text-title-2">{position + ") " + name}</div>
      <div className="m-5 text-lg">{exerciseDisplay}</div>
    </>
  );
}

function SolveReducer(state: TFState, action: TFAction): TFState {
  switch (action.type) {
    case TFActionKind.CHOOSE:
      let chooseState = [...state];
      chooseState[action.index].tfvalue = action.value;
      return chooseState;

    case TFActionKind.JUSTIFY:
      let justifyState = [...state];
      justifyState[action.index].justification = action.value;
      return justifyState;

    default:
      throw new Error();
  }
}

function TFSolve(props: any) {
  let initState: TFState = [];
  props.problem.statements.map((text: any) =>
    initState.push({ phrase: text, tfvalue: "", justification: "" })
  );

  const [state, dispatch] = useReducer(SolveReducer, initState);

  return (
    <>
      <ExerciseHeader header={props.enunciado}></ExerciseHeader>
      <div className="grid-layout-exercise mt-4 gap-2 min-h-max items-center">
        <div className="flex text-xl font-bold px-4">V</div>
        <div className="flex text-xl font-bold px-4">F</div>
        <div></div>
        {state.map((_solve: TFStatement, index: number) => {
          return (
            <TFStatement
              key={index}
              id={index}
              justify={props.justify}
              state={state}
              dispatch={dispatch}
            />
          );
        })}
      </div>
    </>
  );
}

function TFStatement(props: any) {
  let name = "radio-button-" + props.id;
  return (
    <>
      <div className="flex items-start justify-center">
        <input
          className="radio-green"
          type="radio"
          name={name}
          onClick={() =>
            props.dispatch({
              type: TFActionKind.CHOOSE,
              index: props.id,
              value: "true",
            })
          }
        ></input>
      </div>
      <div className="flex items-start justify-center">
        <input
          className="radio-red"
          type="radio"
          name={name}
          onClick={() =>
            props.dispatch({
              type: TFActionKind.CHOOSE,
              index: props.id,
              value: "false",
            })
          }
        ></input>
      </div>
      <div className="">
        <p>{props.state[props.id].phrase}</p>
      </div>
      <TFJustify
        id={props.id}
        state={props.state}
        dispatch={props.dispatch}
        justify={props.justify}
      ></TFJustify>
    </>
  );
}

function TFJustify(props: any) {
  return props.justify === "none" ? (
    <div className="col-span-3"></div>
  ) : (
    <div
      className={`${
        props.justify === "all" ||
        (props.justify === "false-only" &&
          props.state[props.id].tfvalue === "false")
          ? "h-28"
          : "h-0"
      } col-span-3 transition-[height] duration-75`}
    >
      <div className="h-full px-7 overflow-hidden">
        <textarea
          className={`${
            props.justify === "all" ||
            (props.justify === "false-only" &&
              props.state[props.id].tfvalue === "false")
              ? ""
              : "hidden"
          } basic-input-text`}
          name={"justification" + props.id}
          rows={3}
          placeholder="Justifique a sua resposta"
          value={props.state[props.id].justification}
          onChange={(e) =>
            props.dispatch({
              type: TFActionKind.JUSTIFY,
              index: props.id,
              value: e.target.value,
            })
          }
        ></textarea>
      </div>
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

export function TFEdit({ enunciado, problem }: ExerciseProps) {
  let initState: TFEditState = { justify: false, header: "", statements: [] };
  initState.header = enunciado.text;
  problem.statements.map((text: any) =>
    initState.statements.push({ phrase: text, tfvalue: "" })
  );
  const [state, dispatch] = useReducer(TFEditReducer, initState);

  return (
    <>
      <form>
        <ExerciseHeaderEdit
          header={{ ...enunciado, text: state.header }}
          editFunc={(e: any) => {
            dispatch({
              type: TFEditActionKind.CHANGEHEADER,
              payload: { value: e },
            });
          }}
        ></ExerciseHeaderEdit>

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
            {state.statements.map((_item, counter) => {
              return (
                <TFStatementEdit
                  id={counter}
                  key={counter}
                  state={state}
                  dispatch={dispatch}
                ></TFStatementEdit>
              );
            })}
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
  return (
    <>
      <tr>
        <td className="p-3">
          <input
            className="radio-green"
            type="radio"
            name={name}
            onChange={() =>
              props.dispatch({
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
              props.dispatch({
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
              props.dispatch({
                type: TFEditActionKind.CHANGESTATEMENT,
                payload: { id: props.id, value: e.target.value },
              })
            }
            value={props.state.statements[props.id].phrase}
          ></input>
        </td>
        <td>
          <input
            className="edit-btn"
            type="button"
            onClick={() =>
              props.dispatch({
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
