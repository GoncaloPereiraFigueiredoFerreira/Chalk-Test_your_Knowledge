import "./Correction.css";
import { useEffect, useState } from "react";
//import { PopUp } from "../../interactiveElements/PopUp";

import { Test } from "../SolveTest/SolveTest";
import { TestPreview } from "../TestPreview";
import {
  ExerciseJustificationKind,
  ExerciseType,
  Resolution,
} from "../../../objects/Exercise/Exercise";
import { basictest } from "../Preview/PreviewTest";
import axios from "axios";
import { students } from "./example";
import { Answer } from "../../../objects/Answer/Answer";

function renderSolution(solution: Resolution) {
  const [solPreview, setSolPreview] = useState(<></>);

  switch (solution.data.type) {
    case ExerciseType.MULTIPLE_CHOICE:
      setSolPreview(<></>);
      break;
    case ExerciseType.TRUE_OR_FALSE:
      setSolPreview(<></>);
      break;
    case ExerciseType.OPEN_ANSWER:
      setSolPreview(<></>);
      break;
  }

  return <div>{solPreview}</div>;
}

function renderResolutions(
  exerciseID: string,
  resolutions: Resolutions,
  setResolutions: Function
) {
  const [resID, setResID] = useState<string>("");
  const [resInt, setResInt] = useState(<></>);

  useEffect(() => {
    setResInt(
      <>
        {resID !== "" ? (
          <>
            <p className="hover:text-blue-500" onClick={() => setResID(resID)}>
              Return
            </p>
            <Answer
              solution={resolutions[exerciseID].solution}
              cotation={resolutions[exerciseID].cotation}
              justifyKind={resolutions[exerciseID].justifyKind}
              resolution={resolutions[exerciseID].studentRes[resID]}
            ></Answer>
          </>
        ) : exerciseID !== "" ? (
          <>
            {Object.entries(resolutions[exerciseID].studentRes).map(
              ([key, res]) => (
                <>
                  <button
                    className="flex flex-row"
                    onClick={() => setResID(res.id!)}
                  >
                    <div>
                      <p>{key + "but 'group'.'exercise would be better'"}</p>
                      <p>{res.student!.name}</p>
                    </div>
                    <div></div>
                  </button>
                </>
              )
            )}
          </>
        ) : (
          <>
            {Object.entries(resolutions).map(([, exercise]) =>
              Object.entries(exercise.studentRes).map(([key, res]) => (
                <>
                  <button className="flex flex-row">
                    <div>
                      <p>{key + "but 'group'.'exercise would be better'"}</p>
                      <p>{res.student!.name}</p>
                    </div>
                    <div></div>
                  </button>
                </>
              ))
            )}
          </>
        )}
      </>
    );
  }, [resID, exerciseID]);

  return <div>{resInt}</div>;
}

export interface Resolutions {
  [exerciseID: string]: {
    solution: Resolution;
    cotation: number;
    justifyKind: ExerciseJustificationKind;
    studentRes: { [resolutionID: string]: Resolution };
  };
}

export function Correction() {
  const [test, setTest] = useState<Test>(basictest);
  const [resolutions, setResolutions] = useState<Resolutions>();
  const [corrected, setCorrected] = useState<Resolutions>();
  const [exID, setExID] = useState<string>("");
  const [showSideMenu, setshowSideMenu] = useState(true);
  const [resolutionMenu, setResolutionMenu] = useState(<></>);
  const [solution, setSolution] = useState(<></>);

  function getExResByID(exerciseID: string): {
    [resolutionID: string]: Resolution;
  } {
    let temp: { [resolutionID: string]: Resolution } = {};
    /* axios
      .get("http://localhost:5173/exercises/" + { exerciseID } + "/resolutions")
      .then((response) => console.log(response))
      .catch((error) => console.log(error));
 */
    return temp;
  }

  useEffect(() => {
    let temp: Resolutions = {};
    test.groups.map((group) =>
      group.exercises.map(
        (exercise) =>
          (temp[exercise.id] = {
            solution: exercise.solution!,
            cotation: exercise.cotation!,
            justifyKind: exercise.justifyKind!,
            studentRes: getExResByID(exercise.id),
          })
      )
    );
    setResolutions(temp);
  }, []);

  useEffect(() => {
    setSolution(renderSolution(resolutions![exID].solution));
    setResolutionMenu(renderResolutions(exID, resolutions!, setResolutions));
  }, [exID]);

  return (
    <>
      <div className="h-screen overflow-auto">
        <div className="flex flex-col w-full gap-4 min-h-max bg-2-1 ">
          <div className="flex w-full justify-between border-gray-2-2 dark:text-white">
            {/*
                Test Preview
               */}
            <div className="w-full flex flex-row divide-x-2 border-gray-2-2">
              <div className="flex flex-col w-3/4 h-screen overflow-auto bg-2-1 px-4 pt-6">
                <h1 className="text-3xl font-medium">
                  Correção do teste: {test.title}
                </h1>
                <TestPreview
                  test={test}
                  setShowExID={setExID}
                  showExId={exID}
                ></TestPreview>
              </div>

              <div
                className={`${
                  showSideMenu ? "w-full" : "w-0"
                } flex flex-col h-screen overflow-auto bg-2-1 transition-[width]`}
              >
                <div className="p-4 flex flex-col w-full h-screen space-y-4">
                  {/* Solução se houver */}
                  <div className="bg-3-1 w-full h-1/3 rounded-lg p-4">
                    <h3 className="text-xl font-medium">Solução</h3>
                    {/*Render de uma solução*/}
                    {exID === "" ? (
                      <p>Escolha um Exercicio para ver a sua solução</p>
                    ) : (
                      <>
                        <p>Solução do exercício: {exID}</p>
                        <div>
                          {exID !== "" ? (
                            solution
                          ) : (
                            <p>
                              Selecione um exercicio ou resolução para
                              visualizar a solução
                            </p>
                          )}
                        </div>
                      </>
                    )}
                  </div>
                  {/* Resoluçoes */}
                  <div className="bg-3-1 w-full h-full rounded-lg p-4">
                    <h3 className="text-xl font-medium">
                      Resoluções por corrigir
                    </h3>
                    {exID === "" ? (
                      <p>Lista de todas as resoluções por corrigir</p>
                    ) : (
                      <>
                        <p>Lista de Resoluções do exercicio: {exID}</p>
                        {resolutionMenu}
                      </>
                    )}

                    {/*
                    <div className="flex h-full items-center justify-center">
                      <p className="text-xl text-gray-500">
                        Não existem mais resoluções para corrigir sobre este
                        exercício
                      </p>
                    </div>
                      */}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
