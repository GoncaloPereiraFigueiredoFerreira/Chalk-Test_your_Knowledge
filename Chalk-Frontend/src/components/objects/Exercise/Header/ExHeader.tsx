import { textToHTML } from "../../../interactiveElements/TextareaBlock";
import { ExerciseHeader } from "../Exercise";

export enum ImgPos {
  TOP = "TOP",
  BOT = "BOT",
  LEFT = "LEFT",
  RIGHT = "RIGHT",
}

interface HeaderProps {
  header: ExerciseHeader;
}

export function ExerciseHeaderComp({ header }: HeaderProps) {
  if (header.imagePath) {
    const imgComponent = (
      <div className="flex w-full justify-center m-4">
        <img className="max-h-52" src={header.imagePath}></img>
      </div>
    );

    let style;

    switch (header.imagePosition) {
      case ImgPos.BOT:
        style = "flex flex-col justify-center ";
        break;
      case ImgPos.TOP: {
        style = "flex flex-col-reverse justify-center ";
        break;
      }
      default: {
        style = "grid grid-cols-2 items-center";
        break;
      }
    }
    return (
      <div className={"pb-4 text-base " + style}>
        {ImgPos.LEFT === header.imagePosition ? imgComponent : null}
        {textToHTML(header.text)}
        {ImgPos.LEFT === header.imagePosition ? null : imgComponent}
      </div>
    );
  } else {
    return (
      <div className="pb-2 text-black dark:text-white text-base">
        {textToHTML(header.text)}
      </div>
    );
  }
}
