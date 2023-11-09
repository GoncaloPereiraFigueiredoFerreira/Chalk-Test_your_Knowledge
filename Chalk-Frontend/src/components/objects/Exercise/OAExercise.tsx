import { useState } from "react";
import { DownloadIcon, FileUploadIcon, ListIcon } from "../SVGImages/SVGImages";
import { ExerciseHeader, ExerciseHeaderEdit } from "./ExHeader";

//Open Answer Exercise
export function OAExercise(props: any) {
  let exerciseDisplay = <></>;
  switch (props.contexto) {
    case "solve":
      exerciseDisplay = <OASolve enunciado={props.enunciado}></OASolve>;
      break;

    case "edit":
      exerciseDisplay = <OAEdit enunciado={props.enunciado}></OAEdit>;
      break;

    case "previz":
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
    <>
      <div className="m-5 text-xl">
        <p className="text-4xl strong mb-8">Resposta Aberta</p>
        {exerciseDisplay}
      </div>
    </>
  );
}

function OASolve(props: any) {
  const [state, setState] = useState("");

  return (
    <>
      <ExerciseHeader header={props.enunciado}></ExerciseHeader>

      <form>
        <div className="w-full mb-4 border border-gray-200 rounded-lg bg-gray-50 dark:bg-gray-700 dark:border-gray-600">
          <div className="flex items-center justify-between px-3 py-2 border-b dark:border-gray-600">
            <div className="flex flex-wrap items-center divide-gray-200 sm:divide-x dark:divide-gray-600">
              <div className="flex items-center space-x-1 sm:pr-4">
                <button
                  type="button"
                  className="p-2 text-gray-500 rounded cursor-pointer hover:text-gray-900 hover:bg-gray-100 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600"
                >
                  <FileUploadIcon></FileUploadIcon>
                  <span className="sr-only">Attach file</span>
                </button>
              </div>
              <div className="flex flex-wrap items-center space-x-1 sm:pl-4">
                <button
                  type="button"
                  className="p-2 text-gray-500 rounded cursor-pointer hover:text-gray-900 hover:bg-gray-100 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600"
                >
                  <ListIcon></ListIcon>
                  <span className="sr-only">Add list</span>
                </button>

                <button
                  type="button"
                  className="p-2 text-gray-500 rounded cursor-pointer hover:text-gray-900 hover:bg-gray-100 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600"
                >
                  <DownloadIcon></DownloadIcon>
                  <span className="sr-only">Download</span>
                </button>
              </div>
            </div>
          </div>
          <div className="px-4 py-2 bg-white rounded-b-lg dark:bg-gray-800">
            <textarea
              id="editor"
              rows={8}
              className="block w-full px-0 text-gray-800 bg-white border-0 dark:bg-gray-800 focus:ring-0 dark:text-white dark:placeholder-gray-400"
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

function OAEdit(props: any) {
  const [state, setState] = useState(props.enunciado.text);
  return (
    <>
      <ExerciseHeaderEdit
        header={{ ...props.enunciado, text: state }}
        editFunc={setState}
      ></ExerciseHeaderEdit>
    </>
  );
}
