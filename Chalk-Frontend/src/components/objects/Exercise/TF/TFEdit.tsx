import { useState } from "react";
import {
  Exercise,
  ExerciseJustificationKind,
  ExerciseType,
  TFResolutionData,
} from "../Exercise";
import { EditAction, EditActionKind } from "../../EditExercise/EditExercise";
import { DropdownBlock } from "../../../interactiveElements/DropdownBlock";

interface TFEditProps {
  dispatch: React.Dispatch<EditAction>;
  state: Exercise;
}
export function TFEdit({ dispatch, state }: TFEditProps) {
  const [openJustificationkind, setOpenJustificationkind] = useState(
    state.justifyKind != ExerciseJustificationKind.NO_JUSTIFICATION
  );
  return (
    <>
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
          {Object.keys(state.items!).map((value, index) => (
            <TFStatementEdit
              key={index}
              position={index}
              id={value}
              state={state}
              dispatch={dispatch}
            ></TFStatementEdit>
          ))}
        </tbody>
      </table>
      <input
        type="button"
        className="edit-btn"
        value="Add"
        onClick={() => {
          for (
            let newID = 0;
            newID < Object.keys(state.items!).length + 1;
            newID++
          ) {
            if (state.items![newID.toString()] === undefined) {
              dispatch({
                type: EditActionKind.ADD_ITEM,
                data: { id: newID.toString() },
              });
              break;
            }
          }
        }}
      ></input>
      <div className="flex flex-col">
        <div className="mt-5 flex items-center">
          <input
            id="bordered-checkbox"
            type="checkbox"
            className="p-2 rounded outline-0 bg-input-2"
            onChange={() => {
              setOpenJustificationkind(!openJustificationkind);
            }}
            checked={openJustificationkind}
          />
          <label
            htmlFor="bordered-checkbox"
            className="w-full py-4 ml-2 text-sm font-medium text-gray-900 dark:text-gray-300"
          >
            Pedir a justificação?
          </label>
        </div>
        <div
          className={`${
            openJustificationkind ? "max-h-96" : "max-h-0 overflow-hidden"
          } transition-[max-height] ml-3 h-12`}
        >
          <DropdownBlock
            options={[
              ExerciseJustificationKind.JUSTIFY_ALL,
              ExerciseJustificationKind.JUSTIFY_FALSE,
              ExerciseJustificationKind.JUSTIFY_TRUE,
              ExerciseJustificationKind.NO_JUSTIFICATION,
            ]}
            text="Posição"
            chosenOption={state.justifyKind}
            setChosenOption={(justifyKind) =>
              dispatch({
                type: EditActionKind.CHANGE_JUSTIFY_KIND,
                justifyKind: justifyKind,
              })
            }
            style="rounded-lg h-full"
            placement="top"
          ></DropdownBlock>
        </div>
      </div>
    </>
  );
}

interface TFStatementEditProps {
  dispatch: React.Dispatch<EditAction>;
  state: Exercise;
  id: string;
  position: number;
}
function TFStatementEdit({
  dispatch,
  state,
  id,
  position,
}: TFStatementEditProps) {
  const name = "radio-button-" + position;
  if (
    state.solution != undefined &&
    state.solution.data != undefined &&
    state.solution.data.type === ExerciseType.TRUE_OR_FALSE
  ) {
    const solutionItem = state.solution.data.items[id];
    console.log(solutionItem.value);
    return (
      <>
        <tr>
          <td className="p-3">
            <input
              className="radio-green"
              type="radio"
              name={name}
              onChange={() => {
                dispatch({
                  type: EditActionKind.CHANGE_ITEM_TF,
                  data: { id: id, value: true },
                });
              }}
              checked={solutionItem.value}
            ></input>
          </td>
          <td className="p-3">
            <input
              className="radio-red"
              type="radio"
              name={name}
              onChange={() => {
                dispatch({
                  type: EditActionKind.CHANGE_ITEM_TF,
                  data: { id: id, value: false },
                });
              }}
              checked={!solutionItem.value}
            ></input>
          </td>
          <td>
            <input
              type="text"
              className="basic-input-text"
              onChange={(e) =>
                dispatch({
                  type: EditActionKind.CHANGE_ITEM_TEXT,
                  data: { id: id, text: e.target.value },
                })
              }
              value={solutionItem.text}
            ></input>
          </td>
          <td>
            <input
              className="edit-btn"
              type="button"
              onClick={() =>
                dispatch({
                  type: EditActionKind.REMOVE_ITEM,
                  data: { id: id },
                })
              }
              value="Remove"
            ></input>
          </td>
        </tr>
      </>
    );
  } else <></>;
}
