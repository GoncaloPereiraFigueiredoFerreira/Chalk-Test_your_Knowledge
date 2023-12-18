import { useState } from "react";
import { Exercise, ExerciseJustificationKind, ExerciseType } from "../Exercise";
import { EditAction, EditActionKind } from "../../EditExercise/EditExercise";
import { DropdownBlock } from "../../../interactiveElements/DropdownBlock";

interface MCEditProps {
  dispatch: React.Dispatch<EditAction>;
  state: Exercise;
}
export function MCEdit({ dispatch, state }: MCEditProps) {
  const [openJustificationkind, setOpenJustificationkind] = useState(
    state.justifyKind != ExerciseJustificationKind.NO_JUSTIFICATION
  );

  return (
    <>
      <p className="block mb-2 text-sm text-gray-900 dark:text-white">
        Adicione as afirmações e escolha a opção correta.
      </p>
      <ul>
        {Object.keys(state.items!).map((value, index) => {
          return (
            <MCStatementEdit
              key={index}
              id={value}
              state={state}
              dispatch={dispatch}
            ></MCStatementEdit>
          );
        })}
      </ul>
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
              if (openJustificationkind)
                dispatch({
                  type: EditActionKind.CHANGE_JUSTIFY_KIND,
                  justifyKind: ExerciseJustificationKind.NO_JUSTIFICATION,
                });
              else
                dispatch({
                  type: EditActionKind.CHANGE_JUSTIFY_KIND,
                  justifyKind: ExerciseJustificationKind.JUSTIFY_ALL,
                });
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

interface MCStatementEditProps {
  dispatch: React.Dispatch<EditAction>;
  state: Exercise;
  id: string;
}
function MCStatementEdit({ dispatch, state, id }: MCStatementEditProps) {
  let name = "mc";
  if (
    state.solution != undefined &&
    state.solution.data != undefined &&
    state.solution.data.type === ExerciseType.MULTIPLE_CHOICE
  ) {
    let solutionItem = state.solution.data.items[id];

    return (
      <>
        <li className="flex items-center">
          <input
            className="radio-blue mr-3"
            type="radio"
            name={name}
            onChange={() => {
              dispatch({
                type: EditActionKind.CHANGE_ITEM_MC,
                data: { id: id },
              });
            }}
            checked={solutionItem.value}
          ></input>
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
        </li>
      </>
    );
  } else <></>;
}
