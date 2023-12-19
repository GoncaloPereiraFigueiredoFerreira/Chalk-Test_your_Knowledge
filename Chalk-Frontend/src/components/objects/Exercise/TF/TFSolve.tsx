import {
  ExerciseJustificationKind,
  SolveProps,
  TFExercise,
  TFResolutionData,
} from "../Exercise";
import { useEffect, useReducer } from "react";
import { ExerciseHeaderComp } from "../Header/ExHeader";

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
  exercise: TFExercise;
  position: string;
  context: SolveProps;
}

export function TFSolve({ exercise, position, context }: TFSolveProps) {
  let initState: TFResolutionData = context.resolutionData as TFResolutionData;

  const [state, dispatch] = useReducer(ExerciseTFReducer, initState);

  useEffect(
    () =>
      dispatch({
        type: TFActionKind.SETSTATE,
        state: context.resolutionData as TFResolutionData,
        index: "",
        payload: "",
      }),
    [exercise.base]
  );

  useEffect(() => context.setExerciseSolution(state), [state]);

  console.log(state.items);
  return (
    <>
      <ExerciseHeaderComp header={exercise.base.statement} />
      <div className="grid-layout-exercise mt-4 gap-2 min-h-max items-center">
        <div className="flex text-xl font-bold px-4">V</div>
        <div className="flex text-xl font-bold px-4">F</div>
        <div></div>
        {Object.keys(state.items).map((index) => (
          <TFShowStatement
            key={index}
            index={index}
            name={`radio-button-${index}-${exercise.identity?.id}-${position}`}
            justifyKind={exercise.props.justifyType}
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
