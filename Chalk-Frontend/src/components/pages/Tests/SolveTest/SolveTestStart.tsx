import { useContext, useEffect, useRef } from "react";
import { SolveTestContext } from "./SolveTest";
import { UserContext } from "../../../../UserContext";

export function SolveTestLanding(props: any) {
  const { test, nExercises } = useContext(SolveTestContext);
  const divRef = useRef<HTMLDivElement>(null);
  const { user } = useContext(UserContext);

  useEffect(() => {
    if (divRef.current)
      divRef.current.innerHTML = test.globalInstructions ?? "";
  }, [divRef, test.globalInstructions]);

  return (
    <>
      <div className="space-y-12 w-full text-black dark:text-white">
        <div className="space-y-2 ">
          <p className="text-xl">
            <strong>Título:</strong> {test.title}
          </p>
          <p className="text-xl">
            <strong>Autor:</strong>{" "}
            {test.specialistId === user.user?.id
              ? user.user.email
              : test.specialistId}
          </p>
          <p className="text-xl">
            <strong>Data:</strong> {test.creationDate}
          </p>
          <div className="text-xl flex space-x-4">
            <strong>Tópicos:</strong>{" "}
            <p className=" px-1 mr-1 rounded-lg bg-gray-600 text-white dark:bg-[#dddddd] dark:text-black ">
              RPCW
            </p>
          </div>
        </div>

        <div className="w-full">
          <h2 className=" font-bold text-lg">Instruções básicas do teste:</h2>

          <p className="w-full text-lg p-4" ref={divRef}></p>
        </div>
        <p className=" text-lg">
          <strong>Número de exercicios:</strong> {nExercises}
        </p>

        <button
          className="absolute right-4 bottom-4 p-4 rounded-lg bg-[#acacff] hover:bg-[#5555ce] dark:bg-gray-600 hover:dark:bg-[#ffd025] text-black hover:text-white dark:text-white hover:dark:text-black font-bold hover:scale-110 transition-all duration-100"
          onClick={() => props.startTest(true)}
        >
          Começar o teste {"->"}
        </button>
        <div className="absolute right-4 top-0 text-2xl">
          <p>Cotação máxima: {test.globalPoints} valores</p>
          <p>Duração da prova: {test.globalPoints} minutos</p>
        </div>
      </div>
    </>
  );
}
