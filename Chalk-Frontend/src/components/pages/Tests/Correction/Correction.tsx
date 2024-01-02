import "./Correction.css";
import { useEffect, useState } from "react";
//import { PopUp } from "../../interactiveElements/PopUp";
import { FaArrowLeft } from "react-icons/fa6";
import { Test } from "../SolveTest/SolveTest";
import { TestPreview } from "../TestPreview";
import {
  ExerciseJustificationKind,
  ExerciseType,
  Resolution,
  ResolutionType,
} from "../../../objects/Exercise/Exercise";
import { exampleTest } from "../Preview/PreviewTest";
import { Answer } from "../../../objects/Answer/Answer";

export interface Resolutions {
  [exerciseID: string]: {
    solution: Resolution;
    cotation: number;
    studentRes: { [resolutionID: string]: Resolution };
  };
}

const exampleRes: Resolutions = {
  "11111": {
    solution: {
      id: "12341243",
      exerciseID: "11111",
      cotation: 0,
      type: ResolutionType.SOLUTION,
      data: {
        type: ExerciseType.OPEN_ANSWER,
        text: "Coisas sobre pica",
      },
    },
    cotation: 10,
    studentRes: {
      "1298401204801248": {
        id: "1233441243",
        student: {
          name: "Luis Silva",
          id: "lisinhogamer@hotmail.com",
          email: "lisinhogamer@hotmail.com",
        },
        exerciseID: "11111",
        cotation: 0,
        type: ResolutionType.PENDING,
        data: {
          type: ExerciseType.OPEN_ANSWER,
          text: "Coisas sobre pica",
        },
      },
    },
  },

  "22222": {
    solution: {
      id: "12341243",
      exerciseID: "22222",
      cotation: 0,
      type: ResolutionType.SOLUTION,
      data: {
        type: ExerciseType.TRUE_OR_FALSE,
        items: {
          1: { text: "Portugal", value: true },
          2: { text: "Polo norte", value: true },
          3: { text: "Pigs", value: true },
          4: { text: "Setúbal", value: false },
        },
        justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
      },
    },
    cotation: 10,
    studentRes: {
      "2134213412": {
        id: "12341243",
        exerciseID: "22222",
        cotation: 0,
        student: {
          name: "Luis Silva",
          id: "lisinhogamer@hotmail.com",
          email: "lisinhogamer@hotmail.com",
        },
        type: ResolutionType.PENDING,
        data: {
          type: ExerciseType.TRUE_OR_FALSE,
          items: {
            1: { text: "Portugal", value: true },
            2: { text: "Polo norte", value: false },
            3: { text: "Pigs", value: true },
            4: { text: "Setúbal", value: true },
          },
          justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
      },
      "21342134432141212": {
        id: "12341243",
        exerciseID: "22222",
        cotation: 0,
        student: {
          name: "Luis Silva",
          id: "lisinhogamer@hotmail.com",
          email: "lisinhogamer@hotmail.com",
        },
        type: ResolutionType.PENDING,
        data: {
          type: ExerciseType.TRUE_OR_FALSE,
          items: {
            1: { text: "Portugal", value: true },
            2: { text: "Polo norte", value: false },
            3: { text: "Pigs", value: true },
            4: { text: "Setúbal", value: true },
          },
          justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
      },
      "21342134432134214123441212": {
        id: "12341243",
        exerciseID: "22222",
        student: {
          name: "Luis Silva",
          id: "lisinhogamer@hotmail.com",
          email: "lisinhogamer@hotmail.com",
        },
        cotation: 0,
        type: ResolutionType.PENDING,
        data: {
          type: ExerciseType.TRUE_OR_FALSE,
          items: {
            1: { text: "Portugal", value: true },
            2: { text: "Polo norte", value: false },
            3: { text: "Pigs", value: true },
            4: { text: "Setúbal", value: true },
          },
          justifyType: ExerciseJustificationKind.NO_JUSTIFICATION,
        },
      },
    },
  },

  "33333": {
    solution: {
      id: "12341243",
      exerciseID: "33333",
      cotation: 0,
      type: ResolutionType.SOLUTION,
      data: {
        type: ExerciseType.CHAT,
        msgs: [],
      },
    },
    cotation: 10,
    studentRes: {
      "213442134213412": {
        id: "213442134213412",
        exerciseID: "33333",
        cotation: 0,
        student: {
          name: "Luis Silva",
          id: "lisinhogamer@hotmail.com",
          email: "lisinhogamer@hotmail.com",
        },
        type: ResolutionType.PENDING,
        data: {
          type: ExerciseType.CHAT,
          msgs: [
            "Espanha",
            "E com França?",
            "Portugal",

            "Qual o pais que faz fronteira com Espanha?",
          ],
        },
      },
    },
  },
};

function renderResolutions(
  resolutions: Resolutions,
  exID: string,
  setExId: Function
) {
  const [selResolution, selectRes] = useState<Resolution | undefined>();
  const [auxEx, setAux] = useState<string>(exID);

  useEffect(() => {
    if (exID !== auxEx) selectRes(undefined);
  }, [exID]);

  const selectResolution = (exID: string, resID: string) => {
    selectRes(resolutions[exID].studentRes[resID]);
    setExId(exID);
    setAux(exID);
  };

  const backToList = () => {
    selectRes(undefined);
    setAux("");
    setExId("");
  };

  return selResolution !== undefined && exID !== ""
    ? renderResolution(selResolution, resolutions[exID].cotation, backToList)
    : renderResolutionList(resolutions, selectResolution);
}

function renderResolutionList(resolutions: Resolutions, setExId: Function) {
  return (
    <div className="overflow-y-scroll">
      <h3 className="text-xl font-medium">Resoluções por corrigir</h3>
      <div className="flex-col space-y-4">
        {Object.keys(resolutions).map((key1, index1) => {
          return (
            <div key={index1} className="flex-col space-y-4">
              <p className="text-xl font-medium border-b-2 border-slate-700">
                Exercício {key1}
              </p>
              {Object.keys(resolutions[key1].studentRes).map((key2, index2) => {
                return (
                  <div
                    key={index2}
                    className="p-8 rounded-lg bg-3-2 flex cursor-pointer"
                    onClick={() => setExId(key1, key2)}
                  >
                    Resolução {key2}
                  </div>
                );
              })}
            </div>
          );
        })}
      </div>
    </div>
  );
}

function renderResolution(
  resolution: Resolution,
  cotation: number,
  backToList: Function
) {
  return (
    <div className="flex flex-col w-full space-y-4 h-full">
      <div className="flex items-center space-x-2 justify-between">
        <div className="flex items-center space-x-2">
          <div
            className=" rounded-full hover:bg-gray-600 p-2 cursor-auto"
            onClick={() => backToList()}
          >
            <FaArrowLeft />
          </div>
          <h1 className="text-xl">Resolução {resolution.id}</h1>
        </div>
        <button type="button" className="p-4 bg-yellow-200">
          <p className="text-sm">Assistência na Correção</p>
        </button>
      </div>

      <h2>
        <strong>Aluno</strong>: {resolution.student?.name}
      </h2>
      <label className="font-bold">Resposta:</label>
      <div className="bg-white rounded-lg border-2 border-gray-600 p-4">
        <Answer solution={resolution} />
      </div>
      <label className="font-bold">Comentário:</label>
      <input id={resolution.id + "comment"} type="text"></input>
      <div className="flex space-x-2 items-center">
        <label className="font-bold">Nota:</label>
        <input
          id={resolution.id + "grade"}
          className=""
          type="number"
          max={cotation}
        ></input>
        <p> Cotação máxima: {cotation}</p>
      </div>

      <button
        type="button"
        className="p-3 bg-yellow-300 rounded-md mt-auto"
        onClick={() => backToList()}
      >
        Submit
      </button>
    </div>
  );
}

function renderSolution(resolutions: Resolutions, exid: string) {
  if (exid in resolutions)
    return <Answer solution={resolutions[exid].solution} />;
  else return <p>Selecione um exercício para ver a sua resolução</p>;
}

export function Correction() {
  const [test, setTest] = useState<Test>(exampleTest);
  const [resolutions, setResolutions] = useState<Resolutions>(exampleRes);
  const [exID, setExID] = useState<string>("");
  const [showSideMenu, setshowSideMenu] = useState(true);

  const [solutionC, setSolutionC] = useState(<></>);

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
                  {/* Solução se houver h-1/3*/}
                  <div className="bg-3-1 w-full border-2 border-black rounded-lg p-4">
                    <h3 className="text-xl font-medium">Solução</h3>
                    {renderSolution(resolutions, exID)}
                  </div>
                  {/* Resoluçoes */}
                  <div className="bg-3-1 w-full h-full rounded-lg p-4 ">
                    {Object.keys(resolutions).length !== 0 ? (
                      renderResolutions(resolutions, exID, setExID)
                    ) : (
                      <div className="flex h-full items-center justify-center">
                        <p className="text-xl text-gray-500">
                          Não existem mais resoluções para corrigir sobre este
                          exercício
                        </p>
                      </div>
                    )}
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
