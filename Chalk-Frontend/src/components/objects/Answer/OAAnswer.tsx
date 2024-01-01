import { ExerciseType } from "../Exercise/Exercise";

import { AnswerProps } from "./Answer";

export function OAAnswer({ solution }: AnswerProps) {
  if (solution.data.type == ExerciseType.OPEN_ANSWER) {
    return (
      <>
        <div className="w-full mb-4 border-2 rounded-lg ex-1">
          <div className="px-4 py-2 rounded-b-lg">
            <textarea
              id="editor"
              rows={8}
              className="block w-full px-0 border-0 focus:ring-0"
              placeholder="Write your answer..."
              value={solution.data.text}
              disabled
            ></textarea>
          </div>
        </div>
      </>
    );
  }
}
