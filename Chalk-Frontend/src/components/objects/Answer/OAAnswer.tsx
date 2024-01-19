import { TextareaBlock } from "../../interactiveElements/TextareaBlock";
import { ExerciseType } from "../Exercise/Exercise";

import { AnswerProps } from "./Answer";

export function OAAnswer({ solution }: AnswerProps) {
  if (solution.data.type == ExerciseType.OPEN_ANSWER) {
    return (
      <>
        <div className="w-full text-black bg-white border-[#dddddd]">
          <div className="px-4 rounded-b-lg">
            <TextareaBlock
              value={solution.data.text}
              className=" border-0 border-white pt-2"
              disabled={true}
            ></TextareaBlock>
          </div>
        </div>
      </>
    );
  }
}
