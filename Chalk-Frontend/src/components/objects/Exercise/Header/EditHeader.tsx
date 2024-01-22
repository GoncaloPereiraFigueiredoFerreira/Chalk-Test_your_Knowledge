import { ImgPos } from "./ExHeader";
import { DropdownBlock } from "../../../interactiveElements/DropdownBlock";
import { EditAction, EditActionKind } from "../../EditExercise/EditExercise";
import { Exercise } from "../Exercise";
import { useState } from "react";
import { TextareaBlock } from "../../../interactiveElements/TextareaBlock";

function translateImgPosToString(imgPos: ImgPos) {
  switch (imgPos) {
    case ImgPos.BOT:
      return "Baixo";
    case ImgPos.TOP:
      return "Cima";
    case ImgPos.LEFT:
      return "Esquerda";
    case ImgPos.RIGHT:
      return "Direita";
  }
}

function translateStringToImgPos(stringPos: string) {
  switch (stringPos) {
    case "Baixo":
      return ImgPos.BOT;
    case "Cima":
      return ImgPos.TOP;
    case "Esquerda":
      return ImgPos.LEFT;
    case "Direita":
      return ImgPos.RIGHT;
  }
}

interface EditHeaderProps {
  dispatch: React.Dispatch<EditAction>;
  state: Exercise;
}

export function EditHeader({ dispatch, state }: EditHeaderProps) {
  const [addImg, setAddImg] = useState(state.base.statement.imagePath !== "");

  return (
    <>
      <div>
        <label
          htmlFor="message"
          className="block pb-2 text-xl font-medium text-black dark:text-white"
        >
          Enunciado:
        </label>
        <TextareaBlock
          toolbar={true}
          rows={6}
          placeholder="Escreva aqui o enunciado..."
          value={state.base.statement.text}
          onChange={(value) =>
            dispatch({
              type: EditActionKind.CHANGE_STATEMENT,
              dataString: value,
            })
          }
        ></TextareaBlock>
      </div>
      <div className="px-4 pb-4 font-medium">
        <input
          id="putImg"
          type="checkbox"
          className="p-2 rounded outline-0 bg-[#dddddd] border-[#dddddd] focus:ring-0 dark:bg-slate-600 dark:border-slate-600 dark:focus:border-slate-600"
          onChange={() => {
            if (addImg)
              dispatch({
                type: EditActionKind.REMOVE_IMG,
              });
            else
              dispatch({
                type: EditActionKind.ADD_IMG,
              });
            setAddImg(!addImg);
          }}
          checked={addImg}
        ></input>
        <label
          htmlFor="putImg"
          className="w-full py-4 ml-2 text-base font-medium text-black dark:text-slate-300"
        >
          Adicionar uma imagem
        </label>
        <div
          className={`${
            addImg ? "max-h-96" : "max-h-0"
          } transition-[max-height] overflow-hidden`}
        >
          <div className={"flex flex-row pt-4 gap-4"}>
            <div className="flex flex-col w-full">
              <label
                htmlFor="image"
                className="pb-2 pl-1 text-base text-black dark:text-white"
              >
                Imagem:
              </label>
              <input
                id="image"
                placeholder="Imagem"
                type="url"
                className="w-full rounded-lg text-black dark:text-white bg-inherit border-2 border-[#dddddd] dark:border-slate-700 focus:ring-0 focus:border-[#dddddd] focus:dark:border-slate-700"
                value={state.base.statement.imagePath}
                onChange={(e) =>
                  dispatch({
                    type: EditActionKind.CHANGE_IMG_URL,
                    dataString: e.target.value,
                  })
                }
              ></input>
            </div>

            <div className="flex flex-col">
              <label
                htmlFor="pos"
                className="pb-2 text-base text-black dark:text-white"
              >
                Posição:
              </label>
              <DropdownBlock
                options={Object.values(ImgPos).map((value) => {
                  return translateImgPosToString(value);
                })}
                text="Posição"
                chosenOption={
                  state.base.statement.imagePosition
                    ? translateImgPosToString(
                        state.base.statement.imagePosition
                      )
                    : undefined
                }
                setChosenOption={(position) =>
                  dispatch({
                    type: EditActionKind.CHANGE_IMG_POS,
                    dataImgPos: translateStringToImgPos(position),
                  })
                }
                style="h-11 rounded-lg text-base"
                placement="bottom"
              ></DropdownBlock>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
