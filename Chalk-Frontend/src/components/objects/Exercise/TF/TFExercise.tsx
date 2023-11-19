import { useReducer } from "react";
import { ExerciseHeader } from "../ExHeader";

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

interface TFExerciseProps {
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
}: TFExerciseProps) {
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
