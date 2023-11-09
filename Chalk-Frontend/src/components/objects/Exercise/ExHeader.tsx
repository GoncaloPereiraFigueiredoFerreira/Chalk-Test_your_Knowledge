import { useState } from "react";

enum ImgPos {
  TOP = "TOP",
  BOT = "BOT",
}

type ExerciseHeader = {
  text: string;
  img?: { pos: ImgPos; url: string };
};

type HeaderProps = { header: ExerciseHeader };

type EditHeaderProps = { header: ExerciseHeader; editFunc: Function };

export function ExerciseHeader({ header }: HeaderProps) {
  let component = <p>{header.text}</p>;
  if ("img" in header) {
    let imgComponent = (
      <div className="flex justify-center m-4 object-fit h-80">
        <img src={header.img!.url}></img>
      </div>
    );
    switch (header.img!.pos) {
      case ImgPos.TOP: {
        component = (
          <>
            {imgComponent}
            {component}
          </>
        );
        break;
      }
      case ImgPos.BOT: {
        console.log("helo=");
        component = (
          <>
            {component}
            {imgComponent}
          </>
        );
        break;
      }
    }
  }
  return <div className="flex-col mb-6">{component}</div>;
}

// The edit func needs to be able to add/alter the image
export function ExerciseHeaderEdit({ header, editFunc }: EditHeaderProps) {
  const [img, setImg] = useState("img" in header);
  return (
    <>
      <div className="mb-9">
        <div>
          <label
            htmlFor="message"
            className="block mb-2 ml-1 text-sm text-gray-900 dark:text-white"
          >
            Enunciado:
          </label>
          <textarea
            id="message"
            className="header-textarea"
            placeholder="Write the header of your exercise"
            value={header.text}
            onChange={(e) => editFunc(e.target.value)}
          ></textarea>
        </div>
        <div className="mt-5 p-3 border font-medium border-gray-200 rounded dark:border-gray-700">
          <input
            id="putImg"
            type="checkbox"
            className="basic-checkbox"
            onClick={() => {
              setImg(!img); //should also clear image
            }}
            checked={img}
          ></input>
          <label
            htmlFor="putImg"
            className="w-full py-4 ml-2 text-sm  text-gray-900 dark:text-gray-300"
          >
            Deseja adicionar uma imagem ao seu enunciado?
          </label>
          <div className={"flex mt-3 " + (!img ? "hidden" : "")}>
            <div>
              <label
                htmlFor="image"
                className="block mb-2 ml-1 text-sm text-gray-900 dark:text-white"
              >
                Imagem:
              </label>
              <input
                id="image"
                placeholder="Input your image url"
                type="url"
                className="basic-input-text"
                value={header.img?.url}
                onChange={() => {}} //preencher
              ></input>
            </div>

            <div className="ml-4">
              <label
                htmlFor="pos"
                className="block mb-2 text-sm text-gray-900 dark:text-white"
              >
                Posição:
              </label>
              <select
                id="pos"
                className="basic-input-text"
                value={header.img?.pos}
                onChange={() => {}} //preencher
              >
                {Object.keys(ImgPos).map((key: string) => {
                  return <option value={key}>{key}</option>;
                })}
              </select>
            </div>
            <div className="ml-4">
              <label
                htmlFor="pre-viz"
                className="block mb-2 text-sm text-gray-900 dark:text-white"
              >
                Pré-visualização:
              </label>
              <div className="flex object-fit h-48">
                <img src={header.img!.url}></img>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
