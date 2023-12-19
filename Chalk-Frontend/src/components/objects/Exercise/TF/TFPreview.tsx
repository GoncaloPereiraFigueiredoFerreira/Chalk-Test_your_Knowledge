import {
  ExerciseJustificationKind,
  PreviewProps,
  TFExercise,
} from "../Exercise";
import { useState } from "react";
import { ExerciseHeaderComp, ImgPos } from "../Header/ExHeader";

export interface TFPreviewProps {
  exercise: TFExercise;
  position: string;
  context: PreviewProps;
}

export function TFPreview({ exercise, position, context }: TFPreviewProps) {
  return (
    <>
      <ExerciseHeaderComp header={exercise.base.statement} />
      <p>
        <strong>Tipo de Justificação:</strong> {exercise.props.justifyType}
      </p>
      <div className="grid-layout-exercise mt-4 gap-2 min-h-max items-center">
        <div className="flex text-xl font-bold px-4">V</div>
        <div className="flex text-xl font-bold px-4">F</div>
        <div></div>
        {Object.entries(exercise.props.items).map(([index, value]) => (
          <TFShowStatement
            key={index}
            text={value.text}
            name={`radio-button-${index}-${exercise.identity?.id}-${position}`}
            justifyKind={exercise.props.justifyType}
          />
        ))}
      </div>
    </>
  );
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
          disabled
        ></input>
      </div>
      <div className="flex items-start justify-center">
        <input
          className="radio-red"
          type="radio"
          name={props.name}
          onChange={() => setOpenJustify("false")}
          disabled
        ></input>
      </div>
      <div className="">
        <p>{props.text}</p>
      </div>
      {/*<TFJustify open={openJustify} justifyKind={props.justifyKind}></TFJustify>*/}
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
        justify ? "h-16" : "h-0"
      } col-span-3 transition-[height] duration-75`}
    >
      <div className=" px-7 overflow-hidden">
        <textarea
          className={`${justify ? "" : "hidden"} basic-input-text`}
          name={"justification"}
          rows={1}
          placeholder="Justifique a sua resposta"
        ></textarea>
      </div>
    </div>
  );
}
