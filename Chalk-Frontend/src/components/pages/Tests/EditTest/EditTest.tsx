import { useState, useContext, useEffect } from "react";
import { useParams } from "react-router-dom";
import { APIContext } from "../../../../APIContext";
import { TranslateTestExerciseIN } from "../../../objects/Exercise/Exercise";
import { Test, InitTest } from "../../../objects/Test/Test";
import { CreateTest } from "../../CreateTestPage/CreateTestPage";

export function EditTest() {
  const [test, setTest] = useState<Test | undefined>(undefined);
  const { contactBACK } = useContext(APIContext);
  const [createComp, setComp] = useState(<></>);
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

  useEffect(() => {
    if (test !== undefined) setComp(<CreateTest test={test}></CreateTest>);
  }, [test]);

  return createComp;
}
