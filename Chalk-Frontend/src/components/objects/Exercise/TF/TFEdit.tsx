import { useReducer } from "react";

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

interface TFEditProps {
  enunciado: any;
  problem: any;
  name: string;
  position: string;
  justify: string;
}

export function TFEdit({ enunciado, problem }: TFEditProps) {
  let initState: TFEditState = { justify: false, header: "", statements: [] };
  initState.header = enunciado.text;
  problem.statements.map((text: any) =>
    initState.statements.push({ phrase: text, tfvalue: "" })
  );
  const [state, dispatch] = useReducer(TFEditReducer, initState);

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
