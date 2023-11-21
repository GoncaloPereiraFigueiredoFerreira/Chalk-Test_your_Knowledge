import { ImgPos } from "../Exercise/ExHeader";
import { Dropdown } from "../../interactiveElements/Dropdown";
import "./EditExercise.css";

interface EditHeaderProps {
  setAddImg: (value: boolean) => void;
  addImg: boolean;
  setPostionImg: (value: ImgPos) => void;
  postionImg: ImgPos;
  setImg: (value: string) => void;
  img: string;
}

export function EditHeader({
  setAddImg,
  addImg,
  setPostionImg,
  postionImg,
  setImg,
  img,
}: EditHeaderProps) {
  // const { userState, dispatch } = useUserContext();

  return (
    <>
      <div className="mb-9">
        <div>
          <label
            htmlFor="message"
            className="block mb-2 ml-1 text-lg text-gray-900 dark:text-white"
          >
            Enunciado:
          </label>
          <textarea
            id="header"
            className="header-textarea"
            placeholder="Escreva aqui o enunciado..."
          ></textarea>
        </div>
        <div className="mt-2 mx-3 font-medium">
          <input
            id="putImg"
            type="checkbox"
            className="p-2 rounded outline-0 bg-input-2"
            onClick={() => {
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
            <div className={"flex mt-3 gap-5 h-full"}>
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
                  value={img}
                  onChange={(e) => setImg(e.target.value)} //preencher
                ></input>
              </div>

              <div className="flex flex-col">
                <label
                  htmlFor="pos"
                  className="mb-2 text-sm text-gray-900 dark:text-white"
                >
                  Posição:
                </label>
                <Dropdown
                  options={Object.values(ImgPos)}
                  text="Posição"
                  chosenOption={postionImg}
                  setChosenOption={setPostionImg}
                  className="rounded-lg h-full"
                ></Dropdown>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
