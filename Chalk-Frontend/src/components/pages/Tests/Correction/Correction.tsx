import "./Correction.css";
import { useContext, useEffect, useState } from "react";
import { TestPreview } from "../TestPreview";
import {
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
    solution?: Resolution;
    position?: string;
    maxCotation: number;
    studentRes: { [resolutionID: string]: Resolution };
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
    let newRubrics: Rubrics = { ...rubrics };
    newRubrics[id] = rubric;
    setRubrics(newRubrics);
  };

  const addResolution = (exId: string, resId: string, res: Resolution) => {
    let resCpy = { ...resolutions };
    if (!(exId in resCpy)) {
      resCpy[exId] = { studentRes: {}, maxCotation: 0 };
    }

    let cpyStudentRes = { ...resCpy[exId].studentRes };
    cpyStudentRes[resId] = { ...res };
    resCpy[exId].studentRes = cpyStudentRes;

    setResolutions(resCpy);
  };

  const gradeResolution = (resId: string, points: number, comment: string) => {
    contactBACK(
      "/exercises/resolutions/" + resId + "/manual-correction",
      "POST",
      undefined,
      { points: points, comment: comment }
    ).then(() => {
      let resCpy = { ...resolutions[exID].studentRes };
      delete resCpy[resId];
      let newResolutions = { ...resolutions };
      newResolutions[exID] = { ...newResolutions[exID], studentRes: resCpy };
      setResolutions(newResolutions);
    });
  };

  useEffect(() => {
    contactBACK("tests/" + testID, "GET").then((response) => {
      response.json().then((testJson: any) => {
        testJson.groups = testJson.groups.map((group: any) => {
          group.exercises = group.exercises.map((ex: any) => {
            return TranslateTestExerciseIN(ex);
          });
          return group;
        });
        setTest(testJson);
      });
    });
  }, []);

  useEffect(() => {
    if (exID !== "" && !(exID in resolutions)) {
      contactBACK("exercises/" + exID + "/rubric", "GET")
        .then((response) => {
          response
            .json()
            .then((json) => {
              if (json.type === "OA" || json.type === "CE") {
                contactBACK("exercises/" + exID + "/resolutions", "GET", {
                  page: "0",
                  itemsPerPage: "50",
                  onlyNotRevised: "true",
                }).then((response2) => {
                  response2
                    .json()
                    .then((resJson) => {
                      setRubric(exID, json);
                      resJson.map((res: any) => {
                        let newRes: Resolution = {
                          id: res.resolution.id,
                          exerciseID: exID,
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
            })
            .catch(() => {});
        })
        .catch(() => {});
    }
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
                className={`w-full flex flex-col h-screen overflow-auto bg-2-1`}
              >
                <div className="p-4 flex flex-col w-full h-screen space-y-4">
                  {/* Solução se houver */}
                  <div className="bg-3-1 w-full space-y-3 min-h-[30%] max-h-[40%] rounded-lg p-4 overflow-y-auto">
                    <h3 className="text-xl font-medium">
                      Critérios de Correção
                    </h3>
                    <Solution exid={exID} rubrics={rubrics}></Solution>
                  </div>
                  {/* Resoluçoes */}
                  <div className=" bg-3-1 w-full h-full rounded-lg p-4">
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
