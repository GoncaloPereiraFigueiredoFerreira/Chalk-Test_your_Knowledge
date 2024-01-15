import { useContext, useEffect, useState } from "react";

import { TestPreview } from "../TestPreview";
import { TranslateTestExerciseIN } from "../../../objects/Exercise/Exercise";
import { Link, useParams } from "react-router-dom";
import { InitTest, Test } from "../../../objects/Test/Test";
import { APIContext } from "../../../../APIContext";

export function PreviewTest() {
  const [test, setTest] = useState<Test>(InitTest());
  const { contactBACK } = useContext(APIContext);
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
  }, []);

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
              <Link
                to="../edit"
                className=" p-4 rounded-lg bg-blue-300  dark:bg-blue-800"
              >
                Editar teste
              </Link>
              <Link
                to="../solve"
                className=" p-4 rounded-lg bg-blue-300  dark:bg-blue-800"
              >
                Resolver teste
              </Link>
            </div>
          </div>
          <TestPreview
            test={test}
            setShowExID={() => {}}
            showExId={""}
          ></TestPreview>
        </div>
      </div>
    </>
  );
}
