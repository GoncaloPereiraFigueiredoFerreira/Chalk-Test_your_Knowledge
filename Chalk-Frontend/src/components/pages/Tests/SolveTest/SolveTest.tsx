import { createContext, useContext, useEffect, useState } from "react";
import {
  InitResolutionDataEx,
  ResolutionData,
  TranslateTestExerciseIN,
} from "../../../objects/Exercise/Exercise";

import { InitTest, Test } from "../../../objects/Test/Test";
import { SolveTestEnd } from "./SolveTestEnd";
import { SolveTestExercise } from "./SolveTestExercise";
import { SolveTestLanding } from "./SolveTestStart";
import { useParams } from "react-router-dom";
import { APIContext } from "../../../../APIContext";

function CountExercises(test: Test) {
  let result: number = 0;
  test.groups.map((group: any) => {
    result += group.exercises.length;
  });
  return result;
}

export const SolveTestContext = createContext<{
  test: Test;
  nExercises: number;
  resolutions: ResolutionData[][];
  setExerciseSolution: Function;
}>({
  test: {
    type: "",
    conclusion: "",
    author: "",
    title: "",
    creationDate: "",
    globalPoints: 0,
    globalInstructions: "",
    groups: [],
  },
  resolutions: [],
  setExerciseSolution: () => {},
  nExercises: 0,
});

function initResolutions(test: Test): ResolutionData[][] {
  let initResolution: ResolutionData[][] = [];

  test.groups.map((group, groupID) => {
    let groupRes: ResolutionData[] = [];
    initResolution.push(groupRes);

    group.exercises.map((exercise) => {
      initResolution[groupID].push(InitResolutionDataEx(exercise));
    });
  });
  return initResolution;
}

export function SolveTest() {
  const [started, startTest] = useState(false);
  const [ended, endTest] = useState(false);
  const [test, setTest] = useState<Test>(InitTest());
  const { testID } = useParams();
  const { contactBACK } = useContext(APIContext);
  const [resolutions, setResolution] = useState<ResolutionData[][]>([]);
  const [resolutionID, setResolutionID] = useState<string>("");

  useEffect(() => {
    contactBACK("tests/" + testID, "GET").then((response) => {
      response.json().then((testJson: any) => {
        console.log(testJson);
        testJson.groups = testJson.groups.map((group: any) => {
          group.exercises = group.exercises.map((ex: any) => {
            return TranslateTestExerciseIN(ex);
          });
          return group;
        });
        console.log(testJson);
        setTest(testJson);
        setResolution(initResolutions(testJson));
      });
    });
  }, []);

  const setExerciseResolution = (
    groupID: number,
    exerciseID: number,
    resolutionData: ResolutionData
  ) => {
    let tmpRes = [...resolutions];
    let tmpRes2 = [...tmpRes[groupID - 1]];
    tmpRes2[exerciseID - 1] = resolutionData;
    tmpRes[groupID - 1] = tmpRes2;
    setResolution(tmpRes);
  };

  const startTestResolution = () => {
    contactBACK("tests/" + testID + "/resolutions/start", "POST").then(
      (response) => {
        response.text().then((id) => {
          console.log("ResolutionID:", id);
          if (id !== "") {
            setResolutionID(id);
            startTest(true);
          }
        });
      }
    );
  };

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max px-16 pb-8 bg-2-1 h-screen overflow-y-scroll">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-gray-2-2 mt-10">
          <label className="flex text-title-1">Teste de Avaliação</label>
        </div>
        <div className="flex rounded-lg bg-1-1 h-full">
          <div className="m-7 relative w-full ">
            <SolveTestContext.Provider
              value={{
                test: test,
                resolutions: resolutions,
                setExerciseSolution: setExerciseResolution,
                nExercises: CountExercises(test),
              }}
            >
              {!started ? (
                <SolveTestLanding
                  startTest={startTestResolution}
                ></SolveTestLanding>
              ) : (
                <>
                  {!ended ? (
                    <SolveTestExercise
                      endTest={endTest}
                      resolutionID={resolutionID}
                    ></SolveTestExercise>
                  ) : (
                    <SolveTestEnd></SolveTestEnd>
                  )}
                </>
              )}
            </SolveTestContext.Provider>
          </div>
        </div>
      </div>
    </>
  );
}
