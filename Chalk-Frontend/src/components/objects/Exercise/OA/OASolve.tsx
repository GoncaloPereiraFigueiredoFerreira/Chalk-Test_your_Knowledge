import { useEffect, useState } from "react";
import {
  DownloadIcon,
  FileUploadIcon,
  ListIcon,
} from "../../SVGImages/SVGImages";
import { ExerciseHeaderComp } from "../Header/ExHeader";
import {
  ExerciseType,
  OAExercise,
  OAResolutionData,
  SolveProps,
} from "../Exercise";

export interface OASolveProps {
  exercise: OAExercise;
  position: string;
  context: SolveProps;
}

export function OASolve(props: OASolveProps) {
  let initState: OAResolutionData = props.context
    .resolutionData as OAResolutionData;

  const [state, setState] = useState<OAResolutionData>(initState);

  const setText = (text: string) => {
    setState({
      type: ExerciseType.OPEN_ANSWER,
      text: text,
    });
  };

  useEffect(() => {
    setState(initState);
  }, [props.exercise]);

  useEffect(() => {
    props.context.setExerciseSolution(state);
  }, [state]);

  return (
    <>
      <ExerciseHeaderComp
        header={props.exercise.base.statement}
      ></ExerciseHeaderComp>

      <form>
        <div className="w-full mb-4 border-2 rounded-lg ex-1">
          <div className="flex items-center justify-between px-3 py-2 border-b-2 rounded-t-lg ex-2">
            <div className="flex flex-wrap items-center sm:divide-x-2 ex-division">
              <div className="flex items-center space-x-1 sm:pr-4">
                <button
                  type="button"
                  className="p-2 rounded light-mode-gray-icon"
                >
                  <FileUploadIcon></FileUploadIcon>
                  <span className="sr-only">Attach file</span>
                </button>
              </div>
              <div className="flex flex-wrap items-center space-x-1 sm:pl-4">
                <button
                  type="button"
                  className="p-2 rounded cursor-pointer light-mode-gray-icon"
                >
                  <ListIcon></ListIcon>
                  <span className="sr-only">Add list</span>
                </button>

                <button
                  type="button"
                  className="p-2 rounded cursor-pointer light-mode-gray-icon"
                >
                  <DownloadIcon></DownloadIcon>
                  <span className="sr-only">Download</span>
                </button>
              </div>
            </div>
          </div>
          <div className="px-4 py-2 rounded-b-lg">
            <textarea
              id="editor"
              rows={8}
              className="block w-full px-0 border-0 focus:ring-0"
              placeholder="Write your answer..."
              value={state.text}
              onChange={(e) => setText(e.target.value)}
              required
            ></textarea>
          </div>
        </div>
      </form>
    </>
  );
}
