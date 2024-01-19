import { useState, useEffect, useContext } from "react";
import { ExerciseType, Resolution } from "../../../objects/Exercise/Exercise";
import { Resolutions } from "./Correction";
import { FaArrowLeft } from "react-icons/fa";
import { TextareaBlock } from "../../../interactiveElements/TextareaBlock";
import { Answer } from "../../../objects/Answer/Answer";
import { APIContext } from "../../../../APIContext";

interface ResolutionsProps {
  resolutions: Resolutions;
  exID: string;
  setExId: Function;
  gradeEx: Function;
}

interface Corrections {
  [resId: string]: { points: number; comment: string };
}

export function ResolutionsComp({
  resolutions,
  exID,
  setExId,
  gradeEx,
}: ResolutionsProps) {
  const [selResolution, selectRes] = useState<Resolution | undefined>();
  const [auxEx, setAux] = useState<string>(exID);
  const [corrections, setCorrections] = useState<Corrections>({});
  const { contactBACK } = useContext(APIContext);

  const askAI = (resId: string, exId: string, type: ExerciseType) => {
    if (type === ExerciseType.CHAT || type === ExerciseType.OPEN_ANSWER) {
      let endpoint = type === ExerciseType.OPEN_ANSWER ? "open" : "chat";
      contactBACK("/ai/" + endpoint + "/evaluation", "GET", {
        resolutionId: resId,
        exerciseId: exId,
      }).then((response) =>
        response.text().then((cotation) => {
          console.log(cotation);
          let newCorr = { ...corrections };
          if (!(resId in newCorr))
            newCorr[resId] = {
              points: Number.parseFloat(cotation),
              comment: "",
            };
          else {
            newCorr[resId].points = Number.parseFloat(cotation);
          }

          setCorrections(newCorr);
        })
      );
    }
  };

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

  const addPoints = (resId: string, points: number) => {
    let cpy = { ...corrections };
    if (!(resId in corrections)) {
      cpy[resId] = { points: 0, comment: "" };
    }
    cpy[resId] = { ...cpy[resId], points: points };
    setCorrections(cpy);
  };

  const addComment = (resId: string, comment: string) => {
    let cpy = { ...corrections };
    if (!(resId in corrections)) {
      cpy[resId] = { points: 0, comment: "" };
    }
    cpy[resId] = { ...cpy[resId], comment: comment };
    setCorrections(cpy);
  };

  const submitCorrection = (exResId: string, testResId: string) => {
    if (exResId in corrections) {
      gradeEx(
        testResId,
        exResId,
        corrections[exResId].points,
        corrections[exResId].comment
      );
      backToList();
    }
  };

  useEffect(() => {
    if (exID !== auxEx) selectRes(undefined);
  }, [exID]);

  return selResolution !== undefined && exID !== ""
    ? SingleResolution(
        selResolution,
        resolutions[exID] ? resolutions[exID].maxCotation : 0,
        corrections,
        backToList,
        addPoints,
        addComment,
        submitCorrection,
        () => {
          askAI(selResolution.id, exID, resolutions[exID].type);
        }
      )
    : ResolutionList(resolutions, selectResolution);
}

function ResolutionList(resolutions: Resolutions, setExId: Function) {
  return (
    <div className="overflow-y-scroll">
      <h3 className="text-xl font-medium">Resoluções por corrigir</h3>
      <div className="flex-col space-y-4">
        {Object.keys(resolutions).map((key1, index1) => {
          if (Object.keys(resolutions[key1].studentRes).length !== 0)
            return (
              <div key={index1} className="flex-col space-y-4">
                <p className="text-xl font-medium border-b-2 border-slate-700">
                  Exercício {resolutions[key1].position ?? key1}
                </p>
                {Object.keys(resolutions[key1].studentRes).map(
                  (key2, index2) => {
                    return (
                      <div
                        key={index2}
                        className="p-8 rounded-lg bg-3-2 flex cursor-pointer"
                        onClick={() => setExId(key1, key2)}
                      >
                        Resolução{" "}
                        {resolutions[key1].studentRes[key2].student?.name}
                      </div>
                    );
                  }
                )}
              </div>
            );
        })}
      </div>
    </div>
  );
}

function SingleResolution(
  resolution: Resolution,
  cotation: number,
  correction: Corrections,
  backToList: Function,
  addPoints: Function,
  addComment: Function,
  submitCorrection: Function,
  askAI: Function
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
          <h1 className="text-xl">Resolução de {resolution.student?.name}</h1>
        </div>
        <button
          type="button"
          className="p-4 bg-yellow-200"
          onClick={() => {
            askAI();
          }}
        >
          <p className="text-sm">Assistência na Correção</p>
        </button>
      </div>

      <h2>
        <strong>Aluno</strong>: {resolution.student?.name}
      </h2>
      <label className="font-bold">Resposta:</label>
      <div className="bg-white rounded-lg border-2 border-gray-600 p-1">
        <Answer solution={resolution} />
      </div>
      <label className="font-bold">Comentário:</label>
      <TextareaBlock
        className="bg-white rounded-lg border-2 border-gray-600"
        value={
          correction[resolution.id] !== undefined
            ? correction[resolution.id].comment
            : ""
        }
        onChange={(text) => {
          addComment(resolution.id, text);
        }}
      ></TextareaBlock>

      <div className="flex space-x-2 items-center">
        <label className="font-bold">Nota:</label>
        <input
          id={resolution.id + "grade"}
          className="bg-white rounded-lg border-2 border-gray-600"
          type="number"
          value={
            correction[resolution.id] !== undefined
              ? correction[resolution.id].points
              : 0
          }
          onChange={(e) => {
            addPoints(resolution.id, e.target.value);
          }}
          max={cotation}
        ></input>
        <p> Cotação máxima: {cotation}</p>
      </div>

      <button
        type="button"
        className="p-3 bg-yellow-300 rounded-md mt-auto"
        onClick={() => {
          submitCorrection(resolution.id, resolution.testResolutionId);
        }}
      >
        Submit
      </button>
    </div>
  );
}
