import "./Correction.css";
import { useState } from "react";
//import { PopUp } from "../../interactiveElements/PopUp";

import { Test } from "../SolveTest/SolveTest";
import { TestPreview } from "../TestPreview";
import {
  ExerciseComponent,
  Resolution,
} from "../../../objects/Exercise/Exercise";
import { basictest } from "../Preview/PreviewTest";

function renderSolution() {}

interface Resolutions {
  [id: string]: { solution: Resolution; studentRes: Resolution[] };
}

export function Correction() {
  const [test, setTest] = useState<Test>(basictest);
  const [resolutions, setResolutions] = useState<Resolutions>();
  const [exID, setExID] = useState<string>("22222");
  const [showSideMenu, setshowSideMenu] = useState(true);

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
                      <p>Solução do exercício: {exID}</p>
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
                      <p>Lista de Resoluções do exercicio: {exID}</p>
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
