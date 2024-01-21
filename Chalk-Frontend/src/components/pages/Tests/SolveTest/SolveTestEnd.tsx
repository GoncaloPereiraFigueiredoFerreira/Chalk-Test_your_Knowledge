import { useContext, useEffect, useState } from "react";
import { SolveTestContext } from "./SolveTest";
import { APIContext } from "../../../../APIContext";

export function SolveTestEnd({ resolutionID, maxPoints }: any) {
  const context = useContext(SolveTestContext);
  const { contactBACK } = useContext(APIContext);
  const [grade, setGrade] = useState();

  useEffect(() => {
    if (resolutionID !== undefined)
      contactBACK("tests/resolutions/" + resolutionID, "GET").then((json) => {
        if (json.status !== "NOT_REVISED") setGrade(json.totalPoints);
      });
  }, []);

  return (
    <div className="dark:text-white m-20">
      <p className="text-2xl">
        <strong>Completaste o teu teste!</strong>
      </p>
      {grade !== undefined ? (
        <p className="text-4xl font-medium">
          Nota obtida: {grade} / {maxPoints}
        </p>
      ) : (
        <p className="text-2xl font-medium">
          Verifica mais tarde as tuas avaliações!
        </p>
      )}

      <p className="ml-20 ">{context.test.conclusion}</p>
    </div>
  );
}
