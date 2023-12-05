import { ExerciseJustificationKind } from "../Exercise";
import { useReducer } from "react";
import { ExerciseHeader } from "../Header/ExHeader";
import { TFActionKind, TFSolveProps, TFState } from "../TF/TFSolve";

// MCAction Definition
export interface MCAction {
  type: TFActionKind;
  index: string;
  payload?: string;
}

// Takes the current ExerciseState and an action to update the ExerciseState
function ExerciseMCReducer(tfState: TFState, tfAction: MCAction) {
  switch (tfAction.type) {
    case TFActionKind.CHOOSE:
      let newState = { ...tfState };
      Object.keys(newState).map(
        (index) => (newState[index].value = index === tfAction.index)
      );
      return newState;

    case TFActionKind.JUSTIFY:
      if (tfAction.payload != undefined)
        return {
          ...tfState,
          [tfAction.index]: {
            ...tfState[tfAction.index],
            justification: tfAction.payload as string,
          },
        };
      else throw new Error("No data provided in tfAction.payload");
  }
}

export function MCSolve({
  id,
  items,
  position,
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

  const [state, dispatch] = useReducer(ExerciseMCReducer, initState);

  return (
    <>
      <ExerciseHeader header={statement}></ExerciseHeader>
      <ul>
        {Object.entries(state).map(([index, value]) => (
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
                onChange={() =>
                  dispatch({
                    type: TFActionKind.CHOOSE,
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
      !props.state[props.index].value) ||
    (props.justifyKind === ExerciseJustificationKind.JUSTIFY_MARKED &&
      props.state[props.index].value);

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
