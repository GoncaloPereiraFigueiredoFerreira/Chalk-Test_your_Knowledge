import "../Exercise.css";
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
import { HiOutlineTrash } from "react-icons/hi";

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
      <p className="block pb-4 text-base text-black dark:text-white">
        Adicione as afirmações e escolha a opção correta.
      </p>
      <ul className="flex flex-col gap-2">
        {Object.keys(exercise.props.items!).map((value, index) => (
          <MCStatementEdit
            key={index}
            position={index}
            id={value}
            solution={context.solutionData}
            dispatch={context.dispatch}
          ></MCStatementEdit>
        ))}
        <button
          className="flex justify-center w-full p-2 items-center gap-2 text-base rounded-lg font-medium btn-base-color group"
          onClick={() => {
            for (
              let newID = 0;
              newID < Object.keys(exercise.props.items!).length + 1;
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
        >
          Adicionar
        </button>
      </ul>
      <div className="flex flex-col">
        <div className="pt-5 flex items-center">
          <input
            id="bordered-checkbox"
            type="checkbox"
            className="p-2 rounded outline-0 bg-[#dddddd] border-[#dddddd] focus:ring-0 dark:bg-slate-600 dark:border-slate-600 dark:focus:border-slate-600 outline-none"
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
            className="w-full py-4 pl-2 text-base font-medium text-black dark:text-slate-300"
          >
            Pedir a justificação?
          </label>
        </div>
        <div
          className={`${
            openJustificationkind ? "max-h-96" : "max-h-0"
          } transition-[max-height] pl-3 h-12 overflow-hidden`}
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
            style="rounded-lg h-full text-base"
            placement="bottom"
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
  position: number;
}
function MCStatementEdit({ dispatch, id, solution }: MCStatementEditProps) {
  const name = "mc";
  if (solution.type === ExerciseType.MULTIPLE_CHOICE) {
    const solutionItem = solution.items[id];
    return (
      <li className="flex items-center gap-2">
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
          className="w-full rounded-lg border-2 border-[#dddddd] focus:ring-0 bg-inherit dark:border-slate-700 dark:focus:border-slate-700"
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
        <button
          className="flex p-2.5 text-base rounded-lg font-medium btn-base-color group"
          onClick={() =>
            dispatch({
              type: EditActionKind.REMOVE_ITEM,
              dataString: id,
            })
          }
        >
          <HiOutlineTrash className="size-5" />
        </button>
      </li>
    );
  } else return <></>;
}
