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
  const [testResolution, setTestRes] = useState<TestResolution>();
  const { contactBACK } = useContext(APIContext);
  const { user } = useContext(UserContext);
  const { testID } = useParams();

  useEffect(() => {
    contactBACK("tests/" + testID, "GET").then((testJson: any) => {
      testJson.groups = testJson.groups.map((group: any) => {
        group.exercises = group.exercises.map((ex: any) => {
          return TranslateTestExerciseIN(ex);
        });
        return group;
      });
      setTest(testJson);
    });

    if (user.user?.role === UserRole.STUDENT) {
      contactBACK(
        "tests/" + testID + "/resolutions/" + user.user?.id + "/last",
        "GET"
      ).then((res: any) => {
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
    }
  }, []);

  useEffect(() => {
    if (
      user.user?.role === UserRole.STUDENT &&
      selEx !== "" &&
      testResolution
    ) {
      contactBACK(
        "exercises/resolutions/" + testResolution.resolutions[selEx].resId,
        "GET"
      ).then((exRes) => {
        const newResData = TranslateTestResolutionIN(exRes.data);
        const tmp = { ...testResolution };
        tmp.resolutions[selEx].data = newResData;
        tmp.resolutions[selEx].points = exRes.points;
        tmp.resolutions[selEx].comment = exRes.comment.items[0].text;
        setTestRes(tmp);
      });
    }
  }, [selEx]);

  return (
    <>
      <div className="flex flex-col w-full h-screen overflow-auto min-h-max px-8 pb-8 text-black dark:text-white bg-white dark:bg-slate-900">
        <div className="flex w-full justify-between pt-8 px-4 pb-6 mb-3 border-b-2 border-slate-400 dark:border-slate-600">
          <p className="flex text-4xl text-slate-600 dark:text-white">
            Pré-visualizar: {test.title}
          </p>
          <div className="flex gap-4">
            {user.user?.role === UserRole.SPECIALIST ? (
              <>
                <Link
                  to="../edit"
                  className="py-2 px-4 text-base rounded-lg font-medium btn-base-color"
                >
                  Editar teste
                </Link>
              </>
            ) : (
              <>
                <div className="py-2 px-4 text-base rounded-lg font-medium border-2 bg-[#d8e3f1] dark:bg-[#1e2a3f] border-[#95abca] dark:border-slate-600">
                  Nota: {testResolution ? testResolution.totalPoints : "-"} /{" "}
                  {test.globalPoints}
                </div>
                <Link
                  to="../solve"
                  className="py-2 px-4 text-base rounded-lg font-medium btn-base-color"
                >
                  Resolver teste
                </Link>
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
    </>
  );
}
