import { ExerciseHeader } from "../Exercise";
import { textToHTMLHooks } from "../../../interactiveElements/TextareaBlock";

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
  let imgComponent = <></>;
  let style = "";

  if (header.imagePath) {
    imgComponent = (
      <div className="flex min-w-fit justify-center p-4">
        <img className="max-h-52" src={header.imagePath}></img>
      </div>
    );

    switch (header.imagePosition) {
      case ImgPos.BOT:
        style = "flex flex-col justify-center ";
        break;
      case ImgPos.TOP: {
        style = "flex flex-col-reverse justify-center ";
        break;
      }
      default: {
        style = "flex items-center";
        break;
      }
    }
  }
  return (
    <div className={"pb-2 text-black dark:text-white text-base " + style}>
      {ImgPos.LEFT === header.imagePosition ? imgComponent : null}
      {textToHTMLHooks(header.text)}
      {ImgPos.LEFT !== header.imagePosition ? imgComponent : null}
    </div>
  );
}
