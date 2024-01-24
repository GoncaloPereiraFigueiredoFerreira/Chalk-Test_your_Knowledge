import { useContext } from "react";
import { SolveTestContext } from "./SolveTest";

export function SolveTestLanding(props: any) {
  const { test, nExercises } = useContext(SolveTestContext);
  return (
    <>
      <div className="space-y-12 w-full text-black dark:text-white">
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
            {test.tags.map((tag, key) => {
              return <Tag key={key}>{tag.name}</Tag>;
            })}
          </div>
        </div>

        <div className="w-full">
          <h2 className=" font-bold text-lg">Instruções básicas do teste:</h2>

          <p className="w-full text-lg p-4">{test.globalInstructions}</p>
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
