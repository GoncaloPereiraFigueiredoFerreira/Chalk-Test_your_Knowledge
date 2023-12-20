import {
  ExerciseJustificationKind,
  ExerciseType,
  ResolutionType,
  TFResolutionData,
} from "../Exercise/Exercise";

import { AnswerProps } from "./Answer";

export function TFAnswer({ solution }: AnswerProps) {
  if (solution.data.type == ExerciseType.TRUE_OR_FALSE) {
    let solDate = solution.data as TFResolutionData;
    if (solution.type === ResolutionType.SOLUTION) {
      return (
        <>
          <div className="grid-layout-exercise mt-4 gap-2 min-h-max items-center">
            <div className="flex text-xl font-bold px-4">V</div>
            <div className="flex text-xl font-bold px-4">F</div>
            <div></div>
            {Object.keys(solution.data.items).map((index, key) => {
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
        </>
      );
    } else {
      return (
        <>
          <div className="grid-layout-exercise mt-4 gap-2 min-h-max items-center">
            <div className="flex text-xl font-bold px-4">V</div>
            <div className="flex text-xl font-bold px-4">F</div>
            <div></div>
            {Object.keys(solution.data.items).map((index, key) => {
              let justify =
                solDate.justifyType === ExerciseJustificationKind.JUSTIFY_ALL ||
                (solDate.justifyType ===
                  ExerciseJustificationKind.JUSTIFY_UNMARKED &&
                  !solDate.items[index].value) ||
                (solDate.justifyType ===
                  ExerciseJustificationKind.JUSTIFY_MARKED &&
                  solDate.items[index].value);

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
                  {solDate.justifyType ===
                  ExerciseJustificationKind.NO_JUSTIFICATION ? (
                    <div className="col-span-3"></div>
                  ) : (
                    <div
                      className={`${
                        justify ? "h-28" : "h-0"
                      } transition-[height] duration-75`}
                    >
                      <div className="h-full px-7 overflow-hidden">
                        <textarea
                          className={`${
                            justify ? "" : "hidden"
                          } basic-input-text`}
                          name={"justification" + index}
                          rows={3}
                          placeholder="Justifique a sua resposta"
                          value={solDate.items[index].justification}
                          disabled
                        ></textarea>
                      </div>
                    </div>
                  )}
                </>
              );
            })}
          </div>
        </>
      );
    }
  }
}
