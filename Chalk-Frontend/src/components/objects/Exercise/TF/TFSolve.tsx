import {
  ExerciseJustificationKind,
  ResolutionData,
  TFResolutionData,
} from "../Exercise";
import { useEffect, useReducer } from "react";
import { ExerciseHeader, ImgPos } from "../Header/ExHeader";

//------------------------------------//
//                                    //
//              TFState               //
//                                    //
//------------------------------------//

// Type of actions allowed on the state

export enum TFActionKind {
  CHOOSE = "CHOOSE",
  JUSTIFY = "JUSTIFY",
  SETSTATE = "SETSTATE",
}

// TFAction Definition
export interface TFAction {
  type: TFActionKind;
  index: string;
  payload: string | boolean;
  state?: TFResolutionData;
}

// Takes the current ExerciseState and an action to update the ExerciseState
function ExerciseTFReducer(tfState: TFResolutionData, tfAction: TFAction) {
  switch (tfAction.type) {
    case TFActionKind.CHOOSE:
      if (typeof tfAction.payload === "string")
        throw new Error("payload type error");

      let newItemsCHOOSE = { ...tfState.items };
      newItemsCHOOSE[tfAction.index].value = tfAction.payload;
      return { ...tfState, items: newItemsCHOOSE };

    case TFActionKind.JUSTIFY:
      if (typeof tfAction.payload === "boolean")
        throw new Error("payload type error");

      let newItemsJUST = { ...tfState.items };
      newItemsJUST[tfAction.index].justification = tfAction.payload;
      return { ...tfState, items: newItemsJUST };

    case TFActionKind.SETSTATE:
      return { ...tfAction.state! };
    default:
      return tfState;
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
    };
  };
  resolution: ResolutionData;
  setResolution: Function;
}

export function TFSolve({
  id,
  position,
  statement,
  justifyKind,
  resolution,
  setResolution,
}: TFSolveProps) {
  let initState: TFResolutionData = resolution as TFResolutionData;

  const [state, dispatch] = useReducer(ExerciseTFReducer, initState);

  useEffect(
    () =>
      dispatch({
        type: TFActionKind.SETSTATE,
        state: resolution as TFResolutionData,
        index: "",
        payload: "",
      }),
    [statement]
  );

  useEffect(() => setResolution(state), [state]);

  return (
    <>
      <ExerciseHeader header={statement} />
      <div className="grid-layout-exercise mt-4 gap-2 min-h-max items-center">
        <div className="flex text-xl font-bold px-4">V</div>
        <div className="flex text-xl font-bold px-4">F</div>
        <div></div>
        {Object.keys(state.items).map((index) => (
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
}

function TFShowStatement(props: any) {
  return (
    <>
      <div className="flex items-start justify-center">
        <input
          className="radio-green"
          type="radio"
          name={props.name}
          checked={
            "value" in props.state.items[props.index] &&
            props.state.items[props.index].value
          }
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
          checked={
            "value" in props.state.items[props.index] &&
            !props.state.items[props.index].value
          }
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
        <p>{props.state.items[props.index].text}</p>
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
      "value" in props.state.items[props.index] &&
      !props.state.items[props.index].value) ||
    (props.justifyKind === ExerciseJustificationKind.JUSTIFY_TRUE &&
      "value" in props.state.items[props.index] &&
      props.state.items[props.index].value);

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
          rows={1}
          placeholder="Justifique a sua resposta"
          value={props.state.items[props.index].justification}
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
