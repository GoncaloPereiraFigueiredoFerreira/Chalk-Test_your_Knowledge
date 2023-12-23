import { useState } from "react";
import {
  CreateEditProps,
  ExerciseJustificationKind,
  ExerciseType,
  ResolutionData,
  TFExercise,
} from "../Exercise";
import { EditAction, EditActionKind } from "../../EditExercise/EditExercise";
import { DropdownBlock } from "../../../interactiveElements/DropdownBlock";

interface TFEditProps {
  context: CreateEditProps;
  exercise: TFExercise;
}

export function TFEdit({ exercise, context }: TFEditProps) {
  const [openJustificationkind, setOpenJustificationkind] = useState(
    exercise.props.justifyType !== ExerciseJustificationKind.NO_JUSTIFICATION
  );
  console.log(openJustificationkind);
  console.log(exercise.props.justifyType);

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
          {Object.keys(exercise.props.items).map((value, index) => (
            <TFStatementEdit
              key={index}
              position={index}
              id={value}
              dispatch={context.dispatch}
              solution={context.solutionData}
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
            newID < Object.keys(exercise.props.items).length + 1;
            newID++
          ) {
            if (exercise.props.items[newID.toString()] === undefined) {
              context.dispatch({
                type: EditActionKind.ADD_ITEM,
                dataString: newID.toString(),
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
                context.dispatch({
                  type: EditActionKind.CHANGE_JUSTIFY_KIND,
                  dataJK: ExerciseJustificationKind.NO_JUSTIFICATION,
                });
              else
                context.dispatch({
                  type: EditActionKind.CHANGE_JUSTIFY_KIND,
                  dataJK: ExerciseJustificationKind.JUSTIFY_ALL,
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
            chosenOption={exercise.props.justifyType}
            setChosenOption={(justifyKind) =>
              context.dispatch({
                type: EditActionKind.CHANGE_JUSTIFY_KIND,
                dataJK: justifyKind,
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
  id: string;
  solution: ResolutionData;
  position: number;
}
function TFStatementEdit({
  dispatch,
  id,
  position,
  solution,
}: TFStatementEditProps) {
  const name = "radio-button-" + position;

  if (solution.type === ExerciseType.TRUE_OR_FALSE) {
    const solutionItem = solution.items[id];

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
                  dataString: id,
                  dataItem: { value: true },
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
                  dataString: id,
                  dataItem: { value: false },
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
                  dataString: id,
                  dataItem: {
                    text: e.target.value,
                  },
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
                  dataString: id,
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
