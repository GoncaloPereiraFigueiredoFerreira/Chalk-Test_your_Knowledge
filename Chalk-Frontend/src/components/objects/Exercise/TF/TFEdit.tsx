import { useReducer, useState } from "react";
import { ExerciseJustificationKind } from "../Exercise";

enum TFEditActionKind {
  JUSTIFY = "JUSTIFY",
  ADD_STATEMENT = "ADD_STATEMENT",
  CORRECT_TF = "CORRECT_TF",
  CHANGE_STATEMENT = "CHANGE_STATEMENT",
  REMOVE_STATEMENT = "REMOVE_STATEMENT",
}

type TFEditState = {
  justify: ExerciseJustificationKind;
  statements: TFStatement[];
};

type TFEditAction = {
  type: TFEditActionKind;
  payload: {
    id?: number;
    justify?: ExerciseJustificationKind;
    tfvalue?: string;
    phrase?: string;
  };
};

function TFEditReducer(state: TFEditState, action: TFEditAction) {
  switch (action.type) {
    case TFEditActionKind.JUSTIFY:
      if (action.payload.justify) {
        return { ...state, justify: action.payload.justify };
      } else throw new Error("Invalid action - action.payload.justify");

    case TFEditActionKind.ADD_STATEMENT:
      let temp1 = [...state.statements];
      temp1.push({ phrase: "", tfvalue: "", justification: "" });
      let addState = { ...state, statements: temp1 };
      return addState;

    case TFEditActionKind.CORRECT_TF:
      if (action.payload.id != undefined)
        if (action.payload.tfvalue != undefined) {
          let newStatments = [...state.statements];
          newStatments[action.payload.id].tfvalue = action.payload.tfvalue;
          let correctstate = { ...state, statments: newStatments };
          return correctstate;
        } else throw new Error("Invalid action - action.payload.tfvalue");
      else throw new Error("Invalid action - action.payload.id");

    case TFEditActionKind.CHANGE_STATEMENT:
      if (action.payload.id != undefined)
        if (action.payload.phrase != undefined) {
          let newStatments = [...state.statements];
          newStatments[action.payload.id].phrase = action.payload.phrase;
          let correctstate = { ...state, statments: newStatments };
          return correctstate;
        } else throw new Error("Invalid action - action.payload.phrase");
      else throw new Error("Invalid action - action.payload.id");

    case TFEditActionKind.REMOVE_STATEMENT:
      if (action.payload.id != undefined) {
        let newStatments = [...state.statements];
        newStatments.splice(action.payload.id, 1);
        let correctstate = { ...state, statments: newStatments };
        return correctstate;
      } else throw new Error("Invalid action - action.payload.id");
  }
}

interface TFEditProps {
  justify: ExerciseJustificationKind;
  statements: string[] | TFStatement[];
}

export function TFEdit({ justify, statements }: TFEditProps) {
  let initState: TFEditState;
  if (statements.length > 0) {
    if (typeof statements[0] === "string") {
      initState = {
        justify: justify,
        statements: [] as TFStatement[],
      };
      (statements as string[]).map((statement: string) => {
        initState.statements.push({
          phrase: statement,
          tfvalue: "",
          justification: "",
        });
      });
    } else if (
      "justification" in statements[0] &&
      "phrase" in statements[0] &&
      "tfvalue" in statements[0]
    ) {
      initState = {
        justify: justify,
        statements: statements as TFStatement[],
      };
    } else return <></>;
  } else return <></>;

  const [state, dispatch] = useReducer(TFEditReducer, initState);

  const [openJustificationkind, setOpenJustificationkind] = useState(
    justify != ExerciseJustificationKind.NO_JUSTIFICATION
  );

  return (
    <>
      <form>
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
            dispatch({ type: TFEditActionKind.ADD_STATEMENT, payload: {} });
          }}
        ></input>
        <div className="mt-5 flex items-center pl-4 border border-gray-200 rounded dark:border-gray-700">
          <input
            id="bordered-checkbox"
            type="checkbox"
            onChange={() => setOpenJustificationkind(!openJustificationkind)}
            checked={openJustificationkind}
            name="bordered-checkbox"
            className="basic-checkbox"
          />
          <label
            htmlFor="bordered-checkbox"
            className="w-full py-4 ml-2 text-sm font-medium text-gray-900 dark:text-gray-300"
          >
            Pedir a justificação?
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
                type: TFEditActionKind.CORRECT_TF,
                payload: { id: props.id, tfvalue: "true" },
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
                type: TFEditActionKind.CORRECT_TF,
                payload: { id: props.id, tfvalue: "false" },
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
                type: TFEditActionKind.CHANGE_STATEMENT,
                payload: { id: props.id, phrase: e.target.value },
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
                type: TFEditActionKind.REMOVE_STATEMENT,
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
