import { ImgPos } from "./ExHeader";
import { DropdownBlock } from "../../../interactiveElements/DropdownBlock";
import { EditAction, EditActionKind } from "../../EditExercise/EditExercise";
import { Exercise } from "../Exercise";
import { useState } from "react";
import { TextareaBlock } from "../../../interactiveElements/TextareaBlock";

interface EditHeaderProps {
  dispatch: React.Dispatch<EditAction>;
  state: Exercise;
}

export function EditHeader({ dispatch, state }: EditHeaderProps) {
  const [addImg, setAddImg] = useState(
    state.base.statement.imagePath != undefined
  );

  return (
    <>
      <div className="mb-9">
        <div>
          <label
            htmlFor="message"
            className="block mb-2 ml-1 text-xl font-medium text-gray-900 dark:text-white"
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
        <div className="mt-2 mx-3 font-medium">
          <input
            id="putImg"
            type="checkbox"
            className="p-2 rounded outline-0 bg-input-2"
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
            className="w-full py-4 ml-2 text-sm  text-gray-900 dark:text-gray-300"
          >
            Adicionar uma imagem
          </label>
          <div
            className={`${
              addImg ? "max-h-96" : "max-h-0 overflow-hidden"
            } transition-[max-height]`}
          >
            <div className={"flex mt-3 gap-5"}>
              <div className="flex flex-col">
                <label
                  htmlFor="image"
                  className="mb-2 ml-1 text-sm text-gray-900 dark:text-white"
                >
                  Imagem:
                </label>
                <input
                  id="image"
                  placeholder="Imagem"
                  type="url"
                  className="flex rounded-lg bg-input-1"
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
                  className="mb-2 text-sm text-gray-900 dark:text-white"
                >
                  Posição:
                </label>
                <DropdownBlock
                  options={Object.values(ImgPos)}
                  text="Posição"
                  chosenOption={state.base.statement.imagePosition}
                  setChosenOption={(position) =>
                    dispatch({
                      type: EditActionKind.CHANGE_IMG_POS,
                      dataImgPos: position,
                    })
                  }
                  style="flex min-h-max rounded-lg"
                  placement="bottom"
                ></DropdownBlock>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
