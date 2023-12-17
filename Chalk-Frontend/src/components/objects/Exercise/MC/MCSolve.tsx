import {
  ExerciseJustificationKind,
  MCResolutionData,
  ResolutionData,
} from "../Exercise";
import { useEffect, useReducer } from "react";
import { ExerciseHeader, ImgPos } from "../Header/ExHeader";

export interface MCSolveProps {
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

// MCAction Definition
export interface MCAction {
  type: MCActionKind;
  index: string;
  payload?: string;
  state?: MCResolutionData;
}

export enum MCActionKind {
  CHOOSE = "CHOOSE",
  JUSTIFY = "JUSTIFY",
  SETSTATE = "SETSTATE",
}

// Takes the current ExerciseState and an action to update the ExerciseState
function ExerciseMCReducer(mcState: MCResolutionData, mcAction: MCAction) {
  switch (mcAction.type) {
    case MCActionKind.CHOOSE:
      let newItems = { ...mcState.items };

      Object.keys(newItems).map(
        (index) => (newItems[index].value = index === mcAction.index)
      );
      return { ...mcState, items: newItems };

    case MCActionKind.JUSTIFY:
      let newItemsJUST = { ...mcState.items };
      newItemsJUST[mcAction.index].justification = mcAction.payload ?? "";
      return { ...mcState, items: newItemsJUST };

    case MCActionKind.SETSTATE:
      return { ...mcAction.state! };

    default:
      return mcState;
  }
}

export function MCSolve({
  id,
  items,
  position,
  statement,
  justifyKind,
  resolution,
  setResolution,
}: MCSolveProps) {
  let initState: MCResolutionData = resolution as MCResolutionData;

  const [state, dispatch] = useReducer(ExerciseMCReducer, initState);

  useEffect(() => {
    setResolution(state);
  }, [state]);

  useEffect(() => {
    dispatch({
      type: MCActionKind.SETSTATE,
      index: "",
      state: resolution as MCResolutionData,
    });
  }, [statement]);

  return (
    <>
      <ExerciseHeader header={statement}></ExerciseHeader>
      <ul>
        {Object.entries(state.items).map(([index, value]) => (
          <div key={index}>
            <label
              htmlFor={"mc" + id + index + position}
              className="flex px-4 py-2 gap-2 items-center hover:bg-gray-300"
            >
              <input
                id={"mc" + id + index + position}
                name={"mc" + id + position}
                type="radio"
                className="radio-blue mr-3"
                checked={value.value}
                onChange={() =>
                  dispatch({
                    type: MCActionKind.CHOOSE,
                    index: index,
                  })
                }
              ></input>
              {value.text}
            </label>
            <MCJustify
              index={index}
              state={state}
              dispatch={dispatch}
              justifyKind={justifyKind}
            ></MCJustify>
          </div>
        ))}
      </ul>
    </>
  );
}

function MCJustify(props: any) {
  let justify =
    props.justifyKind === ExerciseJustificationKind.JUSTIFY_ALL ||
    (props.justifyKind === ExerciseJustificationKind.JUSTIFY_UNMARKED &&
      !props.state.items[props.index].value) ||
    (props.justifyKind === ExerciseJustificationKind.JUSTIFY_MARKED &&
      props.state.items[props.index].value);

  return props.justifyKind === ExerciseJustificationKind.NO_JUSTIFICATION ? (
    <div className="col-span-3"></div>
  ) : (
    <div
      className={`${justify ? "h-28" : "h-0"} transition-[height] duration-75`}
    >
      <div className="h-full px-7 overflow-hidden">
        <textarea
          className={`${justify ? "" : "hidden"} basic-input-text`}
          name={"justification" + props.index}
          rows={3}
          placeholder="Justifique a sua resposta"
          value={props.state.items[props.index].justification}
          onChange={(e) =>
            props.dispatch({
              type: MCActionKind.JUSTIFY,
              index: props.index,
              payload: e.target.value,
            })
          }
        ></textarea>
      </div>
    </div>
  );
}
