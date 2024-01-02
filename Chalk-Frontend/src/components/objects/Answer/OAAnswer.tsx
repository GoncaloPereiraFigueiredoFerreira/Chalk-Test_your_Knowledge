import { TextareaBlock } from "../../interactiveElements/TextareaBlock";
import { ExerciseType } from "../Exercise/Exercise";

import { AnswerProps } from "./Answer";

export function OAAnswer({ solution }: AnswerProps) {
  if (solution.data.type == ExerciseType.OPEN_ANSWER) {
    return (
      <>
        <div className="w-full ex-1">
          <div className="px-4 rounded-b-lg">
            <TextareaBlock
              value={solution.data.text}
              className=""
              disabled={true}
            ></TextareaBlock>
          </div>
        </div>
      </>
    );
  }
}
