import { useContext, useEffect, useState } from "react";

import { TestPreview } from "../TestPreview";
import {
  ResolutionData,
  TranslateTestExerciseIN,
  TranslateTestResolutionIN,
} from "../../../objects/Exercise/Exercise";
import { Link, useParams } from "react-router-dom";
import { InitTest, Test } from "../../../objects/Test/Test";
import { APIContext } from "../../../../APIContext";
import { UserContext, UserRole } from "../../../../UserContext";

export interface TestResolution {
  totalPoints: number;
  status: string;
  resolutions: {
    [id: string]: {
      resId: string;
      data: ResolutionData | undefined;
      points: number;
      comment: string;
    };
  };
}

export function PreviewTest() {
  const [test, setTest] = useState<Test>(InitTest());
  const [selEx, setSelectedEx] = useState<string>("");
  const [testResolution, setTestRes] = useState<TestResolution>({
    totalPoints: 0,
    status: "",
    resolutions: {},
  });
  const { contactBACK } = useContext(APIContext);
  const { user } = useContext(UserContext);
  const { testID } = useParams();

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
    if (user.user?.role === UserRole.STUDENT) {
      contactBACK(
        "tests/" + testID + "/resolutions/" + user.user?.id + "/last",
        "GET"
      ).then((response) => {
        response.json().then((res: any) => {
          const temp: {
            [id: string]: {
              resId: string;
              data: ResolutionData | undefined;
              points: number;
              comment: string;
            };
          } = {};

          res.groups.map((groupRes: any) => {
            Object.keys(groupRes.resolutions).map((exRes: string) => {
              temp[exRes] = {
                resId: groupRes.resolutions[exRes].resolutionId,
                data: undefined,
                points: groupRes.resolutions[exRes].points,
                comment: "",
              };
            });
          });
          setTestRes({
            resolutions: temp,
            totalPoints: res.totalPoints,
            status: res.status,
          });
        });
      });
    }
  }, []);

  useEffect(() => {
    if (user.user?.role === UserRole.STUDENT && selEx !== "") {
      contactBACK(
        "exercises/resolutions/" + testResolution.resolutions[selEx].resId,
        "GET"
      ).then((response) => {
        response.json().then((exRes) => {
          const newResData = TranslateTestResolutionIN(exRes.data);
          const tmp = { ...testResolution };
          tmp.resolutions[selEx].data = newResData;
          tmp.resolutions[selEx].points = exRes.points;
          tmp.resolutions[selEx].comment = exRes.comment.items[0].text;
          setTestRes(tmp);
        });
      });
    }
  }, [selEx]);

  return (
    <>
      <div className="flex flex-row divide-x-2 border-gray-2-2">
        <div className="flex flex-col w-full h-screen overflow-auto bg-2-1 min-h-max px-16 pb-8 dark:text-white">
          <div className="flex  w-full justify-between mt-8 px-4 pb-6 mb-3 border-b-2 border-gray-2-2">
            <div className="flex flex-col">
              <label className=" text-title-1">
                Pré-visualizar: {test.title}
              </label>
            </div>
            <div className="flex space-x-4">
              {user.user?.role === UserRole.SPECIALIST ? (
                <>
                  <Link
                    to="../edit"
                    className=" p-4 rounded-lg bg-blue-300  dark:bg-blue-800"
                  >
                    Editar teste
                  </Link>
                </>
              ) : (
                <>
                  <Link
                    to="../solve"
                    className=" p-4 rounded-lg bg-blue-300  dark:bg-blue-800"
                  >
                    Resolver teste
                  </Link>
                  <div className=" p-4 rounded-lg bg-blue-300  dark:bg-blue-800">
                    Nota: {testResolution ? testResolution.totalPoints : "-"} /{" "}
                    {test.globalPoints}
                  </div>
                </>
              )}
            </div>
          </div>
          <TestPreview
            test={test}
            setShowExID={setSelectedEx}
            showExId={selEx}
            testResolution={testResolution}
          ></TestPreview>
        </div>
      </div>
    </>
  );
}
