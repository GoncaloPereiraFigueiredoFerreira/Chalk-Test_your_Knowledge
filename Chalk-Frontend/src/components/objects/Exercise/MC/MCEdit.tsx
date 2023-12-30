import { useState } from "react";
import {
  CreateEditProps,
  ExerciseJustificationKind,
  ExerciseType,
  MCExercise,
  ResolutionData,
} from "../Exercise";
import { EditAction, EditActionKind } from "../../EditExercise/EditExercise";
import { DropdownBlock } from "../../../interactiveElements/DropdownBlock";

interface MCEditProps {
  context: CreateEditProps;
  exercise: MCExercise;
}

export function MCEdit({ context, exercise }: MCEditProps) {
  const [openJustificationkind, setOpenJustificationkind] = useState(
    exercise.props.justifyType !== ExerciseJustificationKind.NO_JUSTIFICATION
  );

  return (
    <>
      <p className="block mb-2 text-sm text-gray-900 dark:text-white">
        Adicione as afirmações e escolha a opção correta.
      </p>
      <ul>
        {Object.keys(exercise.props.items!).map((value, index) => {
          return (
            <MCStatementEdit
              key={index}
              id={value}
              solution={context.solutionData}
              dispatch={context.dispatch}
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
            newID < Object.keys(exercise.props.items!).length + 1;
            newID++
          ) {
            if (exercise.props.items![newID.toString()] === undefined) {
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
              ExerciseJustificationKind.JUSTIFY_MARKED,
              ExerciseJustificationKind.JUSTIFY_UNMARKED,
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

interface MCStatementEditProps {
  dispatch: React.Dispatch<EditAction>;
  solution: ResolutionData;
  id: string;
}
function MCStatementEdit({ dispatch, id, solution }: MCStatementEditProps) {
  let name = "mc";
  if (solution.type === ExerciseType.MULTIPLE_CHOICE) {
    let solutionItem = solution.items[id];
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
                dataString: id,
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
                dataString: id,
                dataItem: {
                  text: e.target.value,
                },
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
                dataString: id,
              })
            }
            value="Remove"
          ></input>
        </li>
      </>
    );
  } else <></>;
}