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
    <div className="flex dark:text-white m-20">
      <div className="w-1/2">
        <p className="text-4xl mb-4">
          <strong>Completaste o teu teste!</strong>
        </p>
        <div>
          {grade !== undefined ? (
            <p className="text-2xl mt-16">
              Nota obtida: {grade} / {maxPoints}
            </p>
          ) : (
            <p className="text-xl ">
              Verifica mais tarde as tuas avaliações para consultar a tua nota!
            </p>
          )}
        </div>
        {context.test.conclusion !== "" && (
          <div className="text-2xl w-full mt-10">
            <p className="font-medium">Mensagem de conclusão do teste:</p>
            <p className="mt-3 ml-4">{context.test.conclusion}</p>
          </div>
        )}
      </div>
      <div className="flex w-1/2 justify-center">
        <img src="/better-chalky-thumbsup.webp" className="h-[35rem]"></img>
      </div>
    </div>
  );
}
