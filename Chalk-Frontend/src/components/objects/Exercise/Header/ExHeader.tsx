export enum ImgPos {
  TOP = "TOP",
  BOT = "BOT",
  LEFT = "LEFT",
  RIGHT = "RIGHT",
}

export interface ExerciseHeader {
  text: string;
  imagePath: string;
  imagePosition: ImgPos;
}

interface HeaderProps {
  header: ExerciseHeader;
}

export function ExerciseHeader({ header }: HeaderProps) {
  if (header.imagePath) {
    const imgComponent = (
      <div className="flex w-full justify-center m-4">
        <img className="max-h-52" src={header.imagePath}></img>
      </div>
    );

    var style;

    switch (header.imagePosition) {
      case ImgPos.BOT:
      case ImgPos.TOP: {
        style = "flex flex-col justify-center ";
        break;
      }
      default: {
        style = "grid grid-cols-2 items-center";
        break;
      }
    }
    return (
      <div className={"mx-12 mb-6 " + style}>
        {ImgPos.LEFT === header.imagePosition ? imgComponent : null}
        {header.text}
        {ImgPos.LEFT === header.imagePosition ? null : imgComponent}
      </div>
    );
  } else {
    return <div className={"mx-12 mb-6 " + style}>{header.text}</div>;
  }
}
