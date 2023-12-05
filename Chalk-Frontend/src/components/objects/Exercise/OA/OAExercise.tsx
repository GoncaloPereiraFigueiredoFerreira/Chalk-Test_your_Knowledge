import { useState } from "react";
import {
  DownloadIcon,
  FileUploadIcon,
  ListIcon,
} from "../../SVGImages/SVGImages";
import { ExerciseHeader } from "../Header/ExHeader";
import { Exercise } from "../Exercise";

interface ExerciseProps {
  position: string;
  contexto: string;
  exercise: Exercise;
}

export function OAExercise({ position, contexto, exercise }: ExerciseProps) {
  let exerciseDisplay = <></>;
  switch (contexto) {
    case "solve":
      exerciseDisplay = <OASolve statement={exercise.statement}></OASolve>;
      break;

    case "preview":
      exerciseDisplay = <></>;
      break;

    case "correct":
      exerciseDisplay = <></>;
      break;

    case "psolution":
      exerciseDisplay = <></>;
      break;
  }
  return (
    <div className="">
      <div className="m-5 text-title-2">{position + ") " + exercise.title}</div>
      <div className="m-5 text-lg">{exerciseDisplay}</div>
    </div>
  );
}

function OASolve(props: any) {
  const [_state, setState] = useState("");

  return (
    <>
      <ExerciseHeader header={props.statement}></ExerciseHeader>

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
              onChange={(e) => setState(e.target.value)}
              required
            ></textarea>
          </div>
        </div>
      </form>
    </>
  );
}
