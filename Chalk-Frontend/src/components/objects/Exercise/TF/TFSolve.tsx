import { ExerciseJustificationKind } from "../Exercise";
import { useReducer } from "react";
import { ExerciseHeader, ImgPos } from "../Header/ExHeader";

//------------------------------------//
//                                    //
//              TFState               //
//                                    //
//------------------------------------//

export interface TFState {
  [id: string]: {
    text: string;
    justification: string;
    type: string;
    value: boolean;
  };
}

// Type of actions allowed on the state
export enum TFActionKind {
  CHOOSE = "CHOOSE",
  JUSTIFY = "JUSTIFY",
}

// TFAction Definition
export interface TFAction {
  type: TFActionKind;
  index: string;
  payload: string | boolean;
}

// Takes the current ExerciseState and an action to update the ExerciseState
function ExerciseTFReducer(tfState: TFState, tfAction: TFAction) {
  switch (tfAction.type) {
    case TFActionKind.CHOOSE:
      if (typeof tfAction.payload === "string")
        throw new Error("payload type error");

      return {
        ...tfState,
        [tfAction.index]: {
          ...tfState[tfAction.index],
          value: tfAction.payload as boolean,
        },
      };

    case TFActionKind.JUSTIFY:
      if (typeof tfAction.payload === "boolean")
        throw new Error("payload type error");
      return {
        ...tfState,
        [tfAction.index]: {
          ...tfState[tfAction.index],
          justification: tfAction.payload as string,
        },
      };
  }
}

export interface TFSolveProps {
  id: string;
  position: string;
  statement: {
    imagePath?: string;
    imagePosition?: ImgPos;
    text: string;
  };
  justifyKind: ExerciseJustificationKind;
  items: {
    [id: string]: {
      text: string;
      type: string;
    };
  };
}

export function TFSolve({
  id,
  position,
  items,
  statement,
  justifyKind,
}: TFSolveProps) {
  let initState: TFState = Object.fromEntries(
    Object.entries(items).map(([index, value]) => [
      index,
      {
        justification: "",
        text: value.text,
        type: value.type,
        value: false,
      },
    ])
  );

  const [state, dispatch] = useReducer(ExerciseTFReducer, initState);

  return (
    <>
      <ExerciseHeader header={statement} />
      <div className="grid-layout-exercise mt-4 gap-2 min-h-max items-center">
        <div className="flex text-xl font-bold px-4">V</div>
        <div className="flex text-xl font-bold px-4">F</div>
        <div></div>
        {Object.keys(state).map((index) => (
          <TFShowStatement
            key={index}
            index={index}
            name={`radio-button-${index}-${id}-${position}`}
            justifyKind={justifyKind}
            state={state}
            dispatch={dispatch}
          />
        ))}
      </div>
    </>
  );
  return <></>;
}

function TFShowStatement(props: any) {
  return (
    <>
      <div className="flex items-start justify-center">
        <input
          className="radio-green"
          type="radio"
          name={props.name}
          onChange={() => {
            props.dispatch({
              type: TFActionKind.CHOOSE,
              index: props.index,
              payload: true,
            });
          }}
        ></input>
      </div>
      <div className="flex items-start justify-center">
        <input
          className="radio-red"
          type="radio"
          name={props.name}
          onChange={() => {
            props.dispatch({
              type: TFActionKind.CHOOSE,
              index: props.index,
              payload: false,
            });
          }}
        ></input>
      </div>
      <div className="">
        <p>{props.state[props.index].text}</p>
      </div>
      <TFJustify
        index={props.index}
        state={props.state}
        dispatch={props.dispatch}
        justifyKind={props.justifyKind}
      ></TFJustify>
    </>
  );
}

function TFJustify(props: any) {
  let justify =
    props.justifyKind === ExerciseJustificationKind.JUSTIFY_ALL ||
    (props.justifyKind === ExerciseJustificationKind.JUSTIFY_FALSE &&
      !props.state[props.index].value) ||
    (props.justifyKind === ExerciseJustificationKind.JUSTIFY_TRUE &&
      props.state[props.index].value);
  return props.justifyKind === ExerciseJustificationKind.NO_JUSTIFICATION ? (
    <div className="col-span-3"></div>
  ) : (
    <div
      className={`${
        justify ? "h-28" : "h-0"
      } col-span-3 transition-[height] duration-75`}
    >
      <div className="h-full px-7 overflow-hidden">
        <textarea
          className={`${justify ? "" : "hidden"} basic-input-text`}
          name={"justification" + props.index}
          rows={3}
          placeholder="Justifique a sua resposta"
          value={props.state[props.index].justification}
          onChange={(e) =>
            props.dispatch({
              type: TFActionKind.JUSTIFY,
              index: props.index,
              payload: e.target.value,
            })
          }
        ></textarea>
      </div>
    </div>
  );
}
