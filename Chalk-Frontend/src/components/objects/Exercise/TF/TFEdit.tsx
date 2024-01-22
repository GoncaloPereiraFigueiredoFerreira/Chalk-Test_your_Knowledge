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
import { HiOutlineTrash } from "react-icons/hi";

import "../Exercise.css";

interface TFEditProps {
  context: CreateEditProps;
  exercise: TFExercise;
}

export function TFEdit({ exercise, context }: TFEditProps) {
  const [openJustificationkind, setOpenJustificationkind] = useState(
    exercise.props.justifyType !== ExerciseJustificationKind.NO_JUSTIFICATION
  );

  return (
    <>
      <p className="block mb-4 text-md text-black dark:text-white">
        Adicione as afirmações e indique se são verdadeiras ou falsas
      </p>
      <ul className="flex flex-col gap-2">
        {Object.keys(exercise.props.items).map((value, index) => (
          <TFStatementEdit
            key={index}
            position={index}
            id={value}
            dispatch={context.dispatch}
            solution={context.solutionData}
          ></TFStatementEdit>
        ))}
        <button
          className="flex justify-center w-full p-2 items-center gap-2 text-base rounded-lg font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-slate-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black transition-all duration-100 group"
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
        <div className="mt-5 flex items-center">
          <input
            id="bordered-checkbox"
            type="checkbox"
            className="p-2 rounded outline-0 border-[#dddddd] focus:ring-0 dark:bg-slate-600 dark:border-slate-600 dark:focus:border-slate-600 outline-none"
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
            className="w-full py-4 ml-2 text-sm font-medium text-black dark:text-slate-300"
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
      <li className="flex items-center gap-2">
        <input
          className="radio-green mr-3"
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
        <input
          className="radio-red mr-3"
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
          className="flex p-2.5 text-base rounded-lg font-medium bg-[#acacff] hover:bg-[#5555ce] dark:bg-slate-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black transition-all duration-100 group"
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
