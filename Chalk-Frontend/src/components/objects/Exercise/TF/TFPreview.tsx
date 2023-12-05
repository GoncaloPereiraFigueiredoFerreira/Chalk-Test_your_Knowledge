import { ExerciseJustificationKind } from "../Exercise";
import { useState } from "react";
import { ExerciseHeader } from "../Header/ExHeader";
import { TFSolveProps } from "./TFSolve";

export function TFPreview({
  id,
  position,
  items,
  statement,
  justifyKind,
}: TFSolveProps) {
  return (
    <>
      <ExerciseHeader header={statement} />
      <div className="grid-layout-exercise mt-4 gap-2 min-h-max items-center">
        <div className="flex text-xl font-bold px-4">V</div>
        <div className="flex text-xl font-bold px-4">F</div>
        <div></div>
        {Object.entries(items).map(([index, value]) => (
          <TFShowStatement
            key={index}
            text={value.text}
            name={`radio-button-${index}-${id}-${position}`}
            justifyKind={justifyKind}
          />
        ))}
      </div>
    </>
  );
  return <></>;
}

function TFShowStatement(props: any) {
  const [openJustify, setOpenJustify] = useState("");

  return (
    <>
      <div className="flex items-start justify-center">
        <input
          className="radio-green"
          type="radio"
          name={props.name}
          onChange={() => setOpenJustify("true")}
        ></input>
      </div>
      <div className="flex items-start justify-center">
        <input
          className="radio-red"
          type="radio"
          name={props.name}
          onChange={() => setOpenJustify("false")}
        ></input>
      </div>
      <div className="">
        <p>{props.text}</p>
      </div>
      <TFJustify open={openJustify} justifyKind={props.justifyKind}></TFJustify>
    </>
  );
}

function TFJustify(props: any) {
  let justify =
    props.justifyKind === ExerciseJustificationKind.JUSTIFY_ALL ||
    (props.justifyKind === ExerciseJustificationKind.JUSTIFY_FALSE &&
      props.open === "false") ||
    (props.justifyKind === ExerciseJustificationKind.JUSTIFY_TRUE &&
      props.open === "true");
  return props.justifyKind === ExerciseJustificationKind.NO_JUSTIFICATION ? (
    <div className="col-span-3"></div>
  ) : (
    <div
      className={`${
        justify ? "h-28" : "h-0"
      } col-span-3 transition-[height] duration-75`}
    >
      <div className="h-full px-7 overflow-hidden">
        <textarea
          className={`${justify ? "" : "hidden"} basic-input-text`}
          name={"justification"}
          rows={3}
          placeholder="Justifique a sua resposta"
        ></textarea>
      </div>
    </div>
  );
}
