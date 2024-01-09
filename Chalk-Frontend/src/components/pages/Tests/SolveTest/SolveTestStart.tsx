import { useContext } from "react";
import { SolveTestContext } from "./SolveTest";

export function SolveTestLanding(props: any) {
  const { test, nExercises } = useContext(SolveTestContext);
  return (
    <>
      <div className="space-y-12 w-full  dark:text-white">
        <div className="space-y-2 ">
          <p className="text-xl">
            <strong>Título:</strong> {test.title}
          </p>
          <p className="text-xl">
            <strong>Autor:</strong> {test.author}
          </p>
          <p className="text-xl">
            <strong>Data:</strong> {test.creationDate}
          </p>
          <div className="text-xl flex space-x-4">
            <strong>Tópicos:</strong>{" "}
            <p className=" border border-gray-400 rounded-lg "> RPCW</p>
          </div>
        </div>

        <div className="w-full">
          <h2 className=" font-bold text-md">Instruções básicas do teste:</h2>

          <p className="w-full border-2 rounded-lg border-gray-200 p-4">
            {test.globalInstructions}
          </p>
        </div>
        <p>
          <strong>Número de exercicios:</strong> {nExercises}
        </p>

        <button
          className="absolute right-4 bottom-4 p-4 rounded-lg bg-blue-300  dark:bg-blue-800"
          onClick={() => props.startTest(true)}
        >
          Começar o teste {"->"}
        </button>
        <div className="absolute right-4 top-0 text-2xl">
          <p>Cotação máxima: {test.globalCotation} valores</p>
          <p>Duração da prova: {test.globalCotation} minutos</p>
        </div>
      </div>
    </>
  );
}
