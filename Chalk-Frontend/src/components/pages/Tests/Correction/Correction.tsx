import "./Correction.css";
import { useContext, useEffect, useState } from "react";
import { TestPreview } from "../TestPreview";
import {
  ExerciseType,
  Resolution,
  ResolutionType,
  TranslateTestExerciseIN,
  TranslateTestResolutionIN,
} from "../../../objects/Exercise/Exercise";
import { Rubric } from "../../../objects/Rubric/Rubric";
import { InitTest, Test } from "../../../objects/Test/Test";
import { APIContext } from "../../../../APIContext";
import { useParams } from "react-router-dom";
import { ResolutionsComp } from "./Resolutions";
import { Solution } from "./Solution";

export interface Resolutions {
  [exerciseID: string]: {
    type: ExerciseType;
    solution?: Resolution;
    position?: string;
    maxCotation: number;
    studentRes: {
      [resolutionID: string]: Resolution;
    };
  };
}

export interface Rubrics {
  [exerciseID: string]: Rubric;
}

export function Correction() {
  const [test, setTest] = useState<Test>(InitTest());
  const [resolutions, setResolutions] = useState<Resolutions>({});
  const [rubrics, setRubrics] = useState<Rubrics>({});
  const [exID, setExID] = useState<string>("");
  const { contactBACK } = useContext(APIContext);
  const { testID } = useParams();

  const setRubric = (id: string, rubric: Rubric) => {
    const newRubrics: Rubrics = { ...rubrics };
    newRubrics[id] = rubric;
    setRubrics(newRubrics);
  };

  const addResolution = (exId: string, resId: string, res: Resolution) => {
    const resCpy = { ...resolutions };
    const cpyStudentRes = { ...resCpy[exId].studentRes };
    cpyStudentRes[resId] = { ...res };
    resCpy[exId].studentRes = cpyStudentRes;
    setResolutions(resCpy);
  };

  const gradeResolution = (
    testResId: string,
    exResId: string,
    points: number,
    comment: string
  ) => {
    contactBACK(
      "/tests/resolutions/" + testResId + "/" + exResId + "/manual-correction",
      "PUT",
      undefined,
      { points: points, comment: comment },
      "none"
    ).then(() => {
      const resCpy = { ...resolutions[exID].studentRes };
      delete resCpy[exResId];
      const newResolutions = { ...resolutions };
      newResolutions[exID] = { ...newResolutions[exID], studentRes: resCpy };
      setResolutions(newResolutions);
    });
  };

  useEffect(() => {
    contactBACK("tests/" + testID, "GET").then((testJson: any) => {
      let collectMaxCot: Resolutions = {};
      testJson.groups = testJson.groups.map((group: any, Gindex: number) => {
        group.exercises = group.exercises.map((ex: any, Eindex: number) => {
          let newEx = TranslateTestExerciseIN(ex);
          collectMaxCot[newEx.identity.id] = {
            type: newEx.type,
            maxCotation: newEx.identity.points ?? 0,
            studentRes: {},
            position: Gindex + 1 + "." + (Eindex + 1),
          };
          return newEx;
        });
        return group;
      });
      setResolutions(collectMaxCot);
      setTest(testJson);
    });
  }, []);

  useEffect(() => {
    if (exID !== "") {
      contactBACK("exercises/" + exID + "/rubric", "GET").then((json) => {
        contactBACK("exercises/" + exID + "/resolutions", "GET", {
          page: "0",
          itemsPerPage: "50",
          onlyNotRevised: "true",
        })
          .then((page) => {
            let resJson = page.items;
            if (json.type === "CE" || json.type === "OA") setRubric(exID, json);
            resJson.map((res: any) => {
              const newRes: Resolution = {
                id: res.resolution.id,
                exerciseID: exID,
                testResolutionId: res.resolution.testResolutionId,
                student: {
                  id: res.student.id,
                  name: res.student.name,
                  email: res.student.email,
                },
                cotation: 0,
                type: ResolutionType.PENDING,
                data: TranslateTestResolutionIN(res.resolution.data),
              };
              addResolution(exID, newRes.id, newRes);
            });
          })
          .catch(() => {});
      });
    }
  }, [exID]);

  return (
    <>
      <div className="h-screen overflow-auto">
        <div className="flex flex-col w-full gap-4 min-h-max bg-white dark:bg-black ">
          <div className="flex w-full justify-between border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd] dark:text-white">
            {/*
                Test Preview
            */}
            <div className="w-full flex flex-row divide-x-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd]">
              <div className="flex flex-col w-3/4 h-screen overflow-auto bg-white dark:bg-black px-4 pt-6">
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
                className={`w-full flex flex-col h-screen overflow-auto bg-white dark:bg-black`}
              >
                <div className="p-4 flex flex-col w-full h-screen space-y-4">
                  {/* Solução se houver */}
                  <div className="bg-[#dddddd] dark:bg-gray-600 w-full space-y-3 min-h-[30%] max-h-[40%] rounded-lg p-4 overflow-y-auto border-2 border-[#dddddd] dark:border-gray-600">
                    <h3 className="text-xl font-medium">
                      Critérios de Correção
                    </h3>
                    <Solution exid={exID} rubrics={rubrics}></Solution>
                  </div>
                  {/* Resoluçoes */}
                  <div className=" bg-[#dddddd] dark:bg-gray-600 w-full h-full rounded-lg p-4">
                    <ResolutionsComp
                      resolutions={resolutions}
                      exID={exID}
                      setExId={setExID}
                      gradeEx={gradeResolution}
                    ></ResolutionsComp>
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
