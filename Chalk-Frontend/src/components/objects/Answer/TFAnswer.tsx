import { TextareaBlock } from "../../interactiveElements/TextareaBlock";
import {
  ExerciseJustificationKind,
  ExerciseType,
  ResolutionType,
  TFResolutionData,
} from "../Exercise/Exercise";

import { AnswerProps } from "./Answer";

export function TFAnswer({ solution }: AnswerProps) {
  if (solution.data.type == ExerciseType.TRUE_OR_FALSE) {
    const solDate = solution.data as TFResolutionData;
    if (solution.type === ResolutionType.SOLUTION) {
      return (
        <div>
          <div className="grid-layout-exercise m-2 gap-2 min-h-max items-center text-black">
            <div className="flex text-xl font-bold px-4 ">V</div>
            <div className="flex text-xl font-bold px-4 ">F</div>
            <div></div>
            {Object.keys(solution.data.items).map((index) => {
              return (
                <>
                  <div className="flex items-start justify-center">
                    <input
                      className="radio-green"
                      type="radio"
                      checked={solDate.items[index].value}
                      disabled
                    ></input>
                  </div>
                  <div className="flex items-start justify-center">
                    <input
                      className="radio-red"
                      type="radio"
                      checked={!solDate.items[index].value}
                      disabled
                    ></input>
                  </div>
                  <div className="">
                    <p>{solDate.items[index].text}</p>
                  </div>
                </>
              );
            })}
          </div>
        </div>
      );
    } else {
      return (
        <div>
          <div className="grid-layout-exercise m-2 gap-2 min-h-max items-center text-black">
            <div className="flex text-xl font-bold px-4">V</div>
            <div className="flex text-xl font-bold px-4">F</div>
            <div></div>
            {Object.keys(solution.data.items).map((index) => {
              const justify: boolean =
                (solDate.justifyType ===
                  ExerciseJustificationKind.JUSTIFY_ALL &&
                  "value" in solDate.items[index]) ||
                (solDate.justifyType ===
                  ExerciseJustificationKind.JUSTIFY_FALSE &&
                  "value" in solDate.items[index] &&
                  !solDate.items[index].value) ||
                (solDate.justifyType ===
                  ExerciseJustificationKind.JUSTIFY_TRUE &&
                  "value" in solDate.items[index] &&
                  solDate.items[index].value!);

              return (
                <>
                  <div className="flex items-start justify-center">
                    <input
                      className="radio-green"
                      type="radio"
                      checked={solDate.items[index].value}
                      disabled
                    ></input>
                  </div>
                  <div className="flex items-start justify-center">
                    <input
                      className="radio-red"
                      type="radio"
                      checked={!solDate.items[index].value}
                      disabled
                    ></input>
                  </div>
                  <div className="flex flex-row items-center space-x-4">
                    <p>{solDate.items[index].text}</p>

                    {solDate.justifyType ===
                    ExerciseJustificationKind.NO_JUSTIFICATION ? (
                      <div className="col-span-3"></div>
                    ) : (
                      <>
                        <div
                          className={`${
                            justify ? "" : "h-0"
                          } transition-[height] duration-75`}
                        >
                          <p className={`${justify ? "" : "hidden"} text-xs`}>
                            Justificação:
                          </p>
                          <div className="h-full px-2 overflow-hidden">
                            <TextareaBlock
                              className={`${
                                justify ? "" : "hidden"
                              } basic-input-text`}
                              value={solDate.items[index].justification}
                              disabled={true}
                            ></TextareaBlock>
                          </div>
                        </div>
                      </>
                    )}
                  </div>
                </>
              );
            })}
          </div>
        </div>
      );
    }
  }
}
