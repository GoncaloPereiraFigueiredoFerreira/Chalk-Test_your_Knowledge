import { createContext, useContext, useEffect, useRef, useState } from "react";
import {
  InitResolutionDataEx,
  ResolutionData,
  TranslateTestExerciseIN,
} from "../../../objects/Exercise/Exercise";

import { ExerciseGroup, InitTest, Test } from "../../../objects/Test/Test";
import { SolveTestEnd } from "./SolveTestEnd";
import { SolveTestExercise } from "./SolveTestExercise";
import { SolveTestLanding } from "./SolveTestStart";
import { useParams } from "react-router-dom";
import { APIContext } from "../../../../APIContext";

function CountExercises(test: Test) {
  let result: number = 0;
  test.groups.map((group: ExerciseGroup) => {
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
  test: InitTest(),
  resolutions: [],
  setExerciseSolution: () => {},
  nExercises: 0,
});

function initResolutions(test: Test): ResolutionData[][] {
  const initResolution: ResolutionData[][] = [];

  test.groups.map((group, groupID) => {
    const groupRes: ResolutionData[] = [];
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
    contactBACK("tests/" + testID, "GET").then((testJson: any) => {
      testJson.groups = testJson.groups.map((group: any) => {
        group.exercises = group.exercises.map((ex: any) => {
          return TranslateTestExerciseIN(ex);
        });
        return group;
      });
      setTest(testJson);
      setResolution(initResolutions(testJson));
    });
  }, []);

  const setExerciseResolution = (
    groupID: number,
    exerciseID: number,
    resolutionData: ResolutionData
  ) => {
    const tmpRes = [...resolutions];
    const tmpRes2 = [...tmpRes[groupID - 1]];
    tmpRes2[exerciseID - 1] = resolutionData;
    tmpRes[groupID - 1] = tmpRes2;
    setResolution(tmpRes);
  };

  const startTestResolution = () => {
    contactBACK(
      "tests/" + testID + "/resolutions/start",
      "POST",
      undefined,
      undefined,
      "string"
    ).then((id) => {
      if (id && id !== "") {
        setResolutionID(id);
        startTest(true);
      }
    });
  };

  return (
    <>
      <div className="flex flex-col w-full gap-4 min-h-max px-8 pb-8 bg-white dark:bg-slate-900 h-screen overflow-y-scroll">
        <div className="flex w-full justify-between px-4 pb-6 mb-3 border-b-2 border-black dark:border-black divide-[#dddddd] dark:divide-[#dddddd] mt-10">
          <label className="flex text-4xl text-gray-600 dark:text-white">
            Teste de Avaliação
          </label>
        </div>
        <div className="flex rounded-lg bg-gray-300 dark:bg-gray-800 h-full">
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
                    <SolveTestEnd
                      resolutionID={resolutionID}
                      maxPoints={test.globalPoints}
                    ></SolveTestEnd>
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
